package com.taisucky.demo;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词过滤
 * @author taisucky
 */
public class EvilWordCrop {
    private Map evilWordsMap = null;
    private static String replaceString = null;

    /**
     * 敏感词过滤
     */
    public void crop(String inputString){

        EvilWordCrop filter = new EvilWordCrop();
        // 获取敏感词
        Set<String> set = filter.getSensitiveWord(inputString);
        System.out.println("含敏感词的个数为：" + set.size() + "。包含：" + set);
        //  替换敏感字
        Iterator<String> iterator = set.iterator();
        String word = null;
        while (iterator.hasNext()) {
            word = iterator.next();
         //  将原字符串中的敏感关键词替换成带有replaceChar
            String[] words = inputString.split(" ");

            for (int i = 0; i < words.length; i++) {
                String currentWord = words[i];
                if(currentWord.contains(word)){
                    //初始 替换符号的长度
                    getReplaceCharsS("*", currentWord.length());
                    inputString = inputString.replaceAll(currentWord, replaceString);
                }
            }
        }
        System.out.println(inputString);
    }

    /**
     * 构造函数，初始化敏感词库
     */
    public EvilWordCrop() {
        evilWordsMap = new InitEvilWords().initKeyWord();
    }

    /**
     * 获取文字中的敏感词
     *
     */
    public Set<String> getSensitiveWord(String txt ) {
        Set<String> sensitiveWordList = new HashSet<String>();
        for (int i = 0; i < txt.length(); i++) {
            int length = CheckSensitiveWord(txt, i);
            //判断是否包含敏感字符
            if (length > 0) {
                //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;
                //减1的原因，是因为for会自增
            }
        }
        return sensitiveWordList;
    }


    /**
     * 获取替换字符串,无返回值
     */
    private static void getReplaceCharsS(String replaceChar, int length) {
        replaceString = "";
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        replaceString = resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：
     */
    @SuppressWarnings({"rawtypes"})
    public int CheckSensitiveWord(String txt, int beginIndex ) {
        boolean flag = false;
        //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;
        //匹配标识数默认为0
        char word = 0;
        Map nowMap = evilWordsMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
                //存在，则判断是否为最后一个
            if (nowMap != null) {
                //找到相应key，匹配标识+1
                matchFlag++;
                    //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;

                }
            } else {     //不存在，直接返回
                break;
            }
        }
            //长度必须大于等于1，为词
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }
}