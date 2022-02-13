package com.softtech.softtechspringboot.learning.repository;

import com.softtech.softtechspringboot.learning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
