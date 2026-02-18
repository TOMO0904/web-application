package com.example.demo.controller;

// ★ここから下の import が必要でした！
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TaskController {

    // ★追加：データベースとの窓口（Repository）を変数として用意
    private final TaskRepository taskRepository;

    // ★追加：Spring Bootに「この窓口を使ってね」と設定する（これをDependency Injectionと言います）
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // 用意した窓口を使って、DBから全タスクを探して返す
    }

    @PostMapping("/api/tasks")
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task); // 用意した窓口を使って、送られてきたタスクをDBに保存する
    }

}