package voosh.ai.spotify.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long artistId;

    @Column(nullable = false)
    private String name;

    private boolean grammy;

    private boolean hidden;
}
