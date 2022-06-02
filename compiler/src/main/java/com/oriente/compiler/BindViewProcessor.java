package com.oriente.compiler;

import com.google.auto.service.AutoService;
import com.oriente.anno.BindView;
import com.oriente.anno.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {
    Messager mMessager;
    Elements mElementUtils;
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "init");
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(BindView.class.getCanonicalName());
        set.add(Route.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "process...");
        //查找到所有的类中被BindView所标记的内容（成员变量）
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(BindView.class);
        /**
         * Element   对应的是    PackageElement-包, TypeElement-类  VariableElement-属性  ExecutableElement-方法
         */
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

                    JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + newName);
                    writer = sourceFile.openWriter();

                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("package " + packageName + ";\n");
                    stringBuffer.append("import android.view.View;\n\n");
                    //通过反射方法1
//                    stringBuffer.append("public class " + newName + "{\n");
                    //通过接口 可以减少反射
                    stringBuffer.append("public class " + newName + " implements IButterKnife<" + packageName + "." + activityName + ">{\n");

                    stringBuffer.append("public void bind(" + packageName + "." + activityName + " target){\n");
                    for (VariableElement variableElement : variableElements) {
                        String variableName = variableElement.getSimpleName().toString();

                        //获取到上面注解所持有的value
                        int resId = variableElement.getAnnotation(BindView.class).value();
                        stringBuffer.append("target." + variableName + "=target.findViewById (" + resId + ");\n");


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
        PackageElement packageOf = processingEnv.getElementUtils().getPackageOf(typeElement);
        return packageOf.getQualifiedName().toString();

    }

}
