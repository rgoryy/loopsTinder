package com.example.clothestinder.dao;

import com.example.clothestinder.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDAO extends JpaRepository<Request, Long> {
}
