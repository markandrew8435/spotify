package voosh.ai.spotify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voosh.ai.spotify.entities.Album;
import voosh.ai.spotify.service.AlbumService;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    // Create Album
    @PostMapping
    public ResponseEntity<Album> createAlbum(
            @RequestBody Album album,
            @RequestParam Long artistId) {
        Album createdAlbum = albumService.createAlbum(artistId, album);
        return new ResponseEntity<>(createdAlbum, HttpStatus.CREATED);
    }
}
