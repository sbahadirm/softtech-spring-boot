package com.softtech.softtechspringboot.learning.controller;

import com.softtech.softtechspringboot.learning.entity.User;
import com.softtech.softtechspringboot.learning.entity.UserPhone;
import com.softtech.softtechspringboot.learning.service.entityservice.UserEntityService;
import com.softtech.softtechspringboot.learning.service.entityservice.UserPhoneEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
//@RestController
//@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private UserPhoneEntityService userPhoneEntityService;

    @PostMapping
    public User save(){

        User user = new User();

        user = userEntityService.save(user);

        return user;
    }

    @PostMapping("/phones")
    public UserPhone savePhone(){

        UserPhone userPhone = new UserPhone();
        userPhone.setUserId(1L);
        userPhone.setPhoneNumber("0232323232");

        userPhone = userPhoneEntityService.save(userPhone);

        return userPhone;
    }
}
