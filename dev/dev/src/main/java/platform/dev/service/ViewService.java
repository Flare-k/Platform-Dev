package platform.dev.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import platform.dev.handler.CustomApiException;
import platform.dev.model.response.user.UserInfo;
import platform.dev.repository.LikesRepository;
import platform.dev.repository.ViewRepository;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class ViewService {
    private final ViewRepository viewRepository;
    private final UserService userService;

    // 좋아요 클릭
    @Transactional
    public void views(Long postId, String token) {
        // 현재 로그인 중인 유저 확인
        UserInfo me = userService.me(token);

        try {
            viewRepository.viewPost(postId, me.getUserId());
        } catch (Exception e) {
            throw new CustomApiException("이미 본 게시물입니다.");
        }
    }
}
