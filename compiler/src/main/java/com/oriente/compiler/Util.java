package com.oriente.compiler;

import java.util.Collection;
import java.util.Map;

public class Util {

    public static boolean isEmpty(Object args) {
        if (args instanceof Character) {
            return args == null || "".equals(args);
        } else if (args instanceof Map) {
            return args == null || ((Map<?, ?>) args).isEmpty();
        } else if (args instanceof Collection) {
            return args == null || ((Collection) args).isEmpty();
        }
        return args != null;
    }
}
