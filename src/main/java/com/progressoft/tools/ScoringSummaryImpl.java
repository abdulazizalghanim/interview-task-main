package com.progressoft.tools;

import lombok.Data;


import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class ScoringSummaryImpl implements ScoringSummary{
    private BigDecimal mean, standardDeviation, variance, median, min, max;


    @Override
    public BigDecimal mean() {
        return mean.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal standardDeviation() {
        return standardDeviation.setScale(2,RoundingMode.HALF_EVEN);
    }


    @Override
    public BigDecimal variance() {
        return variance.setScale(2,RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal median() {
        return median.setScale(2,RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal min() {
        return min.setScale(2,RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal max() {
        return max.setScale(2,RoundingMode.HALF_EVEN);
    }
}
