@256
D=A
@SP
M=D
// Create a return address label and push it to the stack.
// D = index.
@RETADDRESS_Initialize_0
D=A
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@LCL
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@ARG
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@THIS
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@THAT
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// RAM[ARG] = RAM[0] - 5 - numArgs.
@5
D=A
@0
D=M-D
@2
M=D
// RAM[LCL] = RAM[0].
@0
D=M
@1
M=D
@Sys.init
0;JMP
(RETADDRESS_Initialize_0)
(SimpleFunction.test)
@0
D=A
@0
A=M
M=D
@0
M=M+1
@0
D=A
@0
A=M
M=D
@0
M=M+1
// D = index.
@0
D=A
// D = RAM[LCL+index].
@1
A=D+M
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// D = index.
@1
D=A
// D = RAM[LCL+index].
@1
A=D+M
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@0
A=M-1
D=M
A=A-1
M=M+D
@0
M=M-1
@0
A=M-1
M=!M
// D = index.
@0
D=A
// D = RAM[ARG+index].
@2
A=D+M
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@0
A=M-1
D=M
A=A-1
M=M+D
@0
M=M-1
// D = index.
@1
D=A
// D = RAM[ARG+index].
@2
A=D+M
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@0
A=M-1
D=M
A=A-1
M=M-D
@0
M=M-1
// RAM[R11] = RAM[LCL].
@1
D=M
@R11
M=D
// RAM[R12] = RAM[RAM[LCL]-5].
@5
A=D-A
D=M
@R12
M=D
@ARG
D=M
@0
D=D+A
// Use Register 13 to store the Base Address + index.
@R13
M=D
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[RAM[R13]] = D.
@R13
A=M
M=D
// RAM[0] = RAM[ARG] + 1.
@2
D=M
@0
M=D+1
@R11
D=M-1
AM=D
D=M
@THAT
M=D
@R11
D=M-1
AM=D
D=M
@THIS
M=D
@R11
D=M-1
AM=D
D=M
@ARG
M=D
@R11
D=M-1
AM=D
D=M
@LCL
M=D
// Goto Return Address.
@R12
A=M
0;JMP
