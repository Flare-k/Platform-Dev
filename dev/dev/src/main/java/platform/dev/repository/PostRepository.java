package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import platform.dev.model.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Post p SET p.view_count = :view_count WHERE p.post_id = :post_id", nativeQuery = true)
    void updateViewCount(Long viewCount, Long postId);
}
