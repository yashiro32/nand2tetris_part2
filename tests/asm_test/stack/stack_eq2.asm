@SP
AM=M-1
D=M
A=A-1
D=M-D
@FALSE
D;JNE
@SP
A=M-1
M=-1
@CONTINUE
0;JMP
(FALSE)
@SP
A=M-1
M=0
(CONTINUE)