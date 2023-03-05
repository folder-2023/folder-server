package com.gdsc.forder.service;


import com.gdsc.forder.domain.Calendar;
import com.gdsc.forder.domain.Fill;
import com.gdsc.forder.domain.User;
import com.gdsc.forder.domain.UserFill;
import com.gdsc.forder.dto.AddCalendarDTO;
import com.gdsc.forder.dto.GetCalendarDTO;
import com.gdsc.forder.dto.GetFillDTO;
import com.gdsc.forder.repository.CalendarRepository;
import com.gdsc.forder.repository.FillRepository;
import com.gdsc.forder.repository.UserFillRepository;
import com.gdsc.forder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OldService {

    private final UserRepository userRepository;
    private final FillRepository fillRepository;
    private final UserFillRepository userFillRepository;
    private final CalendarRepository calendarRepository;

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



    public void saveCalendar(Long userId, AddCalendarDTO addCalendarDTO){
        User user = userRepository.findById(userId).get();
        Calendar calendar = new Calendar();
        calendar.setUser(user);

        LocalDate date = LocalDate.parse(addCalendarDTO.getCalendarDate(), DateTimeFormatter.ISO_DATE);
        calendar.setCalendarDate(date);

        String calendarTime = addCalendarDTO.getCalendarTime();
        LocalTime localTimeCalendar= LocalTime.parse(calendarTime);

        calendar.setCalendarTime(localTimeCalendar);
        calendar.setContent(addCalendarDTO.getContent());
        calendarRepository.save(calendar);
    }

    public GetCalendarDTO getCalendar(Long userId){
        User user = userRepository.findById(userId).get();
        Calendar calendar = calendarRepository.findByUser(user);
        GetCalendarDTO result = new GetCalendarDTO();
        result.setCalendarId(calendar.getCalendarId());
        result.setCalendarDate(calendar.getCalendarDate());
        result.setContent(calendar.getContent());
        result.setUserId(calendar.getUser().getId());
        result.setCalendarTime(calendar.getCalendarTime().toString());
        return result;
    }
}
