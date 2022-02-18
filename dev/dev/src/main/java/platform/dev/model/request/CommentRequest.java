package platform.dev.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequest {
    @NotBlank
    private String text;

    @NotNull
    private Long postId;
}
