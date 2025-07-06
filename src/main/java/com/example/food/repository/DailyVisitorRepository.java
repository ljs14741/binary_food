package com.example.food.repository;

import com.example.food.entity.DailyVisitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyVisitorRepository extends JpaRepository<DailyVisitor, Long> {
    Optional<DailyVisitor> findByPageNameAndDate(String pageName, LocalDate date);
}
