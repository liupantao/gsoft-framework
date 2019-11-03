//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsoft.framework.codemap.convert;

import com.gsoft.framework.core.convert.AbstractConvert;
import com.gsoft.framework.core.dataobj.Record;
import org.springframework.stereotype.Component;

@Component
public class CodeTypeConvert extends AbstractConvert {
    private static final long serialVersionUID = 8024160460449744023L;

    public CodeTypeConvert() {
    }

    public String getName() {
        return "codemapType";
    }

    protected void initRecord(Record record) {
        record.put("1", "代码集");
        record.put("2", "SQL代码集");
    }
}
