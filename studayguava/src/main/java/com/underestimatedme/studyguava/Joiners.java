package com.underestimatedme.studyguava;

import com.google.common.base.Joiner;
import com.underestimatedme.util.TestUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author mj
 * @date Joiners.java v1.0
 */
public class Joiners {

    private static final String COMMA = ",";
    private static final String EMPTY = "";
    private static final String EQUALS = "=";

    public static String oldJoiner(List<String> dataList) {
        // 传统的方法实现
        if (dataList == null) {
            return null;
        }
        StringBuilder accum = new StringBuilder();
        for (String item : dataList) {
            accum.append(item == null ? EMPTY : item).append(COMMA);
        }
        String joinResult = accum.toString();
        return joinResult.substring(0, joinResult.length() - 1);
    }

    public static String oldJoiner(Map<String,String> dataMap){
        if (dataMap == null){
            return null;
        }
        // 老的方式，转换代码和判断比较多
        StringBuilder accum = new StringBuilder();
        for (Map.Entry<String,String> item : dataMap.entrySet()) {
            accum.append(item.getKey() == null ? EMPTY : item.getKey()).append(EQUALS).append(item.getValue()).append(COMMA);
        }
        String joinResult = accum.toString();
        return joinResult.substring(0, joinResult.length() - 1);
    }

    public static String joiner(List<String> dataList) {
        if (dataList == null) {
            return null;
        }
        // 该方法使用类似于python中的",".join(list)
        return Joiner.on(COMMA).useForNull(EMPTY).join(dataList);
    }

    public static String appendTo(List<String> dataList){
        return Joiner.on(COMMA).useForNull(EMPTY).appendTo(new StringBuilder(), dataList).toString();
    }

    public static String joiner(Map<String,String> dataMap){
        if (dataMap == null){
            return null;
        }
        // 转化map值
        return Joiner.on(COMMA).useForNull(EMPTY).withKeyValueSeparator(EQUALS).join(dataMap);
    }


    public static void main(String[] args) {
        // 要求:将list转化为使用逗号分隔的字符串
        List<String> dataList = Arrays.asList("1", "2", "3", null);
        System.out.println(appendTo(dataList));

        TestUtil.assertIsTrue(TestUtil.equals(joiner(dataList), oldJoiner(dataList)), "值不等");
        dataList = null;
        TestUtil.assertIsTrue(TestUtil.equals(joiner(dataList), oldJoiner(dataList)), "值不等");

        Map<String,String> dataMap = new HashMap<>(5);
        dataMap.put("a","1");
        dataMap.put("b","2");
        dataMap.put("c","3");
        dataMap.put(null,"4");
        dataMap.put("e","5");
        TestUtil.assertIsTrue(TestUtil.equals(joiner(dataMap), oldJoiner(dataMap)), "值不等");
        dataMap = null;
        TestUtil.assertIsTrue(TestUtil.equals(joiner(dataMap), oldJoiner(dataMap)), "值不等");
    }

}
