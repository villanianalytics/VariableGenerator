package com.acceleratetechnology.repository;

import com.acceleratetechnology.domain.Cubes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cubes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CubesRepository extends JpaRepository<Cubes, Long> {
}
