package com.ravenxrz.copyactiondaemon.clipboard;

public class RedundantLineBreakProcessor {
    public static String format(String text) {
        String[] splitText = text.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String tempStr : splitText) {
            // 有些空行
            if (tempStr == null || tempStr.equals("")) continue;
            char lastChar = tempStr.charAt(tempStr.length() - 1);
            if (lastChar == '.' || lastChar == '。') {  // 最后字符是. 也就是说.后又是换行，大概率是分段
                sb.append(tempStr).append("\n");
            } else if (tempStr.charAt(tempStr.length() - 1) == '-') {  // 英文末尾的连接符
                sb.append(tempStr, 0, tempStr.length() - 1);
            } else {
                if (ChineseOrEnglish.isChinese(tempStr)) {
                    // 中文不需要多余空格
                    sb.append(tempStr);
                } else {
                    // 英文需要多余空格
                    sb.append(tempStr).append(" ");
                }
            }
        }
        return sb.toString();
    }
}
