package com.example.store.service;

import com.example.store.dto.request.AuthenticationRequestDTO;
import com.example.store.dto.request.UsersRequestDTO;
import com.example.store.dto.response.AuthenticationResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponseDTO register(UsersRequestDTO userDTO);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
