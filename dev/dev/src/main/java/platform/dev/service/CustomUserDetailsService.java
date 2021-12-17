package platform.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import platform.dev.exception.User.UserNotExistException;
import platform.dev.model.CustomUserDetails;
import platform.dev.model.User;
import platform.dev.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotExistException::new);

        return new CustomUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getNickname(),
                user.getAddress(),
                true, true, true, true
        );
    }
}