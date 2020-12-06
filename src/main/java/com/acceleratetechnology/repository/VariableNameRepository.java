package com.acceleratetechnology.repository;

import com.acceleratetechnology.domain.VariableName;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VariableName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariableNameRepository extends JpaRepository<VariableName, Long> {
}
