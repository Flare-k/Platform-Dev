package platform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.dev.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
