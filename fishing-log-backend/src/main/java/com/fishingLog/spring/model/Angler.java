package com.fishingLog.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Setter @Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "encryptedPassword", "salt", "createdAt", "updatedAt"})
@ToString(exclude = {"encryptedPassword", "salt"})
@Table(name="angler")

public class Angler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;
    @Column(nullable = false)
    private String salt;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;
    @ManyToMany
    private Set<Record> records;

    public Angler(Long id, String firstName, String lastName, String userName, String email, String encryptedPassword, String salt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
