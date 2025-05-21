package com.manittotie.manilib.groups.listener;

import com.manittotie.manilib.groups.domain.Groups;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class GroupListener {
    @PrePersist
    public void prePersist(Groups groups) {
        LocalDateTime now = LocalDateTime.now();
        groups.setCreatedAt(now);
        groups.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(Groups groups) { groups.setUpdatedAt(LocalDateTime.now()); }
}
