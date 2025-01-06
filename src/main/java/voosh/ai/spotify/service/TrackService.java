package voosh.ai.spotify.service;

import mes.job_cron.entities.Track;
import mes.job_cron.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    // Create Track
    public Track createTrack(Track track) {

        return trackRepository.save(track);
    }

    // Get All Tracks
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    // Get Tracks with Filters
    public List<Track> getTracksWithFilters(Long artistId, Long albumId, Boolean hidden) {
//        TODO Exception handling is he key, also filter with userId
        if (artistId != null) {
            return trackRepository.findByArtistId(artistId);
        } else if (albumId != null) {
            return trackRepository.findByAlbumId(albumId);
        } else if (hidden != null) {
            return trackRepository.findByHidden(hidden);
        }
        return trackRepository.findAll();
    }

    // Get Track by ID
    public Track getTrackById(Long trackId) {
//        TODO find and replace all the other exceptions with ApiException
//        TODO get the user in from track and the jwt and try to match them
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track not found"));
    }

    // Update Track
    public Track updateTrack(Long trackId, Track updatedTrack) {
//        TODO get the user in from track and the jwt and try to match them
        Track track = getTrackById(trackId);
        track.setName(updatedTrack.getName());
        track.setDuration(updatedTrack.getDuration());
        track.setHidden(updatedTrack.isHidden());
        return trackRepository.save(track);
    }

    // Delete Track
    public void deleteTrack(Long trackId) {
//        TODO get the user in from track and the jwt and try to match them

        Track track = getTrackById(trackId);
        trackRepository.delete(track);
    }
}
