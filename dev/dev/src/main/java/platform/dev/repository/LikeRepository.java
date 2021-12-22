package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.dev.model.Likes;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
}
