package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.dev.model.Comment;
import platform.dev.model.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentByPost(Post post);        // 게시물 지울 때 엮여있는 댓글 또한 삭제
    void deleteCommentByCommentId(Long commentId);
}
