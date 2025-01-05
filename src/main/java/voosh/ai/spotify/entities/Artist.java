package voosh.ai.spotify.entities;

import jakarta.persistence.*;

@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long artistId;

    @Column(nullable = false)
    private String name;

    private boolean grammy; // Indicates if the artist has won a Grammy

    private boolean hidden; // Visibility toggle

    // Getters and Setters
}
