package org.finmate.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private Long id;
    private String name; // 상품이름
    private String bankName; // 은행명 또는 판매 회사이름
    private ProductType productType; // 상품 타입 예금 or 적금 or 펀드
    private Integer riskLevel; // 위험도 지수 예적금 1, 펀드 2~7
    private Double expectedReturn; // 수익률 또는 대표금리
    private Long minAmount; // 최소 가입 금액 null가능
    private Long maxAmount; //최대 가입금액 null가능
    private Integer minTerm; //최소 기간 null가능
    private Integer maxTerm; //최대 기간 null가능
    private String url; // 상품 페이지 주소
    private String description; //상품 소개 요약
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer adventureScore;
    private String valueTag;
    private String speedTag;
    private String strategyTag;
    private Integer minFinanceScore;

    private DepositVO deposit;
    private SavingsVO savings;
    private FundVO fund;
    private ProductRateVO productRate;

    @Override
    public String toString() {
        String result = "상품명: " + name + "\n" +
                "은행명: " + bankName + "\n" +
                "상품 유형: " + productType + "\n" +
                "위험 등급: " + riskLevel + "\n" +
                "예상 수익률: " + expectedReturn + "%\n" +
                "가입 금액: " +
                (minAmount != null ? minAmount + "원 이상" : "") +
                (maxAmount != null ? " ~ " + maxAmount + "원 이하" : "") + "\n" +
                "가입 기간: " +
                (minTerm != null ? minTerm + "개월 이상" : "") +
                (maxTerm != null ? " ~ " + maxTerm + "개월 이하" : "") + "\n" +
                "상품 소개: " + description + "\n";
        if (deposit != null) {
            result += "[예금 상세 정보]\n";
            result += "기본 가입 기간: " + deposit.getDefaultTermMonths() + "개월\n";
            result += "자유 입출금 여부: " + (deposit.getIsFlexible() ? "가능" : "불가능") + "\n";
            result += "중도 해지 시 패널티: " + deposit.getEarlyWithdrawalPenalty() + "%\n";
            result += "이자 계산 방식: " + deposit.getInterestType() + "\n";
            result += "최대 우대 이율: " + deposit.getBonusRate() + "%\n";
            result += "복리 주기: " + deposit.getCompoundingPeriod() + "\n";
        }
        else if (savings != null) {
            result += "[적금 상세 정보]\n";
            result += "기본 가입 기간: " + savings.getDefaultTermMonths() + "개월\n";
            result += "자유 입출금 여부: " + (savings.getIsFlexible() ? "가능" : "불가능") + "\n";
            result += "납입 주기: " + savings.getPaymentCycle() + "\n";
            result += "월 최대 납입액: " + savings.getMaxMonthlyPayment() + "원\n";
            result += "중도 해지 시 패널티: " + savings.getEarlyWithdrawalPenalty() + "%\n";
            result += "이자 계산 방식: " + savings.getInterestType() + "\n";
            result += "최대 우대 이율: " + savings.getBonusRate() + "%\n";
            result += "복리 주기: " + savings.getCompoundingPeriod() + "\n";
        }
        else if (fund != null) {
            result += "[펀드 상세 정보]\n";
            result += "펀드 유형: " + fund.getFundType() + "\n";
            result += "운용사: " + fund.getManager() + "\n";
            result += "설정일: " + fund.getInceptionDate() + "\n";
            result += "초기 기준가: " + fund.getInitialNav() + "원\n";
            result += "현재 기준가: " + fund.getNav() + "원\n";
            result += "순자산(AUM): " + fund.getAum() + "원\n";
            result += "기준일: " + fund.getBaseDate() + "\n";
            result += "총 보수: " + fund.getExpenseRatio() + "%\n";
            result += "환매 기간: " + fund.getRedemptionPeriod() + "일\n";
            result += "리스크 등급: " + fund.getRiskGrade() + "\n";
        }

        if (productRate != null) {
            boolean isFund = fund != null;
            String label = isFund ? "수익률" : "금리";

            if (productRate.getReturnRate1m() != null)
                result += "1개월 " + label + ": " + String.format("%.2f",productRate.getReturnRate1m()) + "%\n";
            if (productRate.getReturnRate3m() != null)
                result += "3개월 " + label + ": " + String.format("%.2f",productRate.getReturnRate3m()) + "%\n";
            if (productRate.getReturnRate6m() != null)
                result += "6개월 " + label + ": " + String.format("%.2f",productRate.getReturnRate6m()) + "%\n";
            if (productRate.getReturnRate12m() != null)
                result += "12개월 " + label + ": " + String.format("%.2f",productRate.getReturnRate12m()) + "%\n";
            if (productRate.getReturnRate24m() != null)
                result += "24개월 " + label + ": " + String.format("%.2f",productRate.getReturnRate24m()) + "%\n";
            if (productRate.getReturnRate36m() != null)
                result += "36개월 " + label + ": " + String.format("%.2f",productRate.getReturnRate36m()) + "%\n";
            if (productRate.getReturnRate60m() != null)
                result += "60개월 " + label + ": " + String.format("%.2f",productRate.getReturnRate60m()) + "%\n";
        }
        return result;
    }
}
