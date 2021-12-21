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
import platform.dev.model.response.post.PostResponse;
import platform.dev.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/upload")
    public ResponseEntity<PostResponse> upload(PostRequest postRequest, @RequestParam("thumbnail") MultipartFile multipartFile) {
        PostInfo postInfo = postService.upload(postRequest, multipartFile);
        PostResponse response = new PostResponse(HttpStatus.OK, Controller.FILE_UPLOAD_SUCCESS_MESSAGE, postInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
