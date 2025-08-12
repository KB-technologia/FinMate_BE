package org.finmate.common.controller;


import lombok.RequiredArgsConstructor;
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
public class HealthController {

    private final DataSource dataSource;

    // 준비/의존성까지 확인 (DB)
    @GetMapping("/healthz")
    public ResponseEntity<String> readiness() {
        return checkDb() ? ResponseEntity.ok("ok")
                : ResponseEntity.status(503).body("db error");
    }

    @GetMapping("/healthz/liveness")
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
