//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsoft.framework.codemap;

import com.gsoft.framework.context.annotation.Module;

@Module(
    name = "codemap",
    entityPackages = {"entity"},
    scanPackages = {"conf", "service", "convert", "web.attr"},
    caption = "代码集"
)
public class ModuleConfig {
    public static final String CODEMPA_TYPE_NORMAL = "1";
    public static final String CODEMPA_TYPE_SQL = "2";

    public ModuleConfig() {
    }
}
