package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ProjectNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 仮の工数（ユーザーが手動で設定する値）
    private Integer manualEffort;

    // 親ノードへの参照
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ProjectNode parent;

    // 子ノードのリスト
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectNode> children = new ArrayList<>();

    // =========================================
    // カスタムビジネスロジック
    // =========================================

    /**
     * プロジェクトの総工数を取得する
     */
    @JsonProperty("totalEffort")
    public Integer getTotalEffort() {
        // 子ノードが何もなければ、自身に設定された「仮の工数」を返す
        if (this.children == null || this.children.isEmpty()) {
            return this.manualEffort != null ? this.manualEffort : 0;
        }

        // 子ノードがある場合は、自身の手動入力値は無視して、子ノードの総工数を合算する
        int total = 0;
        for (ProjectNode child : this.children) {
            total += child.getTotalEffort(); // 再帰呼び出し
        }
        return total;
    }

    // =========================================
    // Getter / Setter
    // =========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getManualEffort() {
        return manualEffort;
    }

    public void setManualEffort(Integer manualEffort) {
        this.manualEffort = manualEffort;
    }

    public ProjectNode getParent() {
        return parent;
    }

    public void setParent(ProjectNode parent) {
        this.parent = parent;
    }

    public List<ProjectNode> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectNode> children) {
        this.children = children;
    }
}