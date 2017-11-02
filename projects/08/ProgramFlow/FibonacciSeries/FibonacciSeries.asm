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
// RAM[address] = D.
@4
M=D
@0
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the thatBaseAddress + index.
@0
D=A
@4
D=M+D
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
@1
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the thatBaseAddress + index.
@1
D=A
@4
D=M+D
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
@2
D=A
@0
A=M
M=D
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
// Use Register 13 to store the Arguments Base Address + index.
@0
D=A
@2
D=M+D
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
(MAIN_LOOP_START)
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
AM=M-1
D=M
@COMPUTE_ELEMENT
D;JNE
@END_PROGRAM
0;JMP
(COMPUTE_ELEMENT)
// D = index.
@0
D=A
// D = RAM[THAT+index].
@4
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
// D = RAM[THAT+index].
@4
A=D+M
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// RAM[RAM[0]-2] + RAM[RAM[0]-1].
@0
A=M-1
D=M
A=A-1
M=M+D
// RAM[0] -= 1.
@0
M=M-1
// Use Register 13 to store the thatBaseAddress + index.
@2
D=A
@4
D=M+D
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
// D = RAM[THIS/THAT].
@4
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
@1
D=A
@0
A=M
M=D
@0
M=M+1
// RAM[RAM[0]-2] + RAM[RAM[0]-1].
@0
A=M-1
D=M
A=A-1
M=M+D
// RAM[0] -= 1.
@0
M=M-1
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[address] = D.
@4
M=D
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
@1
D=A
@0
A=M
M=D
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
// Use Register 13 to store the Arguments Base Address + index.
@0
D=A
@2
D=M+D
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
@MAIN_LOOP_START
0;JMP
(END_PROGRAM)
