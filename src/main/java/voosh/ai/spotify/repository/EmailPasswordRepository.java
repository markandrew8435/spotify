package voosh.ai.spotify.repository;

import mes.job_cron.entities.EmailPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailPasswordRepository extends JpaRepository<EmailPasswordEntity, Long> {
    EmailPasswordEntity findByEmail(String email);
}
