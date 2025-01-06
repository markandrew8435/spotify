package voosh.ai.spotify.service;

import mes.job_cron.constants.Category;
import mes.job_cron.exception.ApiException;
import mes.job_cron.repository.AlbumRepository;
import mes.job_cron.repository.ArtistRepository;
import mes.job_cron.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackRepository trackRepository;

    void validateFavoriteCategory(Category category, Long itemId) throws ApiException {
        switch (category) {
            case ARTIST:
                validateArtistId(itemId);
                break;

            case ALBUM:
                validateAlbumId(itemId);
                break;

            case TRACK:
                validateTrackId(itemId);
                break;

            default:
                throw new ApiException("Invalid category", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateTrackId(Long itemId) throws ApiException {
        if (!trackRepository.existsById(itemId)) {
            throw new ApiException("Track not found", HttpStatus.NOT_FOUND);
        }
    }

    public void validateAlbumId(Long itemId) throws ApiException {
        if (!albumRepository.existsById(itemId)) {
            throw new ApiException("Album not found", HttpStatus.NOT_FOUND);
        }
    }

    public void validateArtistId(Long artistId) throws ApiException {
        if(!artistRepository.existsById(artistId)) throw new ApiException("Artist not found", HttpStatus.NOT_FOUND);
    }
}
