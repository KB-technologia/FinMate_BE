package org.finmate.portfolio.mapper;

import org.apache.ibatis.annotations.Param;
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
     * @return
     */
    public int updatePortfolio(PortfolioVO portfolio);

    /**
     * 과거 포트폴리오 이력 조회
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    public PortfolioVO getHistoryPortfolio(@Param("userId")Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}

