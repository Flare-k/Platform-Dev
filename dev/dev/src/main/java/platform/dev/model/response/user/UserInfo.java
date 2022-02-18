package platform.dev.model.response.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfo {
    private Long userId;
    private String email;
    private String name;
    private String nickname;
    private String address;
}