package platform.dev.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import platform.dev.model.Comment;
import platform.dev.model.Post;
import platform.dev.model.User;
import platform.dev.model.response.user.UserInfo;
import platform.dev.repository.CommentRepository;
import platform.dev.repository.PostRepository;
import platform.dev.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private UserService userService;

    @Transactional
    public Comment addComment(String text, Long postId, String token) {
        Post post = postRepository.findByPostId(postId).get();
        UserInfo me = userService.me(token);
        Optional<User> user = userRepository.findByEmail(me.getEmail());

        Comment comment = Comment.builder()
                .text(text)
                .post(post)
                .user(user.get())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteCommentByCommentId(commentId);
    }
}
