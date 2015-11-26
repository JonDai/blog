package com.jondai.blog.util;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class StringUtil {
    private static Log log = LogFactory.getLog(StringUtil.class);


    private static final char QUOTE_ENCODE[] = "&quot;".toCharArray();
    private static final char AMP_ENCODE[] = "&amp;".toCharArray();
    private static final char LT_ENCODE[] = "&lt;".toCharArray();
    private static final char GT_ENCODE[] = "&gt;".toCharArray();
    private static MessageDigest digest = null;
    private static final int fillchar = 61;
    private static final String cvt =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static Random randGen = new Random();
    private static char numbersAndLetters[] =
            "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".
            toCharArray();
    private static final char zeroArray[] =
            "0000000000000000000000000000000000000000000000000000000000000000".
            toCharArray();


    static private StringUtil instance;

    public StringUtil() {
    }

    //创建实例
    public static synchronized StringUtil getInstance() {
        if (instance == null) {
            instance = new StringUtil();
        }
        return instance;
    }

    /**
     * 分隔字符串 参数说明:source为要分隔的串，sign为分隔符
     * @param source
     * @param sign
     * @return
     */
    public Enumeration split(String source, String sign) {
        Vector ar = new Vector();
        int start = 0;
        String st = source;
        while (true) {
            int ipos = st.indexOf(sign);
            if (ipos < 0) {
                ar.addElement(st.substring(0));
                break;
            } else {
                ar.addElement(st.substring(0, ipos));
                st = st.substring(ipos + 1);
            }
        }
        return ar.elements();
    }

    /**
     * 判断字串desc是否存在于source，存在则返回true,不存在返回false
     * @param source
     * @param desc
     * @return
     */
    public static boolean inStr(String source, String desc) {
        if (source.indexOf(desc) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串内码转换--用于从数据库读取数据时
     * @param source
     * @param charset
     * @return
     */
    public static String decodeGB(String source, String charset) {
        try {
            return (new String(source.getBytes(charset), "GB2312"));
        } catch (Exception E) {
            return source;
        }
    }

    /**
     * 转换为GBK编码
     * @param source
     * @return
     */
    public static String decodeGB(String source) {
        try {
            return new String(source.getBytes("ISO8859-1"), "GBK");
        } catch (Exception E) {
            return source;
        }

    }

    /**
     * 字符串内码转换--用于写入数据库时
     * @param source
     * @param charset
     * @return
     */
    public static String encodeGB(String source, String charset) {
        try {
            return (new String(source.getBytes("GB2312"), charset));
        } catch (Exception e) {
            return source;
        }
    }

    public static String encodeGB(String source) {
        try {
            return (new String(source.getBytes("GBK"), "ISO8859-1"));
        } catch (Exception E) {
            return source;
        }
    }


    /**
     * 替换source中的str1为str2
     * @param source
     * @param str1
     * @param str2
     * @return
     */
    public static String replace(String source, char str1, String str2) {
        if (source == null) {
            return source;
        }
        String desc = "";
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == str1) {
                desc = desc + str2;
            } else {
                desc = desc + String.valueOf(source.charAt(i));
            }
        }
        return desc;
    }

    /**
     * 替换source中的str1为str2
     *
     * @param source
     * @param str1
     * @param str2
     * @return
     */
    public static String replace(String source, String str1, String str2) {
        if (source == null) {
            return source;
        }
        String desc = "";
        int i = 0;
        while (i < source.length()) {
            if (source.startsWith(str1, i)) {
                desc = desc + str2;
                i = i + str1.length();
            } else {
                desc = desc + String.valueOf(source.charAt(i));
                i++;
            }

        }
        return desc;
    }

    /**
     * 判断字符串是否为空
     *
     * @param s
     * @return 是否为空
     */
    public static boolean isNullStr(String s) {
        if (s == null || s.trim().length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将Object对象转变为String类型对象，对于null值返回空字符串.
     *
     * @param inObj 待处理的对象.
     */
    public static String killNull(Object inObj) {
        if (inObj == null) {
            return "";
        }
        return inObj.toString().trim();
    }

    /**
     * 将Object对象转变为String类型对象，对于null值返回缺省字符串.
     *
     * @param inObj 待处理的对象.
     */
    public static String killNull(Object inObj, String toStr) {
        if (inObj == null) {
            return toStr;
        }
        return inObj.toString().trim();
    }

    /**
     * 把字符串的编码从GB2312转换为ISO8859.
     *
     * @param gStr 输入的GB2312编码字符串.
     * @return 转换后的ISO8859编码字符串.
     */
    public static String gbToIso(String gStr) {
        try {
            if (gStr == null)
                return null;
            return (new String(gStr.getBytes("GB2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            return gStr;
        }
    }

    /**
     * 把字符串的编码从ISO8859转换为GB2312.
     *
     * @param iStr 输入的ISO8859编码字符串.
     * @return 转换后的GB2312编码字符串.
     */
    public static String isoToGb(String iStr) {
        try {
            if (iStr == null)
                return null;
            return (new String(iStr.getBytes("ISO8859-1"), "GB2312"));
        } catch (UnsupportedEncodingException e) {
            return iStr;
        }
    }

    /**
     * 此方法完成：对一个字符串，按某种分割符，分成字符串数组
     *
     * @param sourceString :源字符串
     * @param strInterval  :间隔
     */
    public static Vector stringToVecor(String sourceString, String strInterval) {
        Vector returnStr = new Vector();
        if (sourceString == "" || strInterval == "") {
            returnStr.addElement(sourceString);
            return returnStr;
        }
        int found_str = sourceString.indexOf(strInterval);
        while (found_str >= 0) {
            returnStr.addElement(sourceString.substring(0, found_str));
            sourceString = sourceString.substring(found_str + strInterval.length());
            found_str = sourceString.indexOf(strInterval);
        }
        if (found_str < 0)
            returnStr.addElement(sourceString);
        return returnStr;
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password  Password or other credentials to use in authenticating
     *                  this username
     * @param algorithm Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            log.error("Exception: " + e);

            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if (((int) encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when storing passwords
     * as cookies.
     * <p/>
     * This is weak encoding in that anyone can use the decodeString
     * routine to reverse the encoding.
     *
     * @param str
     * @return String
     */
    public static String encodeString(String str) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return new String(encoder.encodeBuffer(str.getBytes())).trim();
    }

    /**
     * Decode a string using Base64 encoding.
     *
     * @param str
     * @return String
     */
    public static String decodeString(String str) {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        try {
            return new String(dec.decodeBuffer(str));
        } catch (IOException io) {
            throw new RuntimeException(io.getMessage(), io.getCause());
        }
    }


    //////////////////////////////新导入的一些方法，还没有加注释文档


    public static final String replaceIgnoreCase(String line, String oldString, String newString) {
        if (line == null)
            return null;
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            return buf.toString();
        } else {
            return line;
        }
    }

    public static final String replaceIgnoreCase(String line, String oldString, String newString, int count[]) {
        if (line == null)
            return null;
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            int counter = 1;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else {
            return line;
        }
    }

    public static final String replace(String line, String oldString, String newString, int count[]) {
        if (line == null)
            return null;
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 1;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else {
            return line;
        }
    }

    public static final String stripTags(String in) {
        if (in == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>')
                if (ch == '<') {
                    if (i + 3 < len && input[i + 1] == 'b' && input[i + 2] == 'r' && input[i + 3] == '>') {
                        i += 3;
                    } else {
                        if (i > last)
                            out.append(input, last, i - last);
                        last = i + 1;
                    }
                } else if (ch == '>')
                    last = i + 1;
        }

        if (last == 0)
            return in;
        if (i > last)
            out.append(input, last, i - last);
        return out.toString();
    }

    public static final String escapeHTMLTags(String in) {
        if (in == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>')
                if (ch == '<') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else if (ch == '>') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(GT_ENCODE);
                }
        }

        if (last == 0)
            return in;
        if (i > last)
            out.append(input, last, i - last);
        return out.toString();
    }

    public static final synchronized String hash(String data) {
        if (digest == null)
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            }
        try {
            digest.update(data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
        }
        return encodeHex(digest.digest());
    }

    public static final String encodeHex(byte bytes[]) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }

        return buf.toString();
    }

    public static final byte[] decodeHex(String hex) {
        char chars[] = hex.toCharArray();
        byte bytes[] = new byte[chars.length / 2];
        int byteCount = 0;
        for (int i = 0; i < chars.length; i += 2) {
            int newByte = 0;
            newByte |= hexCharToByte(chars[i]);
            newByte <<= 4;
            newByte |= hexCharToByte(chars[i + 1]);
            bytes[byteCount] = (byte) newByte;
            byteCount++;
        }

        return bytes;
    }

    private static final byte hexCharToByte(char ch) {
        switch (ch) {
            case 48: // '0'
                return 0;

            case 49: // '1'
                return 1;

            case 50: // '2'
                return 2;

            case 51: // '3'
                return 3;

            case 52: // '4'
                return 4;

            case 53: // '5'
                return 5;

            case 54: // '6'
                return 6;

            case 55: // '7'
                return 7;

            case 56: // '8'
                return 8;

            case 57: // '9'
                return 9;

            case 97: // 'a'
                return 10;

            case 98: // 'b'
                return 11;

            case 99: // 'c'
                return 12;

            case 100: // 'd'
                return 13;

            case 101: // 'e'
                return 14;

            case 102: // 'f'
                return 15;

            case 58: // ':'
            case 59: // ';'
            case 60: // '<'
            case 61: // '='
            case 62: // '>'
            case 63: // '?'
            case 64: // '@'
            case 65: // 'A'
            case 66: // 'B'
            case 67: // 'C'
            case 68: // 'D'
            case 69: // 'E'
            case 70: // 'F'
            case 71: // 'G'
            case 72: // 'H'
            case 73: // 'I'
            case 74: // 'J'
            case 75: // 'K'
            case 76: // 'L'
            case 77: // 'M'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 83: // 'S'
            case 84: // 'T'
            case 85: // 'U'
            case 86: // 'V'
            case 87: // 'W'
            case 88: // 'X'
            case 89: // 'Y'
            case 90: // 'Z'
            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
            case 94: // '^'
            case 95: // '_'
            case 96: // '`'
            default:
                return 0;
        }
    }

    public static String encodeBase64(String data) {
        byte bytes[] = (byte[]) null;
        try {
            bytes = data.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
        }
        return encodeBase64(bytes);
    }

    public static String encodeBase64(byte data[]) {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
        for (int i = 0; i < len; i++) {
            int c = data[i] >> 2 & 0x3f;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            c = data[i] << 4 & 0x3f;
            if (++i < len)
                c |= data[i] >> 4 & 0xf;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            if (i < len) {
                c = data[i] << 2 & 0x3f;
                if (++i < len)
                    c |= data[i] >> 6 & 0x3;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                i++;
                ret.append('=');
            }
            if (i < len) {
                c = data[i] & 0x3f;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                ret.append('=');
            }
        }

        return ret.toString();
    }

    public static String decodeBase64(String data) {
        byte bytes[] = (byte[]) null;
        try {
            bytes = data.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
        }
        return decodeBase64(bytes);
    }

    public static String decodeBase64(byte data[]) {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len * 3) / 4);
        for (int i = 0; i < len; i++) {
            int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            i++;
            int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            c = c << 2 | c1 >> 4 & 0x3;
            ret.append((char) c);
            if (++i < len) {
                c = data[i];
                if (61 == c)
                    break;
                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c);
                c1 = c1 << 4 & 0xf0 | c >> 2 & 0xf;
                ret.append((char) c1);
            }
            if (++i >= len)
                continue;
            c1 = data[i];
            if (61 == c1)
                break;
            c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c1);
            c = c << 6 & 0xc0 | c1;
            ret.append((char) c);
        }

        return ret.toString();
    }

    public static final String[] toLowerCaseWordArray(String text) {
        if (text == null || text.length() == 0)
            return new String[0];
        ArrayList wordList = new ArrayList();
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(text);
        int start = 0;
        for (int end = boundary.next(); end != -1; end = boundary.next()) {
            String tmp = text.substring(start, end).trim();
            tmp = replace(tmp, "+", "");
            tmp = replace(tmp, "/", "");
            tmp = replace(tmp, "\\", "");
            tmp = replace(tmp, "#", "");
            tmp = replace(tmp, "*", "");
            tmp = replace(tmp, ")", "");
            tmp = replace(tmp, "(", "");
            tmp = replace(tmp, "&", "");
            if (tmp.length() > 0)
                wordList.add(tmp);
            start = end;
        }

        return (String[]) wordList.toArray(new String[wordList.size()]);
    }

    public static final String randomString(int length) {
        if (length < 1)
            return null;
        char randBuffer[] = new char[length];
        for (int i = 0; i < randBuffer.length; i++)
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];

        return new String(randBuffer);
    }

    public static final String chopAtWord(String string, int length) {
        if (string == null || string.length() == 0)
            return string;
        char charArray[] = string.toCharArray();
        int sLength = string.length();
        if (length < sLength)
            sLength = length;
        for (int i = 0; i < sLength - 1; i++) {
            if (charArray[i] == '\r' && charArray[i + 1] == '\n')
                return string.substring(0, i + 1);
            if (charArray[i] == '\n')
                return string.substring(0, i);
        }

        if (charArray[sLength - 1] == '\n')
            return string.substring(0, sLength - 1);
        if (string.length() <= length)
            return string;
        for (int i = length - 1; i > 0; i--)
            if (charArray[i] == ' ')
                return string.substring(0, i).trim();

        return string.substring(0, length);
    }

    public static String chopAtWordsAround(String input, String wordList[], int numChars) {
        if (input == null || "".equals(input.trim()) || wordList == null || wordList.length == 0 || numChars == 0)
            return null;
        String lc = input.toLowerCase();
        for (int i = 0; i < wordList.length; i++) {
            int pos = lc.indexOf(wordList[i]);
            if (pos > -1) {
                int beginIdx = pos - numChars;
                if (beginIdx < 0)
                    beginIdx = 0;
                int endIdx = pos + numChars;
                if (endIdx > input.length() - 1)
                    endIdx = input.length() - 1;
                char chars[];
                for (chars = input.toCharArray(); beginIdx > 0 && chars[beginIdx] != ' ' && chars[beginIdx] != '\n' && chars[beginIdx] != '\r'; beginIdx--) ;
                for (; endIdx < input.length() && chars[endIdx] != ' ' && chars[endIdx] != '\n' && chars[endIdx] != '\r'; endIdx++) ;
                return input.substring(beginIdx, endIdx);
            }
        }

        return input.substring(0, input.length() >= 200 ? 200 : input.length());
    }

    /**
     * 将输入的字符串按照每行显示 width个字符折行。
     * @param input
     * @param width
     * @param locale
     * @return
     */
    public static String wordWrap(String input, int width, Locale locale) {
        if (input == null)
            return "";
        if (width < 5)
            return input;
        if (width >= input.length())
            return input;
        StringBuffer buf = new StringBuffer(input);
        boolean endOfLine = false;
        int lineStart = 0;
        for (int i = 0; i < buf.length(); i++) {
            if (buf.charAt(i) == '\n') {
                lineStart = i + 1;
                endOfLine = true;
            }
            if (i > (lineStart + width) - 1)
                if (!endOfLine) {
                    int limit = i - lineStart - 1;
                    BreakIterator breaks = BreakIterator.getLineInstance(locale);
                    breaks.setText(buf.substring(lineStart, i));
                    int end = breaks.last();
                    if (end == limit + 1 && !Character.isWhitespace(buf.charAt(lineStart + end)))
                        end = breaks.preceding(end - 1);
                    if (end != -1 && end == limit + 1) {
                        buf.replace(lineStart + end, lineStart + end + 1, "\n");
                        lineStart += end;
                    } else if (end != -1 && end != 0) {
                        buf.insert(lineStart + end, '\n');
                        lineStart = lineStart + end + 1;
                    } else {
                        buf.insert(i, '\n');
                        lineStart = i + 1;
                    }
                } else {
                    buf.insert(i, '\n');
                    lineStart = i + 1;
                    endOfLine = false;
                }
        }

        return buf.toString();
    }

    public static final String highlightWords(String string, String words[], String startHighlight, String endHighlight) {
        if (string == null || words == null || startHighlight == null || endHighlight == null)
            return null;
        StringBuffer regexp = new StringBuffer();
        for (int x = 0; x < words.length; x++) {
            regexp.append(words[x]);
            if (x != words.length - 1)
                regexp.append("|");
        }

        regexp.insert(0, "s/\\b(");
        regexp.append(")\\b/");
        regexp.append(startHighlight);
        regexp.append("$1");
        regexp.append(endHighlight);
        regexp.append("/igm");
        return null;
    }

    public static final String escapeForXML(String string) {
        if (string == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = string.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>')
                if (ch == '<') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else if (ch == '&') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(AMP_ENCODE);
                } else if (ch == '"') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(QUOTE_ENCODE);
                } else if (ch != '\n' && ch != '\r' && ch != '\t' && ch < ' ') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                }
        }

        if (last == 0)
            return string;
        if (i > last)
            out.append(input, last, i - last);
        return out.toString();
    }

    public static final String unescapeFromXML(String string) {
        string = replace(string, "&lt;", "<");
        string = replace(string, "&gt;", ">");
        string = replace(string, "&quot;", "\"");
        return replace(string, "&amp;", "&");
    }

    public static final String zeroPadString(String string, int length) {
        if (string == null || string.length() > length) {
            return string;
        } else {
            StringBuffer buf = new StringBuffer(length);
            buf.append(zeroArray, 0, length - string.length()).append(string);
            return buf.toString();
        }
    }

    public static final String dateToMillis(Date date) {
        return zeroPadString(Long.toString(date.getTime()), 15);
    }

    /**
     * 根据文件的扩展名判断输出流的mime类型
     *
     * @deprecated
     */
    public static String getMimeType(String strExt) {
        strExt = strExt.toLowerCase();
        String strMimeType = "application/octet-stream"; //缺省配置为服务器不能判断的mime类型
        //application ypes
        if ("htm".equals(strExt) || "html".equals(strExt)) {
            strMimeType = "text/html";
        } else if ("txt".equals(strExt)) {
            strMimeType = "text/plain";
        } else if ("rtf ".equals(strExt)) {
            strMimeType = "application/rtf";
        } else if ("pdf".equals(strExt)) {
            strMimeType = "application/pdf";
        } else if ("doc".equals(strExt)) {
            strMimeType = "application/msword";
        } else if ("ppt".equals(strExt) || "ppz".equals(strExt) ||
                "pps".equals(strExt) || "pot".equals(strExt)) {
            strMimeType = "application/mspowerpoint ";
        }
        //Archive/Compressed Archives

        else if ("gtar".equals(strExt)) {
            strMimeType = "application/x-gtar";
        } else if ("tar".equals(strExt)) {
            strMimeType = "application/x-tar";
        } else if ("zip".equals(strExt)) {
            strMimeType = "application/zip";
        }

        //Image types
        else if ("gif".equals(strExt)) {
            strMimeType = "application/rtf";
        } else if ("png".equals(strExt)) {
            strMimeType = "application/rtf";
        } else if ("jpeg".equals(strExt) || "jpg".equals(strExt) ||
                "jpe".equals(strExt)) {
            strMimeType = "image/jpeg";
        } else if ("tiff".equals(strExt) || "tif".equals(strExt)) {
            strMimeType = "application/tiff";
        }
        return strMimeType;
    }

    /**
     * To show all the session attribute names.the result will be output to console.
     * 将HttpSession对象中的属性值显示到控制台上
     * @param session HttpSession
     */
    public static void showAllSessionNames(HttpSession session) {
        java.util.Enumeration attrs = session.getAttributeNames();
        log.debug("----------------sessions begin------------------------");
        while (attrs.hasMoreElements()) {
            String temp = attrs.nextElement().toString();
            log.debug("attribute:" + temp + "\t value:" + session.getAttribute(temp));
        }
        log.debug("-----------------sessions end-------------------------");
    }

    /**
     * 将HttpServletRequeset对象 中所有的 parameters属性的值显示到控制台
     * @param request HttpServletRequest
     * @ To show all the parameters attribute names.the result will be output to console.
     */
    public static void showAllParameters(HttpServletRequest request) {
        java.util.Enumeration names = request.getParameterNames();
        log.debug("----------------parameters  begin-----------------------");
        while (names.hasMoreElements()) {
            String temp = names.nextElement().toString();
            log.debug("param name:" + temp + "\t value:" +
                    decodeGB(request.getParameter(temp)));
        }
        log.debug("-----------------parameters end-------------------------");
    }

    /**
     * 将以指定的分隔符的字符串转换为List
     * @param str
     * @param split
     * @return
     */
    public static List stringToList(String str, String split) {
        List r = new ArrayList();
        if ((str != null) && (!str.trim().equals(""))) {
            str = leftTrim(str, split);
            int pre = 0;
            int index = str.indexOf(split, pre);
            while (index >= 0) {
                String freg = str.substring(pre, index);
                if (freg != null && !freg.trim().equals("")) {
                    r.add(freg);
                }
                pre = index + split.length();
                index = str.indexOf(split, pre);
            }
            String left = str.substring(pre);
            if (left != null && !left.trim().equals("")) {
                r.add(left);
            }
        }
        return r;
    }

    private static String trim(String s1, String s2) {
        s1 = leftTrim(s1, s2);
        return rightTrim(s1, s2);
    }

    private static String leftTrim(String s1, String s2) {
        while (s1.startsWith(s2)) {
            s1 = s1.substring(s2.length());
        }
        return s1;
    }

    private static String rightTrim(String s1, String s2) {
        while (s1.endsWith(s2)) {
            s1 = s1.substring(0, s1.length() - s2.length());
        }
        return s1;
    }
    /**
     * 将List 中的String对象连接成一个字符串，以指定的字符串分隔
     * @param list
     * @param link
     * @return
     */
    public static String listToString(List list, String link) {
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            sb.append(iterator.next()).append(link);
        }
        String rt = sb.toString();
        if (rt.length() > link.length()) {
            rt = rt.substring(0, rt.length() - link.length());
        }
        return rt;
    }

    /**
     * 对字符串表示的数字，按照指定的小数点位数四舍五入。
     *
     * @param numberStr
     * @param decimalIndex 小数点后第几位
     * @return
     */
    public static String round(String numberStr, int decimalIndex) {
        //判断是否为数字。

        //截取字符串
        String desStr = numberStr.substring(0, (numberStr.lastIndexOf(".") + 1) + (decimalIndex + 1));
        //四舍五入
        BigDecimal deSource = new BigDecimal(desStr);
        String newStr = String.valueOf(deSource.setScale(decimalIndex, BigDecimal.ROUND_HALF_UP).doubleValue());

        return new String(newStr);
    }
    /**
     * 将java.sql.Clob转成String ,静态方法
     * @param clob 字段
     * @return 内容字串，如果出现错误，返回null
     */
    public  static String clob2String(Clob clob)   
    {   
        if(clob == null) {   
            return null;   
        }   
        try  
        {   
            Reader inStreamDoc = clob.getCharacterStream();   
               
            char[] tempDoc = new char[(int) clob.length()];   
            inStreamDoc.read(tempDoc);   
            inStreamDoc.close();   
            return new String(tempDoc);   
        }   
        catch (IOException e)   
        {   
            e.printStackTrace();   
        }   
        catch (SQLException es)   
        {   
            es.printStackTrace();   
          }   
             
          return null;   
      }
    
    
    
    

	/**
	 * 对字符串null的判断及转化
	 * @param str
	 * @return String
	 */
	public final static String toNoNull(String str) {
		if (str == null || str.equals("") || str.equalsIgnoreCase("null")) {
			return "";
		}
		return str;
	}

	/**
	 * 删除字符串中的空格
	 * @param str
	 * @return String
	 */
	public final static String delBlank(String str) {
		if (str == null || str.equals(""))
			return null;
		//		int len = str.length();
		int tmp = 0;
		String frontStr = "";
		String lastStr = "";
		while ((tmp = str.indexOf(" ")) != -1) {
			frontStr = str.substring(0, tmp);
			lastStr = str.substring(tmp + 1);
			str = frontStr + lastStr;
		}
		return str;
	}

	/**用于将String转为int
	 * @param str
	 * @return int
	 */
	public static final int getStr2int(String str) {
		int str_int = 0;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0;
		}
		try {
			str_int = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : 数据格式转化出现错误 " + str);
		}
		return str_int;
	}

	public static final float getStr2float(String str) {
		float str_float = 0.0F;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0.0F;
		}
		try {
			str_float = Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : 数据格式转化float出现错误 " + str);
		}
		return str_float;
	}

	public static final double getStr2double(String str) {
		double str_double = 0.0D;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0.0D;
		}
		try {
			str_double = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : 数据格式转化double出现错误 " + str);
		}
		return str_double;
	}

	public static final boolean getStr2boolean(String str) {
		if (str == null) {
			return false;
		}
		if (str.equals("1")) {
			return true;
		}
		return false;

	}

	public static final long getStr2long(String str) {
		long str_long = 0;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0;
		}
		try {
			str_long = Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : 数据格式转化long出现错误 " + str);
		}
		return str_long;
	}

	public static final String trimLeft(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return "";
		}
		String tmpstr = str;
		while ((tmpstr.substring(0, 1)).equals(" ")) {
			tmpstr = tmpstr.substring(1);
			if (tmpstr == null || tmpstr.equals("")) {
				break;
			}
		}
		return tmpstr;
	}

	public static final String trimRight(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return "";
		}
		String tmpstr = str;
		while ((tmpstr.length() - 1) == (tmpstr.lastIndexOf(" "))) {
			tmpstr = tmpstr.substring(0, tmpstr.length() - 1);
			if (tmpstr == null || tmpstr.equals("")) {
				break;
			}
		}
		return tmpstr;
	}

	public static final String trim(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return "";
		}
		String tmpstr = str;
		tmpstr = trimLeft(tmpstr);
		tmpstr = trimRight(tmpstr);
		return tmpstr;
	}

	public static final String getBlankStr(int num) {
		String str = " ";
		for (int i = 0; i < num; i++) {
			str = str + "  ";
		}
		return str;
	}


}