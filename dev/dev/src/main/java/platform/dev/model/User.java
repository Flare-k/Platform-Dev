package platform.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String address;

    public User(
            String email,
            String name,
            String nickname,
            String password,
            String address
    ) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
    }
}