package com.oriente.compiler;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public abstract class AbsClassProcessor extends AbstractProcessor {
    public Messager messager;
    public Filer filer;
    public Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        int size = annotations == null ? 0 : annotations.size();
        if (size > 0) {
            handProcess(annotations, roundEnv);
        }
        return false;
    }

    public abstract boolean handProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);
}
