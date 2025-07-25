package org.finmate.portfolio.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.finmate.member.domain.CustomUser;
import org.finmate.portfolio.dto.PortfolioApiResponse;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/member/portfolio")
@RequiredArgsConstructor
@Log4j2
@Api(tags = "재무 포트폴리오 API")
public class PortfolioController {

    final private PortfolioService portfolioService;

    @ApiOperation(value = "재무 포트폴리오 등록", notes = "사용자의 재무 포트폴리오를 등록")
    @PostMapping
    public ResponseEntity<PortfolioApiResponse<Long>> create(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestBody PortfolioDTO dto) {
        Long userId = customUser.getUser().getId();
        dto.setUserId(userId);
        portfolioService.createPortfolio(dto);
        return ResponseEntity.ok(PortfolioApiResponse.<Long>builder()
                .message("재무 포트폴리오 등록 완료")
                .data(dto.getId())
                .build());
    }

    @ApiOperation(value = "재무 포트폴리오 조회", notes = "userId로 재무 포트폴리오를 조회")
    @GetMapping
    public ResponseEntity<PortfolioDTO> get(@AuthenticationPrincipal CustomUser customUser) {
        Long userId = customUser.getUser().getId();
        PortfolioDTO dto = portfolioService.getPortfolioByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "재무 포트폴리오 수정", notes = "재무 포트폴리오 수정")
    @PatchMapping
    public ResponseEntity<PortfolioApiResponse<Long>> update(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestBody PortfolioDTO dto)
    {
        Long userId = customUser.getUser().getId();
        dto.setUserId(userId);
        portfolioService.updatePortfolio(dto);
        return ResponseEntity.ok(
                PortfolioApiResponse.<Long>builder()
                        .message("재무 포트폴리오 수정 완료")
                        .data(dto.getId())
                        .build()
        );
    }

    @ApiOperation(value = "재무 포트폴리오 삭제", notes = "userId로 재무 포트폴리오를 삭제")
    @DeleteMapping
    public ResponseEntity<PortfolioApiResponse<Long>> delete(@AuthenticationPrincipal CustomUser customUser) {
        Long userId = customUser.getUser().getId();
        portfolioService.deletePortfolioByUserId(userId);
        return ResponseEntity.ok(
                PortfolioApiResponse.<Long>builder()
                        .message("포트폴리오 삭제 완료")
                        .data(userId)
                        .build()
        );
    }
}
