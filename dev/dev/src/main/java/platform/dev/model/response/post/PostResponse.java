package platform.dev.model.response.post;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {
    private HttpStatus httpStatus;
    private String message;
    private PostInfo postInfo;
}
