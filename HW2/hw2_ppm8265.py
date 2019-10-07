'''
 MSiA 422 Homework 2 -- Written by Parth Patel
******************************************************************************* 
 THIS SCRIPT IS PRIMARILY TO SATISFY THE PROVIDED TEST CASES
 PLEASE REFER TO THE SUBMITTED JUPYTER NOTEBOOK FILE FOR COMPLETE DOCUMENTATION AND RESULTS
 
 The jupyter notebook code better utilizes the class structure so the code is not
 syntactically identical but functionally they are
*******************************************************************************

Directions:
Write a a class that offers 2 functions that works exactly like the sorted() built-in function in python
'''
'''
Define the class that has bubble and merge sorting methods
#  
The class has three methods defined:
- get_list() returns the list that the class object was initialized with (unused in this script)
- bubble_sort() implements a bubble sort algorithm on inputted list and returns a sorted version of the inputted list.
- merge_sort() applies a merge sort algorithm and returns a sorted version of the inputted list.
'''
#imports below are not used in this script (are used in the notebook)
#these were kept because code was pulled from the notebook and reworked to work with the test case
#not all 'unnecessary' reminants from the notebook were removed
import time  ##used (in the notebook) for returning time measure when a given sort function is called on its own
import timeit ##used (in the notebook) ffor coparing the two sort functions for reporting performance
import matplotlib.pyplot as plt  ##used (in the notebook) for generating performance comparison plot
import random ##used (in the notebook) for random generation of data for performance comparison and test cases

#class will inherit the base object class
class MySorted(object):
    '''
    A class that offers 2  sorting functions (bubble and merge algorithms) which receives inputs exactly like the sorted() built-in
    function in python.
    
    inputs: iterable of objects: any list of objects (of the same type, e.g. list of numbers, list of strings, list of lists)
    key: A custom key function can be supplied to customize the sort order, e.g. for sorting list can provide key str.lower(); defaults to None so do not supply anything unless desired
    reversed: A flag field that allows the order of sort to be defined. E.g. if descending order sort is desired supply True. If ascending order is desired supply True or do not provide the arg as it is defaulted to True
    
    '''
    #This is used in the jupyter notebook code, not in this script for results testing 
    def __init__(self,a_list=[],key_in=lambda x:x,reverse=False):
        self.list_in = a_list
        self.key = key_in
        if reverse == True or reverse == False:
            self.rev_flag = reverse
        else:
            raise ValueError("Reversed field supplied must be True or False")

    #method to return the supplied list (unused in this script, but used in the notebook)
    def get_list(self):
        '''
        input: none (method of MySorted class instance)
        output: returns the iterable of objects for instance of class MySorted
        '''
        return self.list_in
    
    #class method for implementing a bubble sort algorithm to the supplied list
    def bubble_sorted(self,iterable=[],key=lambda x:x,reverse=False):
        '''
        input: iterable desired to be sorted, key, reverse flag
        output: method that sorts (via bubble sort algorithm) the iterable of objects associated for an instance of MySorted class
        '''
        start_time = time.time()
        nComp = 0 #used in the jupyter notebook
        nSwap = 0

        if reverse == False:
            for pass_num in range(len(iterable) - 1, 0, -1):
                for i in range(pass_num):
                    nComp += 1
                    if key(iterable[i]) > key(iterable[i + 1]):                
                        iterable[i],iterable[i + 1] = iterable[i + 1] ,iterable[i]
                        nSwap+=1
            end_time = time.time()
        else:
            for pass_num in range(len(iterable) - 1, 0, -1):
                for i in range(pass_num):
                    nComp += 1
                    if key(iterable[i]) < key(iterable[i + 1]):                
                        iterable[i],iterable[i + 1] = iterable[i + 1] ,iterable[i]
                        nSwap+=1
            end_time = time.time()
        return iterable
    
    #class method for implementing a merge sort algorithm to the supplied list
    def merge_sorted(self,a_list,key=lambda x:x,reverse=False):
        '''
        input: iterable desired to be sorted, key, reverse flag
        output: method that sorts (via merge sort algorithm) the iterable of objects associated for an instance of MySorted class
        '''
        nComp = 0
        nSwap = 0
        lft_comp = 0
        rgt_comp = 0
        lft_swap = 0
        rgt_swap = 0
        start_time = time.time()
        if len(a_list) > 1:    
            mid = len(a_list) // 2
            left_half = a_list[:mid]
            right_half = a_list[mid:]
            self.merge_sorted(left_half,key,reverse)
            self.merge_sorted(right_half,key,reverse)

            i = 0
            j = 0
            k = 0

            if reverse == False:
                while i < len(left_half) and j < len(right_half):
                    nComp += 1
                    if key(left_half[i]) < key(right_half[j]):
                        nSwap+=1
                        a_list[k] = left_half[i]
                        i = i + 1
                    else:
                        a_list[k] = right_half[j]
                        j = j + 1
                    k = k + 1
            else:
                while i < len(left_half) and j < len(right_half):
                    nComp += 1
                    if key(left_half[i]) > key(right_half[j]):
                        nSwap+=1
                        a_list[k] = left_half[i]
                        i = i + 1
                    else:
                        a_list[k] = right_half[j]
                        j = j + 1
                    k = k + 1

            while i < len(left_half):
                a_list[k] = left_half[i]
                i = i + 1
                k = k + 1

            while j < len(right_half):
                a_list[k] = right_half[j]
                j = j + 1
                k = k + 1
        end_time = time.time()
        return(a_list)