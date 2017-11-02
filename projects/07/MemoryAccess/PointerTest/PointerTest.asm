@3030
D=A
@0
A=M
M=D
@0
M=M+1
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[address] = D.
@3
M=D
@3040
D=A
@0
A=M
M=D
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
@32
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the thisBaseAddress + index.
@2
D=A
@3
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
@46
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the thatBaseAddress + index.
@6
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
@3
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
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
@0
A=M-1
D=M
A=A-1
M=M+D
@0
M=M-1
// D = index.
@2
D=A
// D = RAM[THIS+index].
@3
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
// D = index.
@6
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
@0
A=M-1
D=M
A=A-1
M=M+D
@0
M=M-1
