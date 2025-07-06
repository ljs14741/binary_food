package com.example.food.repository;

import com.example.food.entity.TotalVisitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalVisitorRepository extends JpaRepository<TotalVisitor, Long> {
    Optional<TotalVisitor> findByPageName(String pageName);
}