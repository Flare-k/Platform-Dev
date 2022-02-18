package platform.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JsonIgnoreProperties({"postList"})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime createdDate;

    @PrePersist
    public void createdDate() {
        this.createdDate = LocalDateTime.now();
    }

    @Builder
    public Comment(String text, Post post, User user) {
        this.text = text;
        this.post = post;
        this.user = user;
    }
}
