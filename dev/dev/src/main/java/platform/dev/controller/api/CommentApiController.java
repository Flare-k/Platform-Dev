package platform.dev.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import platform.dev.constant.Util;
import platform.dev.model.request.CommentRequest;
import platform.dev.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentRequest commentRequest,
                                        BindingResult bindingResult,
                                        @RequestHeader(value = Util.AUTHORIZATION) String token) {
        return new ResponseEntity<>(commentService.addComment(commentRequest.getText(), commentRequest.getPostId(), token), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK);
    }
}
