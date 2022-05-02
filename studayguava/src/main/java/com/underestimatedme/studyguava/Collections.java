package com.underestimatedme.studyguava;

import com.google.common.collect.Lists;
import com.underestimatedme.util.TestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author mj
 * @date Collections.java v1.0
 */
public class Collections {

    public static List<String> oldCreateList(String a, String b, String c){
        // m1: java入门的方法，无fa可说
//        List<String> resultList = new ArrayList<>();
//        resultList.add(a);
//        resultList.add(b);
//        resultList.add(c);
//        return resultList;
        // m2: 创建了一个list-Arrays$ArrayList,仅能读取，不支持add/remove操作(java.lang.UnsupportedOperationException)
//        return Arrays.asList(a,b,c);
        // m3: 包了一层ArrayList，可add/remove，但性能有降低，且很难看
        return new ArrayList<>(Arrays.asList(a,b,c));
    }

    public static List<String> createList(String a, String b, String c){
        // 创建一个array list
        return Lists.newArrayList(a,b,c);
    }

    public static List<List<String>> partition(List<String> sourceList, int size){
        // 将list按size分块，apache同样提供了ListUtils.partition方法
        return Lists.partition(sourceList, size);
    }

    public static List<Integer> oldTransform(List<String> sourceList){
        if(sourceList==null){
            return null;
        }
        // 原生转换
        return sourceList.stream().filter(Objects::nonNull).map(String::length).collect(Collectors.toList());
    }

    public static List<Integer> transform(List<String> sourceList){
        if(sourceList==null){
            return null;
        }
        // Lists转换，比起原生代码略短
        // 好像没有过滤方法
        return Lists.transform(sourceList, s->s==null?null:s.length());
    }


    public static void main(String[] args){
        String a="1",b="2",c="3";
        List<String> oldList = oldCreateList(a, b, c);
        TestUtil.assertIsTrue(TestUtil.equals(oldCreateList(a, b, c), createList(a,b,c)), "值不等");
        oldList.add("12");
        System.out.println(partition(oldList, 3));
        TestUtil.assertIsTrue(TestUtil.equals(oldTransform(oldList),transform(oldList)),"值不等");
        TestUtil.assertIsTrue(TestUtil.equals(oldTransform(null),transform(null)),"值不等");
        oldList.add(null);
        TestUtil.assertIsTrue(TestUtil.equals(oldTransform(oldList),transform(oldList)),"值不等");

    }
}
