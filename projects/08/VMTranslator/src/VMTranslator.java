import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class VMTranslator {
	public VMTranslator() {
		
	}
	
	public void translate(String path) {
		File file = new File(path);
		
		String str = "";
		
		CodeWriter writer = new CodeWriter("Initialize");
		str += writer.generateInitAsm();
		
		// Check if path provided is referring to a file or directory.
		if (file.isFile()) {
			String filename = FileWrapper.getFileName(path, ".vm"); // Get the file name from the path provided.
			String route = FileWrapper.getRoute(path);
			
			str += translateFile(path, filename);
			
			FileWrapper.writeFile(route + "\\" + filename+".asm", str); // Write the translated machine code to a file.
			
			System.out.println("Single file compilation completed.");
		} else if (file.isDirectory()) {
			Path paths = Paths.get(path);
			String filename = paths.getFileName().toString();
			String route = path;
			
			File[] directoryList = file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".vm");
				}
			}); // Get the list of files existing in the directory.
			// Iterate through every file in the directory.
			for (File fil : directoryList) {
				System.out.println("File path: " + fil.getAbsolutePath());
				str += translateFile(fil.getAbsolutePath(), FileWrapper.getFileName(fil.getAbsolutePath(), ".vm"));
			}
			
			FileWrapper.writeFile(route + "\\" + filename+".asm", str); // Write the translated machine code to a file.
			
			System.out.println("Files in directory compilation completed.");
		} else {
			System.out.println("Error! Could not acces the path! Please provide a valid path.");
		}
		
	}
	
	public String translateFile(String path, String filename) {
		Parser parser = new Parser();
		
		// String filename = FileWrapper.getFileName(path); // Get the file name from the path provided.
		CodeWriter writer = new CodeWriter(filename);
		
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
				
				line = line.trim(); // Remove the string of white spaces.
				
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
					} else if (comdType == CommandType.BRANCH) {
						BranchComd comd = parser.branchComdMap.get(line_arr[0]);
						
						asms += writer.generate(comd, line_arr[1]);
					} else if (comdType == CommandType.FUNC) {
						FunctionComd comd = parser.functionComdMap.get(line_arr[0]);
						
						asms += writer.generate(comd, line_arr);
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
		
		return asms;
	}
	
	
}
