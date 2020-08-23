package com.smallchili.blog.utils;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * 2020/8/23
 * 自己写的复制属性的类
 */
public class CopyUtil {

    /**
     * @param source 源对象
     * @param target 目标对象
     * @return
     * @throws Exception
     *
     * <p>用来复制对象属性 </p>
     * source对象不为null的属性会覆盖target对象的值，
     * 如果source对象为null的属性不会覆盖target里的对应属性值
     */
    public static void notNullCopy(Object source, Object target) throws IllegalArgumentException {
        //参数校验
        if (Objects.isNull(source)) {
            throw new IllegalArgumentException("Source must not be null");
        }
        if (Objects.isNull(target)) {
            throw new IllegalArgumentException("Target must not be null");
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        //获取source类的所有声明的属性全部
        Field[] sourceFields = sourceClass.getDeclaredFields();
        for (Field sField : sourceFields) {
            Field tgField = null;
            try {
                //如果target类没有该属性会抛一个异常，这里要进行捕获
                tgField = targetClass.getDeclaredField(sField.getName());
            } catch (NoSuchFieldException e) {

            }
            //如果存在
            if (tgField != null) {
                //如果该属性是私有的，允许反射访问
                if (!Modifier.isPublic(tgField.getModifiers())) {
                    tgField.setAccessible(true);
                }
                if (!Modifier.isPublic(sField.getModifiers())) {
                    sField.setAccessible(true);
                }
                //如果属性类型一样
                if (tgField.getType().equals(sField.getType())) {
                    try {
                         Object value = sField.get(source);
                        //如果source对象该属性不为空
                         if (value != null) {
                            //给target对象对应属性赋值
                             tgField.set(target,value);
                        }
                    }catch (IllegalAccessException e){

                    }


                }
            }

        }

    }

    @Deprecated
    public static void notNullCopy2(Object source, Object target) throws Exception {
        //参数校验
        if (Objects.isNull(source)) {
            throw new IllegalArgumentException("Source must not be null");
        }
        if (Objects.isNull(target)) {
            throw new IllegalArgumentException("Target must not be null");
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        //获取source类的所有声明的属性全部
        Field[] sourceFields = sourceClass.getDeclaredFields();
        for (Field sField : sourceFields) {
            Field tgField = null;
            try {
                //如果target类没有该属性会抛一个异常，这里要进行捕获
                tgField = targetClass.getDeclaredField(sField.getName());
            } catch (NoSuchFieldException e) {

            }
            //如果存在
            if (tgField != null) {
                //如果该属性是私有的，允许反射访问
                if (!Modifier.isPublic(tgField.getModifiers())) {
                    tgField.setAccessible(true);
                }
                if (!Modifier.isPublic(sField.getModifiers())) {
                    sField.setAccessible(true);
                }
                //如果属性类型一样
                if (tgField.getType().equals(sField.getType())) {
                    //如果source对象该属性不为空
                    if (sField.get(source) != null) {
                        //给target对象对应属性赋值
                        tgField.set(target, sField.get(source));
                    }

                }
            }

        }

    }






}
