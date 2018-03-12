// Name: Sri Chandra Lekha Kommuri
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Encoder {
		
	public static void main(String args[]) {
		if(args.length != 2) {
			System.exit(0);
		}
		String fileName = args[0];
		int bitLength = Integer.parseInt(args[1]);
		Encoder encoder = new Encoder();
		encoder.encoding(bitLength, fileName);
	}
	
	void encoding(int bitLength, String fileName) {
		double MAX_TABLE_SIZE = java.lang.Math.pow(2, bitLength);
		Map<String, Integer> table = new HashMap<String, Integer>(); // Table for strings and codes  
		
		for(int i = 0; i < 256; i++) { // storing ASCII 256 characters and their ASCII values to table
			String symbol = (char)i + "";
			table.put(symbol, i);
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			File f = new File(fileName.split("\\.")[0] + ".lzw");
			OutputStreamWriter osw = new OutputStreamWriter((new FileOutputStream(f)),"UTF-16"); // Writing to .lzw file as UTF-16 characters
			
			String string = null;
			int num;
			char symbol;
			
			while((num = br.read()) != -1) { // while there are still input characters to read
				symbol = (char)num;
				String newSymbol;
				
				if(string == null) {
					newSymbol = symbol + "";
				}
				else {
					newSymbol = string + symbol;
				}
				
				Integer code = table.get(newSymbol);
				
				if(code != null) {
					string = newSymbol;
				}
				else {
					//System.out.println(table.get(string));
					osw.write(table.get(string));
					if(table.size() < MAX_TABLE_SIZE) { // if table is not full
						table.put(newSymbol, table.size()); // newSymbol now has a code
					}
					
					string = symbol + "";
				}
				
			}
			
			//System.out.println(table.get(string));
			osw.write(table.get(string));
			br.close();
			osw.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
