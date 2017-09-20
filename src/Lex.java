import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lex {

	public static int isNumber(String num)
	{
		/** Parsing Table:
		  		0 	1	2	3	4	5	6
		 		0	+	-	.	c	d	e
		 ----------------------------
		 0	|	1		2		3
		 1	|				4			6
		 2	|	1				3
		 3	|				4		3	6
		 4	|						5
		 5	|						5	6
		 6	|		7	7			8
		 7	|						8
		 8	|						8
		 here: c = 1-9  d = 0-9  e = E|e
		 accepting state: 1,3,5,8
		 **/
		int[][] table = new int[9][7];
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				table[i][j] = -1;
			}
		}
		table[0][0] = 1;
		table[0][2] = 2;
		table[0][4] = 3;
		table[1][3] = 4;
		table[1][6] = 6;
		table[2][0] = 1;
		table[2][4] = 3;
		table[3][3] = 4;
		table[3][5] = 3;
		table[3][6] = 6;
		table[4][5] = 5;
		table[5][5] = 5;
		table[5][6] = 6;
		table[6][1] = 7;
		table[6][2] = 7;
		table[6][5] = 8;
		table[7][5] = 8;
		table[8][5] = 8;
		
		
		
		int state = 0;
		
		Pattern c = Pattern.compile("[1-9]");
		Pattern d = Pattern.compile("[0-9]");
		
		
		char[] input = num.toCharArray();
		int[] intinput = new int[num.length()];
		for (int i = 0; i < intinput.length; i++) {
			intinput[i] = -1;
		}
		
		for (int i = 0; i < input.length; i++) {
			Matcher mc = c.matcher(String.valueOf(input[i]));
			Matcher md = c.matcher(String.valueOf(input[i]));
			if (input[i] == '0')
				intinput[i] = 0;
			else if (input[i] == '+')
				intinput[i] = 1;
			else if (input[i] == '-')
				intinput[i] = 2;
			else if (input[i] == '.')
				intinput[i] = 3;
			else if (input[i] == 'e' || input[i] == 'E')
				intinput[i] = 6;
			else if (mc.matches())
				intinput[i] = 4;
			else if (md.matches())
				intinput[i] = 5;
		}
		
		try {
			for (int i = 0; i < input.length; i++) {
				if (intinput[i] == 0 && table[state][intinput[i]] == -1)
					intinput[i] = 5;
				if (intinput[i] == 4 && table[state][intinput[i]] == -1)
					intinput[i] = 5;
				state = table[state][intinput[i]];
			}
		} catch(IndexOutOfBoundsException e) {
			return 1;
		}
		
		
		if (state == 1 || state == 3 || state == 5 || state == 8)
			return 0;
		
		return 1;
	}
	
	public static int isString(String s) {
		char[] arr = s.toCharArray();
		if (s.length() < 2)
			return 1;
		if (arr[0] != '"' || arr[s.length() - 1] != '"')
			return 1;
		
		for (int i = 1; i < (s.length() - 1); i++) {
			if (arr[i] == '\\') {
				if ((arr[i+1] == '"' && (i+1) != (s.length()-1)) || arr[i+1] == '\\' || arr[i+1] == '/' || arr[i+1] == 'b' ||
						arr[i+1] == 'f' || arr[i+1] == 'n' || arr[i+1] == 'r' || arr[i+1] == 't')
					i++;
				else if (arr[i+1] == 'u') {
					String nu = String.valueOf(arr[i+2]);
					try {
						Integer.parseInt(nu);
					} catch (NumberFormatException e) {
						return 1;
					}
					
					nu = String.valueOf(arr[i+3]);
					try {
						Integer.parseInt(nu);
					} catch (NumberFormatException e) {
						return 1;
					}
					
					nu = String.valueOf(arr[i+4]);
					try {
						Integer.parseInt(nu);
					} catch (NumberFormatException e) {
						return 1;
					}
					
					nu = String.valueOf(arr[i+5]);
					try {
						Integer.parseInt(nu);
					} catch (NumberFormatException e) {
						return 1;
					}					
					i += 5;
				}
				else {
					return 1;
				}
			}
			else if (arr[i] == '"') {
				return 1;
			}
		}		
		return 0;
	}
	
	public static int isLeftBrac(String s) {
		if (s.equals("{"))
			return 0;
		return 1;
	}
	
	public static int isLeftMid(String s) {
		if (s.equals("[")) 
			return 0;
		return 1;
	}
	
	public static int isComma(String s) {
		if (s.equals(","))
			return 0;
		return 1;
	}
	
	public static int isColon(String s) {
		if (s.equals(":"))
			return 0;
		return 1;
	}
	
	public static int isRightMid(String s) {
		if (s.equals("]"))
			return 0;
		return 1;
	}
	
	public static int isRightBrac(String s) {
		if (s.equals("}"))
			return 0;
		return 1;
	}
	
	public static int isTrue(String s) {
		if (s.equals("true"))
			return 0;
		return 1;
	}
	
	public static int isFalse(String s) {
		if (s.equals("false"))
			return 0;
		return 1;
	}
	
	public static int isNull(String s) {
		if (s.equals("null"))
			return 0;
		return 1;
	}
	

}
