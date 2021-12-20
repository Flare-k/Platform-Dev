package platform.dev.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import platform.dev.exception.EmptyValueExistException;
import platform.dev.exception.User.*;
import platform.dev.model.CustomUserDetails;
import platform.dev.model.User;
import platform.dev.model.request.User.LoginRequest;
import platform.dev.model.request.User.SignUpRequest;
import platform.dev.model.response.User.UserInfo;
import platform.dev.repository.UserRepository;
import platform.dev.util.JwtUtil;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;

    public void signUp(SignUpRequest signUpRequest) {
        String email = Optional.ofNullable(signUpRequest.getEmail()).orElseThrow(EmptyValueExistException::new);
        String password = Optional.ofNullable(signUpRequest.getPassword()).orElseThrow(EmptyValueExistException::new);
        String confirmPassword = Optional.ofNullable(signUpRequest.getConfirmPassword()).orElseThrow(EmptyValueExistException::new);
        String name = Optional.ofNullable(signUpRequest.getName()).orElseThrow(EmptyValueExistException::new);
        String nickname = Optional.ofNullable(signUpRequest.getNickname()).orElseThrow(EmptyValueExistException::new);
        String address = Optional.ofNullable(signUpRequest.getAddress()).orElseThrow(EmptyValueExistException::new);
        
        boolean alreadyExistEmail = userRepository.existsByEmail(email);
        boolean alreadyExistNickname = userRepository.existsByNickname(nickname);

        if(alreadyExistEmail){
            throw new UserAlreadyExistException();
        }

        if(alreadyExistNickname){
            throw new NickNameAlreadyExistException();
        }

        if(!password.equals(confirmPassword)){
            throw new PasswordNotMatchException();
        }

        String encodedPassword = passwordEncoder.encode(password);

        User newUser = User.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(encodedPassword)
                .address(address).build();

        System.out.println("newUser = " + newUser);

        userRepository.save(newUser);
    }

    public String loginAndGenerateToken(LoginRequest loginRequest) throws Exception {
        String email = Optional.ofNullable(loginRequest.getEmail()).orElseThrow(EmptyValueExistException::new);
        String password = Optional.ofNullable(loginRequest.getPassword()).orElseThrow(EmptyValueExistException::new);

        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()){
            throw new UserNotExistException();
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    ));
        }catch(Exception e){
            throw new LoginFailException();
        }

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getEmail());

        String token = jwtUtil.generateToken(email);

        return token;
    }

    public UserInfo me(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getEmail();

        String parsedToken = token.substring(7);

        boolean isValidateToken = jwtUtil.validateToken(parsedToken, email);

        if (!isValidateToken) {
            throw new UserNotExistException();
        }

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new UserNotExistException();
        }

        UserInfo userInfo = UserInfo.builder()
                .userId(user.get().getUserId())
                .email(user.get().getEmail())
                .name(user.get().getName())
                .nickname(user.get().getNickname())
                .address(user.get().getAddress())
                .build();

        return userInfo;
    }

    public UserInfo otherUser(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);

        if (user.isEmpty()) {
            throw new UserNotExistException();
        }

        UserInfo userInfo = UserInfo.builder()
                .userId(user.get().getUserId())
                .email(user.get().getEmail())
                .name(user.get().getName())
                .nickname(user.get().getNickname())
                .address(user.get().getAddress())
                .build();

        return userInfo;
    }
}