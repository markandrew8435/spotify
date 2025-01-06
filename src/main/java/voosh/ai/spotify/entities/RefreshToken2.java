package voosh.ai.spotify.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@DynamicUpdate
public class RefreshToken2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private Date expiration;
//    private String  email;

}