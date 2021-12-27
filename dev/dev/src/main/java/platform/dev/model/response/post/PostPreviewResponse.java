package platform.dev.model.response.post;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
// 게시글 미리보기
public class PostPreviewResponse {
    private List<PostInfo> postInfoList;
}
