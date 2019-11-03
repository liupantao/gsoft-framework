//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsoft.framework.codemap.service.impl;

import com.gsoft.framework.codemap.dao.CodeitemDao;
import com.gsoft.framework.codemap.dao.CodemapDao;
import com.gsoft.framework.codemap.entity.Codeitem;
import com.gsoft.framework.codemap.entity.Codemap;
import com.gsoft.framework.codemap.service.CodemapManager;
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
import com.gsoft.framework.taglib.convert.Convert;
import com.gsoft.framework.taglib.convert.DBConvertProvider;
import com.gsoft.framework.util.Assert;
import com.gsoft.framework.util.ConditionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("codemapManager")
@Transactional
public class CodemapManagerImpl extends BaseManagerImpl implements CodemapManager, DBConvertProvider {
    @Autowired
    private CodemapDao codemapDao;
    @Autowired
    private CodeitemDao codeitemDao;

    public CodemapManagerImpl() {
    }

    public List<Codemap> getCodemaps() throws BusException {
        return this.codemapDao.findAll();
    }

    public List<Codemap> getCodemaps(Collection<Condition> conditions, Collection<Order> orders) throws BusException {
        return this.codemapDao.commonQuery(conditions, orders);
    }

    @ServiceMapping
    public Codemap getCodemap(@ServiceParam(name = "codemapId") String id) {
        return (Codemap)this.codemapDao.findOne(id);
    }

    @ServiceMapping
    public PagerRecords getPagerCodemaps(Pager pager, @ConditionCollection(domainClazz = Codemap.class) Collection<Condition> conditions, @OrderCollection Collection<Order> orders) throws BusException {
        PagerRecords pagerRecords = this.codemapDao.findByPager(pager, conditions, orders);
        return pagerRecords;
    }

    @ServiceMapping
    public Codemap saveCodemap(Codemap o) throws BusException {
        return (Codemap)this.codemapDao.save(o);
    }

    @ServiceMapping
    public void removeCodemap(@ServiceParam(name = "codemapId") String id) throws BusException {
        this.codemapDao.delete(id);
    }

    public void removeCodemaps(String[] ids) throws BusException {
        String[] var5 = ids;
        int var4 = ids.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String id = var5[var3];
            this.removeCodemap(id);
        }

    }

    public boolean exsitCodemap(String id) throws BusException {
        return this.codemapDao.exists(id);
    }

    public boolean exsitCodemap(String propertyName, Object value) throws BusException {
        return this.codemapDao.exists(propertyName, value);
    }

    @ServiceMapping
    public PagerRecords getPagerCodeitemsByCodeMap(Pager pager, Codemap codemap, @OrderCollection Collection<Order> orders) {
        if (codemap != null && codemap.getCodemapId() != null) {
            Codemap dbCodemap = (Codemap)this.codemapDao.findOne(codemap.getCodemapId());
            String codemapType = dbCodemap.getType();
            if ("1".equals(codemapType)) {
                Collection<Condition> conditions = new ArrayList();
                conditions.add(ConditionUtils.getCondition("codemap.codemapId", "EQUALS", codemap.getCodemapId()));
                orders.add(ConditionUtils.getOrder("index", true));
                orders.add(ConditionUtils.getOrder("itemValue", true));
                return this.codeitemDao.findByPager(pager, conditions, orders);
            } else if (!"2".equals(codemapType)) {
                throw new BusException("不支持的代码集类型！");
            } else {
                List<Codeitem> codeitems = this.getBySql(dbCodemap.getExpression());
                List<Codeitem> records = new ArrayList();
                int endIndex = Math.min(pager.getStartIndex() + pager.getPageSize(), codeitems.size());

                for(int i = pager.getStartIndex(); i < endIndex; ++i) {
                    records.add((Codeitem)codeitems.get(i));
                }

                PagerRecords pagerRecords = new PagerRecords(records, (long)codeitems.size());
                pagerRecords.setPager(pager);
                return pagerRecords;
            }
        } else {
            return new PagerRecords(new ArrayList(), 0L);
        }
    }

    private List<Codeitem> getBySql(String expression) {
        List<Codeitem> list = new ArrayList();
        Query query = this.codeitemDao.getEntityManager().createNativeQuery(expression);
        List<Object[]> result = query.getResultList();
        Iterator var6 = result.iterator();

        while(var6.hasNext()) {
            Object[] o = (Object[])var6.next();
            if (o.length == 2) {
                Codeitem codeitem = new Codeitem();
                String code = o[0] == null ? "" : o[0].toString();
                String show = o[1] == null ? "" : o[1].toString();
                codeitem.setItemValue(code);
                codeitem.setItemCaption(show);
                list.add(codeitem);
            }
        }

        return list;
    }

    @ServiceMapping
    public String exportCodemapToSql(@ServiceParam(name = "codemapId") String[] ids) {
        StringBuffer sqlBufs = new StringBuffer();
        String[] var6 = ids;
        int var5 = ids.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String id = var6[var4];
            sqlBufs.append(this.buildCodemapInsertSql(id));
        }

        return sqlBufs.toString();
    }

    private String buildCodemapInsertSql(String id) {
        StringBuffer sqlInsertBuf = new StringBuffer();
        Assert.notNull(id, "id不能为空");
        Codemap codemap = this.getCodemap(id);
        if (codemap == null) {
            return "";
        } else {
            sqlInsertBuf.append("insert into youi_codemap(codemap_id, code, caption, type, expression) values (").append(this.buildItemInsertValue(codemap.getCodemapId())).append(this.buildItemInsertValue(codemap.getCode())).append(this.buildItemInsertValue(codemap.getCaption())).append(this.buildItemInsertValue(codemap.getType())).append(this.buildItemInsertValue(codemap.getExpression(), true)).append(");\n");
            List<Codeitem> items = this.codeitemDao.findList("codemap.codemapId", id);
            if (items.size() > 0) {
                Iterator var6 = items.iterator();

                while(var6.hasNext()) {
                    Codeitem item = (Codeitem)var6.next();
                    sqlInsertBuf.append("insert into youi_codeitem (item_id, codemap_id, item_value, item_caption) values (");
                    sqlInsertBuf.append(this.buildItemInsertValue(item.getItemId())).append(this.buildItemInsertValue(id)).append(this.buildItemInsertValue(item.getItemValue())).append(this.buildItemInsertValue(item.getItemCaption(), true));
                    sqlInsertBuf.append(");\n");
                }
            }

            return sqlInsertBuf.toString();
        }
    }

    private String buildItemInsertValue(String value) {
        return this.buildItemInsertValue(value, false);
    }

    private String buildItemInsertValue(String value, boolean isLast) {
        if (value == null) {
            value = "";
        }

        String insertValue = "'" + value + "'";
        if (!isLast) {
            insertValue = insertValue + ",";
        }

        return insertValue;
    }

    public Convert<?> getConvert(String name, Locale locale) {
        return this.loadConvert(name);
    }

    private Convert<String> loadConvert(String name) {
        Convert convert = new Convert();

        List codeitems;
        try {
            codeitems = this.loadCodeitems(name);
        } catch (Exception var6) {
            return null;
        }

        Iterator var5 = codeitems.iterator();

        while(var5.hasNext()) {
            Codeitem codeitem = (Codeitem)var5.next();
            convert.put(codeitem.getItemValue(), codeitem.getItemCaption());
        }

        convert.setName(name);
        return convert;
    }

    public List<Codeitem> loadCodeitems(String name) {
        List<Codeitem> codeitems = new ArrayList();
        Codemap codemap = (Codemap)this.codemapDao.findOneByProperty("code", name);
        if (codemap != null) {
            String codemapType = codemap.getType();
            if ("1".equals(codemapType)) {
                Collection<Condition> conditions = new ArrayList();
                Collection<Order> orders = new ArrayList();
                conditions.add(ConditionUtils.getCondition("codemap.code", "EQUALS", name));
                orders.add(ConditionUtils.getOrder("index", true));
                orders.add(ConditionUtils.getOrder("itemValue", true));
                codeitems = this.codeitemDao.commonQuery(conditions, orders);
            } else if ("2".equals(codemapType)) {
                codeitems = this.getBySql(codemap.getExpression());
            }
        }

        return (List)codeitems;
    }
}
