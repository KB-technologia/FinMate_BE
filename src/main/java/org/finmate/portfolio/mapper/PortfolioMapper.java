package org.finmate.portfolio.mapper;

import org.finmate.portfolio.domain.PortfolioVO;

import java.time.LocalDate;


public interface PortfolioMapper {

    /**
     * userId에 해당하는 포트폴리오 조회
     * @param userId
     * @return
     */
    public PortfolioVO getPortfolio(Long userId);

    /**
     * 포트폴리오 생성
     * @param vo
     * @return
     */
    public int insertPortfolio(PortfolioVO vo);

    /**
     * 포트폴리오 삭제
     * @param userId
     * @return
     */
    public int deletePortfolio(Long userId);

    /**
     * 포트폴리오 업데이트
     * @param portfolio
     * @deprecated
     * @return
     */
    public int updatePortfolio(PortfolioVO portfolio);

    /**
     * 과거 포트폴리오 내역 조회
     * @param userId
     * @param date
     * @return
     */
    public PortfolioVO getHistoryPortfolio(Long userId, LocalDate date);

}
