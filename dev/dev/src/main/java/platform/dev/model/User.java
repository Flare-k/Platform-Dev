package platform.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Size(min=2, max=10)
    @NotBlank
    @Column(nullable = false, unique = true)
    private String nickname;

    @Size(min=5, max=20)
    @NotBlank
    @Column(nullable = false)
    private String password;

    private String address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Post> postList;

    public User(String email, String name, String nickname, String password, String address) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
    }

}