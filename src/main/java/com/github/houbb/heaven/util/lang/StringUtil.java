/*
 * Copyright (c)  2019. houbinbin Inc.
 * heaven All rights reserved.
 */

package com.github.houbb.heaven.util.lang;

import com.github.houbb.heaven.util.util.CollectionUtil;

import java.util.List;

/**
 * 字符串工具类
 *
 * @author bbhou
 * @since 0.0.1
 */
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 空格
     */
    public static final String BLANK = " ";

    /**
     * 是否全部为大写
     * @param string 待检验字符
     * @return 是否为大写
     */
    public static boolean isUpperCase(final String string) {
        if(StringUtil.isEmpty(string)) {
            return false;
        }

        char[] characters = string.toCharArray();
        for(char c : characters) {
            if(!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部为小写
     * @param string 待检验字符
     * @return 是否为大写
     */
    public static boolean isLowerCase(final String string) {
        if(StringUtil.isEmpty(string)) {
            return false;
        }

        char[] characters = string.toCharArray();
        for(char c : characters) {
            if(!Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否包含大写字母
     * @param string 待检验字符
     * @return 是否为大写
     */
    public static boolean containsUppercase(final String string) {
        if(StringUtil.isEmpty(string)) {
            return false;
        }

        char[] characters = string.toCharArray();
        for(char c : characters) {
            if(Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含小写字母
     * @param string 待检验字符
     * @return 是否为大写
     */
    public static boolean containsLowercase(final String string) {
        if(StringUtil.isEmpty(string)) {
            return false;
        }

        char[] characters = string.toCharArray();
        for(char c : characters) {
            if(Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否为空
     *
     * @param string 字符串
     * @return {@code true} 为空
     */
    public static boolean isEmpty(final String string) {
        return null == string || EMPTY.equals(string);
    }

    /**
     * 是否为非空
     *
     * @param string 字符串
     * @return {@code true} 为非空
     */
    public static boolean isNotEmpty(final String string) {
        return !isEmpty(string);
    }

    /**
     * 是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 是否不为空
     *
     * @param str 字符串
     * @return 是否不为空
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }



    /**
     * 根据任意多的空格进行分割字符串。
     * 1. 入参为空,则返回空字符串数组
     *
     * @param string 字符串
     * @return 割字符串数组
     */
    public static String[] splitByAnyBlank(final String string) {
        if (StringUtil.isEmpty(string)) {
            return new String[0];
        }

        final String pattern = "\\s+";
        return string.split(pattern);
    }


    /**
     * 获取的驼峰写法。
     * 1.这是 mybatis-gen 源码
     *
     * @param inputString             输入字符串
     * @param firstCharacterUppercase 首字母是否大写。
     * @return 驼峰写法
     */
    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字母小写字符串
     */
    public static String firstToLowerCase(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字母大写结果
     */
    public static String firstToUpperCase(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 默认为 “”
     * 1. 如果为 null TO ""
     * 2. 返回本身
     *
     * @param string 字符串
     * @return 非 null 的字符串
     */
    public static String defaultEmpty(final String string) {
        if (isEmpty(string)) {
            return EMPTY;
        }
        return string;
    }


    /**
     * 将数组进行连接
     * from:    apache lang3
     *
     * @param array      object array
     * @param separator  分隔符
     * @param startIndex 开始下标
     * @param endIndex   结束下标
     * @return join string
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }

        if (separator == null) {
            separator = "";
        }

        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        } else {
            StringBuilder buf = new StringBuilder(noOfItems * 16);

            for (int i = startIndex; i < endIndex; ++i) {
                if (i > startIndex) {
                    buf.append(separator);
                }

                if (array[i] != null) {
                    buf.append(array[i]);
                }
            }

            return buf.toString();
        }
    }

    /**
     * 字符串拼接
     * @param stringList 字符串列表
     * @param splitter 分隔符
     * @return 结果
     * @since 0.1.1
     */
    public static String join(final List<String> stringList, final String splitter) {
        if(CollectionUtil.isEmpty(stringList)) {
            return StringUtil.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stringList.get(0));
        for(int i = 1; i < stringList.size(); i++) {
            stringBuilder.append(splitter).append(stringList.get(i));
        }
        return stringBuilder.toString();
    }

    /**
     * 驼峰命名转下划线
     *
     * @param camelStr 驼峰字符串
     * @return 下划线字符串
     */
    public static String camelToUnderline(String camelStr) {
        if (StringUtil.isEmpty(camelStr)) {
            return StringUtil.EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        char[] chars = camelStr.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                sb.append('_');
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 下划线转驼峰命名
     *
     * @param underlineStr 下划线字符串
     * @return 驼峰字符串
     */
    public static String underlineToCamel(String underlineStr) {
        if (StringUtil.isEmpty(underlineStr)) {
            return StringUtil.EMPTY;
        }

        int len = underlineStr.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = underlineStr.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(underlineStr.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 重复多少次
     * @param component 组成信息
     * @param times 重复次数
     * @return 重复多次的字符串结果
     */
    public static String repeat(final String component, final int times) {
        if(StringUtil.isEmpty(component)
                || times <= 0) {
            return StringUtil.EMPTY;
        }

        StringBuilder stringBuffer = new StringBuilder();
        for(int i = 0; i < times; i++) {
            stringBuffer.append(component);
        }

        return stringBuffer.toString();
    }

    /**
     * 构建新的字符串
     * @param original 原始对象
     * @param middle 中间隐藏信息
     * @param prefixLength 前边信息长度
     * @return 构建后的新字符串
     * @since 0.0.8
     */
    public static String buildString(final Object original,
                                     final String middle,
                                     final int prefixLength) {
        if(ObjectUtil.isNull(original)) {
            return null;
        }

        final String string = original.toString();
        final int stringLength = string.length();

        String prefix = "";
        String suffix = "";

        if(stringLength >= prefixLength) {
            prefix = string.substring(0, prefixLength);
        } else {
            prefix = string.substring(0, stringLength);
        }

        int suffixLength = stringLength - prefix.length() - middle.length();
        if(suffixLength > 0) {
            suffix = string.substring(stringLength -suffixLength);
        }

        return prefix + middle + suffix;
    }

    /**
     * 过滤掉空格
     *
     * @param original 原始字符串
     * @return 过滤后的字符串
     * @since 0.1.0
     */
    public static String trim(final String original) {
        if (StringUtil.isBlank(original)) {
            return original;
        }
        return original.trim();
    }

    /**
     * 如果字符串是<code>null</code>，则返回指定默认字符串，否则返回字符串本身。
     *
     * <pre>
     * nullToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * nullToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;&quot;
     * nullToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * nullToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     * @since 0.1.0
     */
    public static String nullToDefault(CharSequence str, String defaultStr) {
        return (str == null) ? defaultStr : str.toString();
    }

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串
     *
     * @param str        被填充的字符串
     * @param filledChar 填充的字符
     * @param len        填充长度
     * @param isPre      是否填充在前
     * @return 填充后的字符串
     * @since 0.1.0
     */
    public static String fill(String str, char filledChar, int len, boolean isPre) {
        final int strLen = str.length();
        if (strLen > len) {
            return str;
        }

        String filledStr = StringUtil.repeat(String.valueOf(filledChar), len - strLen);
        return isPre ? filledStr.concat(str) : str.concat(filledStr);
    }

    /**
     * 对象转换为字符串
     * @param object 对象
     * @param defaultWhenNull 对象为空时的默认值
     * @return 结果
     * @since 0.1.5
     */
    public static String objectToString(final Object object,
                             final String defaultWhenNull) {
        if(ObjectUtil.isNull(object)) {
            return defaultWhenNull;
        }
        return object.toString();
    }

    /**
     * 对象转换为字符串
     * 1. 默认为空时返回 null
     * @param object 对象
     * @return 结果
     * @since 0.1.5
     */
    public static String objectToString(final Object object) {
        return objectToString(object, null);
    }

    /**
     * 对 single 的信息重复多次
     * @param single 单个字符
     * @param times 重复次数
     * @return 结果
     * @since 0.1.9
     */
    public static String times(final String single,
                               final int times) {
        if(StringUtil.isEmpty(single)) {
            return single;
        }
        if(times <= 0) {
            return single;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < times; i++) {
            stringBuilder.append(single);
        }
        return stringBuilder.toString();
    }

    /**
     * 首字母大写
     * @param string 字符串
     * @return 大写的结果
     * @since 0.1.11
     */
    public static String capitalFirst(final String string) {
        if(StringUtil.isEmpty(string)) {
            return string;
        }

        if(string.length() <= 1) {
            return string.toUpperCase();
        }

        char capitalChar = Character.toUpperCase(string.charAt(0));
        return capitalChar + string.substring(1);
    }

}
