package voosh.ai.spotify.service;

import mes.job_cron.constants.Category;
import mes.job_cron.constants.Constants;
import mes.job_cron.entities.Favorite;
import mes.job_cron.exception.ApiException;
import mes.job_cron.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ValidationService validationService;

    // Add Favorite
    public Favorite addFavorite(Favorite favorite, Long userId) throws ApiException {
        validationService.validateFavoriteCategory(favorite.getCategory(), favorite.getItemId());
        favorite.setUserId(userId);
        return favoriteRepository.save(favorite);
    }

    // Get Favorites by User
    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    // Get Favorites by Category
    public List<Favorite> getFavoritesByCategoryAndUserId(Category category, Long userId) {
        return favoriteRepository.findByCategoryAndUserId(category, userId);
    }

    // Remove Favorite
    public void removeFavorite(Long favoriteId, Long userId) throws ApiException {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        if(!Objects.equals(userId, favorite.getUserId()))
            throw new ApiException(Constants.FORBIDDEN_ACCESS, HttpStatus.FORBIDDEN);

        favoriteRepository.delete(favorite);
    }

    // Validate favorite category and item existence
}

