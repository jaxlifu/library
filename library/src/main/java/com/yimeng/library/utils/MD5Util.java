/**
 * @brief Package com.ijinshan.browser.utils
 * @author zuoyi
 * @version 1.0.0.0
 * @date 2013-04-23
 * @since 1.0.0.0
 */
package com.yimeng.library.utils;

/**
 * @file MD5Util.java
 * @brief about md5 utils
 * @author B3a4a
 * @version 1.0.0.0
 * @date 2014-08-27
 */

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String TAG = "Md5Util";
    private static final int OffsetBig = 256;
    private static final int OffsetSmall = 16;
    private static final int BeginIndex = 8;
    private static final int EndIndex = 24;
    protected static MessageDigest fileMessageDigest = null;
    protected static MessageDigest stringMessageDigest = null;

    static {
        try {
            stringMessageDigest = MessageDigest.getInstance("MD5");
            fileMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Util.class.getName()
                    + "failure");
            nsaex.printStackTrace();
        }
    }

    /**
     * generate the String's md5 value
     *
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        if (TextUtils.isEmpty(s))
            return getMD5String("".getBytes());
        else
            return getMD5String(s.getBytes());
    }

    /**
     * generate file md5 value
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static synchronized String getFileMD5String(File file) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            fileMessageDigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(fileMessageDigest.digest());
    }

    /**
     * 将字符串装换为MD5
     *
     * @param str 要转换的字符串
     * @return str的md5字符串
     */
    public static String strToMd5(String str) {
        String md5Str = null;
        if (str != null && str.length() != 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte[] b = md.digest();

                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0) {
                        i += OffsetBig;
                    }
                    if (i < OffsetSmall) {
                        buf.append("0");
                    }
                    buf.append(Integer.toHexString(i));
                }
                // 32位
                md5Str = buf.toString();
                // 16位
                //md5Str = buf.toString().substring(BeginIndex, EndIndex);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return md5Str;
    }

    public synchronized static String getMD5String(byte[] bytes) {
        stringMessageDigest.update(bytes);
        return bufferToHex(stringMessageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
