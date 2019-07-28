package com.gonyaevaa.orderBook.service;

import com.gonyaevaa.orderBook.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    void saveUser(User user);

    void deleteUser(Long id);

    User findUserById(Long id);
}
