package org.finmate.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValueTag {
    SURVIVAL(0), STABILITY(1), GROWTH(2), HIGH_RETURN(3);

    private final int code;


    public static ValueTag fromCode(int code) {
        for (ValueTag type : ValueTag.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid AssessmentType code: " + code);
    }
}
