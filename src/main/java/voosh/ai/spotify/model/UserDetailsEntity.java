package voosh.ai.spotify.model;

import jakarta.persistence.*;
import voosh.ai.spotify.constants.Roles;

import javax.management.relation.Role;

@Entity
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role; // Enum: ADMIN, EDITOR, VIEWER

    // Getters and Setters
}
