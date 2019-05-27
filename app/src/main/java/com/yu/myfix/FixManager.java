package com.yu.myfix;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class FixManager {

    Context context;

    public FixManager(Context context) {
        this.context = context;
    }

    /**
     * 读取文件，遍历dex文件中的类
     * @param file
     */
    public void load(File file){
        try {
            DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(),
                    new File(context.getCacheDir(), "opt").getAbsolutePath(), Context.MODE_PRIVATE);

            // 遍历dex文件中的类
            Enumeration<String> entry=dexFile.entries();
            while (entry.hasMoreElements()) {
                String clazzName= entry.nextElement();
                Class realClazz= dexFile.loadClass(clazzName, context.getClassLoader());
                if (realClazz != null) {
                    // 修复类
                    fixClazz(realClazz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 修复类中加了特定注解的方法
     * @param clazz
     */
    public void fixClazz(Class clazz){
        //获取类中的方法
        Method[] methods= clazz.getMethods();

        for (Method rightMethod : methods) {

            //筛选添加了Replace注解的方法
            Replace replace = rightMethod.getAnnotation(Replace.class);
            if (replace == null) {
                continue;
            }
            // 获取注解中的类名和方法名
            String clazzName=replace.clazz();
            String methodName=replace.method();

            try {
                // 从内存中获取已经加载过的错误类
                Class wrongClazz = Class.forName(clazzName);
                // 获取错误类的错误方法
                Method wrongMethod = wrongClazz.getDeclaredMethod(methodName, rightMethod.getParameterTypes());
                // 替换方法
                replace(wrongMethod, rightMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * native函数，替换错误方法
     * @param wrongMethod
     * @param rightMethod
     */
    private native void replace(Method wrongMethod, Method rightMethod);

}
