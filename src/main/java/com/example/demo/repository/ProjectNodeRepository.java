package com.example.demo.repository; // ※ご自身のパッケージ名に合わせて変更してください

import com.example.demo.entity.ProjectNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectNodeRepository extends JpaRepository<ProjectNode, Long> {
    // 中身は空っぽで大丈夫です！
}