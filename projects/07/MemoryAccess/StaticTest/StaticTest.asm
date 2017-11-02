@111
D=A
@0
A=M
M=D
@0
M=M+1
@333
D=A
@0
A=M
M=D
@0
M=M+1
@888
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
// RAM[filename.index] = D.
@StaticTest8
M=D
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[filename.index] = D.
@StaticTest3
M=D
// D=RAM[RAM[0]-1].
@0
M=M-1
A=M
D=M
// RAM[filename.index] = D.
@StaticTest1
M=D
// D = RAM[filename.index].
@StaticTest3
D=M
// RAM[RAM[0]] = D.
@0
A=M
M=D
// Increment RAM[0] by 1.
@0
M=M+1
// D = RAM[filename.index].
@StaticTest1
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
// D = RAM[filename.index].
@StaticTest8
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
