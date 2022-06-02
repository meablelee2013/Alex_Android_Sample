package com.oriente.aptsample;


public class ButterKnife {

    public static void bind(Object activity) {
        String name = activity.getClass().getName();
        String binderName = name + "$$ViewBinder";
        try {
            Class<?> aClass = Class.forName(binderName);
            //通过反射方法1
//            Object binder = aClass.newInstance();

//            Method bind = aClass.getDeclaredMethod("bind", activity.getClass());
//            bind.invoke(binder,activity);
//          通过接口来实现
            IButterKnife binder = (IButterKnife) aClass.newInstance();
            binder.bind(activity);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
