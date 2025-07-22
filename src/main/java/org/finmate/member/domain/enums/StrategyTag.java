package org.finmate.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StrategyTag {
    STRATEGY(0, 1), LUCK(2, 3);

    private final int code1;
    private final int code2;


    public static StrategyTag fromCode(int code) {
        for (StrategyTag tag : StrategyTag.values()) {
            if (tag.code1 == code || tag.code2 == code) {
                return tag;
            }
        }
        throw new IllegalArgumentException(
                "No matching StrategyTag for code: " + code
        );
    }
}
