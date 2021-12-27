package platform.dev.model.response.post;

import lombok.*;
import platform.dev.model.response.user.UserInfo;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostInfo {
    private Long postId;
    private String title;
    private String description;
    private String thumbnail;
    private Long likeCount;
    private boolean likeState;
    private Long viewCount;
    private Long needUser;
    private LocalDateTime createdDate;
    private UserInfo userInfo;
    // User me/other가 모두 UserInfo를 리턴으로 받기 때문에
    // 게시글에서 작성자를 클릭했을때 작성자 정보를 받아오기 위해 userInfo로 통일
}
