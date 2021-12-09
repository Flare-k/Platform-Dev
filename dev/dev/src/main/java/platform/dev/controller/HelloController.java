package platform.dev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/hello/{key}")
    public String HelloKey(@PathVariable("key") String key){
        return "Hello" + key;
    }
}
