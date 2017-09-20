import java.util.ArrayList;

public class Search {
	public static int searchJson(String filename, String path) {
		ArrayList<String> tokens = ReadInput.getTokens(filename);
		int errno = Paser.jasonPaser(tokens);
		if (errno != 0) {
			System.out.println("The json file is not valid, error message is: ");
			Paser.judgeJson(filename);
		}
		
		
		String rootValue = "";
		for (String str : tokens) {
			rootValue += str;
		}
		Node tree = new Node();
		tree.constructTree("root", rootValue);
		
		if (path.indexOf("/") == -1) {
			System.out.println("null");
			return 1;
		}
		
		if (path.equals("/")) {
			System.out.println("name: " + tree.childs.get(0).name + "\ntype: object" + "\nvalue:\n" + tree.childs.get(0).value);
		}
		else {
			String tempath = path.substring(1);
			String[] records = tempath.split("/");
			Node node = tree.childs.get(0);
			for(String record : records) {
				node = node.findByName(record);
				if (node == null) {
					System.out.println("null");
					return 1;
				}
			}
			String tempname = node.name;
			String tempvalue = node.value;
			String temptype = "";
			switch(node.type) {
			case 0:
				temptype = "object";
				break;
			case 1:
				temptype = "array";
				break;
			case 2:
				temptype = "string";
				break;
			case 3:
				temptype = "number";
				break;
			}
			
			System.out.println("name: " + tempname + "\ntype: " + temptype + "\nvalue:\n" + tempvalue);
		}
		
		return 0;
	}
	
//	public static void constructTree(Node parent, String name, String value) {
//		if (Lex.isString(value) == 0) {
//			Node kid = new Node(name, value, 2);
//			parent.addChild(kid);
//		}
//		else if (Lex.isNumber(value) == 0) {
//			Node kid = new Node(name, value, 3);
//			parent.addChild(kid);
//		}
//		else if (value.substring(0, 1).equals("{")) {
//			Node kid = new Node(name, value, 0);
//			parent.addChild(kid);
//			value = value.substring(1, value.lastIndexOf("}"));
//			String[] inner = value.split(",");
//			for (String str : inner) {
//				constructTree(kid, str.substring(0, str.indexOf(":")), str.substring(str.indexOf(":") + 1));
//			}
//		}
//		else if (value.substring(0, 1).equals("[")) {
//			Node kid = new Node(name, value, 1);
//			parent.addChild(kid);
//			value = value.substring(1, value.lastIndexOf("]"));
//			String[] inner = value.split(",");
//			int index = 1;
//			for (String str : inner) {
//				constructTree(kid, name + "[" + index + "]", str);
//				index++;
//			}
//		}
//	}
}
