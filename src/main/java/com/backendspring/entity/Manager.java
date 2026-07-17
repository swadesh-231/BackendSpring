package com.backendspring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "managers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder.Default
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted = false;
}
