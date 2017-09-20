import java.util.ArrayList;

public class Node {
	public String name;
	public String value;
	public int type; // object: 0	array: 1	string: 2 number: 3
	ArrayList<Node> childs;
	
	public Node() {
		super();
		this.name = "Doc";
		this.value = "None";
		this.type = -1;
		this.childs = new ArrayList<Node>();
	}
	
	public Node(String name, String value, int type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		this.childs = new ArrayList<Node>();
	}
	
	public void addChild(Node n) {
		childs.add(n);
	}
	
	public Node findByName(String name) {	
		for (Node child : childs) {
			String cname = child.name.replace("\"", "");
			if (cname.equals(name)) {
				return child;
			}
		}
		if (name.contains("[") || name.contains("]")) {
			String tempname = name.substring(0, name.indexOf("["));
			for (Node child : childs) {
				String tempcname = child.name.replace("\"", "");
				if (tempcname.equals(tempname)) {
					for (Node cc : child.childs) {
						String cname = cc.name.replace("\"", "");
						if (cname.equals(name)) {
							return cc;
						}
					}
				}
			}
		}
		return null;
	}
	
	public void constructTree(String name, String value) {
		if (Lex.isString(value) == 0) {
			Node kid = new Node(name, value, 2);
			this.addChild(kid);
		}
		else if (Lex.isNumber(value) == 0) {
			Node kid = new Node(name, value, 3);
			this.addChild(kid);
		}
		else if (value.substring(0, 1).equals("{")) {
			Node kid = new Node(name, value, 0);
			this.addChild(kid);
			value = value.substring(1, value.lastIndexOf("}"));
			
			char[] cuts = value.toCharArray();
			for (int i = 0; i < cuts.length; i++) {
				if (cuts[i] == ',') {
					try {
						for (int j = i; j >= -1; j--) {
							if (cuts[j] == ']' || cuts[j] == '[') {
								if (cuts[j] == ']') {
									try {
										for (int k = i; k <= cuts.length; k++) {
											if (cuts[k] == '[' || cuts[k] == ']') {
												if (cuts[k] == '[') {
													cuts[i] = ';';
													break;
												}
												else {
													break;
												}
											}
										}
									} catch(IndexOutOfBoundsException e) {
										cuts[i] = ';';
									}								
								}
								else {
									break;
								}
							}
						}
					}catch (IndexOutOfBoundsException e) {
						cuts[i] = ';';
					}
				}			
			}
			
			for (int i = 0; i < cuts.length; i++) {
				if (cuts[i] == ';') {
					try {
						for (int j = i; j >= -1; j--) {
							if (cuts[j] == '}' || cuts[j] == '{') {
								if (cuts[j] == '}') {
									try {
										for (int k = i; k <= cuts.length; k++) {
											if (cuts[k] == '{' || cuts[k] == '}') {
												if (cuts[k] == '{') {
													cuts[i] = '/';
													break;
												}
												else {
//													cuts[i] = ',';
													break;
												}
											}
										}
									} catch(IndexOutOfBoundsException e) {
										cuts[i] = '/';
									}								
								}
								else {
//									cuts[i] = ',';
									break;
								}
							}
						}
					}catch (IndexOutOfBoundsException e) {
						cuts[i] = '/';
					}
				}			
			}
			
			for (int i = 0; i < cuts.length; i++) {
				if (cuts[i] == ';')
					cuts[i] = ',';
			}
			
			String newvalue = String.valueOf(cuts);
			
			if (newvalue.indexOf("/") == -1) {
				String str = newvalue;
				String newname = str.substring(0, str.indexOf(":"));
				String new_value = str.substring(str.indexOf(":") + 1);
				kid.constructTree(newname, new_value);
			}
			else {
				String[] inner = newvalue.split("/");
				for (String str : inner) {
					String newname = str.substring(0, str.indexOf(":"));
					String new_value = str.substring(str.indexOf(":") + 1);
					kid.constructTree(newname, new_value);
				}
			}		
		}
		else if (value.substring(0, 1).equals("[")) {
			Node kid = new Node(name, value, 1);
			this.addChild(kid);
			value = value.substring(1, value.lastIndexOf("]"));
			
			
			char[] cuts = value.toCharArray();
			for (int i = 0; i < cuts.length; i++) {
				if (cuts[i] == ',') {
					try {
						for (int j = i; j >= -1; j--) {
							if (cuts[j] == ']' || cuts[j] == '[') {
								if (cuts[j] == ']') {
									try {
										for (int k = i; k <= cuts.length; k++) {
											if (cuts[k] == '[' || cuts[k] == ']') {
												if (cuts[k] == '[') {
													cuts[i] = ';';
													break;
												}
												else {
													break;
												}
											}
										}
									} catch(IndexOutOfBoundsException e) {
										cuts[i] = ';';
									}								
								}
								else {
									break;
								}
							}
						}
					}catch (IndexOutOfBoundsException e) {
						cuts[i] = ';';
					}
				}			
			}
			
			for (int i = 0; i < cuts.length; i++) {
				if (cuts[i] == ';') {
					try {
						for (int j = i; j >= -1; j--) {
							if (cuts[j] == '}' || cuts[j] == '{') {
								if (cuts[j] == '}') {
									try {
										for (int k = i; k <= cuts.length; k++) {
											if (cuts[k] == '{' || cuts[k] == '}') {
												if (cuts[k] == '{') {
													cuts[i] = '/';
													break;
												}
												else {
//													cuts[i] = ',';
													break;
												}
											}
										}
									} catch(IndexOutOfBoundsException e) {
										cuts[i] = '/';
									}								
								}
								else {
//									cuts[i] = ',';
									break;
								}
							}
						}
					}catch (IndexOutOfBoundsException e) {
						cuts[i] = '/';
					}
				}			
			}
			
			for (int i = 0; i < cuts.length; i++) {
				if (cuts[i] == ';')
					cuts[i] = ',';
			}
			
			String newvalue = String.valueOf(cuts);
			
			if (newvalue.indexOf("/") == -1) {
				kid.constructTree(name + "[1]", newvalue);
			}
			else {
				int index = 1;
				String[] inner = newvalue.split("/");
				for (String str : inner) {
					kid.constructTree(name + "[" + index + "]", str);
					index++;
				}
			}
			
		}
	}
}
