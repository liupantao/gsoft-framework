//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsoft.framework.codemap.service.impl;

import com.gsoft.framework.codemap.dao.CodeitemDao;
import com.gsoft.framework.codemap.entity.Codeitem;
import com.gsoft.framework.codemap.service.CodeitemManager;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.core.service.impl.BaseManagerImpl;
import com.gsoft.framework.remote.annotation.ConditionCollection;
import com.gsoft.framework.remote.annotation.OrderCollection;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.remote.annotation.ServiceParam;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("codeitemManager")
@Transactional
public class CodeitemManagerImpl extends BaseManagerImpl implements CodeitemManager {
    @Autowired
    private CodeitemDao codeitemDao;

    public CodeitemManagerImpl() {
    }

    public List<Codeitem> getCodeitems() throws BusException {
        return this.codeitemDao.findAll();
    }

    public List<Codeitem> getCodeitems(Collection<Condition> conditions, Collection<Order> orders) throws BusException {
        return this.codeitemDao.commonQuery(conditions, orders);
    }

    @ServiceMapping
    public Codeitem getCodeitem(@ServiceParam(name = "itemId") String id) {
        return (Codeitem)this.codeitemDao.findOne(id);
    }

    @ServiceMapping
    public PagerRecords getPagerCodeitems(Pager pager, @ConditionCollection(domainClazz = Codeitem.class) Collection<Condition> conditions, @OrderCollection Collection<Order> orders) throws BusException {
        PagerRecords pagerRecords = this.codeitemDao.findByPager(pager, conditions, orders);
        return pagerRecords;
    }

    @ServiceMapping
    public Codeitem saveCodeitem(Codeitem o) throws BusException {
        return (Codeitem)this.codeitemDao.save(o);
    }

    @ServiceMapping
    public void removeCodeitem(@ServiceParam(name = "itemId") String id) throws BusException {
        this.codeitemDao.delete(id);
    }

    public void removeCodeitems(String[] ids) throws BusException {
        String[] var5 = ids;
        int var4 = ids.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String id = var5[var3];
            this.removeCodeitem(id);
        }

    }

    public boolean exsitCodeitem(String id) throws BusException {
        return this.codeitemDao.exists(id);
    }

    public boolean exsitCodeitem(String propertyName, Object value) throws BusException {
        return this.codeitemDao.exists(propertyName, value);
    }
}
