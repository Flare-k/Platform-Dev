package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.dev.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
