package com.underestimatedme.studyguava;

import com.google.common.base.Splitter;
import com.underestimatedme.util.TestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author mj
 * @date Splitters.java v1.0
 */
public class Splitters {

    private static final String COMMA = ",";
    private static final String EMPTY = "";
    private static final String EQUALS = "=";

    public static List<String> oldSplit(String data){
        if(data == null){
            return null;
        }
        String[] array = data.split(COMMA);
        List<String> resultList = new ArrayList<>(array.length);
        for(String item: array){
            if(item != null && !EMPTY.equals(item.trim())){
                resultList.add(item.trim());
            }
        }
        return resultList;
    }

    public static List<String> split(String data){
        if(data == null){
            return null;
        }
        // 可以split 到Iterator/List/Stream
        // 分隔符可以支持正则表达式
        return Splitter.on(COMMA).omitEmptyStrings().trimResults().splitToList(data);
    }


    public static Map<String,String> oldSplit2Map(String data){
        if(data == null){
            return null;
        }
        String[] array = data.split(COMMA);
        Map<String,String> resultMap = new HashMap<>(array.length);
        for(String item: array){
            if(item != null && !EMPTY.equals(item.trim())){
                String[] innerArr = item.split(EQUALS);
                if(innerArr.length==2) {
                    resultMap.put(innerArr[0], innerArr[1]);
                }
            }
        }
        return resultMap;
    }

    public static Map<String,String> split2Map(String data){
        if(data == null){
            return null;
        }
        // 和joiner相同，可以split 到Map(Beta版本)
        return Splitter.on(COMMA).omitEmptyStrings().trimResults().withKeyValueSeparator(EQUALS).split(data);
    }

    public static void main(String[] args) {
        String data = "1,2 , 3, ";
        TestUtil.assertIsTrue(TestUtil.equals(split(data), oldSplit(data)), "值不等");
        TestUtil.assertIsTrue(TestUtil.equals(split(null), oldSplit(null)), "值不等");
        String data2 = "=4,a=1,b=2,c=3,e=5";
        TestUtil.assertIsTrue(TestUtil.equals(split2Map(data2), split2Map(data2)), "值不等");
        TestUtil.assertIsTrue(TestUtil.equals(split2Map(null), split2Map(null)), "值不等");
    }

}
