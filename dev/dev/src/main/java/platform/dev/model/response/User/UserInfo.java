package platform.dev.model.response.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfo {
    private Long userId;
    private String email;
    private String name;
    private String address;
}