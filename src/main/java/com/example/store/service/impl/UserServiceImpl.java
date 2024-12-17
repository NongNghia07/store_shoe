package com.example.store.service.impl;

import com.example.store.dto.request.UsersRequestDTO;
import com.example.store.dto.response.UsersResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Role;
import com.example.store.entity.Users;
import com.example.store.enums.ErrorStatus;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.UserRepository;
import com.example.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceResponseDTO<UsersResponseDTO> create(UsersRequestDTO usersRequestDTO) {
        Users user = modelMapper.map(usersRequestDTO, Users.class);
        user = userRepository.save(user);
        UsersResponseDTO usersResponseDTO = new UsersResponseDTO(user);
        return ServiceResponseDTO.success(HttpStatus.OK,"", usersResponseDTO);
    }

    @Override
    @Transactional
    public UserDetails findByUserNameOrPhone(String userName) {
        // Tìm người dùng qua userName hoặc phone
        Users user = userRepository.findByUserNameOrPhone(userName, userName)
                .orElseThrow(() -> new ApiRequestException("error.user.not.found", ErrorStatus.UNAUTHORIZED_401));

        // Kiểm tra trạng thái người dùng (nếu cần)
        if (!user.getIsStatus()) {
            throw new ApiRequestException("error.user.disabled", ErrorStatus.BAD_REQUEST_400);
        }

        // Lấy danh sách các vai trò của người dùng từ bảng liên kết
        Set<Role> roles = user.getRoles();

        // Chuyển các vai trò thành quyền (GrantedAuthority)
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Lấy quyền của role cha và tất cả các role con của nó
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));  // Quyền của role cha
            if (role.getSubRoles() != null) {
                for (Role subRole : role.getSubRoles()) {
                    authorities.add(new SimpleGrantedAuthority(subRole.getName()));  // Quyền của các role con
                }
            }
        });

        // Tạo đối tượng UserDetails với username, password và quyền của người dùng
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);  // Trả về quyền cho người dùng
    }

    @Override
    public Users findUserByUserName(String userName) {
        Optional<Users> user = userRepository.findByUserNameOrPhone(userName, userName);
        if(user.isEmpty()) {
            throw new ApiRequestException("error.user.not.found", ErrorStatus.BAD_REQUEST_400);
        }
        return user.get();
    }
}
