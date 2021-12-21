package platform.dev.model.response.user;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtherResponse {
    private HttpStatus httpStatus;
    private String message;
    private UserInfo userInfo;
}
