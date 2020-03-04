package com.aroha.RDBMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aroha.RDBMS.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

}
