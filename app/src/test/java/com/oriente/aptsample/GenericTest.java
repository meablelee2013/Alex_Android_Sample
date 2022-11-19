package com.oriente.aptsample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型
 */
public class GenericTest {

    static class TypeReference<T> {
        Type type;

        public TypeReference() {
            //获取泛型T的真实类型
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            //泛型类可以定义多个 TypeReference<A,B,C>,故返回的是一个数组
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            type = actualTypeArguments[0];
        }
    }

    @Test
    public void testGeneric() {
        ResponseData<Data> dataResponseData = new ResponseData<>(new Data("haha"), 200, "成功");
        Gson gson = new Gson();
        String json = gson.toJson(dataResponseData);
        //有{}代码是匿名内部类，没有{}则是一个对象
        Type realType = new TypeToken<ResponseData<Data>>(){}.getType();

        ResponseData<Data> newDataResponseData = gson.fromJson(json, realType);
//        Type type = new TypeReference<String>(){}.type;
        System.out.println(newDataResponseData.getData());

    }
}
