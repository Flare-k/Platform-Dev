package platform.dev.model.response.post;

import lombok.*;
import platform.dev.model.User;

@Builder
@NoArgsConstructor
@Setter
@Getter
@Data
// 게시글 미리보기
public class PostPreviewResponse {
    private Long postId;        // ID
    private String thumbnail;   // 썸네일
    private Long likeCount;     // 좋아요 수
    private Long viewCount;     // 조회 수
    private User user;          // 게시자

    public PostPreviewResponse(Long postId, String thumbnail, Long likeCount, Long viewCount, User user) {
        this.postId = postId;
        this.thumbnail = thumbnail;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.user = user;
    }

}
