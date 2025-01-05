package voosh.ai.spotify.repository;

import voosh.ai.spotify.model.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {
    UserDetailsEntity findByEmail(String email);
}
