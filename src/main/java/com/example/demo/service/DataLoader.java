package com.example.demo.service;

import com.example.demo.entity.ProjectNode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ProjectNodeService service) {
        return args -> {
            // 1. 親プロジェクトを作成（仮の工数100を設定）
            ProjectNode parent = service.createProject("親プロジェクト", 100, null);
            Long parentId = parent.getId();

            // 2. 子プロジェクトを2つ作成（30と40）
            service.createProject("子タスクA", 30, parentId);
            service.createProject("子タスクB", 40, parentId);

            System.out.println("--- テストデータの投入が完了しました ---");
        };
    }
}