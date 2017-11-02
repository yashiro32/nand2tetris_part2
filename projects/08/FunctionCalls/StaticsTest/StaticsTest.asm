// Initialize RAM[0] = 256.
@256
D=A
@SP
M=D
// Call Sys.init funtction.
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
(Class1.set)
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
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[filename.index] = D.
@Class10
M=D
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
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[filename.index] = D.
@Class11
M=D
@0
D=A
@0
A=M
M=D
@0
M=M+1
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
(Class1.get)
// D = RAM[filename.index].
@Class10
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// D = RAM[filename.index].
@Class11
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// RAM[RAM[0]-2] - RAM[RAM[0]-1].
@0
A=M-1
D=M
A=A-1
M=M-D
// RAM[0] -= 1.
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
(Class2.set)
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
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[filename.index] = D.
@Class20
M=D
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
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[filename.index] = D.
@Class21
M=D
@0
D=A
@0
A=M
M=D
@0
M=M+1
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
(Class2.get)
// D = RAM[filename.index].
@Class20
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// D = RAM[filename.index].
@Class21
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// RAM[RAM[0]-2] - RAM[RAM[0]-1].
@0
A=M-1
D=M
A=A-1
M=M-D
// RAM[0] -= 1.
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
(Sys.init)
@6
D=A
@0
A=M
M=D
@0
M=M+1
@8
D=A
@0
A=M
M=D
@0
M=M+1
// Create a return address label and push it to the stack.
// D = index.
@RETADDRESS_Sys_0
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
@7
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
@Class1.set
0;JMP
(RETADDRESS_Sys_0)
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[tempBaseAddress+index] = D.
@5
M=D
@23
D=A
@0
A=M
M=D
@0
M=M+1
@15
D=A
@0
A=M
M=D
@0
M=M+1
// Create a return address label and push it to the stack.
// D = index.
@RETADDRESS_Sys_1
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
@7
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
@Class2.set
0;JMP
(RETADDRESS_Sys_1)
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[tempBaseAddress+index] = D.
@5
M=D
// Create a return address label and push it to the stack.
// D = index.
@RETADDRESS_Sys_2
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
@Class1.get
0;JMP
(RETADDRESS_Sys_2)
// Create a return address label and push it to the stack.
// D = index.
@RETADDRESS_Sys_3
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
@Class2.get
0;JMP
(RETADDRESS_Sys_3)
(WHILE)
@WHILE
0;JMP
