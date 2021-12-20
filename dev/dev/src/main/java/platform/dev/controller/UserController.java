package platform.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.dev.constant.Controller;
import platform.dev.constant.Util;
import platform.dev.model.request.User.LoginRequest;
import platform.dev.model.request.User.SignUpRequest;
import platform.dev.model.response.BasicResponse;
import platform.dev.model.response.TokenContainingResponse;
import platform.dev.model.response.User.MeResponse;
import platform.dev.model.response.User.OtherResponse;
import platform.dev.model.response.User.UserInfo;
import platform.dev.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenContainingResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        String token = userService.loginAndGenerateToken(loginRequest);

        TokenContainingResponse response = new TokenContainingResponse(HttpStatus.OK, Controller.LOG_IN_SUCCESS_MESSAGE, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<BasicResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        BasicResponse response = new BasicResponse(HttpStatus.OK, Controller.SIGN_UP_SUCCESS_MESSAGE);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/me")
    public ResponseEntity<MeResponse> me(@RequestHeader(value = Util.AUTHORIZATION) String token){
        UserInfo user = userService.me(token);
        MeResponse response = new MeResponse(HttpStatus.OK, Controller.LOG_IN_SUCCESS_MESSAGE, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<OtherResponse> otherUser(@PathVariable("nickname") String nickname) {
        System.out.println("nickname = " + nickname);
        UserInfo user = userService.otherUser(nickname);
        OtherResponse response = new OtherResponse(HttpStatus.OK, Controller.FIND_USER_SUCCESS_MESSAGE, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
