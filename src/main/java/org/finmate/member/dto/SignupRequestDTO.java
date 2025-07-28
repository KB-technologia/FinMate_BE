package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원가입 요청", description = "회원가입 시 필요한 사용자 정보 DTO")
public class SignupRequestDTO {

    @ApiModelProperty(value = "사용자 이름", example = "홍길동")
    private String name;

    @ApiModelProperty(value = "사용자 계정 ID", example = "hong123")
    private String accountId;

    @ApiModelProperty(value = "이메일 주소", example = "hong@example.com")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "1234abcd")
    private String password;

    @ApiModelProperty(value = "비밀번호 확인", example = "1234abcd")
    private String passwordConfirm;

    @ApiModelProperty(value = "생년월일", example = "2000-01-01")
    private String birth;

    @ApiModelProperty(value = "성별", example = "MALE, FEMALE")
    private String gender;

    @ApiModelProperty(value = "결혼 여부", example = "MARRIED, SINGLE")
    private String maritalStatus;

    @ApiModelProperty(value = "직업 보유 여부", example = "true, false")
    private Boolean hasJob;

    @ApiModelProperty(value = "대중교통 사용 여부", example = "true, false")
    private Boolean usesPublicTransport;

    @ApiModelProperty(value = "운동 여부", example = "true, false")
    private Boolean exercises;

    @ApiModelProperty(value = "기념일 챙기는 주기", example = "MONTHLY, YEARLY")
    private String anniversaryFrequency;

    @ApiModelProperty(value = "여행 자주 하는지 여부", example = "NEVER, SOMETIMES, OFTEN")
    private String travelsFrequently;

    @ApiModelProperty(value = "자녀 수", example = "2")
    private Integer numberOfChildren;

    @ApiModelProperty(value = "주거 형태", example = "OWNED, LEASED, RENTED")
    private String housingType;

    @ApiModelProperty(value = "중소기업 재직 여부", example = "true, false")
    private Boolean employedAtSme;

    @ApiModelProperty(value = "미소금융 대출 여부", example = "true, false")
    private Boolean usesMicroloan;
}
