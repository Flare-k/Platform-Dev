package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.dev.model.Comment;
import platform.dev.model.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentByPost(Post post);
    void deleteCommentByCommentId(Long commentId);
}
