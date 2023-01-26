package com.example.CRUD2.service;

import com.example.CRUD2.model.User;

import java.util.List;

public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);
}
