package platform.dev.controller.api;

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
import platform.dev.model.response.user.UserInfo;
import platform.dev.service.UserService;


@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public ResponseEntity<TokenContainingResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        String token = userService.loginAndGenerateToken(loginRequest);

        TokenContainingResponse response = new TokenContainingResponse(HttpStatus.OK, Controller.LOG_IN_SUCCESS_MESSAGE, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<BasicResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        BasicResponse response = new BasicResponse(HttpStatus.OK, Controller.SIGN_UP_SUCCESS_MESSAGE);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/me")
    public ResponseEntity<MeResponse> me(@RequestHeader(value = Util.AUTHORIZATION) String token){
        UserInfo user = userService.me(token);
        MeResponse response = new MeResponse(HttpStatus.OK, Controller.LOG_IN_SUCCESS_MESSAGE, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
