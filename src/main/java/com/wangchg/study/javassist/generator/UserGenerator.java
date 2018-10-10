package com.wangchg.study.javassist.generator;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/10/10
 */
public class UserGenerator {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClassUser = classPool.makeClass("com.wangchg.study.javassist.User");

        //定义name字段
        CtClass fieldType = classPool.get("java.lang.String");
        String name = "name";
        CtField ctFieldName = new CtField(fieldType, name, ctClassUser);
        ctFieldName.setModifiers(Modifier.PRIVATE);
        //添加name字段，赋值为javassist
        ctClassUser.addField(ctFieldName, CtField.Initializer.constant("javassist"));

        //定义构造方法
        CtClass[] parameters = new CtClass[]{classPool.get("java.lang.String")};
        CtConstructor constructor = new CtConstructor(parameters, ctClassUser);
        //方法体
        String body = "{this.name=$1;}";
        constructor.setBody(body);
        ctClassUser.addConstructor(constructor);

        //setName getName方法
        ctClassUser.addMethod(CtNewMethod.setter("setName", ctFieldName));
        ctClassUser.addMethod(CtNewMethod.getter("getName", ctFieldName));

        //toString 方法
        CtClass returnType = classPool.get("java.lang.String");
        String methodName = "toString";
        CtMethod toStringMethod = new CtMethod(returnType, methodName, null, ctClassUser);
        toStringMethod.setModifiers(Modifier.PUBLIC);
        String methodBody = "{ return \"name=\"+$0.name;}";
        toStringMethod.setBody(methodBody);
        ctClassUser.addMethod(toStringMethod);

        //代表class文件的CtClass创建完成，现在将其转换成class对象
        Class clazz = ctClassUser.toClass();
        Constructor cons = clazz.getConstructor(String.class);
        Object user = cons.newInstance("wangxiaoxiao");
        Method toString = clazz.getMethod("toString");
        System.out.println(toString.invoke(user));

        ctClassUser.writeFile(".");
    }
}
