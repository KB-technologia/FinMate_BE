package org.finmate.adapter.mydata;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.finmate.adapter.mydata.dto.MyDataProductDTO;
import org.finmate.adapter.mydata.dto.MyDataResponseDTO;
import org.finmate.exception.NotFoundException;
import org.finmate.member.domain.UserVO;
import org.finmate.member.dto.KakaoUserResponse;
import org.finmate.member.mapper.UserMapper;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.mapper.PortfolioMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyDataApi {

    private final ObjectMapper objectMapper;

    private final UserMapper userMapper;
    private final PortfolioMapper portfolioMapper;

    @Scheduled(cron = "0 0 9 * * *")
    //Todo : (cron = "0 0 1 * * *")
    @Transactional
    public void fetchMyData() {
        List<Long> userList = userMapper.getUserIdAll();
        for(Long userId : userList) {
            PortfolioDTO dto = PortfolioDTO.from(portfolioMapper.getPortfolio(userId));

            if (dto == null) continue;

            PortfolioDTO newDto = getMyData(userId).toPortfolioDTO();

            if(newDto == null) continue;

            newDto.setUserId(userId);
            newDto.setCash(dto.getCash() != null ? dto.getCash() : 0.0);
            newDto.setOther(dto.getOther() != null ? dto.getOther() :0.0);

            newDto.update();
            portfolioMapper.insertPortfolio(newDto.toVO());
        }
    }

    @Profile(value = "dev")
    public MyDataResponseDTO getMyData(Long userId) {
        //TODO: api를 사용한다고 치면 userId사용
        try (InputStream is = getClass().getResourceAsStream("/dummy/mydata/user_financial_products.json")) {
            MyDataResponseDTO dto = objectMapper.readValue(is, MyDataResponseDTO.class);
            return dto;
        } catch (IOException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
