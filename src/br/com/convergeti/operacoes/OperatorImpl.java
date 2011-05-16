package br.com.convergeti.operacoes;

import java.util.Map;

public class OperatorImpl implements Operator {
	private String key;
	private String predicate;
	private String value;
	private Map<String, Operator> childs;
	public Map<String, Operator> getChilds() {
		return childs;
	}
	public String getKey() {
		return key;
	}
	public String getPredicate() {
		return predicate;
	}
	public String getValue() {
		return value;
	}
	public void setChilds(Map<String, Operator> childs) {
		this.childs = childs;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
