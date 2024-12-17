package com.example.store.auditing;

import com.example.store.entity.Users;
import com.example.store.enums.ErrorStatus;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.UserRepository;
import com.example.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public class ApplicationAuditAware implements AuditorAware<UUID> {

    private final UserRepository userRepository;  // Inject UserService để tìm thông tin người dùng

    public ApplicationAuditAware(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Users user = userRepository.findByUserNameOrPhone(userPrincipal.getUsername(), userPrincipal.getUsername()).orElseThrow(() -> new ApiRequestException("error.user.not.found", ErrorStatus.BAD_REQUEST_400));
        return Optional.ofNullable(user.getId()); // Trả về UUID của người dùng
    }
}