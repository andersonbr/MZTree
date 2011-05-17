package br.com.convergeti.operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OperationTree extends HashMap<String, OperationTree> {
	private static final long serialVersionUID = 1L;

	private OperationTree parent = null;
	private Operation operation = null;
	private String field = null;
	private String predicate = null;
	private String value = null;

	public OperationTree() {
	}

	public void setParent(OperationTree parent) {
		this.parent = parent;
	}

	public OperationTree getParent() {
		return parent;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Operation getOperation() {
		return operation;
	}

	public static OperationTree createTree(List<String> parameters) {
	
		OperationTree rootNode = null;
		OperationTree currentNode = null;
		
		Integer seq = 0;
		for (String s : parameters) {
			try {
				/**
				 * quebra a linha atual por |
				 */
				String[] split = s.split("\\|");
				/**
				 * elemento restriction
				 */
				String restrictions = split[0];
				/**
				 * model key
				 */
				String modelField = split[1];
				/**
				 * model key operator
				 */
				String modelFieldPredicate = split[2];
				/**
				 * model key value
				 */
				String modelFieldValue = split[3];
				/**
				 * split restrictions splited by ;
				 */
				String[] splitedrestrictions = restrictions.split(";");
				/**
				 * current node
				 */
				int currentNodeIndex = 0;
				/**
				 * Initialize root
				 * e define currentNode para root
				 */
				if (rootNode==null) {
						rootNode = new OperationTree();
				}
				currentNode = rootNode;
				/**
				 * Scan operators of current parameter
				 */
				while (currentNodeIndex < splitedrestrictions.length) {
					/**
					 * level de chave atual na linha
					 */
					String nodeKey = splitedrestrictions[currentNodeIndex];
					char opChar = nodeKey.toLowerCase().charAt(0);
					/**
					 * operator sendo lido
					 */
					OperationTree operationTree = null;
					/**
					 * se a chave atual nao for encontrada
					 */
					if (currentNode.get(nodeKey)==null) {
						/**
						 * cria elemento novo caso nao exista
						 */
						operationTree = new OperationTree();
						/**
						 * definir operacao logica
						 */
						if (opChar=='o')
							operationTree.setOperation(new Or());
						else
							operationTree.setOperation(new And());
						/**
						 * adiciona elemento em currentLevel
						 */
						currentNode.put(nodeKey, operationTree);
						/**
						 * define o node adicionado como atual
						 */
						operationTree.setParent(currentNode);
					} else {
						/**
						 * recuperar elemento caso exista
						 */
						operationTree = currentNode.get(nodeKey);
					}
					/**
					 * definir no atual para o que foi pegue pela chave
					 */
					currentNode = operationTree;
					currentNodeIndex++;
				}
				OperationTree fnode = new OperationTree();
				fnode.setField(modelField);
				fnode.setPredicate(modelFieldPredicate);
				fnode.setValue(modelFieldValue);
				currentNode.put("node"+seq, fnode);
				seq++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return rootNode;
	}
	public static void main(String[] args) {
		
		List<String> parameters = new ArrayList<String>();
		parameters.add("a8;a1;o1|id|eq|10");
		parameters.add("a8;a1;o1|id|eq|20");
		parameters.add("a8;a1;o1|id|eq|30");
		parameters.add("a8;o2;a2|cidade|eq|fortaleza");
		parameters.add("a8;o2;a2|uf|eq|ce");
		parameters.add("a8;o2;a2|pais|eq|br");
		parameters.add("a8;o2;a3|cidade|eq|sobral");
		parameters.add("a8;o2;a3|uf|eq|ce");
		parameters.add("a8;o2;a3|pais|eq|br");
		
		
		OperationTree tree = OperationTree.createTree(parameters); // generate tree
		for (OperationTree op : tree.get("a8").get("a1").get("o1").getParent().get("o1").values()) {
			System.out.println(" -> "+op.getOperation());
		}
		OperationTree op = tree.get("a8");
		System.out.println(" -> "+op.getOperation());
		
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getPredicate() {
		return predicate;
	}
}
