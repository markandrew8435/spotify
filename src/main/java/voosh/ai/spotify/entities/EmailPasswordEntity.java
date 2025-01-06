package voosh.ai.spotify.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mes.job_cron.constants.Roles;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Setter
@Getter
@DynamicUpdate
public class EmailPasswordEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;
}