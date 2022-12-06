package com.alexeymarino.botserver.entity;

import com.alexeymarino.botserver.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usr")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;
    private String name;

    public UserEntity(User user) {
        this.userId = user.getUserId();
        this.name = user.getUserName();
    }

    public UserEntity() {

    }
}
