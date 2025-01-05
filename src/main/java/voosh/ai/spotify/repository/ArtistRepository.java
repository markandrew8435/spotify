package voosh.ai.spotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import voosh.ai.spotify.entities.Artist;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // Custom query methods (if needed)
    List<Artist> findByHidden(boolean hidden);
    
    List<Artist> findByGrammy(boolean grammy);
}
