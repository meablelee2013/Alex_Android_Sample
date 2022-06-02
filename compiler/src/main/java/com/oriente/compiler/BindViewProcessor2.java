package com.oriente.compiler;

import com.oriente.anno.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

public class BindViewProcessor2 extends HandlerProcess.Handler {
    public HandlerProcess handlerProcess;

    public BindViewProcessor2(HandlerProcess mProcessor) {
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
        for (Element element : elementsAnnotatedWith) {

            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getSimpleName().toString();
            Route annotation = typeElement.getAnnotation(Route.class);
            String annotationName = annotation.name();
            String packageName = getPackageName(typeElement);
            Writer writer = null;
            try {
                JavaFileObject sourceFile = mProcessor.filer.createSourceFile(packageName + "." + "RouterTable");
                writer = sourceFile.openWriter();

                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append("package " + packageName + ";\n");


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
        return true;
    }

    private String getPackageName(TypeElement typeElement) {
        return mProcessor.elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }
}
