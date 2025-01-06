package voosh.ai.spotify.repository;

import mes.job_cron.constants.Category;
import mes.job_cron.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserId(Long userId);

    List<Favorite> findByCategoryAndUserId(Category category, Long userId);
}
