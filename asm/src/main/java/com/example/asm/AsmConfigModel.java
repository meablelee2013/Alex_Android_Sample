package com.example.asm;

import java.io.File;
import java.util.List;

public class AsmConfigModel {
    /**
     * 以此参数为开头的类（全限定类名）才插桩
     * 如果不配置此参数则代码所有的类都可以插桩
     */
    public List<String> startWithPatterns;
    /**
     * 排队列表（全限定类名）
     */
    public List<String> excludes;

    /**
     * 排除列表（全限定类名）
     * 以文件形式
     */
    public File excludesByFile;


}
