package platform.dev.model.response.post;

import lombok.*;
import platform.dev.model.Post;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
// 게시글 미리보기
public class PostPreviewResponse {
    private List<PostInfo> postInfoList;
}
