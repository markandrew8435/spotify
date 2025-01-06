package voosh.ai.spotify.service;

import mes.job_cron.entities.Artist;
import mes.job_cron.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    // Create Artist
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    // Get All Artists
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // Get Artists with Filters
    public List<Artist> getArtistsWithFilters(Boolean hidden, Boolean grammy) {
        if (hidden != null) {
            return artistRepository.findByHidden(hidden);
        } else if (grammy != null) {
            return artistRepository.findByGrammy(grammy);
        }
        return artistRepository.findAll();
    }

    // Get Artist by ID
    public Artist getArtistById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
    }

    // Update Artist
    public Artist updateArtist(Long artistId, Artist updatedArtist) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        artist.setName(updatedArtist.getName());
        artist.setGrammy(updatedArtist.isGrammy());
        artist.setHidden(updatedArtist.isHidden());
        return artistRepository.save(artist);
    }

    // Delete Artist
    public void deleteArtist(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        artistRepository.delete(artist);
    }
}
