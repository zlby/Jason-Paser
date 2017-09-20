import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Pretty {
	public static int prettifyJson(String s) {
		ArrayList<String> tokens = new ArrayList<String>();
		tokens = ReadInput.getTokens(s);
		
		int errno = Paser.jasonPaser(tokens);
		
		if (errno != 0) {
			System.out.println("It is not a valid json file, error message is: ");
			Paser.judgeJson(s);
			return 1;
		}
		
		String newfile = s.substring(0, s.indexOf("."));
		newfile = newfile.concat(".pretty.json");
//		System.out.println(newfile);
		
		try {
			File file = new File(newfile);
			if(!file.exists())
	            file.createNewFile();
			FileOutputStream bw = new FileOutputStream(file, false);
			int space = 0;
			for (String token : tokens) {
				if (Lex.isComma(token) == 0) {
					bw.write(token.getBytes("utf-8"));
					bw.write("\r\n".getBytes("utf-8"));
					for (int i = 0; i < space; i++) {
						bw.write(" ".getBytes("utf-8"));
					}
				}
				else if (Lex.isLeftBrac(token) == 0 || Lex.isLeftMid(token) == 0) {
					bw.write(token.getBytes("utf-8"));
					bw.write("\r\n".getBytes("utf-8"));
					space += 4;
					for (int i = 0; i < space; i++) {
						bw.write(" ".getBytes("utf-8"));
					}
				}
				else if (Lex.isRightMid(token) == 0 || Lex.isRightBrac(token) == 0) {					
					bw.write("\r\n".getBytes("utf-8"));
					space -= 4;
					for (int i = 0; i < space; i++) {
						bw.write(" ".getBytes("utf-8"));
					}
					bw.write(token.getBytes("utf-8"));
				}
				else if (Lex.isColon(token) == 0) {
					bw.write(token.getBytes("utf-8"));
					bw.write(" ".getBytes("utf-8"));				
				}
				else {
					bw.write(token.getBytes("utf-8"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done!");
		return 0;
	}
}
