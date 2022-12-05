package com.alexeymarino.botserver.entity;

import com.fasterxml.jackson.annotation.JsonView;
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
}
