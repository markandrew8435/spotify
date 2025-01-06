package voosh.ai.spotify.controller;

import mes.job_cron.entities.Track;
import mes.job_cron.exception.ApiException;
import mes.job_cron.service.TrackService;
import mes.job_cron.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private ValidationService validationService;

    // Create Track
    @PostMapping
    public ResponseEntity<Track> createTrack(@RequestBody Track track) throws ApiException {
        Track createdTrack = trackService.createTrack(track);
        validationService.validateArtistId(track.getArtistId());
        validationService.validateAlbumId(track.getArtistId());

        return new ResponseEntity<>(createdTrack, HttpStatus.CREATED);
    }

    // Get All Tracks
    @GetMapping
    public ResponseEntity<List<Track>> getAllTracks(
            @RequestParam(required = false) Long artistId,
            @RequestParam(required = false) Long albumId,
            @RequestParam(required = false) Boolean hidden) {
        List<Track> tracks = trackService.getTracksWithFilters(artistId, albumId, hidden);
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    // Get Track by ID
    @GetMapping("/{trackId}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long trackId) {
        Track track = trackService.getTrackById(trackId);
        return new ResponseEntity<>(track, HttpStatus.OK);
    }

    // Update Track
    @PutMapping("/{trackId}")
    public ResponseEntity<Track> updateTrack(
            @PathVariable Long trackId,
            @RequestBody Track updatedTrack) throws ApiException {
        Track track = trackService.updateTrack(trackId, updatedTrack);

        validationService.validateArtistId(track.getArtistId());
        validationService.validateAlbumId(track.getArtistId());

        return new ResponseEntity<>(track, HttpStatus.NO_CONTENT);
    }

    // Delete Track
    @DeleteMapping("/{trackId}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long trackId) {
        trackService.deleteTrack(trackId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
