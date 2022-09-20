package com.oriente.compiler.base;

import com.google.auto.service.AutoService;
import com.oriente.compiler.BindViewProcessor;
import com.oriente.compiler.PrintProcessor;
import com.oriente.compiler.RouteProcessor;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;


@AutoService(Processor.class)
public class HandlerProcess extends AbsProcessor {
    private List<Handler> mHandlers;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mHandlers = new LinkedList<>();
        mHandlers.add(new BindViewProcessor(this));
        mHandlers.add(new RouteProcessor(this));
        mHandlers.add(new PrintProcessor(this));
        for (Handler mHandler : mHandlers) {
            mHandler.onInit();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Handler mHandler : mHandlers) {
            for (String supportedAnnotationType : mHandler.getSupportedAnnotationTypes()) {
                if (types.contains(supportedAnnotationType)) {
                    throw new InvalidParameterException("重复的注解:" + supportedAnnotationType);
                } else {
                    types.add(supportedAnnotationType);
                }
            }
        }
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }


    @Override
    public boolean handProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            String annoName = annotation.getQualifiedName().toString();
            for (Handler mHandler : mHandlers) {
                if (mHandler.getAnnotationType().contains(annoName)) {
                    mHandler.onProcess(annotation, roundEnv);
                }
            }
        }
        return false;
    }

    public abstract static class Handler {
        public HandlerProcess mProcessor;
        private Set<String> mAnnotationTypes = null;

        public Handler(HandlerProcess mProcessor) {
            this.mProcessor = mProcessor;
        }

        public final Set<String> getAnnotationType() {
            if (mAnnotationTypes == null) {
                mAnnotationTypes = new HashSet<>();
                for (String supportedAnnotationType : getSupportedAnnotationTypes()) {
                    mAnnotationTypes.add(supportedAnnotationType);
                }
            }
            return mAnnotationTypes;

        }

        public abstract void onInit();

        public abstract String[] getSupportedAnnotationTypes();

        public abstract boolean onProcess(TypeElement element, RoundEnvironment env);

    }
}
