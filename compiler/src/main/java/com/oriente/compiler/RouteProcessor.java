package com.oriente.compiler;

import com.oriente.anno.Route;
import com.oriente.compiler.base.HandlerProcess;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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

        Writer writer = null;
        try {
            String packageName = getPackageName(getElementsAnnotate0(elementsAnnotatedWith));
            JavaFileObject sourceFile = mProcessor.mFiler.createSourceFile(packageName + "." + "RouterTable");
            writer = sourceFile.openWriter();

            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("package " + packageName + ";\n")
                    .append("import java.util.LinkedList;\n")
                    .append("import com.oriente.anno.RouteInfo;")
                    .append("import java.util.List;\n")
                    .append("public class RouterTable{\n")
                    .append("    public static List<RouteInfo> sRouterTable = new LinkedList<>();\n")
                    .append("    static {\n");

            for (Element element : elementsAnnotatedWith) {

                TypeElement typeElement = (TypeElement) element;
                Route annotation = typeElement.getAnnotation(Route.class);
                String annotationName = annotation.name();
                stringBuffer.append("  sRouterTable.add(new RouteInfo(").append('"').append(annotationName).append('"').append("));\n");

            }

            stringBuffer.append("}\n").append("}\n");

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

        return true;
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
        return mProcessor.mMlementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }
}
