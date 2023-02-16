# INSERTION-SORT(A, n)
# for i = 2 to n                       
#   key = A[i]                              
#   // Insert A[i] into the sorted subarray A[1 : i – 1]
#   j = i – 1                               
#   while j > 0 and A[j] > key            
#       A[j + 1] = A[j]  
#       j = j – 1 
#   A[j + 1] = key


def insertion_sort(a, n):
    temp = 0

    for i in range(1, n):        
        key = a[i]  
        j = i - 1   
        while j >= 0 and a[j] > key:  
            a[j+1] = a[j]    
            j = j-1       
        a[j+1] = key
    print(a)



def main():

    a = [5, 2, 4, 6, 1, 3]
    n = len(a)

    print(a)
    
    insertion_sort(a, n)



if __name__ == "__main__":
    main()
