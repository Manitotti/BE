package com.manittotie.manilib.group.listener;

import com.manittotie.manilib.group.domain.Group;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class GroupListener {
    @PrePersist
    public void prePersist(Group group) {
        LocalDateTime now = LocalDateTime.now();
        group.setCreatedAt(now);
        group.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(Group group) { group.setUpdatedAt(LocalDateTime.now()); }
}
