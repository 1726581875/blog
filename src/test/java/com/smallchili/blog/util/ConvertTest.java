package com.smallchili.blog.util;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.utils.ConvertUtil;
import com.smallchili.blog.utils.CopyUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConvertTest {


    public static void main(String[] args) throws Exception {

        Article source = new Article(1,1,1,
                1,"我叫肖明章","hahahahhaha",
                100,100,1000,1,new Date());
        Article target = new Article();
        target.setArticleTitle("哒哒哒哒哒哒多多");

        ArticleHeadMsgDTO articleHeadMsgDTO = new ArticleHeadMsgDTO();
        CopyUtil.notNullCopy(source,articleHeadMsgDTO);

        CopyUtil.notNullCopy(target,source);
        System.out.println(source);
        System.out.println(articleHeadMsgDTO);


       /* Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] fields = sourceClass.getDeclaredFields();
        for (Field field : fields){
            Field tgField = targetClass.getDeclaredField(field.getName());
            //如果该属性是私有的，允许反射可访问
            if(!Modifier.isPublic(tgField.getModifiers())){
                tgField.setAccessible(true);
            }
            if(!Modifier.isPublic(field.getModifiers())){
                field.setAccessible(true);
            }
            if(tgField.get(target) == null && field.get(source) != null){//如果该目标对象该属性为null,源对象不为null
                System.out.println("target class field "+tgField.getName() + " is null");
                 if(tgField.getType().equals(field.getType())){
                     System.out.println("源属性和目标属性名都是" + tgField.getType().getName() + " " + field.getType().getName());
                     //给目标类赋值
                     tgField.set(target , field.get(source));
                 }

            }else {
                System.out.println("target class field "+tgField.getName() + " not null");
            }
        }*/













/*        Field articleId = sourceClass.getDeclaredField("articleId");
        articleId.setAccessible(true);
        Object obj = articleId.get(source);
       if(obj == null){
           System.out.println(articleId.getName() + " is null");
           articleId.set(source,123);
       }
        Method[] declaredMethods = sourceClass.getDeclaredMethods();
        for (Method method:declaredMethods){

        }
        System.out.println(source.getArticleId());*/

        //初始化链表
/*        long start0 = System.currentTimeMillis();
        List<Article> articleList = new ArrayList<>(1000000);
        Article article = new Article(1,1,1,
                1,"我叫肖明章","hahahahhaha",
                100,100,1000,1,new Date());
        for(int i = 0 ; i < 1000000 ; i++){
            articleList.add(article);
        }
        long end0 = System.currentTimeMillis();
        System.out.println("初始化数组 耗时" + (end0 - start0) + " ms");


        //convertList 没指定ArrayList大小
        long start = System.currentTimeMillis();
        List<ArticleHeadMsgDTO> articleHeadMsgDTOS = ConvertUtil.convertList(articleList, ArticleHeadMsgDTO.class);
        long end = System.currentTimeMillis();
        System.out.println("convertList 耗时" + (end - start) + " ms");

        //convertList2 指定ArrayList大小
        long start2 = System.currentTimeMillis();
        List<ArticleHeadMsgDTO> articleHeadMsgDTOS2 = ConvertUtil.convertList(articleList, ArticleHeadMsgDTO.class);
        long end2 = System.currentTimeMillis();
        System.out.println("convertList2 耗时" + (end2 - start2) + " ms");*/




    }

}
