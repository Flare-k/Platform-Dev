package platform.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    private String name;

    @NotBlank
    @NotNull
    @Column(length = 15, nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Post> postList;

    @Builder
    public User(String email, String name, String nickname, String password, String address) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
    }

}