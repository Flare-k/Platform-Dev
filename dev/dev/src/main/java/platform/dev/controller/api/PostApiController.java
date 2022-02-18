package platform.dev.controller.api;

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

@RestController
@RequestMapping("/api")
public class PostApiController {

    @Autowired
    private PostService postService;

    // 게시물 업로드 (form-data)
    @PostMapping("/post/upload")
    public ResponseEntity<PostResponse> postUpload(PostRequest postRequest, @RequestParam("thumbnail") MultipartFile multipartFile, @RequestHeader(value = Util.AUTHORIZATION) String token) {
        PostInfo postInfo = postService.postUpload(postRequest, multipartFile, token);
        PostResponse response = new PostResponse(HttpStatus.OK, Controller.FILE_UPLOAD_SUCCESS_MESSAGE, postInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/post/delete")
    public String postDelete(@RequestParam("postId") Long postId, @RequestHeader(value = Util.AUTHORIZATION) String token) {
        postService.postDelete(postId, token);
        // 사용자 프로필로 리다이렉팅
        return "delete complete";
    }

    // '좋아요'를 누른 게시물 가져오기
    @GetMapping("/post/likes")
    public PostPreviewResponse getLikesPost(@RequestHeader(value = Util.AUTHORIZATION) String token) {
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setPostInfoList(postService.getLikesPost(token));
        return postPreviewResponse;
    }

    // 조회한 게시물 가져오기
    @GetMapping("/post/views")
    public PostPreviewResponse getViewPost(@RequestHeader(value = Util.AUTHORIZATION) String token) {
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setPostInfoList(postService.getViewPost(token));
        return postPreviewResponse;
    }

    // 게시글 수정 (form-data)
    @PutMapping("/post/{postId}/update")
    public String updatePost(PostRequest postRequest, MultipartFile multipartFile, @PathVariable("postId") Long postId, @RequestHeader(value = Util.AUTHORIZATION) String token) {
        postService.updatePost(postRequest, multipartFile, postId, token);
        return "update complete";
    }
    
    // 지원자 추가 시 needUser 1 감소 & 대기 리스트에 지원자 담기
    // 참여 중, 참여 대기 중임을 나타내는 포스트 테이블도 필요

}
