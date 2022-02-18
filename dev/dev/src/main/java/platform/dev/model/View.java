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
                        name="views",
                        columnNames = {"post_id", "user_id"}
                )
        }
)
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewId;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @JsonIgnoreProperties({"postList"})
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public View(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
