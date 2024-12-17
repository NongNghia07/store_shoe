package com.example.store.config;


import com.example.store.repository.TokenRepository;
import com.example.store.service.JwtService;
import com.example.store.service.UserService;
import com.example.store.util.MessageUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Nếu là request đến URL xác thực, bỏ qua filter này
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        // Kiểm tra token có hợp lệ hay không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "error.token.no");
            return;
        }

        jwt = authHeader.substring(7);  // Lấy token từ header
        try {
            userName = jwtService.extractUsername(jwt);  // Lấy username từ token
        } catch (MalformedJwtException malformedJwtException) {
            sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "error.jwt.malformed");
            return;
        } catch (ExpiredJwtException expiredJwtException) {
            sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "error.jwt.expired");
            return;
        } catch (Exception e) {
            sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "api.error.generic");
            return;
        }

        if(userName == null) {
            sendErrorResponse(request, response, HttpStatus.FORBIDDEN, "error.token.username_not_found");
            return;
        }else if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            UserDetails userDetails = null;
            try {
                userDetails = userService.findByUserNameOrPhone(userName);
            } catch (Exception e) {

            }

            // Kiểm tra tính hợp lệ của token trong cơ sở dữ liệu
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if (!isTokenValid) {
                sendErrorResponse(request, response, HttpStatus.FORBIDDEN, "error.token.expired_or_revoked");
                return;
            } else if (jwtService.isTokenValid(jwt, userDetails)) { // Kiểm tra tính hợp lệ của token và so sánh với thông tin người dùng
                // Tạo đối tượng Authentication cho người dùng
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Đặt chi tiết vào trong AuthenticationToken
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đặt Authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                sendErrorResponse(request, response, HttpStatus.FORBIDDEN, "error.token.invalid");
                return;
            }
        }

        // Tiếp tục chuỗi xử lý filter
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");  // Đảm bảo sử dụng UTF-8
        response.getWriter().write("{\"message\": \"" + MessageUtil.getMessage(message) + "\"}");
        response.flushBuffer();
    }
}
