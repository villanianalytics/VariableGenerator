package com.acceleratetechnology.repository;

import com.acceleratetechnology.domain.ListUD;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ListUD entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListUDRepository extends JpaRepository<ListUD, Long> {
}
