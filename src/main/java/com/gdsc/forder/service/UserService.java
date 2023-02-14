package com.gdsc.forder.service;

import com.gdsc.forder.domain.Fill;
import com.gdsc.forder.domain.Role;
import com.gdsc.forder.domain.User;
import com.gdsc.forder.domain.UserFill;
import com.gdsc.forder.dto.*;
import com.gdsc.forder.provider.JwtTokenProvider;
import com.gdsc.forder.repository.FillRepository;
import com.gdsc.forder.repository.UserFillRepository;
import com.gdsc.forder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FillRepository fillRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserFillRepository userFillRepository;

    public UserDTO singUp(JoinUserDTO joinUserDTO) throws Exception {
        if (userRepository.findByLoginId(joinUserDTO.getLoginId()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }
        if (!joinUserDTO.getPassword().equals(joinUserDTO.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }


        User user = userRepository.save(joinUserDTO.toEntity());
        if(user.getGuard()){
            user.setRole(Role.ROLE_GUARD);
        }
        else{
            user.setRole(Role.ROLE_OLD);
        }
        user.encodePassword(passwordEncoder);

        return UserDTO.fromEntity(user);
    }

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

    public void addFill(AddFillDTO addFillDTO, Long userId) {

        List<Fill> fillList = new ArrayList<>();

        List<String> fillNames = Arrays.asList(addFillDTO.getFills().get(0).split("\\|"));
        List<LocalTime> fillTimes = StringToTimeConverter(addFillDTO.getFillTimes());

        for(int i=0; i<fillNames.size(); i++){
            Fill fill = new Fill();
            fill.setFillName(fillNames.get(i));
            fill.setFillTime(fillTimes.get(i));
            fillList.add(fill);
        }

        User user = userRepository.findById(userId).get();

        for(int i=0; i<fillList.size(); i++){
            Fill existedFill = fillRepository.findByOption(fillList.get(i).getFillName(), fillList.get(i).getFillTime());
            UserFill userFill = new UserFill();
            userFill.setUser(user);
            if(existedFill != null){
                userFill.setFill(existedFill);
                userFillRepository.save(userFill);
            }
            else {
                fillRepository.save(fillList.get(i));
                userFill.setFill(fillList.get(i));
                userFillRepository.save(userFill);
            }
        }
    }

    public List<LocalTime> StringToTimeConverter(List<String> source) {
        String[] fillTimes = source.get(0).split("\\|");
        List<LocalTime> result = new ArrayList<>();
        for (String s : fillTimes) {
            LocalTime localTime = LocalTime.parse(s);
            result.add(localTime);
        }
        return result;
    }
}
