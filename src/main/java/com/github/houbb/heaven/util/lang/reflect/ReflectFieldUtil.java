/*
 * Copyright (c)  2019. houbinbin Inc.
 * heaven All rights reserved.
 */

package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 反射字段工具类
 *
 * @since 0.0.2
 * @author bbhou
 */
public final class ReflectFieldUtil {

    private ReflectFieldUtil() {}

    /**
     * 是否可以设置
     * @param sourceField 原始字段
     * @param targetField 目标字段
     * @return 结果
     */
    public static boolean isAssignable(final Field sourceField, final Field targetField) {
        // 如果有任何一个字段为空，直接返回
        if(ObjectUtil.isNull(sourceField)
                || ObjectUtil.isNull(targetField)) {
            return false;
        }

        // 如果 target 的字段为 final 则不进行设置
        if(Modifier.isFinal(targetField.getModifiers())) {
            return false;
        }

        final Class<?> sourceType = sourceField.getType();
        final Class<?> targetType = targetField.getType();
        if(sourceType.isAssignableFrom(targetType)) {
            return true;
        }

        // 基础类型的判断
        Class resolvedPrimitive;
        if (sourceType.isPrimitive()) {
            resolvedPrimitive = PrimitiveUtil.getPrimitiveType(targetType);
            return sourceType == resolvedPrimitive;
        } else {
            resolvedPrimitive = PrimitiveUtil.getPrimitiveType(targetType);
            return resolvedPrimitive != null && sourceType.isAssignableFrom(resolvedPrimitive);
        }

    }

    /**
     * 判断字段为字符串类型
     *
     * @param field 字段
     * @return {@code true} 是
     */
    public static Boolean isString(Field field) {
        return field.getType() == String.class;
    }

    /**
     * 判断字段是否不为字符串类型
     * @param field 字段
     * @return  {@code true} 是
     */
    public static Boolean isNotString(Field field) {
        return !isString(field);
    }


    /**
     * 判断字段field,声明了clazz注解
     *
     * @param field 字段
     * @param clazz 注解
     * @return 是否声明了
     */
    public static boolean isAnnotationPresent(Field field, Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }

    /**
     * 判断字段field,未声明clazz注解
     *
     * @param field 字段
     * @param clazz 注解
     * @return 是否未声明
     */
    public static boolean isNotAnnotationPresent(Field field, Class<? extends Annotation> clazz) {
        return !isAnnotationPresent(field, clazz);
    }

    /**
     * 获取字段的泛型参数类型
     * [java通过反射获取List中的泛型](https://blog.csdn.net/yy19900811/article/details/24239945?utm_source=blogxgwz4)
     * @param field 字段
     * @param paramIndex 泛型参数的下标
     * @return 泛型信息
     */
    public static Class getGenericParamType(final Field field, final int paramIndex) {
        if(ObjectUtil.isNull(field)) {
            return null;
        }

        field.setAccessible(true);
        Type genericType = field.getGenericType();
        if(ObjectUtil.isNull(genericType)) {
            return null;
        }

        // 如果是泛型参数的类型
        if(genericType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            //得到泛型里的class类型对象
            Type[] types = parameterizedType.getActualTypeArguments();
            if(ArrayUtil.isEmpty(types)) {
                return null;
            }

            return (Class<?>)types[paramIndex];
        }
        return null;
    }

    /**
     * 当前类包含指定的注解信息
     * @param clazz 类
     * @param annotationClass 注解类
     * @return 是否包含
     */
    public static boolean containsAnnotationField(final Class clazz,
                                                  final Class<? extends Annotation> annotationClass) {
        ArgUtil.notNull(clazz, "Clazz");
        ArgUtil.notNull(annotationClass, "Annotation class");

        List<Field> fieldList = ClassUtil.getAllFieldList(clazz);
        if(CollectionUtil.isEmpty(fieldList)) {
            return false;
        }

        for(Field field : fieldList) {
            if(field.isAnnotationPresent(annotationClass)) {
                return true;
            }
        }
        return false;
    }

}
