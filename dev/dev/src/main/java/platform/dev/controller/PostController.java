package platform.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import platform.dev.constant.Controller;
import platform.dev.constant.Util;
import platform.dev.model.request.post.PostRequest;
import platform.dev.model.response.post.PostInfo;
import platform.dev.model.response.post.PostPreviewResponse;
import platform.dev.model.response.post.PostResponse;
import platform.dev.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public PostPreviewResponse getHome() {
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setPostInfoList(postService.postHome());
        return postPreviewResponse;
    }

    @GetMapping("/upload")
    public void getUpload() {
        // 업로드 페이지 이동
    }

    // 게시글 자세히보기 (로그인 유저와 작성자가 다르다면 조회수 증가)
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostInfo(@PathVariable("postId") Long postId, @RequestHeader(value = Util.AUTHORIZATION, required = false) String token) {
        PostInfo postInfo = postService.getPostDetail(postId, token);
        PostResponse response = new PostResponse(HttpStatus.OK, Controller.FILE_UPLOAD_SUCCESS_MESSAGE, postInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 좋아요 기능

    // 게시글 수정

    // 게시글 삭제
}
