package com.gdsc.forder.service;


import com.gdsc.forder.domain.User;
import com.gdsc.forder.domain.UserFill;
import com.gdsc.forder.dto.GetFillDTO;
import com.gdsc.forder.repository.UserFillRepository;
import com.gdsc.forder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OldService {

    private final UserRepository userRepository;
    private final UserFillRepository userFillRepository;


    public List<GetFillDTO> getFillInfo(Long userId){
        User user = userRepository.findById(userId).get();
        List<UserFill> userFills = userFillRepository.findByUser(user);

        List<GetFillDTO> result = new ArrayList<>();
        for(int i=0; i<userFills.size(); i++){
            GetFillDTO fillDTO = new GetFillDTO();
            fillDTO.setFillName(userFills.get(i).getFill().getFillName());
            fillDTO.setFillTime(userFills.get(i).getFill().getFillTime());
            fillDTO.setFillCheck(userFills.get(i).getFillCheck());
            result.add(fillDTO);
        }
        return result;
    }
}
