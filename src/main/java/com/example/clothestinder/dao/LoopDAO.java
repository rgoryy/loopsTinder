package com.example.clothestinder.dao;

import com.example.clothestinder.entity.Loop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoopDAO extends JpaRepository<Loop, Long> {
    Loop getLoopByFilePath(String filePath);
}
