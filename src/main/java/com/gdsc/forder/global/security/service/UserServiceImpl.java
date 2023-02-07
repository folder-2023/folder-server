package com.gdsc.forder.global.security.service;

import com.gdsc.forder.global.security.domain.User;
import com.gdsc.forder.global.security.dto.JoinUserDTO;
import com.gdsc.forder.global.security.dto.LoginUserDTO;
import com.gdsc.forder.global.security.provider.JwtTokenProvider;
import com.gdsc.forder.global.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public Long singUp(JoinUserDTO joinUserDTO) throws Exception {
        if (userRepository.findByLoginId(joinUserDTO.getLoginId()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }
        if (!joinUserDTO.getPassword().equals(joinUserDTO.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        User user = userRepository.save(joinUserDTO.toEntity());
        user.encodePassword(passwordEncoder);

        user.getAuthorities();
        return user.getId();
    }

    @Override
    public String login(LoginUserDTO users) {
        User user = userRepository.findByLoginId(users.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));

        String password = users.getPassword();
        if (!user.checkPassword(passwordEncoder, password )) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(user.getRoles().toString());

        return jwtTokenProvider.createToken(user.getLoginId(), roles);
    }
}
