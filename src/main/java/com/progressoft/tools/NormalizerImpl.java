package com.progressoft.tools;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NormalizerImpl implements Normalizer{

    private final FindData findData;
    private final CSVEI csvei;

    public NormalizerImpl(FindData findData, CSVEI csvei) {
        this.findData = findData;
        this.csvei = csvei;
    }

    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {
        ScoringSummaryImpl object = new ScoringSummaryImpl();
        List<List<String>> dataResult = getObjects(csvPath, destPath, colToStandardize, object);
       toZscore(destPath, colToStandardize, object, dataResult);
        return object;
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {
        ScoringSummaryImpl object = new ScoringSummaryImpl();
        List<List<String>> dataResult = getObjects(csvPath, destPath, colToNormalize, object);
        toMinMaxScaling(destPath, colToNormalize, object, dataResult);
        return object;
    }

    private void toMinMaxScaling(Path destPath, String colToNormalize, ScoringSummaryImpl object, List<List<String>> result) {
        int numberIndex = result.get(0).indexOf(colToNormalize);
        for(int i = 0; i< result.size(); i++){
            List<String> temp=new ArrayList<>(result.get(i));
            if(i==0){
                temp.add(numberIndex+1, colToNormalize +"_mm");
            }else{
                BigDecimal value = new BigDecimal(temp.get(numberIndex));
                BigDecimal bigDecimal = findData.findMinMaxScaling(value, object.getMin(), object.getMax());
                temp.add(numberIndex+1,String.valueOf(bigDecimal));
            }
            csvei.exFile(temp, destPath);
        }
    }

    private void toZscore(Path destPath, String colToStandardize, ScoringSummaryImpl object, List<List<String>> result) {
        int numberIndex = result.get(0).indexOf(colToStandardize);

        for(int i = 0; i< result.size(); i++){
            List<String> temp=new ArrayList<>(result.get(i));
            if(i==0){
                temp.add(numberIndex+1, colToStandardize +"_z");
            }else{
                BigDecimal value = new BigDecimal(temp.get(numberIndex));
                BigDecimal bigDecimal = findData.findZscore(value, object.getMean(), object.standardDeviation());
                temp.add(numberIndex+1,String.valueOf(bigDecimal));
            }
            csvei.exFile(temp, destPath);
        }
    }

    private List<List<String>> getObjects(Path csvPath, Path destPath, String colToNormalize, ScoringSummaryImpl object) {
        testParameters(csvPath, destPath, colToNormalize);
        List<List<String>> result = csvei.imFile(csvPath);
        List<BigDecimal> array = getValueOfColumns(result, colToNormalize);
        object.setMean(findData.findMean(array));
        object.setStandardDeviation(findData.findStandardDeviation(array));
        object.setVariance(findData.findVariance(array));
        object.setMedian(findData.findMedian(array));
        object.setMin(findData.findMinValue(array));
        object.setMax(findData.findMaxValue(array));
        return result;
    }

    private void testParameters(Path csvPath, Path destPath, String colToStandardize) {
        if(csvPath ==null || destPath == null || colToStandardize ==null )
            throw new IllegalArgumentException("source file not found");
    }
    private List<BigDecimal> getValueOfColumns(List<List<String>> records , String colToStandardize ) {

        int numberIndex = records.get(0).indexOf(colToStandardize);
        if(!records.get(0).contains(colToStandardize)){
            throw new IllegalArgumentException("column "+colToStandardize+" not found");
        }
        List<String>result=new ArrayList<>();
        for (List<String> record : records) {
            result.add(record.get(numberIndex));
        }
        List<BigDecimal> arr = new ArrayList<>();
        result.remove(0);
        for(int i = 0;i<=result.size()-1;i++){
            arr.add(BigDecimal.valueOf(Double.parseDouble(result.get(i))));
        }
        return arr;
    }

}
