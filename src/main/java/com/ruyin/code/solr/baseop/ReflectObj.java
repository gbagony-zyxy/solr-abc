package com.ruyin.code.solr.baseop;

import com.ruyin.code.solr.bean.Article;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by gbagony on 2017/1/4.
 */
public class ReflectObj {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Article article = new Article("1001", "David", "How to live...", "Worth our to think...", "Just to fight!", new Date(),20);
        Article article1 = (Article) ReflectObj.translate(article);
        System.out.println(article1.getAuthor());
    }

    private static Object translate(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> classType = object.getClass();
        Field[] fields = classType.getDeclaredFields();

        Object objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});

        Arrays.stream(fields).forEach(field -> {
            String name = field.getName();
            String firstLetter = name.substring(0,1).toUpperCase();
            String getMethodName = "get"+firstLetter+name.substring(1);
            String setMethodName = "set"+firstLetter+name.substring(1);

            try {
                Method getMehod = classType.getMethod(getMethodName,new Class[]{});
                Method setMethod = classType.getMethod(setMethodName,new Class[]{field.getType()});

                Object value = getMehod.invoke(object,new Object[]{});
                setMethod.invoke(objectCopy,new Object[]{value});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return objectCopy;
    }
}
