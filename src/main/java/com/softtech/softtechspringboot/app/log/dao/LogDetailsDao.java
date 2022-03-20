package com.softtech.softtechspringboot.app.log.dao;

import com.softtech.softtechspringboot.app.log.entity.LogDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public interface LogDetailsDao extends JpaRepository<LogDetail, Long> {
}
