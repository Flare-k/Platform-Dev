package platform.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import platform.dev.model.response.user.UserInfo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;        // id
    private String title;       // 게시글 제목
    private String description; // 게시글 내용
    private String thumbnail;   // 게시글 썸네일
    private boolean likeState;  // 유저가 '좋아요'를 눌렀는지
    private Long likeCount;     // 좋아요 수
    private Long viewCount;     // 조회 수
    private Long needUser;      // 필요한 인원
    private LocalDateTime createdDate;  // 게시글 등록일

    @JsonIgnoreProperties({"postList"})
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;          // 게시한 유저

    @PrePersist
    public void initDefaultValue() {
        this.createdDate = LocalDateTime.now();
        this.likeCount = 0L;
        this.viewCount = 0L;
        this.likeState = false;
    }

    @Builder
    public Post(String title, String description, String thumbnail, Long needUser, User user) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.needUser = needUser;
        this.user = user;
    }

    public void updateLikesCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public void updateLikesState(Boolean likeState) {
        this.likeState = likeState;
    }

    @Transient
    private UserInfo userInfo;
}
