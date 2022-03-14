package com.progressoft.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import static java.math.RoundingMode.CEILING;
import static java.math.RoundingMode.HALF_EVEN;

public class FindData {

    public BigDecimal findMean(List<BigDecimal> list) {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (int i = 0; i < list.size(); i++) {
            sum = sum.add(list.get(i));
        }
        BigDecimal mean = sum.divide(BigDecimal.valueOf(list.size()),0,CEILING);
        return mean.setScale(2, HALF_EVEN);
    }


    public BigDecimal findStandardDeviation(List<BigDecimal> list) {
        BigDecimal standardDeviation = BigDecimal.valueOf(Math.sqrt(Double.parseDouble(String.valueOf(findVariance(list)))));
        return standardDeviation.setScale(2, HALF_EVEN);
    }


    public BigDecimal findVariance(List<BigDecimal> list) {
        BigDecimal sum = new BigDecimal(0);
        BigDecimal variance;
        for (int i = 0; i < list.size(); i++) {
            BigDecimal num = list.get(i);
            num = num.subtract(findMean(list));
            num = num.pow(2);
            sum = sum.add(num);
        }

        variance = sum.divide(new BigDecimal(list.size()), RoundingMode.HALF_EVEN);
        variance = variance.setScale(0, RoundingMode.HALF_EVEN);
        variance = variance.setScale(2, RoundingMode.HALF_EVEN);
        return variance;


    }

    public BigDecimal findMedian(List<BigDecimal> list) {
        Collections.sort(list);
        return list.get(list.size()/2);
    }

    public BigDecimal findMinValue(List<BigDecimal> list) {
        Collections.sort(list);
        BigDecimal min = list.get(0);
        return min.setScale(2, HALF_EVEN);
    }

    public BigDecimal findMaxValue(List<BigDecimal> list) {
        Collections.sort(list);
        int lastIndex = list.size()-1;
        BigDecimal max = list.get(lastIndex);
        return max.setScale(2, HALF_EVEN);
    }

    public BigDecimal findMinMaxScaling(BigDecimal value, BigDecimal min, BigDecimal max) {
        return value.subtract(min).divide(max.subtract(min),2,HALF_EVEN);
    }

    public BigDecimal findZscore(BigDecimal mark, BigDecimal mean, BigDecimal standarDeviation) {
        return mark.subtract(mean).divide(standarDeviation,2,HALF_EVEN);
    }


}
