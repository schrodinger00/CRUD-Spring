package com.example.CRUD2.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.example.CRUD2.model.User;
import com.example.CRUD2.repository.UserRepository;
import com.example.CRUD2.exception.ResourceNotFoundException;

@Controller
public class AdminController {

    @Autowired
    private UserRepository UserRepository;

    @RequestMapping(value = {"/admin/dashboard"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String adminHome(){
        return "admin/dashboard";
    }

    @RequestMapping(value = {"/admin/user"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String messages(Model model) {
        model.addAttribute("users", UserRepository.findAll());
        return "admin/userList";
    }

    @RequestMapping(value = {"/admin/user/{id}"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String getUserById(@PathVariable Integer id, Model model) {
        User user = UserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist"));
        model.addAttribute("user", user);
        return "admin/updateUser";
    }

    @RequestMapping(value = {"/admin/user/delete/{id}"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteUsers(@PathVariable Integer id){
        User user = UserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Exist"));

        UserRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return "admin/dashboard";
    }

    @RequestMapping(value = {"/admin/user/update/{id}"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String updateUser(@PathVariable Integer id, User userDetails){
        User user = UserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist"));

        user.setFullName(userDetails.getFullName());
        user.setPhone(userDetails.getPhone());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());

        User updatedUser = UserRepository.save(user);
        return "admin/dashboard";
    }
}
