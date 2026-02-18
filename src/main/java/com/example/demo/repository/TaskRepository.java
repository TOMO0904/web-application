package com.example.demo.repository; // ← ここが com.example.demo.repository になっていますか？

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // ← これが付いていますか？
public interface TaskRepository extends JpaRepository<Task, Long> { // ← class ではなく interface ですか？
}