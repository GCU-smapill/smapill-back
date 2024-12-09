package gcu.smapill_back.config.auth;

import gcu.smapill_back.domain.User;
import gcu.smapill_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("loadUserByUsername 함수 실행");

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        return UserDetail.createUserDetail(user.get());
    }
}