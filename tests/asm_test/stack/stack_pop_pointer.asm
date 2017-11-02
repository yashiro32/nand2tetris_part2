// D=RAM[RAM[0]-1].
@R0
M=M-1
A=M
D=M

// RAM[address] = D.
@R3
M=D