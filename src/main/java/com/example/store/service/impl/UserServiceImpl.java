package com.example.store.service.impl;

import com.example.store.dto.request.UsersRequestDTO;
import com.example.store.dto.response.UsersResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Users;
import com.example.store.repository.UserRepository;
import com.example.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        return ServiceResponseDTO.success(HttpStatus.OK, usersResponseDTO);
    }
}
