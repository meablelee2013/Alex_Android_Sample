package com.example.asm

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ASMPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(BaseExtension)
        android.registerTransform(new ASMTransform(project))
    }
}
