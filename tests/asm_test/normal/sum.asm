@R0
D=M
@n
M=D // n = R0
@i
M=1 // i = 1
@sum
M=0 // sum = 0

(LOOP)
@i
D=M
@n
D=D-M
@PUT
D;JGT // if i > n goto PUT

@sum
D=M
@i
D=D+M
@sum
M=D // sum = sum + i

@i
M=M+1 // i = i + 1

@LOOP
0;JMP

(PUT)
@sum
D=M
@R1
M=D // RAM[1] = sum

(END)
@END
0;JMP