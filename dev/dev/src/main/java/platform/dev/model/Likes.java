package platform.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes",
                        columnNames = {"post_id", "user_id"}
                )
        }
)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @JsonIgnoreProperties({"postList"}) //post -> user -> likesList -> user -> postList 무한 참조 막기 위함
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public Likes(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
