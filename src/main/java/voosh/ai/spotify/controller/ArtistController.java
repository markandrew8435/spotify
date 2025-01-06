package voosh.ai.spotify.controller;

import mes.job_cron.constants.Constants;
import mes.job_cron.entities.Artist;
import mes.job_cron.exception.ApiException;
import mes.job_cron.model.CustomResponse;
import mes.job_cron.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // Create Artist
    @PostMapping
    public CustomResponse createArtist(@RequestBody Artist artist) throws ApiException {
        Artist createdArtist = artistService.createArtist(artist);
        return CustomResponse.builder()
                .status(201)
                .data(createdArtist)
                .message(Constants.ARTIST_CREATED)
                .build();
    }

    // Get All Artists with Filters
    @GetMapping
    public CustomResponse getAllArtists(
            @RequestParam(required = false) Boolean hidden,
            @RequestParam(required = false) Boolean grammy) {
        List<Artist> artists = artistService.getArtistsWithFilters(hidden, grammy);
        return CustomResponse.builder()
                .status(200)
                .data(artists)
                .message(Constants.ARTISTS_FETCHED)
                .build();
    }

    // Get Artist by ID
    @GetMapping("/{artistId}")
    public CustomResponse getArtistById(@PathVariable Long artistId) throws ApiException {
        Artist artist = artistService.getArtistById(artistId);
        return CustomResponse.builder()
                .status(200)
                .data(artist)
                .message(Constants.ARTISTS_FETCHED)
                .build();
    }

    // Update Artist
    @PutMapping("/{artistId}")
    public CustomResponse updateArtist(
            @PathVariable Long artistId,
            @RequestBody Artist updatedArtist) throws ApiException {
        Artist artist = artistService.updateArtist(artistId, updatedArtist);
        return CustomResponse.builder()
                .status(200)
                .data(artist)
                .message(Constants.ARTIST_UPDATED)
                .build();
    }

    // Delete Artist
    @DeleteMapping("/{artistId}")
    public CustomResponse deleteArtist(@PathVariable Long artistId) throws ApiException {
        artistService.deleteArtist(artistId);
        return CustomResponse.builder()
                .status(200)
                .message(Constants.ARTIST_DELETED)
                .build();
    }
}
