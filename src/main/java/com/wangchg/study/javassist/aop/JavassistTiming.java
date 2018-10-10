package com.wangchg.study.javassist.aop;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/10/10
 */
public class JavassistTiming {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        String className = "com.wangchg.study.javassist.change.Looper";
        String methodName = "loop";
        CtClass ctClass = ClassPool.getDefault().get(className);
        CtMethod method = ctClass.getDeclaredMethod(methodName);
        String newname = methodName + "$impl";
        method.setName(newname);

        CtMethod newMethod = CtNewMethod.make("public void " + methodName + "(){" + "long start = System.currentTimeMillis();" +
                newname + "();" +
                "System.out.println(\"耗时:\"+(System.currentTimeMillis()-start)+\"ms\");" +
                "}", ctClass);
        ctClass.addMethod(newMethod);
        Looper looper = (Looper) ctClass.toClass().newInstance();
        looper.loop();
    }
}
