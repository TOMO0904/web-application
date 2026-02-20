package com.example.demo.service; // ※ご自身のパッケージ名に合わせてください

import com.example.demo.entity.ProjectNode;
import com.example.demo.repository.ProjectNodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectNodeService {

    private final ProjectNodeRepository repository;

    // コンストラクタによる依存性の注入（Spring Bootの推奨される書き方です）
    public ProjectNodeService(ProjectNodeRepository repository) {
        this.repository = repository;
    }

    /**
     * 新しいプロジェクト（タスク）を作成して保存する
     */
    @Transactional
    public ProjectNode createProject(String name, Integer manualEffort, Long parentId) {
        ProjectNode node = new ProjectNode();
        node.setName(name);
        node.setManualEffort(manualEffort);

        // 親IDが指定されている場合は、親を探して紐付ける
        if (parentId != null) {
            ProjectNode parent = repository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("親プロジェクトが見つかりません"));

            // 親の子リストに追加し、子にも親をセットする
            parent.getChildren().add(node);
            node.setParent(parent);
        }

        // DBに保存
        return repository.save(node);
    }

    /**
     * 指定したプロジェクトの「総工数」を取得する
     */
    public Integer calculateTotalEffort(Long projectId) {
        ProjectNode project = repository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("プロジェクトが見つかりません"));

        // Entityで作った再帰メソッドを呼び出す！
        return project.getTotalEffort();
    }

    /**
     * ルート（一番上の階層）のプロジェクト一覧を取得する
     */
    public List<ProjectNode> getRootProjects() {
        // 親がいない（parent_id が null の）ノードを取得できれば、それがルートです
        // ※ Repository側に簡単なメソッド追加が必要になりますが、一旦全体を取得する形でもOKです
        return repository.findAll().stream()
                .filter(node -> node.getParent() == null)
                .toList();
    }
}