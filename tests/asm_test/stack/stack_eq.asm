@R0
A=M-1
D=A
@R13
M=D
@R0
A=M-1
A=A-1
D=A
@R14
M=D

@R13
A=M
D=M
@R14
A=M
D=D-M
@NOTEQUALZERO
D;JNE
@R14
A=M
M=1
@DECREMENTSP
0;JMP

(NOTEQUALZERO)
@R14
A=M
M=0

(DECREMENTSP)
@R0
M=M-1