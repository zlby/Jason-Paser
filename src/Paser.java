import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

public class Paser {
	public static int jasonPaser(ArrayList<String> s) {
		/*
		 * J	0
		 * O	1
		 * O'	2
		 * M	3
		 * M'	4
		 * P	5
		 * A	6
		 * A'	7
		 * E	8
		 * E'	9
		 * V	10
		 * {	11
		 * [	12
		 * ,	13
		 * :	14
		 * ]	15
		 * }	16
		 * string		17
		 * number	18
		 * true		19
		 * false		20
		 * null		21
		 * $			22
		 * exp			23
		 * 
		 */
		
		String[][] t = new String[23][23];
		
		for (int i = 0; i < 23; i++) {
			for (int j = 0; j < 23; j++) {
				t[i][j] = "error";
			}
		}
		
		t[0][11] = "1";
		t[0][12] = "6";
		t[1][11] = "11,2";
		t[2][16] = "16";
		t[2][17] = "3,16";
		t[3][17] = "5,4";
		t[4][13] = "13,3";
		t[4][16] = "23";
		t[5][17] = "17,14,10";
		t[6][12] = "12,7";
		t[7][11] = "8,15";
		t[7][12] = "8,15";
		t[7][15] = "15";
		t[7][17] = "8,15";
		t[7][18] = "8,15";
		t[7][19] = "8,15";
		t[7][20] = "8,15";
		t[7][21] = "8,15";
		t[8][11] = "10,9";
		t[8][12] = "10,9";
		t[8][17] = "10,9";
		t[8][18] = "10,9";
		t[8][19] = "10,9";
		t[8][20] = "10,9";
		t[8][21] = "10,9";
		t[9][13] = "13,8";
		t[9][15] = "23";
		t[10][11] = "1";
		t[10][12] = "6";
		t[10][17] = "17";
		t[10][18] = "18";
		t[10][19] = "19";
		t[10][20] = "20";
		t[10][21] = "21";
		
		Stack<String> inputStack = new Stack<String>();
		for (int i = s.size() - 1; i >= 0; i--) {
			inputStack.push(s.get(i));
		}
		
		Stack<String> st = new Stack<String>();
		st.push("0");
		
		try {
			while(!st.isEmpty()) {
				if(st.peek().equals("23")) {
					st.pop();
					continue;
				}
				
				if (st.peek().equals(String.valueOf(transfer(inputStack.peek())))) {
					inputStack.pop();
					st.pop();
				}
				else if (t[Integer.parseInt(st.peek())][transfer(inputStack.peek())].equals("error")) {
					return inputStack.size();
				}
				else {
					String str = t[Integer.parseInt(st.peek())][transfer(inputStack.peek())];
//					System.out.println(st.peek() + "---" + str);
					st.pop();
					
					String[] ss = str.split(",");
					for (int i = ss.length - 1; i >= 0; i--) {
						st.push(ss[i]);
					}				
				}
			}
		} catch(IndexOutOfBoundsException e) {
			return inputStack.size();
		}
		
		
		return 0;
	}
	
	public static int transfer(String s) {
		if (s.equals("J"))
			return 0;
		if (s.equals("O"))
			return 1;
		if (s.equals("o"))
			return 2;
		if (s.equals("M"))
			return 3;
		if (s.equals("m"))
			return 4;
		if (s.equals("P"))
			return 5;
		if (s.equals("A"))
			return 6;
		if (s.equals("a"))
			return 7;
		if (s.equals("E"))
			return 8;
		if (s.equals("e"))
			return 9;
		if (s.equals("V"))
			return 10;
		if (Lex.isLeftBrac(s) == 0)
			return 11;
		if (Lex.isLeftMid(s) == 0)
			return 12;
		if (Lex.isComma(s) == 0)
			return 13;
		if (Lex.isColon(s) == 0)
			return 14;
		if (Lex.isRightMid(s) == 0)
			return 15;
		if (Lex.isRightBrac(s) == 0)
			return 16;
		if (Lex.isString(s) == 0)
			return 17;
		if (Lex.isNumber(s) == 0)
			return 18;
		if (Lex.isTrue(s) == 0)
			return 19;
		if (Lex.isFalse(s) == 0)
			return 20;
		if (Lex.isNull(s) == 0)
			return 21;		
		return -1;
	}
	
	public static void judgeJson(String s) {
		ArrayList<String> tokens = new ArrayList<String>();
		tokens = ReadInput.getTokens(s);
		
		int errno = jasonPaser(tokens);
		if (errno == 0) {
			System.out.println("Valid!");
		}
		else {
			int errplace = tokens.size() - errno;
			String errtoken = tokens.get(errplace);
			ArrayList<String> source = new ArrayList<String>();
			try {
				source = ReadInput.readFile(s);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int all = 0;
			String connect = "";
			for (String str : source) {
				all += str.length();
				connect += str;
			}
			int[] sourceLine = new int[all];
			int index = 0;
			for (int i = 0; i < source.size(); i++) {
				for (int j = 0; j < source.get(i).length(); j++){
					sourceLine[index] = i+1;
					index++;
				}
			}
			
			int pretokens = 0;
			for (int i = 0; i < errplace; i++) {
				if (tokens.get(i).equals(errtoken))
					pretokens++;
			}
			
			int in = -1;
			for (int i = 0; i <= pretokens; i++) {
				in = connect.indexOf(errtoken, in+1);
			}
			
//			System.out.println(pretokens);
//			System.out.println(errtoken);
//			System.out.println(in);
			
			int errline = sourceLine[in];
			
			System.out.println("Error!   line: " + errline + "  token: " + errtoken);
		}
	}
}
