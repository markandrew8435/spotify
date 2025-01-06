package voosh.ai.spotify.repository;

import mes.job_cron.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findByArtistId(Long artistId);

    List<Track> findByAlbumId(Long albumId);

    List<Track> findByHidden(boolean hidden);
}
