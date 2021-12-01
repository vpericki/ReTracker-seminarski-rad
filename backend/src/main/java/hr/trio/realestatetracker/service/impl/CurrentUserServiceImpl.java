package hr.trio.realestatetracker.service.impl;

import hr.trio.realestatetracker.model.Role;
import hr.trio.realestatetracker.model.User;
import hr.trio.realestatetracker.model.codebook.Roles;
import hr.trio.realestatetracker.repository.UserRepository;
import hr.trio.realestatetracker.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserRepository userRepository;

    @Override
    public String getLoggedInUserUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    @Override
    public Role getLoggedInUserRole() {
        final User loggedInUser = userRepository.findByUsername(getLoggedInUserUsername());

        return loggedInUser.getRole();
    }

    @Override
    public User getLoggedInUser() {
        return userRepository.findByUsername(getLoggedInUserUsername());
    }

    @Override
    public boolean isLoggedInUserAdmin() {
        return Roles.ROLE_ADMIN.getName().equals(getLoggedInUserRole().getName());
    }

    @Override
    public boolean isAdmin(final User user) {
        return Roles.ROLE_ADMIN.getName().equals(user.getRole().getName());
    }

    @Override
    public boolean isLoggedIn() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }

}
