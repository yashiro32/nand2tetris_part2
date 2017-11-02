import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class JackAnalyzer {
	
	public void analyze(String path) {
		File file = new File(path);
		
		String str = "";
		
		// Check if path provided is referring to a file or directory.
		if (file.isFile()) {
			String filename = FileWrapper.getFileName(path, ".jack"); // Get the file name from the path provided.
			String route = FileWrapper.getRoute(path);
			
			str += translateFile(path, filename);
			
			// FileWrapper.writeFile(route + "\\" + filename + "1T.xml", str); // Write the translated machine code to a file.
			
			System.out.println("Single file compilation completed.");
		} else if (file.isDirectory()) {
			Path paths = Paths.get(path);
			String filename = paths.getFileName().toString();
			String route = path;
			
			File[] directoryList = file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".jack");
				}
			}); // Get the list of files existing in the directory.
			// Iterate through every file in the directory.
			for (File fil : directoryList) {
				System.out.println("File path: " + fil.getAbsolutePath());
				str = translateFile(fil.getAbsolutePath(), FileWrapper.getFileName(fil.getAbsolutePath(), ".jack"));
				
				// FileWrapper.writeFile(route + "\\" + FileWrapper.getFileName(fil.getAbsolutePath(), ".jack") + "1T.xml", str); // Write the translated machine code to a file.
			}
			
			// FileWrapper.writeFile(route + "\\" + filename + "1T.xml", str); // Write the translated machine code to a file.
			
			System.out.println("Files in directory compilation completed.");
		} else {
			System.out.println("Error! Could not acces the path! Please provide a valid path.");
		}
	}
	
	public String translateFile(String path, String filename) {
		JackTokenizer tokenizer = new JackTokenizer();
		
		BufferedReader br = null;
		
		String jacks = "";
		
		String tokenizedXmls = "";
		String parsedXmls = "";
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(path));
				
			boolean commentStart = false;
			while ((line = br.readLine()) != null) {
				int index = line.indexOf("//"); // Search for the start of a comment.
				
				if (index == 0) {
					continue;
				}
				
				// If index > -1, get the part of the string before the comment.
				if (index > -1) {
					line = line.substring(0, index);
				}
				
				int startIndex = line.indexOf("/*"); // Search for the start of a comment.
				// int middleIndex = line.indexOf("*"); // Search for the start of a comment.
				int endIndex = line.indexOf("*/"); // Search for the start of a comment.
				
				if (startIndex == 0) {
					commentStart = true;
				}
				
				if (endIndex > -1) {
					commentStart = false;
				}
				
				if (commentStart) {
					continue;
				}
				
				// If index > -1, get the part of the string before the comment.
				if (startIndex > -1 && endIndex > -1) {
					String searchStr = line.substring(startIndex, endIndex+2);
					
					line = line.replace(searchStr, "");
				} else if (startIndex == -1 && endIndex > -1) {
					line = line.replace("*/", "");
				}
				
				// line = line.trim().replaceAll("\n ", "").replaceAll(" ", ""); // Remove the string of white spaces and newline.
				line = line.trim().replaceAll("\n", ""); // Remove the string of white spaces and newline.
				
				if (line.length() > 0) {
					jacks += line;
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
		
		// System.out.println(jacks);
		
		tokenizedXmls = tokenizer.tokenize(jacks);
		
		CompilationEngine engine = new CompilationEngine(tokenizer.tokensList);
		parsedXmls = engine.compile();
		
		// FileWrapper.writeFile(FileWrapper.getRoute(path) + "\\" + filename + "1T.xml", tokenizedXmls); // Write the tokenized string to a file.
		FileWrapper.writeFile(FileWrapper.getRoute(path) + "\\" + filename + "1.xml", parsedXmls); // Write the parsed string to a file.
		
		return parsedXmls;
	}
	
	
}
