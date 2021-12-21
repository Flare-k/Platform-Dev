package platform.dev.model.response.post;

import lombok.*;
import platform.dev.model.User;
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
    private boolean likeState;
    private Long likeCount;
    private Long viewCount;
    private Long needUser;
    private LocalDateTime createdDate;
    private UserInfo userInfo;
}
