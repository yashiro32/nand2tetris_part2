
public class CodeWriter {
	private int SP = 0;
	private int LCL = 1;
	// private int lclBaseAddress = 300; // Base address of the local segment.
	private int ARG = 2;
	// private int argBaseAddress = 400; // Base address of the argument segment.
	private int THIS = 3;
	// private int thisBaseAddress = 3000; // Base address of the this segment
	private int THAT = 4;
	// private int thatBaseAddress = 3010;  // Base address of the that segment.
	private int tempBaseAddress = 5; // Base address of the temp segment.
	
	private int conditionFlagIndex = 0;
	private int labelCnt = 0;
	
	public String filename = "";
	
	public CodeWriter(String filename) {
		this.filename = filename;
	}
	
	public String generateInitAsm() {
		String str = "";
		
		str += "// Initialize RAM[0] = 256.\n";
		str += "@256" + "\n";
		str += "D=A" + "\n";
		str += "@SP" + "\n";
		str += "M=D" + "\n";
		str += "// Call Sys.init funtction.\n";
		str += generateCallAsm("Sys.init", 0);
		
		return str;
	}
	
	public String generate(ArithmeticComd comd) {
		String str = "";
		
		if (comd == ArithmeticComd.ADD) {
			str = this.generateAddAsm();
		} else if (comd == ArithmeticComd.SUB) {
			str = this.generateSubAsm();
		} else if (comd == ArithmeticComd.NEG) {
			str = this.generateNegAsm();
		} else if (comd == ArithmeticComd.EQ) {
			str = this.generateEqAsm();
		} else if (comd == ArithmeticComd.GT) {
			str = this.generateGtAsm();
		} else if (comd == ArithmeticComd.LT) {
			str = this.generateLtAsm();
		} else if (comd == ArithmeticComd.AND) {
			str = this.generateAndAsm();
		} else if (comd == ArithmeticComd.OR) {
			str = this.generateOrAsm();
		} else if (comd == ArithmeticComd.NOT) {
			str = this.generateNotAsm();
		}
		
		return str;
	}
	
	public String generateAddAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-2] + RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=M+D" + "\n";
		
		str += "// RAM[0] -= 1.\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateSubAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-2] - RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=M-D" + "\n";
		
		str += "// RAM[0] -= 1.\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateNegAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-1] = -M.\n";
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "M=-M" + "\n";
		
		return str;
	}
	
	public String generateEqAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-2] = RAM[RAM[0]-2] - RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
        str += "AM=M-1" + "\n";
        str += "D=M" + "\n";
        str += "A=A-1" + "\n";
        str += "D=M-D" + "\n";
        str += "// Jump to label if D not equal 0.\n";
        str += "@FALSE" + conditionFlagIndex + "\n";
        str += "D;JNE" + "\n";
        str += "// Set RAM[RAM[0]-2] = -1.\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=-1" + "\n";
        str += "// Jump to the CONTINUE label.\n";
        str += "@CONTINUE" + conditionFlagIndex + "\n";
        str += "0;JMP" + "\n";
        str += "// Set RAM[RAM[0]-2] = 0.\n";
        str += "(FALSE" + conditionFlagIndex + ")" + "\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=0" + "\n";
        str += "(CONTINUE" + conditionFlagIndex + ")" + "\n";
        
        conditionFlagIndex++;
		
		return str;
	}
	
	public String generateGtAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-2] = RAM[RAM[0]-2] - RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
        str += "AM=M-1" + "\n";
        str += "D=M" + "\n";
        str += "A=A-1" + "\n";
        str += "D=M-D" + "\n";
        str += "// Jump to label if D not equal 0.\n";
        str += "@FALSE" + conditionFlagIndex + "\n";
        str += "D;JLE" + "\n";
        str += "// Set RAM[RAM[0]-2] = -1.\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=-1" + "\n";
        str += "// Jump to the CONTINUE label.\n";
        str += "@CONTINUE" + conditionFlagIndex + "\n";
        str += "0;JMP" + "\n";
        str += "// Set RAM[RAM[0]-2] = 0.\n";
        str += "(FALSE" + conditionFlagIndex + ")" + "\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=0" + "\n";
        str += "(CONTINUE" + conditionFlagIndex + ")" + "\n";
        
        conditionFlagIndex++;
		
		return str;
	}
	
	public String generateLtAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-2] = RAM[RAM[0]-2] - RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
        str += "AM=M-1" + "\n";
        str += "D=M" + "\n";
        str += "A=A-1" + "\n";
        str += "D=M-D" + "\n";
        str += "// Jump to label if D not equal 0.\n";
        str += "@FALSE" + conditionFlagIndex + "\n";
        str += "D;JGE" + "\n";
        str += "// Set RAM[RAM[0]-2] = -1.\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=-1" + "\n";
        str += "// Jump to the CONTINUE label.\n";
        str += "@CONTINUE" + conditionFlagIndex + "\n";
        str += "0;JMP" + "\n";
        str += "// Set RAM[RAM[0]-2] = 0.\n";
        str += "(FALSE" + conditionFlagIndex + ")" + "\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=0" + "\n";
        str += "(CONTINUE" + conditionFlagIndex + ")" + "\n";
        
        conditionFlagIndex++;
		
		return str;
	}
	
	public String generateAndAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-2] && RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=D&M" + "\n";
		
		str += "// RAM[0] -= 1.\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateOrAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-2] || RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=D|M" + "\n";
		
		str += "// RAM[0] -= 1.\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateNotAsm() {
		String str = "";
		
		str += "// RAM[RAM[0]-1] = !M.\n";
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "M=!M" + "\n";
		
		return str;
	}
	
	public String generate(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		if (comd == MemoryComd.PUSH) {
			str = this.generatePushAsm(comd, memSeg, index);
		} else if (comd == MemoryComd.POP) {
			str = this.generatePopAsm(comd, memSeg, index);
		}
		
		return str;
	}
	
	public String generatePushAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		if (memSeg == MemorySeg.LOCAL) {
			str = generatePushLocalAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.ARG) {
			str = generatePushArgAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.THIS) {
			str = generatePushThisAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.THAT) {
			str = generatePushThatAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.CONSTANT) {
			str = generatePushConstantAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.TEMP) {
			str = generatePushTempAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.STATIC) {
			str = generatePushStaticAsm(comd, memSeg, index, this.filename);
		} else if (memSeg == MemorySeg.POINTER) {
			str = generatePushPointerAsm(comd, memSeg, index);
		}
		
		return str;
	}
	
	public String generatePushLocalAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// D = index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		
		str += "// D = RAM[LCL+index].\n";
		str += "@" + LCL + "\n";
		str += "A=D+M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePushArgAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// D = index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		
		str += "// D = RAM[ARG+index].\n";
		str += "@" + ARG + "\n";
		str += "A=D+M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePushThisAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// D = index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		
		str += "// D = RAM[THIS+index].\n";
		str += "@" + THIS + "\n";
		str += "A=D+M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePushThatAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// D = index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		
		str += "// D = RAM[THAT+index].\n";
		str += "@" + THAT + "\n";
		str += "A=D+M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePushConstantAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePushStaticAsm(MemoryComd comd, MemorySeg memSeg, int index, String filename) {
		String str = "";
		
		str += "// D = RAM[filename.index].\n";
		str += "@" + filename + index + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePushTempAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// D = RAM[tempAddress + index].\n";
		str += "@" + (tempBaseAddress + index) + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePushPointerAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		int address = 0;
		if (index == 0) {
			address = THIS;
		} else if (index == 1) {
			address = THAT;
		}
		
		str += "// D = RAM[THIS/THAT].\n";
		str += "@" + address + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String pushTemplate(String segment, int index, boolean isDirect) {
		String str = "";
		
		str += "@" + segment + "\n";
		str += "D=M" + "\n";
		
		if (!isDirect) {
			str += "// D = RAM[segment+index].\n";
			str += "@" + index + "\n";
			str += "A=D+A" + "\n";
			str += "D=M" + "\n";
		}
		
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		return str;
	}
	
	public String generatePopAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		if (memSeg == MemorySeg.LOCAL) {
			str = generatePopLocalAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.ARG) {
			str = generatePopArgAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.THIS) {
			str = generatePopThisAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.THAT) {
			str = generatePopThatAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.TEMP) {
			str = generatePopTempAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.STATIC) {
			str = generatePopStaticAsm(comd, memSeg, index, this.filename);
		} else if (memSeg == MemorySeg.POINTER) {
			str = generatePopPointerAsm(comd, memSeg, index);
		}
		
		return str;
	}
	
	public String generatePopLocalAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// Use Register 13 to store the Locals Base Address + index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		str += "@" + LCL + "\n";
		str += "D=M+D" + "\n";
		str += "@R13" + "\n";
		str += "M=D" + "\n";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[R13]] = D.\n";
		str += "@R13" + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generatePopArgAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// Use Register 13 to store the Arguments Base Address + index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		str += "@" + ARG + "\n";
		str += "D=M+D" + "\n";
		str += "@R13" + "\n";
		str += "M=D" + "\n";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[R13]] = D.\n";
		str += "@R13" + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generatePopThisAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// Use Register 13 to store the thisBaseAddress + index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		str += "@" + THIS + "\n";
		str += "D=M+D" + "\n";
		str += "@R13" + "\n";
		str += "M=D" + "\n";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[R13]] = D.\n";
		str += "@R13" + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generatePopThatAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// Use Register 13 to store the thatBaseAddress + index.\n";
		str += "@" + index + "\n";
		str += "D=A" + "\n";
		str += "@" + THAT + "\n";
		str += "D=M+D" + "\n";
		str += "@R13" + "\n";
		str += "M=D" + "\n";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[R13]] = D.\n";
		str += "@R13" + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generatePopStaticAsm(MemoryComd comd, MemorySeg memSeg, int index, String filename) {
		String str = "";
		
		// str += "// Use Register 13 to store the filename.index.\n";
		// str += "@" + filename + index + "\n";
		// str	+= "D=A" + "\n";
		// str += "@R13" + "\n";
		// str += "M=D" + "\n";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[filename.index] = D.\n";
		str += "@" + filename + index + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generatePopTempAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		// str += "@" + index + "\n";
		// str += "D=A" + "\n";
		// str += "@" + LCL + "\n";
		// str += "A=M+D" + "\n";
		// str += "M=A" + "\n";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[tempBaseAddress+index] = D.\n";
		str += "@" + (tempBaseAddress + index) + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generatePopPointerAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		int address = 0;
		if (index == 0) {
			address = THIS;
		} else if (index == 1) {
			address = THAT;
		}
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[address] = D.\n";
		str += "@" + address + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String popTemplate(String segment, int index, boolean isDirect) {
		String str = "";
		
		str += "@" + segment + "\n";
		
		if (isDirect) {
			str += "D=A" + "\n";
		} else {
			str += "D=M" + "\n";
			str += "@" + index + "\n";
			str += "D=D+A" + "\n";
		}
		
		str += "// Use Register 13 to store the Base Address + index.\n";
		str += "@R13" + "\n";
		str += "M=D" + "\n";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[RAM[R13]] = D.\n";
		str += "@R13" + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String preFrameTemplate(String position) {
		String str = "";
		
		str += "@R11" + "\n";
		str += "D=M-1" + "\n";
		str += "AM=D" + "\n";
		str += "D=M" + "\n";
		str += "@" + position + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generate(BranchComd comd, String labelName) {
		String str = "";
		
		if (comd == BranchComd.GOTO) {
			str = generateGotoAsm(labelName);
		} else if (comd == BranchComd.IF_GOTO) {
			str = generateIfGotoAsm(labelName);
		} else if (comd == BranchComd.LABEL) {
			str = generateLabelAsm(labelName);
		}
		
		return str;
	}
	
	public String generateGotoAsm(String labelName) {
		String str = "";
		
		str += "@" + labelName + "\n";
		str += "0;JMP" + "\n";
		
		return str;
	}
	
	public String generateIfGotoAsm(String labelName) {
		String str = "";
		
		str += "@" + SP + "\n";
		str += "AM=M-1" + "\n";
		str += "D=M" + "\n";
		str += "@" + labelName + "\n";
		str += "D;JNE" + "\n";
		
		return str;
	}
	
	public String generateLabelAsm(String labelName) {
		String str = "";
		
		str += "(" + labelName + ")" + "\n";
		
		return str;
	}
	
	public String generate(FunctionComd comd, String[] line_arr) {
		String str = "";
		
		if (comd == FunctionComd.CALL) {
			str = generateCallAsm(line_arr[1], Integer.parseInt(line_arr[2]));
		} else if (comd == FunctionComd.FUNCTION) {
			str = generateFunctionAsm(line_arr[1], Integer.parseInt(line_arr[2]));
		} else if (comd == FunctionComd.RETURN) {
			str = generateReturnAsm();
		}
		
		return str;
	}
	
	public String generateCallAsm(String functionName, int numArgs) {
		String str = "";
		
		String returnAddress = "RETADDRESS_" + filename + "_" + labelCnt;
		
		str += "// Create a return address label and push it to the stack.\n";
		str += "// D = index.\n";
		str += "@" + returnAddress + "\n";
		str += "D=A" + "\n";
		str += "// RAM[RAM[0]] = D.\n";
		str += "@" + SP + "\n";
		str += "A=M" + "\n";
		str += "M=D" + "\n";
		str += "// Increment RAM[0] by 1.\n";
		str += "@" + SP + "\n";
		str += "M=M+1" + "\n";
		
		str += pushTemplate("LCL", 0, true);
		str += pushTemplate("ARG", 0, true);
		str += pushTemplate("THIS", 0, true);
		str += pushTemplate("THAT", 0, true);
		
		str += "// RAM[ARG] = RAM[0] - 5 - numArgs.\n";
		str += "@"+ (5 + numArgs) + "\n";
		str += "D=A" + "\n";
		str += "@" + SP + "\n";
		str += "D=M-D" + "\n";
		str += "@" + ARG + "\n";
		str += "M=D" + "\n";
		
		str += "// RAM[LCL] = RAM[0].\n";
		str += "@" + SP + "\n";
		str += "D=M" + "\n";
		str += "@" + LCL + "\n";
		str += "M=D" + "\n";
		
		str += generateGotoAsm(functionName);
		str += generateLabelAsm(returnAddress);
		
		// str += "@" + functionName + "\n";
		// str += "0;JMP" + "\n";
		// str += "(" + returnAddress + ")" + "\n";
		
		labelCnt++;
		
		return str;
	}
	
	public String generateFunctionAsm(String functionName, int numLocals) {
		String str = "";
		
		str += "(" + functionName + ")" + "\n";
		
		for (int i = 0; i < numLocals; i++) {
			str += generatePushConstantAsm(MemoryComd.PUSH, MemorySeg.CONSTANT, 0);
		}
		
		return str;
	}
	
	public String generateReturnAsm() {
		String str = "";
		
		str += "// RAM[R11] = RAM[LCL].\n";
		str += "@" + LCL + "\n";
		str += "D=M" + "\n";
		str += "@R11" + "\n";
		str += "M=D" + "\n";
		
		str += "// RAM[R12] = RAM[RAM[LCL]-5].\n";
		str += "@5" + "\n";
		str += "A=D-A" + "\n";
		str += "D=M" + "\n";
		str += "@R12" + "\n";
		str += "M=D" + "\n";
		
		str += popTemplate("ARG", 0, false);
		
		str += "// RAM[0] = RAM[ARG] + 1.\n";
		str += "@" + ARG + "\n";
		str += "D=M" + "\n";
		str += "@" + SP + "\n";
		str += "M=D+1" + "\n";
		
		str += preFrameTemplate("THAT");
		str += preFrameTemplate("THIS");
		str += preFrameTemplate("ARG");
		str += preFrameTemplate("LCL");
		
		str += "// Goto Return Address.\n";
		str += "@R12" + "\n";
		str += "A=M" + "\n";
		str += "0;JMP" + "\n";
		
		return str;
	}
	
	
}
