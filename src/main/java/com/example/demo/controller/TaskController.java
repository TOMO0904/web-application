package com.example.demo.controller;

// ★ここから下の import が必要でした！
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // ④ 【追加】タスクを削除するAPI（窓口：/api/tasks/{id}）
    // @PathVariable は、URLに含まれる数字（{id}）を変数として受け取る魔法です
    @DeleteMapping("/api/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id); // 指定されたIDのタスクをDBから削除！
    }

    // ⑤ 【追加】タスクを更新（上書き）するAPI（窓口：/api/tasks/{id}）
    // PUTは「データを置き換える」時によく使います
    @PutMapping("/api/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        // 送られてきたタスクデータに「変更したいID」をセットしてから保存すると、
        // Spring Bootが「新規作成」ではなく「既存データの上書き」だと自動で判断してくれます！
        task.setId(id);
        return taskRepository.save(task);
    }
}