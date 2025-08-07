package org.finmate.portfolio.service;

import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.dto.PortfolioRequestDTO;

import java.time.LocalDate;

public interface PortfolioService {
    void createPortfolio(Long userId, PortfolioRequestDTO portfolioRequestDTO);

    PortfolioDTO getPortfolioByUserId(Long userId);

    void updatePortfolio(Long userId, PortfolioRequestDTO portfolioRequestDTO);

    void deletePortfolioByUserId(Long userId);

    PortfolioDTO getHistoryPortfolioByUserId(Long userId, LocalDate date);

    Double calTotalAssets(PortfolioDTO dto);
}
