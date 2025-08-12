package org.finmate.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpeedTag {
    FAST(0), MEDIUM(1), SLOW(2), VERY_SLOW(3);

    private final int code;


    public static SpeedTag fromCode(int code) {
        for (SpeedTag type : SpeedTag.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid AssessmentType code: " + code);
    }
}
