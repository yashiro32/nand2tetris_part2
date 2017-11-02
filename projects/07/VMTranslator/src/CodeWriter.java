
public class CodeWriter {
	private int SP = 0;
	private int LCL = 1;
	private int lclBaseAddress = 300; // Base address of the local segment.
	private int ARG = 2;
	private int argBaseAddress = 400; // Base address of the argument segment.
	private int THIS = 3;
	private int thisBaseAddress = 3000; // Base address of the this segment
	private int THAT = 4;
	private int thatBaseAddress = 3010;  // Base address of the that segment.
	private int tempBaseAddress = 5; // Base address of the temp segment.
	
	private int conditionFlagIndex = 0;
	
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
		
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=M+D" + "\n";
		
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateSubAsm() {
		String str = "";
		
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=M-D" + "\n";
		
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateNegAsm() {
		String str = "";
		
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "M=-M" + "\n";
		
		return str;
	}
	
	public String generateEqAsm() {
		String str = "";
		
		str += "@" + SP + "\n";
        str += "AM=M-1" + "\n";
        str += "D=M" + "\n";
        str += "A=A-1" + "\n";
        str += "D=M-D" + "\n";
        str += "@FALSE" + conditionFlagIndex + "\n";
        str += "D;JNE" + "\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=-1" + "\n";
        str += "@CONTINUE" + conditionFlagIndex + "\n";
        str += "0;JMP" + "\n";
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
		
		str += "@" + SP + "\n";
        str += "AM=M-1" + "\n";
        str += "D=M" + "\n";
        str += "A=A-1" + "\n";
        str += "D=M-D" + "\n";
        str += "@FALSE" + conditionFlagIndex + "\n";
        str += "D;JLE" + "\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=-1" + "\n";
        str += "@CONTINUE" + conditionFlagIndex + "\n";
        str += "0;JMP" + "\n";
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
		
		str += "@" + SP + "\n";
        str += "AM=M-1" + "\n";
        str += "D=M" + "\n";
        str += "A=A-1" + "\n";
        str += "D=M-D" + "\n";
        str += "@FALSE" + conditionFlagIndex + "\n";
        str += "D;JGE" + "\n";
        str += "@" + SP + "\n";
        str += "A=M-1" + "\n";
        str += "M=-1" + "\n";
        str += "@CONTINUE" + conditionFlagIndex + "\n";
        str += "0;JMP" + "\n";
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
		
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=D&M" + "\n";
		
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateOrAsm() {
		String str = "";
		
		str += "@" + SP + "\n";
		str += "A=M-1" + "\n";
		str += "D=M" + "\n";
		str += "A=A-1" + "\n";
		str += "M=D|M" + "\n";
		
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		
		return str;
	}
	
	public String generateNotAsm() {
		String str = "";
		
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
			str = generatePushStaticAsm(comd, memSeg, index);
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
	
	public String generatePushStaticAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// D = RAM[Foo.index].\n";
		str += "@" + "Foo." + index + "\n";
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
			str = generatePopStaticAsm(comd, memSeg, index);
		} else if (memSeg == MemorySeg.POINTER) {
			str = generatePopPointerAsm(comd, memSeg, index);
		}
		
		return str;
	}
	
	public String generatePopLocalAsm(MemoryComd comd, MemorySeg memSeg, int index) {
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
		
		str += "// RAM[lclBaseAddress+index] = D.\n";
		str += "@" + (lclBaseAddress + index) + "\n";
		str += "M=D" + "\n";
		
		return str;
	}
	
	public String generatePopArgAsm(MemoryComd comd, MemorySeg memSeg, int index) {
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
		
		str += "// RAM[argBaseAddress+index] = D.\n";
		str += "@" + (argBaseAddress + index) + "\n";
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
	
	public String generatePopStaticAsm(MemoryComd comd, MemorySeg memSeg, int index) {
		String str = "";
		
		str += "// D=RAM[RAM[0]-1].\n";
		str += "@" + SP + "\n";
		str += "M=M-1" + "\n";
		str += "A=M" + "\n";
		str += "D=M" + "\n";
		
		str += "// RAM[Foo.index] = D.\n";
		str += "@" + "Foo." + index + "\n";
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
}
