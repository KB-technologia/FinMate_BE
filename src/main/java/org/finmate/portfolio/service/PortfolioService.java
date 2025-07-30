package org.finmate.portfolio.service;

import org.finmate.adapter.mydata.dto.MyDataResponseDTO;
import org.finmate.portfolio.domain.InvestmentProfile;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.dto.PortfolioRequestDTO;

import java.time.LocalDate;

public interface PortfolioService {
    void createPortfolio(Long userId, PortfolioRequestDTO portfolioRequestDTO);
    PortfolioDTO getPortfolioByUserId(Long userId);
    void updatePortfolio(PortfolioDTO portfolio);
    void deletePortfolioByUserId(Long userId);

    PortfolioDTO getHistoryPortfolioByUserId(Long userId, LocalDate date);
}
