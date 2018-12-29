/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: MD5.java
 * Author:   Andy_Wang01
 * Date:     Aug 27, 2014 5:55:26 PM
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.bjmu.order.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {

    /**
     * 使用ThreadLocal以空间换时间解决MD5线程安全问题
     */
    @SuppressWarnings("rawtypes")
    private static ThreadLocal threadLocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }
    };

    public static MessageDigest getMessageDigest() {
        return (MessageDigest) threadLocal.get();
    }

    public static String digest(String s, String charset) throws UnsupportedEncodingException {
        getMessageDigest().update(s.getBytes(charset));
        return HexUtil.bytes2Hexstr(getMessageDigest().digest());
    }
}
