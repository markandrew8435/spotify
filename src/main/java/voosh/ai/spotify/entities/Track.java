package voosh.ai.spotify.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trackId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int duration; // Duration in seconds

    @Column(nullable = false)
    private boolean hidden;

    @Column(nullable = false)
    private Long artistId; // Reference to Artist entity

    @Column(nullable = false)
    private Long albumId; // Reference to Album entity
}
