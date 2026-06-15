package com.server.app.services;

import com.server.app.dto.auth.LoginResponseDTO;
import com.server.app.dto.auth.SignupRequest;
import com.server.app.dto.auth.UpdatePasswordRequest;
import com.server.app.dto.user.UserCreateDto;
import com.server.app.dto.user.UserUpdateDto;
import com.server.app.entities.Role;
import com.server.app.entities.User;
import com.server.app.exceptions.ConfictException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.RoleRepository;
import com.server.app.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public User create(UserCreateDto dto) {
        uniqueUsername(dto.getUsername(), null);
        uniqueEmail(dto.getEmail(), null);
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getRole() != null) {
            Role role = roleRepository.findById(dto.getRole())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            user.setRole(role);
        }

        return userRepository.save(user);
    }

    public Page<User> findAll(int page, int size, String search) {
        return userRepository.findAll(PageRequest.of(page, size), search);
    }

    @Transactional
    public User updateUser(int userId, UserUpdateDto dto) {
        User user = findById(userId);

        if (user.isBlocked()) {
            throw new ConfictException("The user: " + user.getUsername() + " is locked");
        }

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            uniqueUsername(dto.getUsername(), userId);
            user.setUsername(dto.getUsername());
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            user.setSurname(dto.getSurname());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            uniqueEmail(dto.getEmail(), userId);
            user.setEmail(dto.getEmail());
        }

        if (dto.getBlocked() != null) {
            user.setBlocked(dto.getBlocked());
        }

        if (dto.getRole() != null) {
            Role role = roleRepository.findById(dto.getRole())
                    .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
            user.setRole(role);
        }

        return userRepository.save(user);
    }

    public User findById(int userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    private void uniqueUsername(String username, Integer id) {
        userRepository.findUserByUsername(username).ifPresent(existing -> {
            if (id == null || existing.getId() != id) {
                throw new ConfictException("El nombre de usuario ya está en uso");
            }
        });
    }

    private void uniqueEmail(String email, Integer id) {
        userRepository.findUserByEmail(email).ifPresent(existing -> {
            if (id == null || existing.getId() != id) {
                throw new ConfictException("El correo electrónico ya está en uso");
            }
        });
    }

    private User findByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    public User login(String username, String password) {
        User user = findByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ConfictException("Credenciales inválidas");
        }

        if (user.isBlocked()) {
            throw new ConfictException("Cuenta bloqueada");
        }
        return user;
    }

    @Transactional
    public User signUp(SignupRequest request) {
        uniqueUsername(request.getUsername(), null);
        uniqueEmail(request.getEmail(), null);

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new NotFoundException("Rol ADMIN no configurado"));

        UserCreateDto createRequest = new UserCreateDto(
                request.getUsername(),
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getPassword(),
                adminRole.getId()
        );

        return create(createRequest);
    }

    @Transactional
    public User updatePassword(Integer userId, UpdatePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ConfictException("Las nuevas contraseñas no coinciden");
        }

        User user = findById(userId);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ConfictException("La contraseña actual es incorrecta");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return user;
    }
}
