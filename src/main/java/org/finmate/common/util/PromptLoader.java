package org.finmate.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public final class PromptLoader {
    private PromptLoader() {} // 생성자 막기

    public static String load(final String path) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        PromptLoader.class.getResourceAsStream(path),
                        StandardCharsets.UTF_8
                )
        )) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            //500 TODO: 예외처리 통일
            throw new RuntimeException("프롬프트 파일 읽기 실패: " + path, e);
        }
    }
}
