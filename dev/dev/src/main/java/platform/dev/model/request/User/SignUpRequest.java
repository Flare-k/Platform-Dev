package platform.dev.model.request.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String confirmPassword;
    private String address;
}