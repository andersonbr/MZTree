import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.convergeti.operacoes.Operator;
import br.com.convergeti.operacoes.OperatorImpl;


public class MZQL {
	
	public Map<String, Operator> generateTree(List<String> parameters) {
		/**
		 * root tree
		 */
		Map<String, Operator> root = new HashMap<String, Operator>();
		/**
		 * pointer tree
		 */
		Map<String, Operator> currentLevel = root;
		Map<String, Operator> parent = null;
		Integer seq = 0;
		for (String s : parameters) {
			try {
				/**
				 * quebra a linha atual por |
				 */
				String[] split = s.split("\\|");
				/**
				 * elemento mzql
				 */
				String mzql = split[0];
				/**
				 * model key
				 */
				String modelKey = split[1];
				/**
				 * model key operator
				 */
				String modelKeyOperator = split[2];
				/**
				 * model key value
				 */
				String modelKeyValue = split[3];
				/**
				 * split mzql by ;
				 */
				String[] splitmzql = mzql.split(";");
				/**
				 * current node
				 */
				int currentMzIndex = 0;
				/**
				 * current mz key
				 */
				String currentMzKey = null;
				while (currentMzIndex < splitmzql.length) {
					/**
					 * level de chave atual na linha
					 */
					currentMzKey = splitmzql[currentMzIndex];
					/**
					 * operator sendo lido
					 */
					Operator operator = null;
					/**
					 * se a chave atual nao for encontrada
					 */
					if (currentLevel.get(currentMzKey)==null) {
						/**
						 * cria elemento
						 */
						operator = new OperatorImpl();
						/**
						 * inicializa filhos
						 */
						HashMap<String, Operator> childs = new HashMap<String, Operator>();
						operator.setChilds(childs);
						/**
						 * adiciona elemento em currentLevel
						 */
						operator.setKey(currentMzKey);
						currentLevel.put(currentMzKey, operator);
					} else {
						operator = currentLevel.get(currentMzKey);
					}
					/**
					 * new currentLevel node
					 */
					parent = currentLevel;
					currentLevel = operator.getChilds();
					currentMzIndex++;
				}
				if (parent!=null) {
					/**
					 * leaf elements
					 */
					Operator leaf = new OperatorImpl();
					leaf.setKey(modelKey);
					leaf.setPredicate(modelKeyOperator);
					leaf.setValue(modelKeyValue);
					currentLevel.put(seq.toString(), leaf);
					//System.out.println("LINHA -> "+parent.keySet().toArray()[0]);
					System.out.println("LINHA -> "+currentLevel.size());
				}
				seq++;
			} catch (Exception e) {
			}
		}

		return root;
	}
	
	public static void main(String[] args) {

		List<String> parameters = new ArrayList<String>();
		parameters.add("e8;e1;o1|id|eq|10");
		parameters.add("e8;e1;o1|id|eq|20");
		parameters.add("e8;o2;e2|cidade|eq|fortaleza");
		parameters.add("e8;o2;e2|uf|eq|ce");
		parameters.add("e8;o2;e2|pais|eq|br");
		parameters.add("e8;o2;e3|cidade|eq|sobral");
		parameters.add("e8;o2;e3|uf|eq|ce");
		parameters.add("e8;o2;e3|pais|eq|br");
		parameters.add("e8;e1;o1|id|eq|30");
		MZQL mzql = new MZQL();
		Map<String, Operator> mozo = mzql.generateTree(parameters);
		System.out.println("Value: "+mozo.get("e8").getChilds().get("e1").getChilds().get("o1").getChilds().get("1").getValue());
		
	}
}
