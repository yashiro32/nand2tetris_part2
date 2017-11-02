import java.util.HashMap;

enum MemSeg {
	LOCAL, ARG, THIS, THAT, CONSTANT, STATIC, POINTER, TEMP, NONE
}

enum OpsComd {
	ADD, SUB, NEG, EQ, GT, LT, AND, OR, NOT
}

public class VMWriter {
	private HashMap<MemSeg, String> memSegMap = new HashMap<MemSeg, String>();
	private HashMap<OpsComd, String> opsComdMap = new HashMap<OpsComd, String>();
	
	public VMWriter() {
		memSegMap.put(MemSeg.LOCAL, "local");
		memSegMap.put(MemSeg.ARG, "argument");
		memSegMap.put(MemSeg.THIS, "this");
		memSegMap.put(MemSeg.THAT, "that");
		memSegMap.put(MemSeg.CONSTANT, "constant");
		memSegMap.put(MemSeg.STATIC, "static");
		memSegMap.put(MemSeg.TEMP, "temp");
		memSegMap.put(MemSeg.POINTER, "pointer");
		
		opsComdMap.put(OpsComd.ADD, "add");
		opsComdMap.put(OpsComd.SUB, "sub");
		opsComdMap.put(OpsComd.NEG, "neg");
		opsComdMap.put(OpsComd.EQ, "eq");
		opsComdMap.put(OpsComd.LT, "lt");
		opsComdMap.put(OpsComd.GT, "gt");
		opsComdMap.put(OpsComd.AND, "and");
		opsComdMap.put(OpsComd.OR, "or");
		opsComdMap.put(OpsComd.NOT, "not");
	}
	
	public String generatePushVm(MemSeg segment, int index) {
		String str = "";
		
		str += "push " + memSegMap.get(segment) + " " + index + "\n";
		
		return str;
	}
	
	public String generatePopVm(MemSeg segment, int index) {
		String str = "";
		
		str += "pop " + memSegMap.get(segment) + " " + index + "\n";
		
		return str;
	}
	
	public String generateArithmeticVm(OpsComd command) {
		String str = "";
		
		str += opsComdMap.get(command) + "\n";
		
		return str;
	}
	
	public String generateLabelVm(String label) {
		String str = "";
		
		str += "label " + label + "\n";
		
		return str; 
	}
	
	public String generateGotoVm(String label) {
		String str = "";
		
		str += "goto " + label + "\n";
		
		return str; 
	}
	
	public String generateIfGotoVm(String label) {
		String str = "";
		
		str += "if-goto " + label + "\n";
		
		return str; 
	}
	
	public String generateCallVm(String name, int numArgs) {
		String str = "";
		
		str += "call " + name + " " + numArgs + "\n";
		
		return str; 
	}
	
	public String generateFunctionVm(String name, int numLocals) {
		String str = "";
		
		str += "function " + name + " " + numLocals + "\n";
		
		return str; 
	}
	
	public String generateReturnVm() {
		String str = "";
		
		str += "return" + "\n";
		
		return str; 
	}
}
