package com.duy.nb.spring.security.entity;

import javax.persistence.*;

@Entity
@Table(name = "key_storage")
public class KeyStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "history_id")
    private Long historyId;
    @Column(name = "key")
    private String key;

    public KeyStorage() {
    }

    public KeyStorage(Long id, Long historyId, String key) {
        this.id = id;
        this.historyId = historyId;
        this.key = key;
    }

    public KeyStorage(Long historyId, String key) {
        this.historyId = historyId;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
