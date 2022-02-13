package com.softtech.softtechspringboot.learning.service.entityservice;

import com.softtech.softtechspringboot.learning.entity.User;
import com.softtech.softtechspringboot.learning.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
public class UserEntityService {

    @Autowired
    private UserDao userDao;

    public User save(User user){
        return userDao.save(user);
    }
}
