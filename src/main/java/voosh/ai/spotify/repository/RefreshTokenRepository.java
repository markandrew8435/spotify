package voosh.ai.spotify.repository;

import mes.job_cron.entities.RefreshToken2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken2, Long> {
    public RefreshToken2 findByToken(String token);
}