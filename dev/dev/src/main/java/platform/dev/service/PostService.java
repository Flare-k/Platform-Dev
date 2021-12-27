package platform.dev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import platform.dev.exception.post.PostNotExistException;
import platform.dev.exception.user.UserNotExistException;
import platform.dev.model.CustomUserDetails;
import platform.dev.model.Post;
import platform.dev.model.User;
import platform.dev.model.request.post.PostRequest;
import platform.dev.model.response.post.PostInfo;
import platform.dev.model.response.user.UserInfo;
import platform.dev.repository.CommentRepository;
import platform.dev.repository.LikesRepository;
import platform.dev.repository.PostRepository;
import platform.dev.repository.UserRepository;
import platform.dev.util.JwtUtil;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor    // uploadUrl은 의존성 주입을 하지않을 것이기 때문에 final 처리한 객체들만 의존성 주입을 도와주는 어노테이션
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    @Value("${post.path}")
    private String uploadUrl;

    @Transactional
    public List<PostInfo> postHome(String token) {
        UserInfo me = userService.me(token);
        Long userId = me.getUserId();

        // Response = List<Post> -> List<PostInfo>
        List<Post> postList = postRepository.findAll();
        List<PostInfo> postInfoList = new ArrayList<>();

        Long finalUserId = userId;

        postList.forEach(post -> {
            post.updateLikesCount((long) post.getLikesList().size());
            post.getLikesList().forEach(likes -> {
                if (likes.getUser().getUserId() == finalUserId) {
                    post.updateLikesState(true);
                }
            });
        });

        postList.forEach(
                post ->
                    postInfoList.add(
                            PostInfo.builder()
                                    .postId(post.getPostId())
                                    .title(post.getTitle())
                                    .description(post.getDescription())
                                    .thumbnail(post.getThumbnail())
                                    .likeCount(post.getLikeCount())
                                    .likeState(post.isLikeState())
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
    public PostInfo postUpload(PostRequest postRequest, MultipartFile multipartFile, String token) {
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
        UserInfo me = userService.me(token);
        Optional<User> user = userRepository.findByEmail(me.getEmail());

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
    @Transactional
    public PostInfo getPostDetail(Long postId, String token) {
        Optional<Post> post = postRepository.findByPostId(postId);

        if (post.isEmpty()) {
            throw new PostNotExistException();
        }

        // 로그인 중인 사용자 확인 (로그인 중인 사용자와 게시글 작성자가 같다면 조회수 업데이트 X)
        UserInfo me = userService.me(token);
        Long userId = me.getUserId();

        // viewCount Update Issue
        Long postUserId = post.get().getUser().getUserId();
        // 좋아요 정보
        post.get().updateLikesCount((long) post.get().getLikesList().size());

        boolean flag = (postUserId == userId);

        if (!flag) {    // 같은 유저가 아니라면
            Long viewCount = post.get().getViewCount() + 1;
            postRepository.updateViewCount(viewCount, postId);
            post = postRepository.findByPostId(postId);
        }

        Optional<Post> finalPost = post;
        post.get().getLikesList().forEach(likes -> {
            if (likes.getUser().getUserId() == userId) {
                finalPost.get().updateLikesState(true);
            }
        });

        PostInfo postInfo = PostInfo.builder()
                .postId(finalPost.get().getPostId())
                .title(finalPost.get().getTitle())
                .description(finalPost.get().getDescription())
                .thumbnail(finalPost.get().getThumbnail())
                .likeCount(finalPost.get().getLikeCount())
                .likeState(finalPost.get().isLikeState())
                .viewCount(finalPost.get().getViewCount())
                .needUser(finalPost.get().getNeedUser())
                .createdDate(finalPost.get().getCreatedDate())
                .userInfo(UserInfo.builder()
                        .userId(finalPost.get().getUser().getUserId())
                        .email(finalPost.get().getUser().getEmail())
                        .name(finalPost.get().getUser().getName())
                        .nickname(finalPost.get().getUser().getNickname())
                        .address(finalPost.get().getUser().getAddress())
                        .build())
                .build();

        return postInfo;
    }

    // 게시글 수정


    // 게시글 삭제
    @Transactional
    public void postDelete(Long postId, String token) {
        UserInfo me = userService.me(token);
        Post post = postRepository.findByPostId(postId).get();

        if (post.getUser().getUserId() == me.getUserId()) {
            // 게시글 작성자와 현재 로그인 유저가 일치한다면..
            likesRepository.deleteLikesByPost(post);
            commentRepository.deleteCommentByPost(post);

            File file = new File(uploadUrl + post.getThumbnail());
            file.delete();

            postRepository.deleteByPostId(postId);
        }

    }

}
