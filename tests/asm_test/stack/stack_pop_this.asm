// Use Register 13 to store the thisBaseAddress + index.
@2
D=A
@R3
D=M+D
@R13
M=D

// D=RAM[RAM[0]-1].
@R0
M=M-1
A=M
D=M

// RAM[thisBaseAddress+index] = D
@R13
A=M
M=D