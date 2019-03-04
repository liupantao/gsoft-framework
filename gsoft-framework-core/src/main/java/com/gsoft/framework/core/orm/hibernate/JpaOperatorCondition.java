package com.gsoft.framework.core.orm.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.Assert;

import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.OperatorCondition;

/**
 * 
 * @author liupantao
 *
 */
public class JpaOperatorCondition implements Condition, OperatorCondition {

	private static final long serialVersionUID = -1828396355514966293L;
	
	private Condition[] conditions;
	private String cOperator;
	
	public final static String O_OR = "OR";
	public final static String O_AND = "AND";

	public JpaOperatorCondition(String operator,Condition... conditions) {
		Assert.notNull(operator,"JpaOperatorCondition Operator cannot be null");
		Assert.notNull(conditions,"JpaOperatorCondition conditions cannot be null");
		Assert.notEmpty(conditions, "JpaOperatorCondition conditions cannot be empty");
		this.cOperator = operator;
		this.conditions = conditions;
	}
	
	@Override
	public Predicate generateExpression(Root<?> root, CriteriaBuilder cb){
		List<Predicate> predicates = new ArrayList<Predicate>();
		for(Condition condition:conditions){
			Predicate predicate = condition.generateExpression(root, cb);
			predicates.add(predicate);
		}
		if(O_OR.equals(cOperator)){
			return cb.or(predicates.toArray(new Predicate[predicates.size()]));
		}else if(O_AND.equals(cOperator)){
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}else{
			Assert.notNull(null, "JpaOperatorCondition Operator cannot be :" + cOperator);
		}
		return null;
	}

	@Override
	public String getProperty() {
		return "";
	}

	@Override
	public String getOperator() {
		return "";
	}

	@Override
	public Object getValue() {
		return null;
	}


	public String getcOperator() {
		return cOperator;
	}

	public void setcOperator(String cOperator) {
		this.cOperator = cOperator;
	}

	public Condition[] getConditions() {
		return conditions;
	}

	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}

}