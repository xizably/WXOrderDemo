package cn.mongode.wxorder.utils;

import java.util.Random;

/**
 * Key的生成工具
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 生成24位数组成的字符串
 */
public class KeyUtil {
    
    /**
     * 生成唯一的主键 - 格式：时间 + 随机数
     * @author: Mongo
     * @date: 2018/5/10
     * @description: 24位数组成的字符串
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
