package com.example.store.service.impl;

import com.example.store.dto.request.AuthenticationRequestDTO;
import com.example.store.dto.request.UsersRequestDTO;
import com.example.store.dto.response.AuthenticationResponseDTO;
import com.example.store.dto.response.UsersResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Token;
import com.example.store.entity.Users;
import com.example.store.enums.ErrorStatus;
import com.example.store.enums.TokenType;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.TokenRepository;
import com.example.store.repository.UserRepository;
import com.example.store.service.AuthenticationService;
import com.example.store.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final ModelMapper modelMapper;

    @Override
    public AuthenticationResponseDTO register(UsersRequestDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        ServiceResponseDTO<UsersResponseDTO> response = userService.create(userDTO);
        Users user = modelMapper.map(response.getData(), Users.class);
        var jwtToken = jwtService.generateToken(modelMapper.map(userDTO, Users.class));
        var refreshToken = jwtService.generateRefreshToken(modelMapper.map(userDTO, Users.class));
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        UserDetails userDetails = userService.findByUserNameOrPhone(request.getUserName());
        // Kiểm tra mật khẩu mà người dùng nhập vào có khớp với mật khẩu đã mã hóa trong cơ sở dữ liệu hay không
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new ApiRequestException("error.invalid.credentials", ErrorStatus.UNAUTHORIZED_401);  // Nếu không khớp, ném ngoại lệ
        }
        var user = repository.findByUserNameOrPhone(request.getUserName(), request.getUserName()).orElseThrow();;
        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Users user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            var user = this.repository.findByUserNameOrPhone(userName, userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
