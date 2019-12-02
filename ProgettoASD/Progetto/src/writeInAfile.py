#!/usr/env python3
import random 
import os

def writeFile(n):

    file_descriptor=open("test.txt", "w") #file descriptor

    print("Type the upper bound of the range: ")
    max_value = (int)(input())

    for i in range(n):
        number = random.randint(1,max_value) #generate random numbers from 1 to 20
        file_descriptor.write("%d, " % number) #write numbers in a file
    file_descriptor.close()



def main():
    print("how many numbers do you want? ")
    n = (int)(input()) #cast to convert string into integer 
    writeFile(n)


if __name__ == '__main__':
    main()
