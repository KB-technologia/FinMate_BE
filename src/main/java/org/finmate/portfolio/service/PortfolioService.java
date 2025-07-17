package org.finmate.portfolio.service;

import org.finmate.portfolio.domain.PortfolioVO;
import org.finmate.portfolio.dto.PortfolioDTO;

public interface PortfolioService {
    public void createPortfolio(PortfolioDTO vo);
    public PortfolioDTO getPortfolioByUserId(Long userId);
    public void updatePortfolio(PortfolioDTO portfolio);
    public void deletePortfolioByUserId(Long userId);
    public void checkRatio(PortfolioDTO vo);
}
