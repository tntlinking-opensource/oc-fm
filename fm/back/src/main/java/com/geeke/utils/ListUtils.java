package com.geeke.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author goblin
 * @Date 2020-05-24
 * @Version 1.0.0
 * @Description
 * @Company www.newtouch.cn
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 **/
public class ListUtils {
    /**
     * @param original 源数组
     * @param length 指定长度
     * @return java.util.List<java.util.List   <   java.lang.String>>
     * @date 2020-05-22
     * @Description 将数组按指定长度分割成子数组
     * @Author goblin
     */


    public static  List<List<String>> subListByLength(List<String> original, int length){
        int total =(original.size() -1 )/length + 1;

        List<List<String>> afterDealWith = new ArrayList<>();
        if( !original.isEmpty()){
            int start = 0;
            for(int i=1;i<=total;i++){
                if( i == total ){
                    List<String> list = new ArrayList<>(original.subList(start, original.size()));
                    afterDealWith.add(list);
                }else{
                    List<String> list = new ArrayList<>(original.subList(start, start+length));
                    afterDealWith.add(list);
                    start += length;

                }
            }
        }

        return afterDealWith;
    }

    public static void main(String[] args) {

    }
}
