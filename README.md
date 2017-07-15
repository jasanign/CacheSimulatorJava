# CacheSimulatorJava

This program simulates the behavior of an instruction cache. This program reads data from standard input and produce statistics about a cache 'trace' to standard output. The input contains cache configuration information followed by memory references consisting of hexadecimal addresses, one per line. The first three lines of the input speficies the number of cache sets, the number of elements of each set (associativity level), and the size of a cache line, in bytes. Following the first three lines each line consists of 3 colon separated values representing the memory address (in hexadecimal notation), type of memory access (R or W), and the size, in bytes, to read or write, (Assuming the word size is 32 bits).

# Example input is below

Number of sets: 2
Set size: 2
Line size: 8
R:4:58
R:4:68
R:4:58
R:4:68
R:4:40
R:4:c
R:4:40
R:4:48
