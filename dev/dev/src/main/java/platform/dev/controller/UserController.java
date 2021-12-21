package platform.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.dev.constant.Controller;
import platform.dev.constant.Util;
import platform.dev.model.request.user.LoginRequest;
import platform.dev.model.request.user.SignUpRequest;
import platform.dev.model.response.BasicResponse;
import platform.dev.model.response.TokenContainingResponse;
import platform.dev.model.response.user.MeResponse;
import platform.dev.model.response.user.OtherResponse;
import platform.dev.model.response.user.UserInfo;
import platform.dev.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{nickname}")
    public ResponseEntity<OtherResponse> otherUser(@PathVariable("nickname") String nickname) {
        System.out.println("nickname = " + nickname);
        UserInfo user = userService.otherUser(nickname);
        OtherResponse response = new OtherResponse(HttpStatus.OK, Controller.FIND_USER_SUCCESS_MESSAGE, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
