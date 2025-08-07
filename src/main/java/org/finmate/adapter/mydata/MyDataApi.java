package org.finmate.adapter.mydata;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.finmate.adapter.mydata.dto.MyDataResponseDTO;
import org.finmate.exception.NotFoundException;
import org.finmate.member.mapper.UserMapper;
import org.finmate.portfolio.domain.PortfolioVO;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.mapper.PortfolioMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyDataApi {

    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;
    private final PortfolioMapper portfolioMapper;

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    //Todo : cron = "0 0 1 * * *", zone = "Asia/Seoul"으로 바꾸기

    @Transactional
    public void fetchMyData() {
        List<Long> userList = userMapper.getUserIdAll();
        for(Long userId : userList) {
            PortfolioDTO newDto = getMyData(userId).toPortfolioDTO();
            newDto.setUserId(userId);

            PortfolioVO lastData = portfolioMapper.getPortfolio(userId);
            PortfolioDTO dto = (lastData != null) ? PortfolioDTO.from(lastData) : null;

            if (dto != null) {
                if (dto.getDeposit() != null) newDto.setDeposit(dto.getDeposit());
                if (dto.getSavings() != null) newDto.setSavings(dto.getSavings());
                if (dto.getBond() != null) newDto.setBond(dto.getBond());
                if (dto.getFund() != null) newDto.setFund(dto.getFund());
                if (dto.getStock() != null) newDto.setStock(dto.getStock());
                if (dto.getCash() != null) newDto.setCash(dto.getCash());
                if (dto.getOther() != null) newDto.setOther(dto.getOther());
            }

            if (newDto.getCash() == null) newDto.setCash(0.0);
            if (newDto.getOther() == null) newDto.setOther(0.0);

            newDto.setTotalAssets(newDto.getCash() + newDto.getOther() + newDto.getStock() + newDto.getFund() +
                    newDto.getBond() + newDto.getSavings() + newDto.getDeposit());
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
