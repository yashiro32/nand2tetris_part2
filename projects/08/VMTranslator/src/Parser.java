import java.util.HashMap;


enum CommandType {
	AL,
	MEM,
	BRANCH,
	FUNC
}

enum ArithmeticComd {
	ADD,
	SUB,
	NEG,
	EQ,
	GT,
	LT,
	AND,
	OR,
	NOT
}

enum MemoryComd {
	PUSH,
	POP
}

enum MemorySeg {
	LOCAL,
	ARG,
	THIS,
	THAT,
	CONSTANT,
	STATIC,
	TEMP,
	POINTER
}

enum BranchComd {
	GOTO,
	IF_GOTO,
	LABEL
}

enum FunctionComd {
	FUNCTION,
	CALL,
	RETURN
}

public class Parser {
	public HashMap<String, ArithmeticComd> arithComdMap = new HashMap<String, ArithmeticComd>();
	public HashMap<String, MemoryComd> memComdMap = new HashMap<String, MemoryComd>();
	public HashMap<String, MemorySeg> memSegMap = new HashMap<String, MemorySeg>();
	public HashMap<String, BranchComd> branchComdMap = new HashMap<String, BranchComd>();
	public HashMap<String, FunctionComd> functionComdMap = new HashMap<String, FunctionComd>();
	
	public Parser() {
		arithComdMap.put("add", ArithmeticComd.ADD);
		arithComdMap.put("sub", ArithmeticComd.SUB);
		arithComdMap.put("neg", ArithmeticComd.NEG);
		arithComdMap.put("eq", ArithmeticComd.EQ);
		arithComdMap.put("gt", ArithmeticComd.GT);
		arithComdMap.put("lt", ArithmeticComd.LT);
		arithComdMap.put("and", ArithmeticComd.AND);
		arithComdMap.put("or", ArithmeticComd.OR);
		arithComdMap.put("not", ArithmeticComd.NOT);
		
		memComdMap.put("push", MemoryComd.PUSH);
		memComdMap.put("pop", MemoryComd.POP);
		
		memSegMap.put("local", MemorySeg.LOCAL);
		memSegMap.put("argument", MemorySeg.ARG);
		memSegMap.put("this", MemorySeg.THIS);
		memSegMap.put("that", MemorySeg.THAT);
		memSegMap.put("constant", MemorySeg.CONSTANT);
		memSegMap.put("static", MemorySeg.STATIC);
		memSegMap.put("temp", MemorySeg.TEMP);
		memSegMap.put("pointer", MemorySeg.POINTER);
		
		branchComdMap.put("goto", BranchComd.GOTO);
		branchComdMap.put("if-goto", BranchComd.IF_GOTO);
		branchComdMap.put("label", BranchComd.LABEL);
		
		functionComdMap.put("function", FunctionComd.FUNCTION);
		functionComdMap.put("call", FunctionComd.CALL);
		functionComdMap.put("return", FunctionComd.RETURN);
	}
	
	public void parse() {
		
	}
	
	public CommandType getCommandType(String command) {
		if (arithComdMap.containsKey(command)) {
			return CommandType.AL;
		} else if (memComdMap.containsKey(command)) {
			return CommandType.MEM;
		} else if (branchComdMap.containsKey(command)) {
			return CommandType.BRANCH;
		} else if (functionComdMap.containsKey(command)) {
			return CommandType.FUNC;
		}
		
		return null;
	}
}
