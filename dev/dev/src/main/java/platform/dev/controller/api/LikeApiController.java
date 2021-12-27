package platform.dev.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.dev.constant.Controller;
import platform.dev.constant.Util;
import platform.dev.service.LikeService;

@RestController
@RequestMapping("/api")
public class LikeApiController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/like/post/{postId}")
    public ResponseEntity<?> clickLikes(@PathVariable("postId") Long postId, @RequestHeader(value = Util.AUTHORIZATION) String token) {
        likeService.clickLikes(postId, token);
        return new ResponseEntity<>(Controller.CLICK_LIKE_SUCCESS_MESSAGE, HttpStatus.OK);
    }

    @PostMapping("/unlike/post/{postId}")
    public ResponseEntity<?> clickUnlikes(@PathVariable("postId") Long postId, @RequestHeader(value = Util.AUTHORIZATION) String token) {
        likeService.clickUnlikes(postId, token);
        return new ResponseEntity<>(Controller.CLICK_UNLIKE_SUCCESS_MESSAGE, HttpStatus.OK);
    }
}
