package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platform.dev.model.Post;
import platform.dev.model.View;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
    void deleteViewByPost(Post post);        // 게시물 지울 때 엮여있는 좋아요 또한 삭제

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO view(post_id, user_id) VALUES(:post_id, :user_id)", nativeQuery = true)
    void viewPost(@Param("post_id") Long postId, @Param("user_id") Long userId);

    @Query(value = "SELECT * FROM view WHERE post_id = :post_id AND user_id = :user_id", nativeQuery = true)
    Optional<View> findByPostIdAndUserId(@Param("post_id") Long postId, @Param("user_id") Long userId);
}
