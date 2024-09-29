package com.example.store.service;

import com.example.store.dto.request.UsersRequestDTO;
import com.example.store.dto.response.UsersResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

public interface UserService {
    ServiceResponseDTO<UsersResponseDTO> craete(UsersRequestDTO usersRequestDTO);
}
