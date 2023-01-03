package com.alexeymarino.botserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usr")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;
    private String name;
}
