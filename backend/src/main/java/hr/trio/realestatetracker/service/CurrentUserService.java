package hr.trio.realestatetracker.service;

import hr.trio.realestatetracker.model.Role;
import hr.trio.realestatetracker.model.User;

public interface CurrentUserService {

    String getLoggedInUserUsername();

    Role getLoggedInUserRole();

    User getLoggedInUser();

    boolean isLoggedInUserAdmin();

    boolean isAdmin(User user);

    boolean isLoggedIn();

}
