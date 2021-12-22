package platform.dev.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import platform.dev.exception.user.UserNotExistException;
import platform.dev.model.Comment;
import platform.dev.model.CustomUserDetails;
import platform.dev.model.Post;
import platform.dev.model.User;
import platform.dev.repository.CommentRepository;
import platform.dev.repository.PostRepository;
import platform.dev.repository.UserRepository;
import platform.dev.util.JwtUtil;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private JwtUtil jwtUtil;

    @Transactional
    public Comment addComment(String text, Long postId, String token) {
        Post post = postRepository.findByPostId(postId).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getEmail();
        String parsedToken = token.substring(7);

        boolean isValidateToken = jwtUtil.validateToken(parsedToken, email);

        if (!isValidateToken) {
            throw new UserNotExistException();
        }

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserNotExistException();
        }


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