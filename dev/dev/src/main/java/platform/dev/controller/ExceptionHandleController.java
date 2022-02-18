package platform.dev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dev.exception.user.UserNotExistException;

@RestController
@RequestMapping("/exception")
public class ExceptionHandleController {

    @GetMapping("/jwt")
    public void JwtException(){
        throw new UserNotExistException();
    }
}