package com.gdsc.forder.service;


import com.gdsc.forder.domain.Fill;
import com.gdsc.forder.domain.User;
import com.gdsc.forder.domain.UserFill;
import com.gdsc.forder.dto.GetFillDTO;
import com.gdsc.forder.repository.FillRepository;
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
    private final FillRepository fillRepository;
    private final UserFillRepository userFillRepository;

    public List<GetFillDTO> getFillInfo(Long userId){
        User user = userRepository.findById(userId).get();
        List<UserFill> userFills = userFillRepository.findByUser(user);

        List<GetFillDTO> result = new ArrayList<>();
        for(int i=0; i<userFills.size(); i++){
            GetFillDTO fillDTO = new GetFillDTO();
            fillDTO.setFillId(userFills.get(i).getFill().getId());
            fillDTO.setFillName(userFills.get(i).getFill().getFillName());
            fillDTO.setFillTime(userFills.get(i).getFill().getFillTime());
            fillDTO.setFillCheck(userFills.get(i).getFillCheck());
            result.add(fillDTO);
        }
        return result;
    }

    public void checkFill(long userId, long fillId, Boolean accept){
        User user = userRepository.findById(userId).get();
        UserFill userFill = userFillRepository.findByOption(user, fillId);
        userFill.setFillCheck(accept);
        userFillRepository.save(userFill);
    }

    public GetFillDTO getFillOne(long userId, long fillId){
        User user = userRepository.findById(userId).get();
        Fill fill = fillRepository.findById(fillId).get();
        UserFill userFill = userFillRepository.findByOption(user, fillId);
        GetFillDTO getFillDTO = new GetFillDTO();
        getFillDTO.setFillId(fillId);
        getFillDTO.setFillTime(fill.getFillTime());
        getFillDTO.setFillCheck(userFill.getFillCheck());
        getFillDTO.setFillName(fill.getFillName());
        return getFillDTO;
    }

    public void resetFillCheck() {
        List<UserFill> userFills = userFillRepository.findAll();
        for(int i=0; i<userFills.size(); i++){
            userFills.get(i).setFillCheck(false);
            userFillRepository.save(userFills.get(i));
        }
    }
}
