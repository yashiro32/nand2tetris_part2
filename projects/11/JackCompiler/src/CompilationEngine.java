import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CompilationEngine {
	public String output = "";
	
	private String filename = "";
	private SymbolTable symTable = new SymbolTable();
	private VMWriter writer = new VMWriter();
	private String currentReturnType = "";
	private int currentNumArgs = 0;
	private int labelCount = 0;
	
	private int index = 0;
	private boolean end = false;
	private List<Token> tokens = new ArrayList<Token>();
	
	public HashMap<TokenType, String> tokenTypesMap = new HashMap<TokenType, String>();
	
	public HashMap<String, Kind> varKindMap = new HashMap<String, Kind>();
	public HashMap<String, Integer> constantMap = new HashMap<String, Integer>();
	public HashMap<Kind, MemSeg> kindMap = new HashMap<Kind, MemSeg>();
	public HashMap<String, OpsComd> opsMap = new HashMap<String, OpsComd>();
	
	private List<String> classVarsList = new ArrayList<String>();
	private List<String> typesList = new ArrayList<String>();
	private List<String> subroutinesList = new ArrayList<String>();
	private List<String> statementsList = new ArrayList<String>();
	private List<String> expressionStopSymbolsList = new ArrayList<>();
	private List<String> opsSymbolsList = new ArrayList<>();
	
	public CompilationEngine(List<Token> tokens, String filename) {
		this.tokens = tokens;
		this.filename = filename;
		
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
		
		varKindMap.put("field", Kind.FIELD);
		varKindMap.put("static", Kind.STATIC);
		varKindMap.put("var", Kind.VAR);
		varKindMap.put("arg", Kind.ARG);
		
		constantMap.put("null", 0);
		constantMap.put("false", 0);
		// constantMap.put("true", -1);
		
		kindMap.put(Kind.FIELD, MemSeg.THIS);
		kindMap.put(Kind.STATIC, MemSeg.STATIC);
		kindMap.put(Kind.VAR, MemSeg.LOCAL);
		kindMap.put(Kind.ARG, MemSeg.ARG);
		
		opsMap.put("+", OpsComd.ADD);
		opsMap.put("-",OpsComd.SUB);
		// opsMap.put("-", OpsComd.NEG);
		opsMap.put("=", OpsComd.EQ);
		opsMap.put("&lt;", OpsComd.LT);
		opsMap.put("&gt;", OpsComd.GT);
		opsMap.put("&amp;", OpsComd.AND);
		opsMap.put("|", OpsComd.OR);
		opsMap.put("~", OpsComd.NOT);
		
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
		String str = "";
		
		index++; // Get the name of the class.
		
		index++; // Get the opening curly bracket of the class.
		
		str += compileClassVarDec(); // Compile the class variables.
		str += compileSubroutineDec(); // Compile the subroutines.
		
		end = true;
		
		// symTable.printClassTable();
		
		return str;
	}
	
	private String compileClassVarDec() {
		String str = "";
		
		while(true) {
			index++;
			if (!classVarsList.contains(this.tokens.get(index).value)) {
				break;
			}
			
			Kind kind = varKindMap.get(this.tokens.get(index).value); // Get the variable kind.
			
			index++;
			String type = this.tokens.get(index).value; // Get the variable type.
			
			while(!this.tokens.get(index).value.equals(";")) {
				index++;
				if (this.tokens.get(index).type == TokenType.IDENTIFIER) { // If the token is an identifier.
					symTable.define(this.tokens.get(index).value, type, kind);
				}
			}
			
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
						
			String subroutineType = this.tokens.get(index).value;
			
			// Reset the subroutine symbol table.
			symTable.startSubroutine();
			if (subroutineType.equals("method")) {
				symTable.define("this", this.filename, Kind.ARG);
			}
			
			index++;
			String returnType = this.tokens.get(index).value; // Get the return type of the subroutine.
			currentReturnType = returnType;
			
			index++; // Get the subroutine name.
			String name = this.tokens.get(index).value;
			
			str += compileParameterList();
			
			str += compileSubroutineBody(name, subroutineType);
			
		}
		
		return str;
	}
	
	private String compileParameterList() {
		String str = "";
		
		index++; // Get opening bracket of the parameters list.
		
		// Generate the parameters list. 
		while(!this.tokens.get(index+1).value.equals(")")) {
			Kind kind = Kind.ARG; // The variable kind  is an argument.
			
			index++;
			String type = this.tokens.get(index).value; // Get the variable type.
			
			index++;
			if (this.tokens.get(index).type == TokenType.IDENTIFIER) { // If the token is an identifier.
				symTable.define(this.tokens.get(index).value, type, kind);
			}
			
			if (this.tokens.get(index+1).value.equals(",")) {
				index++; // Get the "," symbol.
			}
			
		}
		
		index ++; // Get closing bracket of the parameters list.
		
		return str;
	}
	
	private String compileSubroutineBody(String name, String subroutineType) {
		String str = "";
		
		index++; // Get opening curly bracket of the subroutine body.
		
		str += compileVarDec();
		
		
		symTable.printSubroutineTable();
		
		
		str += writer.generateFunctionVm(this.filename + "." + name, symTable.varCount(Kind.VAR));
		if (subroutineType.equals("constructor")) {
			str += writer.generatePushVm(MemSeg.CONSTANT, symTable.varCount(Kind.FIELD));
			str += writer.generateCallVm("Memory.alloc", 1);
			str += writer.generatePopVm(MemSeg.POINTER, 0);
		} else if (subroutineType.equals("method")) {
			str += writer.generatePushVm(MemSeg.ARG, 0);
			str += writer.generatePopVm(MemSeg.POINTER, 0);
		}
		
		str += compileStatements();
		
		index++;  // Get closing curly bracket of the subroutine body.
		
		return str;
	}
	
	private String compileVarDec() {
		String str = "";
		
		while(true) {
			if (!this.tokens.get(index+1).value.equals("var")) {
				break;
			}
			
			index++;
			Kind kind = varKindMap.get(this.tokens.get(index).value); // Get the variable kind.
			
			index++;
			String type = this.tokens.get(index).value; // Get the variable type.
			
			while(!this.tokens.get(index+1).value.equals(";")) { // While not end of the statement.
				index++;
				if (this.tokens.get(index).type == TokenType.IDENTIFIER) { // If the token is an identifier.
					symTable.define(this.tokens.get(index).value, type, kind);
				}
			}
			
			index++; // Get the ";" symbol.
			
		}
		
		return str;
	}
	
	private String compileStatements() {
		String str = "";
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
		
		return str;
	}
	
	private String compileLet() {
		String str = "";
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add("=");
		// stopSymbols.add("(");
		// stopSymbols.add(")");
		stopSymbols.add("[");
		stopSymbols.add("]");
		
		index++; // Get the let statement.
		
		index++; // Get the variable name.
		String nameA = this.tokens.get(index).value;
		boolean varAIsArray = false;
		
		index++; // Get the "=" or "[" symbol if it is an array.
		
		if (this.tokens.get(index).value.equals("[")) {
			varAIsArray = true;
			str += writer.generatePushVm(kindMap.get(symTable.kindOf(nameA)), symTable.indexOf(nameA)); // Push variable onto the stack.
			
			while (!this.tokens.get(index).value.equals("=")) { // Generate the expression inside the array index.
				str += compileExpression(stopSymbols);
				
				index++; // Get the symbol that is not an expression.
			}
			
			str += writer.generateArithmeticVm(OpsComd.ADD);
		}
		
		List<String> stopSymbols2 = new ArrayList<String>();
		stopSymbols2.add(";");
		stopSymbols2.add(",");
		stopSymbols2.add("=");
		// stopSymbols2.add("[");
		// stopSymbols2.add("]");
		
		String nameB = "";
		boolean varBIsArray = false;
		if (this.tokens.get(index+1).type == TokenType.IDENTIFIER && this.tokens.get(index+2).value.equals("[")) {
			nameB = this.tokens.get(index+1).value;
			varBIsArray = true;
			
			// str += writer.generatePushVm(kindMap.get(symTable.kindOf(nameB)), symTable.indexOf(nameB)); // Push variable onto the stack.
			
		}
		
		while (!this.tokens.get(index).value.equals(";")) {
			str += compileExpression(stopSymbols2);
			
			index++; // Get the symbol that is not an expression.
		}
		
		if (varBIsArray) {
			// str += writer.generateArithmeticVm(OpsComd.ADD);
			// str += writer.generatePopVm(MemSeg.POINTER, 1);
			// str += writer.generatePushVm(MemSeg.THAT, 0);
			
		}
		
		// str += writer.generatePopVm(MemSeg.TEMP, 0);
		
		// str += generateXml(); // Get the ";" symbol.
		
		if (!varAIsArray) {
			// str += writer.generatePushVm(MemSeg.TEMP, 0);
			str += writer.generatePopVm(kindMap.get(symTable.kindOf(nameA)), symTable.indexOf(nameA));
		} else {
			str += writer.generatePopVm(MemSeg.TEMP, 0);
			str += writer.generatePopVm(MemSeg.POINTER, 1);
			str += writer.generatePushVm(MemSeg.TEMP, 0);
			str += writer.generatePopVm(MemSeg.THAT, 0);
		}
		
		return str;
	}
	
	private String compileIf() {
		String str = "";
		
		index++; // Get the if statement.
		index++; // Get the opening bracket.
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add(";");
		stopSymbols.add("{");
		stopSymbols.add("}");
		// stopSymbols.add("(");
		stopSymbols.add(")");
		
		while((!this.tokens.get(index).value.equals(")") && !this.tokens.get(index+1).value.equals("{"))) {
			str += compileExpression(stopSymbols);
			
			index++; // Get the symbol that is not an expression.
		}
		
		// String label1 = "label." + this.filename + labelCount;
		String label1 = "label" + labelCount;
		labelCount++;
		// String label2 = "label." + this.filename + labelCount;
		String label2 = "label" + labelCount;
		labelCount++;
		
		index++; // Get the opening curly bracket.
		
		str += writer.generateArithmeticVm(OpsComd.NOT);
		str += writer.generateIfGotoVm(label1);
		
		str += compileStatements();
		
		index++; // Get the closing curly bracket.
		
		str += writer.generateGotoVm(label2);
		
		str += writer.generateLabelVm(label1);
		if (this.tokens.get(index+1).value.equals("else")) {
			index++; // Get the else statement.
			index++; // Get the opening curly bracket.
			
			str += compileStatements();
			
			index++; // Get the closing curly bracket.
		}
		
		str += writer.generateLabelVm(label2); // Create a label for the if condition.
		
		return str;
	}
	
	private String compileWhile() {
		String str = "";
		
		// String label1 = "label." + this.filename + labelCount;
		String label1 = "label" + labelCount;
		labelCount++;
		// String label2 = "label." + this.filename + labelCount;
		String label2 = "label" + labelCount;
		labelCount++;
		
		str += writer.generateLabelVm(label1);
		
		index++; // Get the while statement.
		index++; // Get the opening bracket.
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add(";");
		stopSymbols.add("{");
		stopSymbols.add("}");
		// stopSymbols.add("(");
		stopSymbols.add(")");
		
		while((!this.tokens.get(index).value.equals(")") && !this.tokens.get(index+1).value.equals("{"))) {
			str += compileExpression(stopSymbols);
			
			index++; // Get the symbol that is not an expression.
		}
		
		str += writer.generateArithmeticVm(OpsComd.NOT);
		str += writer.generateIfGotoVm(label2);
		
		index++; // Get the opening curly bracket.
		str += compileStatements();
		index++; // Get the closing curly bracket.
		
		str += writer.generateGotoVm(label1);
		
		str += writer.generateLabelVm(label2);
		
		return str;
	}
	
	private String compileDo() {
		String str = "";
		
		index++; // Get the do statement.
		index++; // Get the class instance or static class call name.
		String name = this.tokens.get(index).value;
		
		this.currentNumArgs = 0; // Reset the current numArgs count to zero.
		
		// If the next symbol is ".", this call is to a subroutine in another class 
		if (this.tokens.get(index+1).value.equals(".")) {
			if (!symTable.typeOf(name).equals("")) { // This is a call to a method on an object.
				str += writer.generatePushVm(kindMap.get(symTable.kindOf(name)), symTable.indexOf(name)); // Push the base address of the object onto the stack as argument 0 to the call. 
				
				name = symTable.typeOf(name);
				
				this.currentNumArgs = 1; // Increment the argument count.
			}
			
			index++; // Get the "." symbol.
			
			index++;
			name = name + "." + this.tokens.get(index).value; // Get the subroutine call name.
		} else { // This is a call to the class's own method.
			str += writer.generatePushVm(MemSeg.POINTER, 0); // Push the base address of this class onto the stack as argument 0 to the call. 
			
			name = this.filename + "." + this.tokens.get(index).value; // Get the subroutine call name.
			
			this.currentNumArgs = 1; // Increment the argument count.
		}
		
		index++; // Get the opening bracket of the subroutine call.
		
		List<String> stopSymbolsList = new ArrayList<String>();
		stopSymbolsList.add(";");
		stopSymbolsList.add(",");
		stopSymbolsList.add("{");
		stopSymbolsList.add("}");
		// stopSymbols.add("(");
		stopSymbolsList.add(")");
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add(";");
		
		
		str += compileExpressionList(stopSymbols, stopSymbolsList);
		
		str += writer.generateCallVm(name, this.currentNumArgs); // Call the subroutine;
		
		str += writer.generatePopVm(MemSeg.TEMP, 0); // Pop the return result from the call to temp since do statement do not assign a value.
		
		index++; // Get the closing bracket of the subroutine call.
		index++; // Get the ";" symbol.
		
		return str;
	}
	
	private String compileReturn() {
		String str = "";
		
		index++; // Get the return statement.
		
		if (this.tokens.get(index+1).value.equals(";")) { // The return type is "void".
			index++; // Get the ";" symbol.
			
			str += writer.generatePushVm(MemSeg.CONSTANT, 0); // Push zero onto the stack
			str += writer.generateReturnVm();
			
			return str;
		}
		
		if (this.tokens.get(index+1).value.equals("this")) { // The return type is "this".
			index++; // Get the "this" keyword.
			index++; // Get the ";" symbol.
			
			str += writer.generatePushVm(MemSeg.POINTER, 0); // Push the base address of this onto the stack.
			str += writer.generateReturnVm();
			
			return str;
		}
		
		while (!this.tokens.get(index).value.equals(";")) {
			str += compileExpression(this.expressionStopSymbolsList);
			
			index++; // Get the symbol that is not an expression.
		}
		
		str += writer.generateReturnVm();
		
		// str += generateXml(); // Get the ";" symbol.
		
		return str;
	}
	
	private String compileExpression(List<String> stopSymbolsList) {
		if (stopSymbolsList.contains(this.tokens.get(index+1).value)) {
			return "";
		}
		
		String str = "";
		String ops = "";
		
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
			
			if (!ops.equals("")) {
				OpsComd comd = this.opsMap.get(ops); // Get the OpsComd from the opsMap.
				
				// Execute the operation after the next expression is executed.
				if (comd != null) { // If the operation is found inside the opsMap.
					str += writer.generateArithmeticVm(comd); // Generate the arithmetic command.
				} else {
					if (ops.equals("*")) { // If the operation is multiply.
						str += writer.generateCallVm("Math.multiply", 2); // Call the Math.multiply subroutine.
					} else if (ops.equals("/")) { // If the operation is divide.
						str += writer.generateCallVm("Math.divide", 2); // Call the Math.divide subroutine.
					}
				}
				ops = ""; // Reset the ops variable.
			}
			
			if (!stopSymbolsList.contains(this.tokens.get(index+1).value)) {
				index++; // Get the symbol;
				ops = this.tokens.get(index).value; // Store the value in variable ops first then execute the operation after the next expression is executed.
			}
			
		}
		
		return str;
	}
	
	private String compileTerm() {
		String str = "";
		
		if (this.tokens.get(index+1).value.equals("(")) { // Indicates this is a part of a expression.
			index++; // Get the "(" symbol.
			
			List<String> stopSymbols = new ArrayList<String>();
			stopSymbols.add(")");
			
			str += compileExpression(stopSymbols);
			
			index++; // Get the ")" symbol.
		}
		
		else if ((this.tokens.get(index+1).value.equals("-") || this.tokens.get(index+1).value.equals("~"))
				&& (this.tokens.get(index+2).type == TokenType.INT_CONSTANT || this.tokens.get(index+2).type == TokenType.IDENTIFIER 
				|| this.tokens.get(index+2).value.equals("(")
				)) { // Indicates this is a unary ops.
			index++;
			String ops = this.tokens.get(index).value; // Get the unary op.
			OpsComd comd = OpsComd.NEG;
			if (ops.equals("-")) {
				comd = OpsComd.NEG;
			} else if (ops.equals("~")) {
				comd = OpsComd.NOT;
			}
			
			str += compileTerm(); // Get the term either variable name or constant.
			
			str += writer.generateArithmeticVm(comd);
		}
		
		else if (this.tokens.get(index+1).type == TokenType.INT_CONSTANT || this.tokens.get(index+1).type == TokenType.STR_CONSTANT
				|| this.tokens.get(index+1).type == TokenType.KEYWORD) {
			index++; // Get the term either variable name or constant.
			if (this.tokens.get(index).type == TokenType.KEYWORD) {
				if (this.constantMap.get(this.tokens.get(index).value) != null) {
					str += writer.generatePushVm(MemSeg.CONSTANT, this.constantMap.get(this.tokens.get(index).value));
				} else if (this.tokens.get(index).value.equals("true")) { // If the keyword is "true".
					str += writer.generatePushVm(MemSeg.CONSTANT, 1); // Push constant 1 onto the stack.
					str += writer.generateArithmeticVm(OpsComd.NEG); // Negate the constant 1.
				} else if (this.tokens.get(index).value.equals("this")) {
					str += writer.generatePushVm(MemSeg.POINTER, 0);
				}
			} else if (this.tokens.get(index).type == TokenType.STR_CONSTANT) {
				str += writer.generatePushVm(MemSeg.CONSTANT, this.tokens.get(index).value.length()); // Push length of String as constant onto the stack.
				str += writer.generateCallVm("String.new", 1); // Call the construct of the String class.
				
				// For each of the characters in the string.
				for (int i = 0; i < this.tokens.get(index).value.length(); i++) {
					str += writer.generatePushVm(MemSeg.CONSTANT, (int)this.tokens.get(index).value.charAt(i)); // Push the character as constant onto the stack.
					str += writer.generateCallVm("String.appendChar", 2); // Call the appendChar of the String class.
				}
			} else {
				str += writer.generatePushVm(MemSeg.CONSTANT, Integer.parseInt(this.tokens.get(index).value)); // Push the value as constant onto the stack.
			}
		}
		
		else if (this.tokens.get(index+1).type == TokenType.IDENTIFIER) {
			if (this.tokens.get(index+2).value.equals("[")) { // Indicates the this part is an array.
				index++; // Get the identifier.
				String nameA = this.tokens.get(index).value;
				
				index++; // Get the "[" symbol.
				
				List<String> stopSymbols = new ArrayList<String>();
				stopSymbols.add("]");
				
				str += writer.generatePushVm(kindMap.get(symTable.kindOf(nameA)), symTable.indexOf(nameA)); // Push variable onto the stack.
				
				str += compileExpression(stopSymbols);
				
				str += writer.generateArithmeticVm(OpsComd.ADD); // Add the base address and the offset of the array.
				str += writer.generatePopVm(MemSeg.POINTER, 1);
				str += writer.generatePushVm(MemSeg.THAT, 0);
				
				index++; // Get the "]" symbol.
			} else if (this.tokens.get(index+2).value.equals(".") || this.tokens.get(index+2).value.equals("(")) { // Indicates the this part is a subroutine call.
				str += compileSubroutineCall();
			} else { // Indicates a standard variable name.
				index++; // Get the term either variable name or constant.
				// Get the variable kind and index and push the variable onto the stack.
				str += writer.generatePushVm(kindMap.get(symTable.kindOf(this.tokens.get(index).value)), symTable.indexOf(this.tokens.get(index).value));
			}
		}
		
		return str;
	}
	
	private String compileExpressionList(List<String> stopSymbols, List<String> stopSymbolsList) {
		String str = "";
		
		// if (this.tokens.get(index+1).value.equals(endingSymbol)) {
		if (stopSymbolsList.contains(this.tokens.get(index+1).value)) {
			return str;
		}
		
		this.currentNumArgs++;
		// if (stopSymbolsList.contains(this.tokens.get(index+2).value)) {
		while (!stopSymbols.contains(this.tokens.get(index+2).value) && !this.tokens.get(index+1).value.equals(")")) { // While index+1 not ")" and index+2 not equals ";". 
			str += compileExpression(stopSymbolsList);
			
			if (!stopSymbols.contains(this.tokens.get(index+2).value) && !this.tokens.get(index+1).value.equals(")")) { // If index+1 not ")" and index+2 not equals ";".
				index++; // Get the symbol that is not an expression.
				if (this.tokens.get(index).value.equals(",")) { // If the symbol is ",".
					this.currentNumArgs++; // Increment the arguments count.
				}
			}
		}
		
		return str;
	}
	
	private String compileSubroutineCall() {
		String str = "";
		
		index++; // Get the class instance or static class call name.
		String name = this.tokens.get(index).value;
		
		this.currentNumArgs = 0; // Reset the current numArgs count to zero.
		
		// If the next symbol is ".", this call is to a subroutine in another class 
		if (this.tokens.get(index+1).value.equals(".")) {
			if (!symTable.typeOf(name).equals("")) { // This is a call to a method on an object.
				str += writer.generatePushVm(kindMap.get(symTable.kindOf(name)), symTable.indexOf(name)); // Push the base address of the object onto the stack as argument 0 to the call. 
				
				name = symTable.typeOf(name);
				
				this.currentNumArgs = 1; // Increment the argument count.
			}
			
			index++; // Get the "." symbol.
			
			index++;
			name = name + "." + this.tokens.get(index).value; // Get the subroutine call name.
		} else { // This is a call to the class's own method.
			str += writer.generatePushVm(MemSeg.POINTER, 0); // Push the base address of this class onto the stack as argument 0 to the call. 
			
			name = this.filename + "." + this.tokens.get(index).value; // Get the subroutine call name.
			
			this.currentNumArgs = 1; // Increment the argument count.
		}
		
		index++; // Get the opening bracket of the subroutine call.
		
		List<String> stopSymbolsList = new ArrayList<String>();
		stopSymbolsList.add(";");
		stopSymbolsList.add(",");
		stopSymbolsList.add("{");
		stopSymbolsList.add("}");
		// stopSymbols.add("(");
		// stopSymbolsList.add(")");
		
		List<String> stopSymbols = new ArrayList<String>();
		stopSymbols.add(";");
		// stopSymbols.add(")");
		
		// str += compileExpressionList(stopSymbols, stopSymbolsList);
		str += compileExpressionList(stopSymbols, this.expressionStopSymbolsList);
		
		str += writer.generateCallVm(name, this.currentNumArgs); // Call the subroutine;
		// str += writer.generatePopVm(MemSeg.TEMP, 0); // Pop the return result from the call to temp since do statement do not assign a value.
		
		index++; // Get the closing bracket of the subroutine call.
		
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
