package platform.dev.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String email;
    private String name;
    private String nickname;

    @NotBlank
    @NotNull
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=.])(?=\\S+$).{8,20}",message = "패스워드는 대문자, 소문자, 특수문자가 적어도 하나씩은 있어야 하며 최소 8자리여야 하며 최대 20자리까지 가능합니다.")
    private String password;

    private String confirmPassword;
    private String address;
}