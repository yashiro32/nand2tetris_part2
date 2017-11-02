// D=RAM[RAM[0]-1].
@R0
M=M-1
A=M
D=M

// RAM[Foo.index] = D.
@Foo.5
M=D