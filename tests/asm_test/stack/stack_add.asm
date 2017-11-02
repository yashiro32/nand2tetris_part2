// Set D to the content of RAM[RAM[0]-1]
@R0 // RAM[0] is the memory location of the stack pointer.
A=M-1 // Set A to M-1
D=M

// Add D to the content of RAM[RAM[0]-2]
A=A-1
M=M+D

// Decrement Stack Pointer by 1.
@R0
M=M-1
