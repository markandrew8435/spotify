package voosh.ai.spotify.controller;

import mes.job_cron.constants.Constants;
import mes.job_cron.entities.Album;
import mes.job_cron.exception.ApiException;
import mes.job_cron.model.CustomResponse;
import mes.job_cron.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    // Create Album
    @PostMapping
    public CustomResponse createAlbum(@RequestBody Album album) throws ApiException {
        Album createdAlbum = albumService.createAlbum(album);
        return CustomResponse.builder()
                .status(201)
                .data(createdAlbum)
                .message(Constants.ALBUM_CREATED)
                .build();
    }

    // Get All Albums
    @GetMapping
    public CustomResponse getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return CustomResponse.builder()
                .status(200)
                .data(albums)
                .message(Constants.ALBUMS_FETCHED)
                .build();
    }

    // Get Album by ID
    @GetMapping("/{id}")
    public CustomResponse getAlbumById(@PathVariable Long id) throws ApiException {
        Album album = albumService.getAlbumById(id);
        return CustomResponse.builder()
                .status(200)
                .data(album)
                .message(Constants.ALBUMS_FETCHED)
                .build();
    }

    // Update Album
    @PutMapping("/{id}")
    public CustomResponse updateAlbum(@PathVariable Long id, @RequestBody Album album) throws ApiException {
        Album updatedAlbum = albumService.updateAlbum(id, album);
        return CustomResponse.builder()
                .status(200)
                .data(updatedAlbum)
                .message(Constants.ALBUM_UPDATED)
                .build();
    }

    // Delete Album
    @DeleteMapping("/{id}")
    public CustomResponse deleteAlbum(@PathVariable Long id) throws ApiException {
        albumService.deleteAlbum(id);
        return CustomResponse.builder()
                .status(200)
                .message(Constants.ALBUM_DELETED)
                .build();
    }
}
