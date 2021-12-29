package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platform.dev.model.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);

    void deleteByPostId(Long postId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Post p SET p.view_count = :view_count WHERE p.post_id = :post_id", nativeQuery = true)
    void updateViewCount(@Param("view_count") Long viewCount, @Param("post_id") Long postId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Post p SET p.title = :title, p.description = :description, p.thumbnail = :thumbnail, p.need_user = :need_user WHERE p.post_id = :post_id", nativeQuery = true)
    void updatePost(@Param("title") String title, @Param("description") String description, @Param("thumbnail") String thumbnail, @Param("need_user") Long needUser, @Param("post_id") Long postId);
}
