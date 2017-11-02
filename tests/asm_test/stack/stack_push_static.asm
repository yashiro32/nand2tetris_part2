// D = RAM[Foo.index].
@Foo.10
D=M
		
// RAM[RAM[0]] = D.
@R0
A=M
M=D

// Increment RAM[0] by 1.
@R0
M=M+1