package com.oriente.compiler;

import com.oriente.anno.Route;
import com.oriente.compiler.base.HandlerProcess;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class RouteProcessor extends HandlerProcess.Handler {
    public HandlerProcess handlerProcess;

    public RouteProcessor(HandlerProcess mProcessor) {
        super(mProcessor);
        this.handlerProcess = mProcessor;
    }


    @Override
    public void onInit() {

    }

    @Override
    public String[] getSupportedAnnotationTypes() {
        return new String[]{Route.class.getCanonicalName()};
    }

    @Override
    public boolean onProcess(TypeElement el, RoundEnvironment env) {
        Set<? extends Element> elementsAnnotatedWith = env.getElementsAnnotatedWith(Route.class);

        javaPoet(elementsAnnotatedWith);
        return true;
    }

    /**
     *  PACKAGE	//表示包
     * ENUM //表示枚举
     * CLASS //表示类
     * ANNOTATION_TYPE	//表示注解
     * INTERFACE //表示接口
     * ENUM_CONSTANT //表示枚举常量
     * FIELD //表示字段
     * PARAMETER //表示参数
     * LOCAL_VARIABLE //表示本地变量
     * EXCEPTION_PARAMETER //表示异常参数
     * METHOD //表示方法
     * CONSTRUCTOR //表示构造函数
     * OTHER //表示其他
     * @param elementsAnnotatedWith
     */

    /**
     * public final class Route{
     * 	Map<String,Class> map = new HashMap()
     *
     * 	public static Class get(String path){
     * 		if(map.contains(path)){
     * 			return map.get(path)
     *                }else{
     *
     *        }* 	}
     *
     * 	private void init(){
     * 		map.put(path,clazz)
     *    }
     * }
     */

    /**
     * public class MainActivity$$Router{
     *     public static Class findTargetClass(String path){
     *         return path.equals("/app/MainActivity")?MainActivity.class:null
     *     }
     * }
     */

    /**
     * @param elementsAnnotatedWith
     */
    private void javaPoet(Set<? extends Element> elementsAnnotatedWith) {
        String packageName = null;
        String className = null;
//        MethodSpec.Builder initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        FieldSpec fieldSpec = FieldSpec.builder(HashMap.class, "routerMaps", Modifier.PRIVATE, Modifier.STATIC).initializer("new HashMap<$1T,$2T>()", String.class, Class.class).build();
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        for (Element element : elementsAnnotatedWith) {
            if (element.getKind() == ElementKind.CLASS) {//元素是类
                TypeElement typeElement = (TypeElement) element;
                mProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "className>>>>>>>>>>>>>>>>>" + typeElement.getSimpleName());
                Route route = element.getAnnotation(Route.class);

                packageName = mProcessor.mElementUtils.getPackageOf(element).getQualifiedName().toString();
                mProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "packageName>>>>>>>>>>>>>>>>>" + packageName);

                className = typeElement.getSimpleName() + "$Route";
                ClassName className1 = ClassName.get((TypeElement) element);
                codeBlockBuilder.addStatement("routerMaps.put($S,$T.class)", route.name(), className1);
//                initMethod.addStatement("routerMaps.put($S,$T.class)", route.name(), className1);
            }
        }
        MethodSpec getClass = MethodSpec.methodBuilder("getTarget").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(Class.class).addParameter(String.class, "path").addStatement("return (Class)routerMaps.get(path)").build();
        TypeSpec classNameSpec = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC, Modifier.FINAL).addField(fieldSpec).addMethod(getClass)
                .addStaticBlock(codeBlockBuilder.build())
//                .addMethod(initMethod.build())
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, classNameSpec).build();

        try {
            javaFile.writeTo(mProcessor.mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * public class Alex {
     * public static void main(String[] args) {
     * System.out.println("Hello,JavaPoet");
     * }
     * }
     */

    private void testJavaPoet() {
        //方法
        MethodSpec mainMethod = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(void.class).addParameter(String[].class, "args").addStatement("$T.out.println($S)", System.class, "Hello,JavaPoet").build();
        //类
        TypeSpec testClass = TypeSpec.classBuilder("Alex").addMethod(mainMethod).addModifiers(Modifier.PUBLIC).build();
        //包
        JavaFile build = JavaFile.builder("com.oriente.compiler", testClass).build();

        try {
            build.writeTo(mProcessor.mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 传统方法 包--类--方法
     *
     * @param elementsAnnotatedWith
     */

    private void tradition(Set<? extends Element> elementsAnnotatedWith) {
        Writer writer = null;
        try {
            String packageName = getPackageName(getElementsAnnotate0(elementsAnnotatedWith));
            JavaFileObject sourceFile = mProcessor.mFiler.createSourceFile(packageName + "." + "RouterTable");
            writer = sourceFile.openWriter();

            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("package " + packageName + ";\n").append("import java.util.LinkedList;\n").append("import com.oriente.anno.RouteInfo;").append("import java.util.List;\n\n").append("public class RouterTable{\n").append("\tpublic static List<RouteInfo> sRouterTable = new LinkedList<>();\n").append("\n\tstatic {\n");

            for (Element element : elementsAnnotatedWith) {

                TypeElement typeElement = (TypeElement) element;
                Route annotation = typeElement.getAnnotation(Route.class);
                String annotationName = annotation.name();
                stringBuffer.append("\t\tsRouterTable.add(new RouteInfo(").append('"').append(annotationName).append('"').append("));\n");

            }

            stringBuffer.append("\t}\n").append("}\n");

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

    private Element getElementsAnnotate0(Set<? extends Element> set) {
        Element element0 = null;
        if (set != null) {
            for (Element element : set) {
                element0 = element;
                break;
            }
        }
        return element0;
    }

    private String getPackageName(Element typeElement) {
        return mProcessor.mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }
}
