package voosh.ai.spotify.service;

import mes.job_cron.entities.Album;
import mes.job_cron.exception.ApiException;
import mes.job_cron.repository.AlbumRepository;
import mes.job_cron.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ValidationService validationService;

    public Album createAlbum(Album album) throws ApiException {
        validationService.validateArtistId(album.getArtistId());
        return albumRepository.save(album);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    public Album updateAlbum(Long id, Album album) {
        Album existingAlbum = albumRepository.findById(id).orElse(null);
        if (existingAlbum != null) {
            existingAlbum.setAlbumId(album.getAlbumId());
            existingAlbum.setName(album.getName());
            existingAlbum.setArtistId(album.getArtistId());
            return albumRepository.save(existingAlbum);
        }
        return null;
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}