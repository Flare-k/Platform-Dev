package platform.dev.model.request.post;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
// 게시글 추가
public class PostRequest {
    private String title;
    private String description;
    private Long needUser;
}
