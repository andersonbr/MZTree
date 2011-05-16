package br.com.convergeti.operacoes;

import java.util.Map;

public interface Operator {
	public Map<String, Operator> getChilds();
	public String getKey();
	public String getPredicate();
	public String getValue();
	public void setChilds(Map<String, Operator> childs);
	public void setKey(String key);
	public void setPredicate(String predicate);
	public void setValue(String value);
}
