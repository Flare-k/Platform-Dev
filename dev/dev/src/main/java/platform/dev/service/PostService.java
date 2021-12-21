package platform.dev.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import platform.dev.exception.User.UserNotExistException;
import platform.dev.model.CustomUserDetails;
import platform.dev.model.Post;
import platform.dev.model.User;
import platform.dev.model.request.post.PostRequest;
import platform.dev.model.response.post.PostInfo;
import platform.dev.model.response.user.UserInfo;
import platform.dev.repository.PostRepository;
import platform.dev.repository.UserRepository;
import platform.dev.util.JwtUtil;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor    // uploadUrl은 의존성 주입을 하지않을 것이기 때문에 final 처리한 객체들만 의존성 주입을 도와주는 어노테이션
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Value("${post.path}")
    private String uploadUrl;

    public List<PostInfo> postHome() {
        // Response = List<PostInfo>
        List<Post> postList = postRepository.findAll();
        // List<Post> -> List<PostInfo>

        List<PostInfo> postInfoList = new ArrayList<>();

        postList.stream().forEach(
                post ->
                    postInfoList.add(
                            PostInfo.builder()
                                    .postId(post.getPostId())
                                    .title(post.getTitle())
                                    .description(post.getDescription())
                                    .thumbnail(post.getThumbnail())
                                    .likeState(post.isLikeState())
                                    .likeCount(post.getLikeCount())
                                    .viewCount(post.getViewCount())
                                    .needUser(post.getNeedUser())
                                    .createdDate(post.getCreatedDate())
                                    .userInfo(UserInfo.builder()
                                            .userId(post.getUser().getUserId())
                                            .email(post.getUser().getEmail())
                                            .name(post.getUser().getName())
                                            .nickname(post.getUser().getNickname())
                                            .address(post.getUser().getAddress())
                                            .build())
                                    .build()
                    )
        );

        return postInfoList;
    }

    // 게시글 업로드
    @Transactional
    public PostInfo postUpload(PostRequest postRequest, MultipartFile multipartFile) {
        UUID uuid = UUID.randomUUID();
        String thumbnail = uuid + "_" + multipartFile.getOriginalFilename();

        Path thumbnailFilePath = Paths.get(uploadUrl + thumbnail);

        try {
            Files.write(thumbnailFilePath, multipartFile.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // 게시자 = 현재 로그인한 사람
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getEmail();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserNotExistException();
        }

        Post newPost = Post.builder()
                .title(postRequest.getTitle())
                .description(postRequest.getDescription())
                .thumbnail(thumbnail)
                .needUser(postRequest.getNeedUser())
                .user(user.get())
                .build();

        Post savedPost = postRepository.save(newPost);

        return PostInfo.builder()
                .postId(savedPost.getPostId())
                .title(savedPost.getTitle())
                .description(savedPost.getDescription())
                .thumbnail(savedPost.getThumbnail())
                .likeState(savedPost.isLikeState())
                .likeCount(savedPost.getLikeCount())
                .viewCount(savedPost.getViewCount())
                .needUser(savedPost.getNeedUser())
                .createdDate(savedPost.getCreatedDate())
                .userInfo(UserInfo.builder()
                        .userId(user.get().getUserId())
                        .email(user.get().getEmail())
                        .name(user.get().getName())
                        .nickname(user.get().getNickname())
                        .address(user.get().getAddress())
                        .build())
                .build();
    }

    // 게시글 자세히보기 (로그인 유저와 작성자가 다르다면 조회수 증가)

    // 좋아요 기능

    // 게시글 수정

    // 게시글 삭제


}
