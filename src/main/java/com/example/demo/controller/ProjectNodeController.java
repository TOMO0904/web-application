package com.example.demo.controller; // ※ご自身のパッケージ名に合わせてください

import com.example.demo.entity.ProjectNode;
import com.example.demo.service.ProjectNodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectNodeController {

    private final ProjectNodeService service;

    public ProjectNodeController(ProjectNodeService service) {
        this.service = service;
    }

    /**
     * ルート（一番上の階層）のプロジェクト一覧を取得する
     * GET: http://localhost:8080/api/projects
     */
    @GetMapping
    public List<ProjectNode> getRootProjects() {
        return service.getRootProjects();
    }

    /**
     * 新しいプロジェクト（タスク）を作成する
     * POST: http://localhost:8080/api/projects
     */
    @PostMapping
    public ProjectNode createProject(
            @RequestParam String name,
            @RequestParam(required = false) Integer manualEffort,
            @RequestParam(required = false) Long parentId) {
        return service.createProject(name, manualEffort, parentId);
    }

    /**
     * 指定したプロジェクトの「総工数」を取得する
     * GET: http://localhost:8080/api/projects/{id}/effort
     */
    @GetMapping("/{id}/effort")
    public Integer getTotalEffort(@PathVariable Long id) {
        return service.calculateTotalEffort(id);
    }
}