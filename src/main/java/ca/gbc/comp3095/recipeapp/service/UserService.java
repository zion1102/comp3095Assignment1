package ca.gbc.comp3095.recipeapp.service;


/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <User Service. Allows for custom query calls to user table.>
 *********************************************************************************/

import ca.gbc.comp3095.recipeapp.dto.UserDto;
import ca.gbc.comp3095.recipeapp.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
