import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CompilationEngine {
	public String output = "";
	
	private int index = 0;
	private boolean end = false;
	private List<Token> tokens = new ArrayList<Token>();
	
	public HashMap<TokenType, String> tokenTypesMap = new HashMap<TokenType, String>();
	
	private List<String> classVarsList = new ArrayList<String>();
	private List<String> typesList = new ArrayList<String>();
	private List<String> subroutinesList = new ArrayList<String>();
	private List<String> statementsList = new ArrayList<String>();
	private List<String> expressionStopSymbolsList = new ArrayList<>();
	private List<String> opsSymbolsList = new ArrayList<>();
	
	public CompilationEngine(List<Token> tokens) {
		this.tokens = tokens;
		
		classVarsList.add("field");
		classVarsList.add("static");
		
		typesList.add("int");
		typesList.add("char");
		typesList.add("boolean");
		
		subroutinesList.add("constructor");
		subroutinesList.add("function");
		subroutinesList.add("method");
		
		statementsList.add("let");
		statementsList.add("if");
		statementsList.add("while");
		statementsList.add("do");
		statementsList.add("return");
		
		tokenTypesMap.put(TokenType.KEYWORD, "keyword");
		tokenTypesMap.put(TokenType.SYMBOL, "symbol");
		tokenTypesMap.put(TokenType.INT_CONSTANT, "integerConstant");
		tokenTypesMap.put(TokenType.STR_CONSTANT, "stringConstant");
		tokenTypesMap.put(TokenType.IDENTIFIER, "identifier");
		
		expressionStopSymbolsList.add(";");
		expressionStopSymbolsList.add(",");
		expressionStopSymbolsList.add("=");
		expressionStopSymbolsList.add("[");
		expressionStopSymbolsList.add("]");
		expressionStopSymbolsList.add("(");
		expressionStopSymbolsList.add(")");
		
		opsSymbolsList.add("+");
		opsSymbolsList.add("-");
		opsSymbolsList.add("*");
		opsSymbolsList.add("/");
		opsSymbolsList.add("&");
		opsSymbolsList.add("|");
		opsSymbolsList.add("<");
		opsSymbolsList.add(">");
		opsSymbolsList.add("=");
	}
	
	public String compile() {
		String str = "";
		
		while (!end) {
			str += compileClass();
		}
		
		return str;
	}
	
	private String compileClass() {
		String str = "<class>\n";
		
		str += "<keyword> " + this.tokens.get(index).value + " </keyword>\n";
		
		str += generateXml("identifier");
		str += generateXml("symbol");
		
		str += compileClassVarDec();
		str += compileSubroutineDec();
		
		str += "<symbol> " + this.tokens.get(index).value + " </symbol>\n"; // Get the closing bracket of the class.
		
		str += "</class>\n";
		
		end = true;
		
		return str;
	}
	
	private String compileClassVarDec() {
		String str = "";
		
		while(true) {
			index++;
			if (!classVarsList.contains(this.tokens.get(index).value)) {
				break;
			}
			
			str += "<classVarDec>\n";
			
			str += "<keyword> " + this.tokens.get(index).value + " </keyword>\n";
			
			// index++;
			// if (!typesList.contains(this.tokens.get(index).value)) {
				// break;
			// }
			
			str += generateXml();
			
			while(!this.tokens.get(index).value.equals(";")) {
				/* index++;
				if (this.tokens.get(index).value.equals(",") || this.tokens.get(index).value.equals(";")) {
					str += " <symbol>" + this.tokens.get(index).value + " </symbol>";
				} else {
					str += " <identifier>" + this.tokens.get(index).value + " </identifier>";
				} */
				
				str += generateXml();
				
			}
			
			str += "</classVarDec>\n";
		}
		
		index--;
		
		return str;
	}
	
	private String compileSubroutineDec() {
		String str = "";
		
		while(true) {
			index++; // Get the subroutine type.
			
			// If this is not a subroutine type break the loop.
			if (!subroutinesList.contains(this.tokens.get(index).value)) {
				break;
			}
			
			str += "<subroutineDec>\n";
			
			str += "<keyword> " + this.tokens.get(index).value + " </keyword>\n";
			
			str += generateXml();
			
			index++; // Get the subroutine name.
			str += "<identifier> " + this.tokens.get(index).value + " </identifier>\n";
			
			str += compileParameterList();
			
			str += compileSubroutineBody();
			
			str += "</subroutineDec>\n";
		}
		
		return str;
	}
	
	private String compileParameterList() {
		String str = "";
		
		index++; // Get opening bracket of the parameters list.
		str += "<symbol> " + this.tokens.get(index).value + " </symbol>\n";
		str += "<parameterList>\n";
		
		// Generate the parameters list. 
		while(!this.tokens.get(index+1).value.equals(")")) {
			str += generateXml();
		}
		
		str += "</parameterList>\n";
		
		index ++ ;
		str += "<symbol> " + this.tokens.get(index).value + " </symbol>\n";
		
		return str;
	}
	
	private String compileSubroutineBody() {
		String str = "";
		
		str += "<subroutineBody>\n";
		
		index++; // Get opening curly bracket of the subroutine body.
		str += "<symbol> " + this.tokens.get(index).value + " </symbol>\n";
		
		str += compileVarDec();
		
		str += compileStatements();
		
		index++;  // Get closing curly bracket of the subroutine body.
		str += "<symbol> " + this.tokens.get(index).value + " </symbol>\n";
		
		str += "</subroutineBody>\n";
		
		return str;
	}
	
	private String compileVarDec() {
		String str = "";
		
		while(true) {
			if (!this.tokens.get(index+1).value.equals("var")) {
				break;
			}
			
			str += "<varDec>\n";
			
			index++;
			str += "<keyword> " + this.tokens.get(index).value + " </keyword>\n";
			
			str += generateXml(); // Get the type of the variable.
			
			while(!this.tokens.get(index+1).value.equals(";")) {
				str += generateXml();
			}
			
			str += generateXml(); // Get the ";" symbol.
			
			str += "</varDec>\n";
		}
		
		return str;
	}
	
	private String compileStatements() {
		String str = "<statements>\n";
		while(true) {
			if (!statementsList.contains(this.tokens.get(index+1).value)) {
				break;
			}
			
			String statementType = this.tokens.get(index+1).value;
			if (statementType.equals("let")) {
				str += compileLet();
			} else if (statementType.equals("if")) {
				str += compileIf();
			} else if (statementType.equals("while")) {
				str += compileWhile();
			} else if (statementType.equals("do")) {
				str += compileDo();
			} else if (statementType.equals("return")) {
				str += compileReturn();
			}
		}
		
		str += "</statements>\n";
		
		return str;
	}
	
	private String compileLet() {
		String str = "<letStatement>\n";
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add("=");
		// stopSymbols.add("(");
		// stopSymbols.add(")");
		stopSymbols.add("[");
		stopSymbols.add("]");
		
		str += generateXml(); // Get the let statement.
		str += generateXml(); // Get the variable name.
		str += generateXml(); // Get the "=" or "[" symbol if it is an array.
		
		while (!this.tokens.get(index).value.equals("=")) {
			str += compileExpression(stopSymbols);
			
			str += generateXml(); // Get the symbol that is not an expression.
		}
		
		List<String> stopSymbols2 = new ArrayList<String>();
		stopSymbols2.add(";");
		stopSymbols2.add(",");
		stopSymbols2.add("=");
		// stopSymbols2.add("[");
		// stopSymbols2.add("]");
		
		while (!this.tokens.get(index).value.equals(";")) {
			str += compileExpression(stopSymbols2);
			
			str += generateXml(); // Get the symbol that is not an expression.
		}
		
		// str += generateXml(); // Get the ";" symbol.
		
		str += "</letStatement>\n";
		
		return str;
	}
	
	private String compileIf() {
		String str = "<ifStatement>\n";
		
		str += generateXml(); // Get the if statement.
		str += generateXml(); // Get the opening bracket.
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add(";");
		stopSymbols.add("{");
		stopSymbols.add("}");
		// stopSymbols.add("(");
		stopSymbols.add(")");
		
		while((!this.tokens.get(index).value.equals(")") && !this.tokens.get(index+1).value.equals("{"))) {
			str += compileExpression(stopSymbols);
			
			str += generateXml(); // Get the symbol that is not an expression.
		}
		
		str += generateXml(); // Get the opening curly bracket.
		str += compileStatements();
		str += generateXml(); // Get the closing curly bracket.
		
		if (this.tokens.get(index+1).value.equals("else")) {
			str += generateXml(); // Get the else statement.
			str += generateXml(); // Get the opening curly bracket.
			str += compileStatements();
			str += generateXml(); // Get the closing curly bracket.
		}
		
		str += "</ifStatement>\n";
		
		return str;
	}
	
	private String compileWhile() {
		String str = "<whileStatement>\n";
		
		str += generateXml(); // Get the if statement.
		str += generateXml(); // Get the opening bracket.
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add(";");
		stopSymbols.add("{");
		stopSymbols.add("}");
		// stopSymbols.add("(");
		stopSymbols.add(")");
		
		while((!this.tokens.get(index).value.equals(")") && !this.tokens.get(index+1).value.equals("{"))) {
			str += compileExpression(stopSymbols);
			
			str += generateXml(); // Get the symbol that is not an expression.
		}
		
		str += generateXml(); // Get the opening curly bracket.
		str += compileStatements();
		str += generateXml(); // Get the closing curly bracket.
		
		str += "</whileStatement>\n";
		
		return str;
	}
	
	private String compileDo() {
		String str = "<doStatement>\n";
		
		str += generateXml(); // Get the do statement.
		str += generateXml(); // Get the class instance or static class call name.
		
		// If the next symbol is ".", this call is to a subroutine in another class 
		if (this.tokens.get(index+1).value.equals(".")) {
			str += generateXml(); // Get the "." symbol.
			str += generateXml(); // Get the subroutine call name.
		}
		
		str += generateXml(); // Get the opening bracket of the subroutine call.
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add(";");
		stopSymbols.add(",");
		stopSymbols.add("{");
		stopSymbols.add("}");
		// stopSymbols.add("(");
		stopSymbols.add(")");
		str += compileExpressionList(";", stopSymbols);
		
		str += generateXml(); // Get the closing bracket of the subroutine call.
		str += generateXml(); // Get the ";" symbol.
		
		str += "</doStatement>\n";
		
		return str;
	}
	
	private String compileReturn() {
		String str = "<returnStatement>\n";
		
		str += generateXml(); // Get the return statement.
		
		if (this.tokens.get(index+1).value.equals(";")) {
			str += generateXml(); // Get the ";" symbol.
			return str += "</returnStatement>\n";
		}
		
		while (!this.tokens.get(index).value.equals(";")) {
			str += compileExpression(this.expressionStopSymbolsList);
			
			str += generateXml(); // Get the symbol that is not an expression.
		}
		
		// str += generateXml(); // Get the ";" symbol.
		
		str += "</returnStatement>\n";
		
		return str;
	}
	
	private String compileExpression(List<String> stopSymbolsList) {
		if (stopSymbolsList.contains(this.tokens.get(index+1).value)) {
			return "";
		}
		
		String str = "<expression>\n";
		
		while (true) {
			if (stopSymbolsList.contains(this.tokens.get(index+1).value)) {
				break;
			}
			
			else if (this.tokens.get(index+1).value.equals("(")) { // Indicates this is a part of a expression.
				str += compileTerm();
			}
			
			else if ((this.tokens.get(index+1).value.equals("-") || this.tokens.get(index+1).value.equals("~"))
					&& (this.tokens.get(index+2).type == TokenType.INT_CONSTANT || this.tokens.get(index+2).type == TokenType.IDENTIFIER 
					|| this.tokens.get(index+2).value.equals("(")
					)) { // Indicates this is a unary ops.
				str += compileTerm();
			}
			
			else if (this.tokens.get(index+1).type == TokenType.INT_CONSTANT || this.tokens.get(index+1).type == TokenType.STR_CONSTANT
					|| this.tokens.get(index+1).type == TokenType.KEYWORD) {
				str += compileTerm();
			}
			
			else if (this.tokens.get(index+1).type == TokenType.IDENTIFIER) {
				if (this.tokens.get(index+2).value.equals("[")) { // Indicates the this part is an array.
					// str += generateXml(); // 
					// str += generateXml(); // Get the "[" symbol.
					
					// str += compileExpression(this.expressionStopSymbolsList);
					str += compileTerm();
				} else if (this.tokens.get(index+2).value.equals(".") || this.tokens.get(index+2).value.equals("(")) { // Indicates the this part is a subroutine call.
					str += compileTerm();
				} else { // Indicates a standard variable name.
					str += compileTerm();
				}
			}
			
			if (!stopSymbolsList.contains(this.tokens.get(index+1).value)) {
				str += generateXml(); // Get the symbol;
			}
			
		}
		
		str += "</expression>\n";
		
		return str;
	}
	
	private String compileTerm() {
		String str = "<term>\n";
		
		if (this.tokens.get(index+1).value.equals("(")) { // Indicates this is a part of a expression.
			str += generateXml(); // Get the "(" symbol.
			
			List<String> stopSymbols = new ArrayList<String>();
			stopSymbols.add(")");
			
			str += compileExpression(stopSymbols);
			
			str += generateXml(); // Get the ")" symbol.
		}
		
		else if ((this.tokens.get(index+1).value.equals("-") || this.tokens.get(index+1).value.equals("~"))
				&& (this.tokens.get(index+2).type == TokenType.INT_CONSTANT || this.tokens.get(index+2).type == TokenType.IDENTIFIER 
				|| this.tokens.get(index+2).value.equals("(")
				)) { // Indicates this is a unary ops.
			str += generateXml(); // Get the unary op.
			str += compileTerm(); // Get the term either variable name or constant.
		}
		
		else if (this.tokens.get(index+1).type == TokenType.INT_CONSTANT || this.tokens.get(index+1).type == TokenType.STR_CONSTANT
				|| this.tokens.get(index+1).type == TokenType.KEYWORD) {
			str += generateXml(); // Get the term either variable name or constant.
		}
		
		else if (this.tokens.get(index+1).type == TokenType.IDENTIFIER) {
			if (this.tokens.get(index+2).value.equals("[")) { // Indicates the this part is an array.
				str += generateXml(); // 
				str += generateXml(); // Get the "[" symbol.
				
				List<String> stopSymbols = new ArrayList<String>();
				stopSymbols.add("]");
				
				str += compileExpression(stopSymbols);
				
				str += generateXml(); // Get the "]" symbol.
			} else if (this.tokens.get(index+2).value.equals(".") || this.tokens.get(index+2).value.equals("(")) { // Indicates the this part is a subroutine call.
				str += compileSubroutineCall();
			} else { // Indicates a standard variable name.
				str += generateXml(); // Get the term either variable name or constant.
			}
		}
		
		str += "</term>\n";
		
		return str;
	}
	
	private String compileExpressionList(String endingSymbol, List<String> stopSymbolsList) {
		String str = "<expressionList>\n";
		
		// if (this.tokens.get(index+1).value.equals(endingSymbol)) {
		if (stopSymbolsList.contains(this.tokens.get(index+1).value)) {
			return str += "</expressionList>\n";
		}
		
		// if (stopSymbolsList.contains(this.tokens.get(index+2).value)) {
		while (!this.tokens.get(index+2).value.equals(endingSymbol)) {
			str += compileExpression(stopSymbolsList);
			
			if (!this.tokens.get(index+2).value.equals(endingSymbol)) {
				str += generateXml(); // Get the symbol that is not an expression.
			}
		}
		
		str += "</expressionList>\n";
		
		return str;
	}
	
	private String compileSubroutineCall() {
		String str = "";
		
		str += generateXml(); // Get the class instance or static class call name.
		
		// If the next symbol is ".", this call is to a subroutine in another class 
		if (this.tokens.get(index+1).value.equals(".")) {
			str += generateXml(); // Get the "." symbol.
			str += generateXml(); // Get the subroutine call name.
		}
		
		str += generateXml(); // Get the opening bracket of the subroutine call.
		
		str += compileExpressionList(";", this.expressionStopSymbolsList);
		
		str += generateXml(); // Get the closing bracket of the subroutine call.
		
		return str;
	}
	
	private boolean hasNext() {
		return index < this.tokens.size();
	}
	
	private String generateXml() {
		String str = "";
		
		index++;
		Token token = this.tokens.get(index);
		str += "<" + tokenTypesMap.get(token.type) + "> " + token.value + " </" + tokenTypesMap.get(token.type) + ">\n";
		
		return str;
	}
	
	private String generateStaticXml() {
		String str = "";
		
		Token token = this.tokens.get(index);
		str += "<" + tokenTypesMap.get(token.type) + "> " + token.value + " </" + tokenTypesMap.get(token.type) + ">\n";
		
		return str;
	}
	
	private String generateXml(String type) {
		String str = "";
		
		index++;
		Token token = this.tokens.get(index);
		str += "<" + type + "> " + token.value + " </" + type + ">\n";
		
		return str;
	}
	
	private String generateXml(String value, String type) {
		String str = "";
		
		str += "<" + type + "> " + value + " </" + type + ">\n";
		
		return str;
	}
}
