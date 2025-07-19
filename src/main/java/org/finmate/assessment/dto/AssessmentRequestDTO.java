package org.finmate.assessment.dto;

import lombok.Data;

import java.util.List;

@Data

// 사용자로부터 테스트 선택지를 받기 위한 DTO
public class AssessmentRequestDTO {

    /**
     * userId는 추후 토큰 형식으로 리팩토링 필요
    **/

    private Long userId; // 사용자 ID
    private List<Integer> choices; // 사용자가 선택한 선택지들
}
