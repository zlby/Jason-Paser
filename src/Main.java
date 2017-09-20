import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String args[]) 
	{		
		if (args.length == 1) {
			String filename = args[0];
			Paser.judgeJson(filename);
		}
		
		if (args.length == 2) {
			if (args[0].equals("-pretty")) {
				String filename = args[1];
				Pretty.prettifyJson(filename);
			}
			else {
				System.out.println("Please enter right argument!");
			}
		}
		
		if (args.length == 3) {
			if (args[0].equals("-find")) {
				String filename = args[1];
				String path = args[2];
				Search.searchJson(filename, path);
			}
			else {
				System.out.println("Please enter right argument!");
			}
		}
		
//		Paser.judgeJson("test.json");
	}
}
