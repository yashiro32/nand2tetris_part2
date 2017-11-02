@10
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the Locals Base Address + index.
@0
D=A
@1
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
@21
D=A
@0
A=M
M=D
@0
M=M+1
@22
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the Arguments Base Address + index.
@2
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
// Use Register 13 to store the Arguments Base Address + index.
@1
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
@36
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the thisBaseAddress + index.
@6
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
@42
D=A
@0
A=M
M=D
@0
M=M+1
@45
D=A
@0
A=M
M=D
@0
M=M+1
// Use Register 13 to store the thatBaseAddress + index.
@5
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
@510
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
// RAM[tempBaseAddress+index] = D.
@11
M=D
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
@5
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
// D = index.
@6
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
// D = index.
@6
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
M=M+D
@0
M=M-1
@0
A=M-1
D=M
A=A-1
M=M-D
@0
M=M-1
// D = RAM[tempAddress + index].
@11
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
