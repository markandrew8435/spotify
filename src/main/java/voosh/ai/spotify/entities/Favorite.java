package voosh.ai.spotify.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mes.job_cron.constants.Category;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@Data
@DynamicUpdate
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long favoriteId;

    @Column(nullable = false)
    private Long userId; // Reference to User entity

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; // Enum: ARTIST, ALBUM, TRACK

    @Column(nullable = false)
    private Long itemId; // ID of the favorite item (Artist, Album, or Track)
}
