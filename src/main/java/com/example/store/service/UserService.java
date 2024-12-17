package com.example.store.service;

import com.example.store.dto.request.UsersRequestDTO;
import com.example.store.dto.response.UsersResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    ServiceResponseDTO<UsersResponseDTO> create(UsersRequestDTO usersRequestDTO);
    UserDetails findByUserNameOrPhone(String userName);
    Users findUserByUserName(String userName);
}
