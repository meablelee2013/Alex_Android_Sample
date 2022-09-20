package com.oriente.compiler;

import com.oriente.anno.BindView;
import com.oriente.compiler.base.HandlerProcess;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;


public class BindViewProcessor extends HandlerProcess.Handler {

    public HandlerProcess mHandlerProcess;

    public BindViewProcessor(HandlerProcess mProcessor) {
        super(mProcessor);
        this.mHandlerProcess = mProcessor;
    }


    @Override
    public void onInit() {

    }

    @Override
    public String[] getSupportedAnnotationTypes() {
        return new String[]{BindView.class.getCanonicalName()};
    }


    /**
     * Element   对应的是    PackageElement-包, TypeElement-类  VariableElement-属性  ExecutableElement-方法
     */
    @Override
    public boolean onProcess(TypeElement ele, RoundEnvironment env) {
        Set<? extends Element> elementsAnnotatedWith = env.getElementsAnnotatedWith(BindView.class);

        //将每个Activity里面的成员变量单独找出来
        Map<String, List<VariableElement>> map = new HashMap<>();

        //遍历所有的成员变量（将每个activity和这个activity 里的成员变量收集起来）
        for (Element element : elementsAnnotatedWith) {
            //属性节点
            VariableElement variableElement = (VariableElement) element;
            //获取到这个成员变量节点的上一个节点(类节点)
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            //获取到类节点的类名
            String activityName = typeElement.getSimpleName().toString();
            List<VariableElement> variableElements = map.get(activityName);
            if (variableElements == null) {
                variableElements = new ArrayList<>();
                map.put(activityName, variableElements);
            }
            variableElements.add(variableElement);

        }
        //生成代码
        if (map.size() > 0) {
            //写代码的对象
            Writer writer = null;
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String activityName = iterator.next();
                //同一个类下面所有的属性变量
                List<VariableElement> variableElements = map.get(activityName);
                String packageName = getPackageName(variableElements.get(0));
                String newName = activityName + "$$ViewBinder";
                //生成Java类
                try {

                    JavaFileObject sourceFile = mProcessor.mFiler.createSourceFile(packageName + "." + newName);
                    writer = sourceFile.openWriter();

                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("package " + packageName + ";\n");
                    stringBuffer.append("import android.view.View;\n\n");
                    //通过反射方法1
//                    stringBuffer.append("public class " + newName + "{\n");
                    //通过接口 可以减少反射
                    stringBuffer.append("public class " + newName + " implements IButterKnife<" + packageName + "." + activityName + ">{\n");

                    stringBuffer.append("\n\tpublic void bind(" + packageName + "." + activityName + " target){\n");
                    for (VariableElement variableElement : variableElements) {
                        String variableName = variableElement.getSimpleName().toString();

                        //获取到上面注解所持有的value
                        int resId = variableElement.getAnnotation(BindView.class).value();
                        stringBuffer.append("\t\ttarget." + variableName + " = target.findViewById (" + resId + ");\n");


                    }
                    stringBuffer.append("   }\n");
                    stringBuffer.append("}\n");
                    writer.write(stringBuffer.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * 获取包名
     *
     * @param variableElement
     * @return
     */
    public String getPackageName(VariableElement variableElement) {
        TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
        PackageElement packageOf = mHandlerProcess.mElementUtils.getPackageOf(typeElement);
        return packageOf.getQualifiedName().toString();

    }

}
