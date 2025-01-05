package voosh.ai.spotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import voosh.ai.spotify.entities.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
