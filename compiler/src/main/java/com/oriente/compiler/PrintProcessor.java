package com.oriente.compiler;

import com.oriente.anno.PrintInfo;
import com.oriente.compiler.base.HandlerProcess;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class PrintProcessor extends HandlerProcess.Handler {
    public HandlerProcess mHandlerProcess;

    public PrintProcessor(HandlerProcess mProcessor) {
        super(mProcessor);
        this.mHandlerProcess = mProcessor;
    }

    @Override
    public void onInit() {

    }

    @Override
    public String[] getSupportedAnnotationTypes() {
        return new String[]{PrintInfo.class.getCanonicalName()};
    }

    @Override
    public boolean onProcess(TypeElement element, RoundEnvironment env) {
        Set<? extends Element> elementsAnnotatedWith = env.getElementsAnnotatedWith(PrintInfo.class);
        mHandlerProcess.mMessager.printMessage(Diagnostic.Kind.NOTE, "annotation class size = " + elementsAnnotatedWith.size());
        for (Element e : elementsAnnotatedWith) {
            if (e instanceof PackageElement) {
                mHandlerProcess.mMessager.printMessage(Diagnostic.Kind.NOTE, "element is packageElement  ");
            } else if (e instanceof TypeElement) {
                mHandlerProcess.mMessager.printMessage(Diagnostic.Kind.NOTE, "element packageName is " + getPackageName(e));
                TypeElement typeElement = (TypeElement) e;
                String className = typeElement.getSimpleName().toString();
                String fullClassName = typeElement.getQualifiedName().toString();
                PrintInfo printInfo = typeElement.getAnnotation(PrintInfo.class);
                mHandlerProcess.mMessager.printMessage(Diagnostic.Kind.NOTE, "element className " + className + "---element fullClassName=" + fullClassName);
                mHandlerProcess.mMessager.printMessage(Diagnostic.Kind.NOTE, "element value()=  " + printInfo.value() + "---element active()=" + printInfo.active());
            }
        }
        return true;
    }

    private String getPackageName(Element e) {
        if (e != null && mHandlerProcess != null && mHandlerProcess.mElementUtils != null) {
            PackageElement packageElement = mHandlerProcess.mElementUtils.getPackageOf(e);
            return packageElement.getQualifiedName().toString();

        }
        return "";
    }


}
