package platform.dev.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import platform.dev.exception.user.UserNotExistException;
import platform.dev.handler.CustomApiException;
import platform.dev.model.CustomUserDetails;
import platform.dev.model.User;
import platform.dev.repository.LikesRepository;
import platform.dev.repository.UserRepository;
import platform.dev.util.JwtUtil;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LikeService {

    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final JwtUtil jwtUtil;
    // 좋아요 클릭
    @Transactional
    public void clickLikes(Long postId, String token) {
        // 현재 로그인중인 유저 확인
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

        try {
            likesRepository.likes(postId, user.get().getUserId());
        } catch (Exception e) {
            throw new CustomApiException("이미 좋아요 하였습니다.");
        }
    }

    // 좋아요 취소
    @Transactional
    public void clickUnlikes(Long postId, String token) {
        // 현재 로그인중인 유저 확인
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

        try {
            likesRepository.unLikes(postId, user.get().getUserId());
        } catch (Exception e) {
            throw new CustomApiException("이미 좋아요 하였습니다.");
        }
    }
}
