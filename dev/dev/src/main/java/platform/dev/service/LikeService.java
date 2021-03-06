package platform.dev.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import platform.dev.handler.CustomApiException;
import platform.dev.model.response.user.UserInfo;
import platform.dev.repository.LikesRepository;
import platform.dev.repository.UserRepository;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class LikeService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // 좋아요 클릭
    @Transactional
    public void clickLikes(Long postId, String token) {
        // 현재 로그인 중인 유저 확인
        UserInfo me = userService.me(token);

        try {
            likesRepository.clickLikes(postId, me.getUserId());
        } catch (Exception e) {
            throw new CustomApiException("이미 좋아요 하였습니다.");
        }
    }

    // 좋아요 취소
    @Transactional
    public void clickUnlikes(Long postId, String token) {
        // 현재 로그인중인 유저 확인
        UserInfo me = userService.me(token);

        try {
            likesRepository.clickUnLikes(postId, me.getUserId());
        } catch (Exception e) {
            throw new CustomApiException("이미 좋아요 하였습니다.");
        }
    }

}
