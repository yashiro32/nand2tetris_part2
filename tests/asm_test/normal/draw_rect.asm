@R0
D=M
@n
M=D // n = RAM[0]

@i
M=0 // i = 0

@SCREEN
D=A
@address
M=D // address = 16384 (base address of the Hack screen)

(LOOP)
@i
D=M
@n
D=D-M
@END
D;JEQ

// RAM[address] = -1
@address
A=M
M=-1

// Increment the address by 32 to go to the next row.
@address
D=M
@32
D=D+A
@address
M=D

// i++
@i
M=M+1

@LOOP
0;JMP

(END)
@END
0;JMP