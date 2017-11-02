
public class Main {
	public static void main(String[] args) {
		JackAnalyzer analyzer = new JackAnalyzer();
		analyzer.analyze("C:/Users/yashiro32/Documents/nand2tetris_part2/projects/10/ArrayTest/Main.jack");
		analyzer.analyze("C:/Users/yashiro32/Documents/nand2tetris_part2/projects/10/ExpressionLessSquare");
		analyzer.analyze("C:/Users/yashiro32/Documents/nand2tetris_part2/projects/10/Square");
		
		// analyzer.analyze("C:/Users/yashiro32/Documents/nand2tetris_part2/projects/10/Square/Square.jack");
	}
}
