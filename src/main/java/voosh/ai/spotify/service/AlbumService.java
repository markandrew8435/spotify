package voosh.ai.spotify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import voosh.ai.spotify.entities.Album;
import voosh.ai.spotify.repository.AlbumRepository;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

//    @Autowired
//    private ArtistRepository artistRepository;
//
//    public Album createAlbum(Long artistId, Album album) {
//        if (!artistRepository.existsById(artistId)) {
//            throw new RuntimeException("Artist not found");
//        }
//        album.setArtistId(artistId);
//        return albumRepository.save(album);
//    }

}
