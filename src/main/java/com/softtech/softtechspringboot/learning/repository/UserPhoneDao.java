package com.softtech.softtechspringboot.learning.repository;

import com.softtech.softtechspringboot.learning.entity.UserPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface UserPhoneDao extends JpaRepository<UserPhone, Long> {
}
