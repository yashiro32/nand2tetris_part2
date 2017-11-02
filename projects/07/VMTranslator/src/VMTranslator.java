import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class VMTranslator {
	public VMTranslator() {
		
	}
	
	public void translate(String path) {
		Parser parser = new Parser();
		CodeWriter writer = new CodeWriter();
		
		BufferedReader br = null;
		
		String asms = "";
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(path));
			
			while ((line = br.readLine()) != null) {
				int index = line.indexOf("/"); // Search for the start of a comment.
				
				// If index > -1, get the part of the string before the comment.
				if (index > -1) {
					line = line.substring(0, index);
				}
				
				// line = line.trim(); // Remove the string of white spaces.
				
				if (line.length() > 0) {
					String[] line_arr = line.split(" ");
					
					CommandType comdType = parser.getCommandType(line_arr[0]);
					
					if (comdType == CommandType.AL) {
						ArithmeticComd comd = parser.arithComdMap.get(line_arr[0]);
						
						asms += writer.generate(comd);
					} else if (comdType == CommandType.MEM) {
						MemoryComd comd = parser.memComdMap.get(line_arr[0]);
						MemorySeg seg = parser.memSegMap.get(line_arr[1]);
						
						asms += writer.generate(comd, seg, Integer.parseInt(line_arr[2]));
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		String filename = FileWrapper.getFileName(path); // Get the file name from the path provided.
		String route = FileWrapper.getRoute(path);
		FileWrapper.writeFile(route + "\\" + filename+".asm", asms); // Write the translated machine code to a file.
	}
	
	
}
