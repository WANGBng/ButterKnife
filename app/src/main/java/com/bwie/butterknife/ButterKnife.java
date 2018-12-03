package com.bwie.butterknife;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2018/12/3.
 * Created by 王丙均
 */

public class ButterKnife {
    public static void bind(final Activity activity){
        Class<Activity> clazz = (Class<Activity>) activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields ) {
            field.setAccessible(true);//

            //该注释里面是否包含此注释
            if (field.isAnnotationPresent(BindView.class)){
                //拿到修饰该属性的注解
                BindView bindView = field.getAnnotation(BindView.class);
                int value = bindView.value();
                //现在需要取到控件的id
                View viewById = activity.findViewById(value);
                try {//给属性赋值
                    field.set(activity,viewById);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.isAnnotationPresent(BindString.class)){
                BindString bindString = field.getAnnotation(BindString.class);
                int value = bindString.value();
                String string = activity.getResources().getString(value);
                try {
                    field.set(activity,string);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        }


        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (final Method method: declaredMethods ) {
            method.setAccessible(true);
            if(method.isAnnotationPresent(OnClick.class)){
                OnClick onClick = method.getAnnotation(OnClick.class);

                int[] onClickValue = onClick.value();

                for (int id :onClickValue) {
                    View viewById = activity.findViewById(id);
                    viewById.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(activity,v);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        }

    }

}
