package voosh.ai.spotify.controller;

import jakarta.servlet.http.HttpServletRequest;
import mes.job_cron.constants.Category;
import mes.job_cron.constants.Constants;
import mes.job_cron.entities.Favorite;
import mes.job_cron.exception.ApiException;
import mes.job_cron.model.CustomResponse;
import mes.job_cron.service.AuthenticationFacade;
import mes.job_cron.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    // Add Favorite
    @PostMapping
    public CustomResponse addFavorite(@RequestBody Favorite favorite, HttpServletRequest httpRequest) throws ApiException {
        Long userId = authenticationFacade.getUser(httpRequest).getId();
        Favorite createdFavorite = favoriteService.addFavorite(favorite, userId);
        return CustomResponse.builder()
                .status(201)
                .data(createdFavorite)
                .message(Constants.FAVORITE_ADDED)
                .build();
    }

    // Get Favorites by User
    @GetMapping("/{category}")
    public CustomResponse getFavoritesByUser(
            @PathVariable(required = false) Category category, HttpServletRequest httpRequest) throws ApiException {
        Long userId = authenticationFacade.getUser(httpRequest).getId();

        List<Favorite> favorites = (category != null)
                ? favoriteService.getFavoritesByCategoryAndUserId(category, userId)
                : favoriteService.getFavoritesByUser(userId);
        return CustomResponse.builder()
                .status(200)
                .data(favorites)
                .message(Constants.FAVORITES_FETCHED)
                .build();
    }

    // Remove Favorite
    @DeleteMapping("/{favoriteId}")
    public CustomResponse removeFavorite(@PathVariable Long favoriteId, HttpServletRequest httpRequest) throws ApiException {
        Long userId = authenticationFacade.getUser(httpRequest).getId();

        favoriteService.removeFavorite(favoriteId, userId);
        return CustomResponse.builder()
                .status(200)
                .message(Constants.FAVORITE_REMOVED)
                .build();
    }
}
