package com.softtech.softtechspringboot.learning.service.entityservice;

import com.softtech.softtechspringboot.learning.entity.UserPhone;
import com.softtech.softtechspringboot.learning.repository.UserPhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
public class UserPhoneEntityService {

    @Autowired
    private UserPhoneDao userPhoneDao;

    public UserPhone save(UserPhone user){
        return userPhoneDao.save(user);
    }
}
