import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadInput {
	public static ArrayList<String> readFile(String s) throws FileNotFoundException
	{
		File file = new File(s);
		FileInputStream fileName = new FileInputStream(file);
		ArrayList<String> strarr = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(fileName));
		String temp = "";		
		try {
			for(;;)
			{
				temp = br.readLine();
				if (temp == null)
					break;
				strarr.add(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < strarr.size(); i++) {
			strarr.set(i, strarr.get(i).replaceAll(" ", ""));//space
			strarr.set(i, strarr.get(i).replaceAll("	", ""));//tab
		}
		return strarr;
	}
	
	public static ArrayList<String> getTokens(String s) {
		ArrayList<String> strarr = new ArrayList<String>();
		try {
			strarr = readFile(s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = "";
		for (int i = 0; i < strarr.size(); i++) {
			str += strarr.get(i);
		}
		
//		str = str.replaceAll("	", "");//clearing tab
		
		char[] cs = str.toCharArray();
		String[] ss = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			ss[i] = String.valueOf(cs[i]);
		}
		
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < str.length(); i++) {
			if (Lex.isColon(ss[i]) == 0 || Lex.isComma(ss[i]) == 0 || Lex.isLeftBrac(ss[i]) == 0 || Lex.isLeftMid(ss[i]) == 0
					|| Lex.isRightBrac(ss[i]) == 0 || Lex.isRightMid(ss[i]) == 0)
				res.add(ss[i]);
			else {
				String temp = "";
				while(Lex.isColon(ss[i]) == 1 && Lex.isComma(ss[i]) == 1 && Lex.isLeftBrac(ss[i]) == 1 && Lex.isLeftMid(ss[i]) == 1
						&& Lex.isRightBrac(ss[i]) == 1 && Lex.isRightMid(ss[i]) == 1) {
					temp += ss[i];
					i++;
				}
				i--;
				res.add(temp);
			}
		}
		
		return res;
	}
}
