// Suppose that arr=100 anf n=10

@100
D=A
@arr
M=D

@10
D=A
@n
M=D

// Initialize i = 0
@i
M=0

(LOOP)
// If (i ==n) goto END.
@i
D=M
@n
D=D-M
@END
D;JEQ

// RAM[arr+i] = -1
@arr
D=M
@i
A=D+M
M=-1

// i++
@i
M=M+1

@LOOP
0;JMP

(END)
@END
0;JMP