package voosh.ai.spotify.entities;

import jakarta.persistence.*;
import voosh.ai.spotify.constants.Category;

@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long favoriteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; // Enum: ARTIST, ALBUM, TRACK

    private Long itemId; // Reference to the ID of the favorite entity (artistId, albumId, or trackId)

    private Long userId; // Reference to User entity

    // Getters and Setters
}
