package com.acceleratetechnology.repository;

import com.acceleratetechnology.domain.ConnectionMgr;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectionMgr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectionMgrRepository extends JpaRepository<ConnectionMgr, Long> {
}
