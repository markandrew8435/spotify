package voosh.ai.spotify.entities;

import jakarta.persistence.*;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trackId;

    @Column(nullable = false)
    private String name;

    private int duration; // Duration in seconds

    private boolean hidden; // Visibility toggle

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist; // Reference to Artist entity

    private Long albumId; // Reference to Album entity

    // Getters and Setters
}
