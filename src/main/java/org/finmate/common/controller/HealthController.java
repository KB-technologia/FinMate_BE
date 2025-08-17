package org.finmate.common.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.finmate.quiz.dto.QuizDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@Api(tags = "헬스체크 API")
public class HealthController {

    private final DataSource dataSource;

    // 준비/의존성까지 확인 (DB)
    @GetMapping("/healthz")
    @ApiOperation(value = "데이터베이스 포함 헬스 체크", notes = "데이터 베이스 연결 및 동작 확인 후 HttpStatus 200 반환")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = String.class)
    public ResponseEntity<String> readiness() {
        return checkDb() ? ResponseEntity.ok("ok")
                : ResponseEntity.status(503).body("db error");
    }

    @GetMapping("/healthz/liveness")
    @ApiOperation(value = "단순 헬스체크", notes = "서버 상태를 확인하는 API 단순 HttpStatus 200 반환")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = String.class)
    public String liveness() { return "ok"; }

    private boolean checkDb() {
        try (Connection c = dataSource.getConnection()) {
            c.setReadOnly(true);
            if (!c.isValid(3)) return false;     // JDBC 드라이버가 지원하면 3초 타임아웃
            String sql = sqlFor(c);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setQueryTimeout(3);              // 3초 타임아웃
                try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
            }
        } catch (Exception e) {
            return false;
        }
    }

    private String sqlFor(Connection c) throws SQLException {
        String p = c.getMetaData().getDatabaseProductName();
        if (p.contains("Oracle")) return "SELECT 1 FROM DUAL";
        // Postgres / MySQL / MariaDB / MSSQL / H2 등
        return "SELECT 1";
    }
}
