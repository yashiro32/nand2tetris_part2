// Use Register 13 to store the thatBaseAddress + index.
@55
D=A
@R4
D=M+D
@R13
M=D

// D=RAM[RAM[0]-1].
@R0
M=M-1
A=M
D=M

// RAM[thatBaseAddress+index] = D.
@R13
A=M
M=D