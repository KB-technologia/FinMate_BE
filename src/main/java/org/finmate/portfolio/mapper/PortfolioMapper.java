package org.finmate.portfolio.mapper;

import org.finmate.portfolio.domain.PortfolioVO;


public interface PortfolioMapper {

    public PortfolioVO selectPortfolioByUserId(Long userId);

    public int insertPortfolio(PortfolioVO vo);

    public int deletePortfolioByUserId(Long userId);

    public int updatePortfolio(PortfolioVO portfolio);

}
