package voosh.ai.spotify.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long albumId;

    @Column(nullable = false)
    private String name;

    private Integer releaseYear; // Release year

    private Boolean hidden; // Visibility toggle

    private Long artistId; // Reference to Artist entity

    // Getters and Setters
}
