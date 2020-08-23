package com.smallchili.blog.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *  2020/8/22
 *  xmz
 *  对象转换工具类
 */
public class ConvertUtil {

    /**
     * 拷贝一个list
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList(List source, Class<T> targetClass) {
        List<T> target = new ArrayList<>(source.size());
        if (!CollectionUtils.isEmpty(source)){
            if (!CollectionUtils.isEmpty(source)){
                for (Object c: source) {
                    T obj = convert(c, targetClass);
                    target.add(obj);
                }
            }
        }
        return target;
    }

    /**
     * 拷贝单个
     * @param source 源对象
     * @param targetClass 目标类的class对象
     * @param <T>
     * @return 目标对象
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (Objects.isNull(source)) {
            return null;
        }
        T obj = null;
        try {
            obj = targetClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }

}
