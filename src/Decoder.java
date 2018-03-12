//Name: Sri Chandra Lekha Kommuri
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Decoder {
	public static void main(String args[]) {
		if(args.length != 2) {
			System.exit(0);
		}
		String fileName = args[0];
		int bitLength = Integer.parseInt(args[1]);
		Decoder decoder = new Decoder();
		decoder.decoding(bitLength, fileName);
	}
	
	void decoding(int bitLength, String fileName) {
		double MAX_TABLE_SIZE = java.lang.Math.pow(2, bitLength);
		Map<Integer, String> table = new HashMap<Integer, String>();// Table for strings and codes
		
		for(int i = 0; i < 256; i++) {// storing ASCII 256 characters and their ASCII values to table
			String symbol = (char)i + "";
			table.put(i, symbol);
		}
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-16")); // Reading from .lzw file which contains UTF-16 characters
			File f = new File(fileName.split("\\.")[0] + "_decoded.txt");
			PrintWriter pw = new PrintWriter(f);
			
			int code = br.read();
			String string = table.get(code);
			//System.out.print(string);
			pw.write(string);
			
			while((code = br.read()) != -1) {// while there are still input characters to read
				String newString = table.get(code);
				if(newString == null) { // needed because sometimes the decoder may not yet have code!
					newString = string + string.charAt(0);
				}
				
				//System.out.print(newString);
				pw.write(newString);
				if(table.size() < MAX_TABLE_SIZE) {// if table is not full
					table.put(table.size(), string + newString.charAt(0));
				}
				string = newString;
				
			}
			br.close();
			pw.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
