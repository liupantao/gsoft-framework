//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsoft.framework.codemap.convert;

import com.gsoft.framework.core.convert.AbstractConvert;
import com.gsoft.framework.core.dataobj.Record;
import org.springframework.stereotype.Component;

@Component
public class BooleanConvert extends AbstractConvert {
    private static final long serialVersionUID = 9137918397468733197L;

    public BooleanConvert() {
    }

    public String getName() {
        return "Boolean";
    }

    protected void initRecord(Record record) {
        record.put("1", "是");
        record.put("0", "否");
    }
}
