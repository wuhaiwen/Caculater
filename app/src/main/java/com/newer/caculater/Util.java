package com.newer.caculater;

import android.widget.EditText;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhaiwen on 2015/11/29.
 */
public class Util {
    public static String Caculator(String string) {
        String text = string;
        String add_minus = null;
        String first;
        String minus;
        List<String> numList = new ArrayList<String>();
        boolean isMinus;
        if(text.length()>2) {
             first = text.substring(0, 2);
             minus = text.substring(0, 1);
             isMinus = minus.equals("-");
            if (isMinus) {
                numList.add(first);
                numList.add(text.substring(2, 3));
                text = text.substring(3, text.length());
            }
        }
        try {
            int splitIndex = 0;
            for(int i=0;i<text.length();i++){
                char c = text.charAt(i);
                if(c == '+'||c == '-'||c=='*'||c=='/'){
                    numList.add(text.substring(splitIndex, i));
                    numList.add(c+"");
                    splitIndex = i+1;
                }
            }
            numList.add(text.substring(splitIndex, text.length()));
            // 因为使用符号做判断，增加前一位和符号，所以最后一位数字不会在循环里处理

            // 先做乘除计算
            List<String> list = new ArrayList<String>();
            String temp = null; // 用于做乘除计算临时变量
            for(int i=1;i<numList.size();i+=2){ // 这里只循环运算符号
                if("+".equals(numList.get(i))||"-".equals(numList.get(i))){
                    if(null != temp){ // 存在临时变量，说明前面进行过乘除计算
                        list.add(temp.toString());
                        temp = null;
                        System.out.println(temp);
                    } else {
                        list.add(numList.get(i-1));
                    }
                    list.add(numList.get(i)); // 把符号加进去
                    if(i==numList.size()-2) { // 处理到最后时遇到直接处理
                        list.add(numList.get(i+1));
                    }
                }else if("*".equals(numList.get(i))){
                    if(null == temp){
                        //temp = Integer.parseInt(numList.get(i-1)) * Integer.parseInt(numList.get(i+1));
                        String str = new String();
                        String s1 = String.valueOf(numList.get(i-1));
                        String s2 = String.valueOf(numList.get(i+1));
                        BigDecimal b1 = new BigDecimal(s1);
                        BigDecimal b2 = new BigDecimal(s2);
                        b1 = b1.multiply(b2);
                        str = b1.toString();
                        temp = str;
                    }else{
                        //temp = temp * Integer.parseInt(numList.get(i+1));
                        String str = new String();
                        String s2 = String.valueOf(numList.get(i+1));
                        BigDecimal b1 = new BigDecimal(temp);
                        BigDecimal b2 = new BigDecimal(s2);
                        b1 = b1.multiply(b2);
                        str = b1.toString();
                        temp = str;
                    }
                    if(i==numList.size()-2) { // 处理到最后时遇到直接处理
                        list.add(temp.toString());
                        temp = null;
                    }
                }else if("/".equals(numList.get(i))){
                    if (null == temp) {
                        //temp = Integer.parseInt(numList.get(i-1)) / Integer.parseInt(numList.get(i+1));
                        String str = new String();
                        String s1 = String.valueOf(numList.get(i - 1));
                        String s2 = String.valueOf(numList.get(i + 1));
                        BigDecimal b1 = new BigDecimal(s1);
                        BigDecimal b2 = new BigDecimal(s2);
                        b1 = b1.divide(b2,20,BigDecimal.ROUND_HALF_UP);
                        str = b1.toString();
                        String s = str;
                        temp = str;
                    } else {
                        //temp = temp / Integer.parseInt(numList.get(i+1));
                        String str = new String();
                        String s2 = String.valueOf(numList.get(i + 1));
                        String s1 = temp;
                        BigDecimal b1 = new BigDecimal(s1);
                        BigDecimal b2 = new BigDecimal(s2);
                        // b1 = b1.divide(b2);
                        b1 = b1.divide(b2,20,BigDecimal.ROUND_HALF_UP);
                        str = b1.toString();
                        temp = str;
                    }
                    if (i == numList.size() - 2) { // 处理到最后时遇到直接处理
                        list.add(temp.toString());
                        temp = null;
                    }

                }
            }

            // 再做加减计算
            // Integer sum = Integer.parseInt(list.get(0)); // 第一位不会在循环里处理
            BigDecimal sum = new BigDecimal(String.valueOf(list.get(0)));
            if(list.size()<2){
                add_minus = sum.toString();
            }else{
                for(int i=1;i<list.size();i+=2){ // 这里只循环运算符号
                    if("+".equals(list.get(i))){
                        BigDecimal b1 = new BigDecimal(String.valueOf(list.get(i+1)));
                        System.out.println(b1);
                        sum = sum.add(b1);
                        add_minus = sum.toString();
                        // sum += Integer.parseInt(list.get(i+1));
                    }
                    if("-".equals(list.get(i))){
                        //sum -= Integer.parseInt(list.get(i+1));
                        BigDecimal b1 = new BigDecimal(String.valueOf(list.get(i+1)));
                        sum = sum.subtract(b1);
                        add_minus = sum.toString();
                    }else{
                        add_minus = sum.toString();
                    }
                }
            }
            return add_minus;
        }catch (Exception e){
            add_minus = "false";
        }
        return add_minus;
    }
}
