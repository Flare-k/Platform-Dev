package platform.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dev.model.response.post.PostPreviewResponse;
import platform.dev.service.PostService;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private PostService postService;

    @GetMapping("")
    public PostPreviewResponse getHome() {
        // Response = PostPreviewResponse
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setPostInfoList(postService.postHome());
        return postPreviewResponse;
    }
}
