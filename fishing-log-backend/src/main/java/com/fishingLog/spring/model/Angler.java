package com.fishingLog.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Collections;
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
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String role;
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

    public Angler(Long id, String firstName, String lastName, String username, String email, String role, String encryptedPassword, String salt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.role = role;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Set<Record> getRecords() {
        if (records == null) return Collections.emptySet();
        return records;
    }
}
