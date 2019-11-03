//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsoft.framework.codemap.service.impl;

import com.gsoft.framework.codemap.service.ConvertManager;
import com.gsoft.framework.core.convert.IConvert;
import com.gsoft.framework.core.dataobj.Record;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.remote.annotation.ServiceParam;
import com.gsoft.framework.taglib.convert.Convert;
import com.gsoft.framework.taglib.convert.ConvertProviderFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("convertManager")
public class ConvertManagerImpl implements ConvertManager {
    @Autowired
    private ConvertProviderFactory convertProviderFactory;

    public ConvertManagerImpl() {
    }

    @ServiceMapping
    public void refreshCached(@ServiceParam(name = "name") String name) {
        this.convertProviderFactory.refreshCached(name);
    }

    @ServiceMapping
    public IConvert<?> getConvert(@ServiceParam(name = "name") String name) {
        IConvert<?> convert = this.convertProviderFactory.getConvert(name, (Locale)null);
        Convert<?> resConvert = new Convert(convert.getRecord());
        resConvert.setName(name);
        return resConvert;
    }

    @ServiceMapping
    public Record getConverts(@ServiceParam(name = "name") List<String> names) {
        Record record = new Record();
        Iterator var4 = names.iterator();

        while(var4.hasNext()) {
            String name = (String)var4.next();
            IConvert<?> convert = this.getConvert(name);
            if (convert != null) {
                record.put(name, this.getConvert(name).getRecord());
            }
        }

        return record;
    }
}
