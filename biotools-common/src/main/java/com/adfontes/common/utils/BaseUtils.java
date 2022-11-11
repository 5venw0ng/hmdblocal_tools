package com.adfontes.common.utils;

import com.alibaba.fastjson.JSONReader;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author zhuwenchao
 * @date 2021-03-03 10:22
 */
public class BaseUtils {

    protected static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);
    /**
     * @author zhuwenchao
     * @date 2021-03-03
     * @param signature 签名
     * @param signatureParams 与签名对比校验的参数
     * @return
     */
    public static boolean checkSignature(String signature, List<String> signatureParams) {
        logger.info("signatureParams："+signatureParams.toString());
        logger.info("signature："+signature);
        //字典序排序
        Collections.sort(signatureParams);
        String content = StringUtils.join(signatureParams, "");
        logger.info("befncodeStr："+content);
        content = UriUtils.encode(content, StandardCharsets.UTF_8);
        // + 号有问题
        //content = URLEncoder.encode(content, StandardCharsets.UTF_8);
        logger.info("afterEncodeStr："+content);
        //md5加密
        String tempSignature = DigestUtils.md5DigestAsHex(content.getBytes()).toUpperCase();
        logger.info("tempSignature："+tempSignature);
        return signature.equals(tempSignature);
    }

    /**
     * @author zhuwenchao
     * @date 2021-03-05
     * @param xTimeMillis x时间戳
     * @param yTimeMillis y时间戳
     * @param effectiveSecond 验证x和y时间间隔不能超过多久
     * @return
     */

    public static boolean compareToDate(Long xTimeMillis, Long yTimeMillis, Long effectiveSecond){
        //转换为秒
        long xSecond = xTimeMillis / 1000;
        long ySecond = yTimeMillis / 1000;
        long diffSecond = xSecond - ySecond;
        long abs = Math.abs(diffSecond);
        //计算x TimeMillis 和 y TimeMillis 之间的差距是否在 有效时间内
        if (abs <= effectiveSecond) {
            return true;
        }
        return false;
    }

    /**
     * 检查开始时间和结束时间是否在有效期内
     * @return
     */
    public static boolean checkEffectiveDate(Date startDate, Date endDate){
        Date now = new Date();
        if (startDate == null || startDate.after(now)) {
            return false;
        }
        return (endDate == null || endDate.after(now));
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    /**
     *
     * @param date 需要添加结尾的字符串日期
     * @param tailFlag 0:开始时间00:00:00，1:结束时间 23:59:59
     */
    public static String processDateTail(String date, Integer tailFlag){
        if (tailFlag == null || tailFlag == 0) {
            date += " 00:00:00";
        } else {
            date += " 23:59:59";
        }
        return date;
    }

    public static String jsonFileReader(String path){
        ClassPathResource resource = new ClassPathResource(path);
        try (InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream())) {
            String json = new JSONReader(inputStreamReader).readString();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按指定长度分割字符串
     * @param inputString
     * @param length
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStr(inputString, length, size);
    }

    private static List<String> getStr(String inputString, int length, int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String str = substring(inputString, index * length, (index + 1) * length);
            list.add(str);
        }
        return list;

    }

    private static String substring(String str, int f, int t) {
        if (f > str.length()) {
            return null;
        }
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

}
