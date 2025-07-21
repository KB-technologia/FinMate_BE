package org.finmate.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.portfolio.domain.PortfolioVO;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.mapper.PortfolioMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Log4j2
@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioMapper portfolioMapper;

    @Override
    public void createPortfolio(PortfolioDTO portfolio) {
        checkRatio(portfolio);
        PortfolioVO vo = portfolio.toVO();
        portfolioMapper.insertPortfolio(vo);
    }


    @Override
    public PortfolioDTO getPortfolioByUserId(Long userId) {
        PortfolioVO vo = portfolioMapper.selectPortfolioByUserId(userId);
        if(vo== null){
            throw new NoSuchElementException("해당 사용자의 포트폴리오가 존재하지 않습니다.");
        }
        return PortfolioDTO.from(vo);
    }

    @Override
    public void updatePortfolio(PortfolioDTO portfolio) {
        checkRatio(portfolio);
        PortfolioVO vo = portfolio.toVO();
        portfolioMapper.updatePortfolio(vo);
    }

@   Override
    public void deletePortfolioByUserId(Long userId) {
        portfolioMapper.deletePortfolioByUserId(userId);
    }

    @Override
    public void checkRatio (PortfolioDTO portfolio) {
        double totalRatio = portfolio.getCashRatio() + portfolio.getBondRatio()
                + portfolio.getEquityRatio() + portfolio.getOtherRatio();
        if (Math.abs(totalRatio - 100.0) > 0.001) {
            throw new IllegalArgumentException("비율 총합이 100%가 아닙니다.");
        }
    }
}