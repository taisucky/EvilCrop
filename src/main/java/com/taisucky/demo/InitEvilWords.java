package com.taisucky.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 */
public class InitEvilWords {
    //字符编码
    private String ENCODING = "utf-8";
    public HashMap sensitiveWordMap;

    public InitEvilWords() {
        super();
    }

    /**
     * 初始化
     */
    public Map initKeyWord() {
        try {
            //读取敏感词库
            Set<String> keyWordSet = readEvilWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型
     */
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());
        //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();
            //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);
                //转换成char型
                Object wordMap = nowMap.get(keyChar);
                //获取

                if (wordMap != null) {
                    //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                else {
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0");
                    //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                //最后一个
                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     */
    @SuppressWarnings("resource")
    private Set<String> readEvilWordFile() throws Exception {
        Set<String> set = null;
        File file1 = new File("EvilWords.txt");
        //读取文件
        InputStreamReader read = new InputStreamReader(new FileInputStream(file1), ENCODING);
        try {
            if (file1.isFile() && file1.exists()) {
                //文件流是否存在
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while ((txt = bufferedReader.readLine()) != null) {
                    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
                //不存在抛出异常信息
            } else {
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //关闭文件流
            read.close();
        }
        return set;
    }
}