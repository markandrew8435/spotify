package voosh.ai.spotify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voosh.ai.spotify.entities.Artist;
import voosh.ai.spotify.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // Create Artist
    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist createdArtist = artistService.createArtist(artist);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    // Get All Artists with Filters
    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists(
            @RequestParam(required = false) Boolean hidden,
            @RequestParam(required = false) Boolean grammy) {
        List<Artist> artists = artistService.getArtistsWithFilters(hidden, grammy);
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    // Get Artist by ID
    @GetMapping("/{artistId}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long artistId) {
        Artist artist = artistService.getArtistById(artistId);
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    // Update Artist
    @PutMapping("/{artistId}")
    public ResponseEntity<Artist> updateArtist(
            @PathVariable Long artistId,
            @RequestBody Artist updatedArtist) {
        Artist artist = artistService.updateArtist(artistId, updatedArtist);
        return new ResponseEntity<>(artist, HttpStatus.NO_CONTENT);
    }

    // Delete Artist
    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long artistId) {
        artistService.deleteArtist(artistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
