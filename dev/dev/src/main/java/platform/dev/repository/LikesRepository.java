package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platform.dev.model.Likes;
import platform.dev.model.Post;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteLikesByPost(Post post);        // 게시물 지울 때 엮여있는 좋아요 또한 삭제
    
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO likes(post_id, user_id) VALUES(:post_id, :user_id)", nativeQuery = true)
    void clickLikes(@Param("post_id") Long postId, @Param("user_id") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM likes WHERE post_id = :post_id AND user_id = :user_id", nativeQuery = true)
    void clickUnLikes(@Param("post_id") Long postId, @Param("user_id") Long userId);
}
