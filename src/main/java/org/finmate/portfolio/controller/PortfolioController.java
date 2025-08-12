package org.finmate.portfolio.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.CustomUser;
import org.finmate.portfolio.dto.PortfolioRequestDTO;
import org.finmate.portfolio.dto.PortfolioResponseDTO;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.service.PortfolioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
@Log4j2
@Api(tags = "재무 포트폴리오 API")
public class PortfolioController {

    final private PortfolioService portfolioService;

    @ApiOperation(value = "재무 포트폴리오 등록", notes = "사용자의 재무 포트폴리오를 등록")
    @ApiResponse(code = 200, message = "등록 성공")
    @PostMapping
    public ResponseEntity<PortfolioResponseDTO<Long>> create(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser,
            @RequestBody final PortfolioRequestDTO requestDTO
    ) {
        Long userId = customUser.getUser().getId();

        portfolioService.createPortfolio(userId, requestDTO);
        return ResponseEntity.ok(PortfolioResponseDTO.<Long>builder()
                .message("재무 포트폴리오 등록 완료")
                .data(userId)
                .build());
    }

    @ApiOperation(value = "재무 포트폴리오 조회", notes = "userId로 재무 포트폴리오를 조회")
    @ApiResponse(code = 200, message = "조회 성공")
    @GetMapping
    public ResponseEntity<PortfolioDTO> get(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser
    ) {
        Long userId = customUser.getUser().getId();
        PortfolioDTO dto = portfolioService.getPortfolioByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "재무 포트폴리오 수정", notes = "재무 포트폴리오 수정")
    @ApiResponse(code = 200, message = "수정 성공")
    @PatchMapping
    public ResponseEntity<PortfolioResponseDTO<Long>> update(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser,
            @RequestBody final PortfolioRequestDTO requestDTO)
    {
        Long userId = customUser.getUser().getId();
        portfolioService.updatePortfolio(userId, requestDTO);
        return ResponseEntity.ok(
                PortfolioResponseDTO.<Long>builder()
                        .message("재무 포트폴리오 수정 완료")
                        .data(userId)
                        .build()
        );
    }

    @ApiOperation(value = "재무 포트폴리오 삭제", notes = "userId로 재무 포트폴리오를 삭제")
    @ApiResponse(code = 200, message = "삭제 성공")
    @DeleteMapping
    public ResponseEntity<PortfolioResponseDTO<Long>> delete(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser
    ) {
        Long userId = customUser.getUser().getId();
        portfolioService.deletePortfolioByUserId(userId);
        return ResponseEntity.ok(
                PortfolioResponseDTO.<Long>builder()
                        .message("포트폴리오 삭제 완료")
                        .data(userId)
                        .build()
        );
    }

    @ApiOperation(value = "과거의 재무 포트폴리오 조회", notes = "userId로 과거 재무 포트폴리오를 조회")
    @ApiResponse(code = 200, message = "조회 성공")
    @GetMapping("/history")
    public ResponseEntity<PortfolioDTO> getHistory(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser,
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            final LocalDate date
    ) {
        Long userId = customUser.getUser().getId();;
        PortfolioDTO dto = portfolioService.getHistoryPortfolioByUserId(userId, date);
        return ResponseEntity.ok(dto);
    }
}
