package ru.mtuci.rbpo_2024_praktika.service.impl;

import com.mtuci.ukhanov.configuration.SecurityConfig;
import com.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import com.mtuci.ukhanov.models.ApplicationRole;
import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.repositories.UserRepository;
import com.mtuci.ukhanov.requests.DataUserRequest;
import com.mtuci.ukhanov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    @Override
    public Optional<ApplicationUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<ApplicationUser> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    private ApplicationUser edit(ApplicationUser user, DataUserRequest request) {
        user.setLogin(request.getLogin());
        user.setPassword_hash(securityConfig.passwordEncoder().encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(ApplicationRole.valueOf(request.getRole()));
        return user;
    }

    @Override
    public ApplicationUser save(DataUserRequest request) {
        return userRepository.save(edit(new ApplicationUser(), request));
    }

    @Override
    public List<ApplicationUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public ApplicationUser update(DataUserRequest request) {
        ApplicationUser user = getUserById(request.getId()).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден")
        );
        return userRepository.save(edit(user, request));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public boolean saveUser(ApplicationUser user, String password) {
        Optional<ApplicationUser> userFromDB = userRepository.findByLogin(user.getLogin());

        if (userFromDB.isPresent()) return false;
        user.setRole(ApplicationRole.USER);

        user.setPassword_hash(securityConfig.passwordEncoder().encode(password));
        user.setEmail(user.getEmail());
        user.setLogin(user.getLogin());

        userRepository.save(user);
        return true;
    }
}
