package platform.dev.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import platform.dev.constant.Controller;
import platform.dev.model.request.post.PostRequest;
import platform.dev.model.response.post.PostInfo;
import platform.dev.model.response.post.PostResponse;
import platform.dev.service.PostService;

@RestController
@RequestMapping("/api")
public class PostApiController {

    @Autowired
    private PostService postService;

    @PostMapping("/post/upload")
    public ResponseEntity<PostResponse> postUpload(PostRequest postRequest, @RequestParam("thumbnail") MultipartFile multipartFile) {
        PostInfo postInfo = postService.postUpload(postRequest, multipartFile);
        PostResponse response = new PostResponse(HttpStatus.OK, Controller.FILE_UPLOAD_SUCCESS_MESSAGE, postInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/post/like")
//    public ResponseEntity<PostResponse> clickLikes() {
//
//    }
}
