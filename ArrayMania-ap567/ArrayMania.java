import java.util.Arrays;
import java.util.Random;

/**
 * Class for assessment 1 in co518
 * 
 * @author Stefan Kahrs & Akash Payne
 * @version 0.1v 
 * @startDate 28/11/14  
 */
public class ArrayMania
{
    //Q1 goes elsewhere (see Exception Classes)
    //  Write a checked Exception subclass WrongInversionException with subclasses TooManyInversionsException 
    //  and TooFewInversionsException. Objects of those classes should carry information why the number of 
    //  inversions is wrong. [8 marks]
    //        - see other classes (WrongInversionException)

    //Q2 method inversionGeneratorI goes here
    //  Write a method int[] inversionGeneratorI(int length,int inversions) which generates and returns an 
    //  arbitrary int array of length length which has inversions many inversions. If it is not possible to 
    //  generate that many/few inversions for an array of that length then the method should throw a 
    //  WrongInversionException. For instance, if the method was called with length==6 and inversions==5 then
    //  the array {3,6,2,8,21,5} could be returned. [42 marks]
    //Q3 comment about time complexity of inversionGeneratorI   
    //  Write a program comment, as part of the documentation of inversionGeneratorI in which you explain
    //  what you think the time complexity of your implementation of inversionGeneratorI is, in terms of its 
    //  parameters, and why that is. Does this depend only on the length or also on the number of inversions? 
    //  [20 marks]
    /**
     * Answer to Question 3a: 
     * The inversion Generator A method, generates an arbitrary int array, randomArray, of length, length,
     * and unsorts the array to a specified upfront amount of inversions, inversions. The time complexity 
     * of this implementation is generate and test, i.e. exhaustive search that consists of systematically
     * list all possible items for the solution and check whether each candidate statisfies the problem
     * statement. Therefore, we can say that the implementation is Linear, O(n), i.e. the time complexity 
     * for searching when compared with data structures such as a sorted array of n element; its
     * cost is proportional to the number of candidate solutions. (Brute force). 
     * The maximum number of inversions in an array of n * elements is n⋅(n-1)2.
     * This shows that the number of elements in the array and the number of inversions in the array are
     * both proportional to the time complexity of the implementation. 
     */
    public int[] inversionGeneratorA(int length, int inversions) throws WrongInversionException
    {
        int max = 100000;
        //Exceptions for the number of Inversions: throw an exception message if caught. 
        //Checks if there are too few Inversions, i.e. none or null
        if( inversions < 0 )
        {
            throw new TooFewInversionsException();
        }
        //Checks if there are too many inversions (i.e. more than (l*(l-1/2), where l is length))
        else if ( inversions > (length * (length - 1) / 2) )
        {
            throw new TooManyInversionsException();
        } 
        else
        {
            // Creates an array with length, length       
            int[] randomArray = new int[length];
            // repeat until inversions match
            while (inversions != (inversionCount(randomArray)))
            {
                Random r = new Random();
                for (int i=0; i<randomArray.length; i++) 
                {
                    randomArray[i] = r.nextInt(max);
                }
            }
            System.out.println(Arrays.toString(randomArray));
            return randomArray;
        }
    }
    
    private int[] aSort(int array[])
    {
        if(array.length > 1) //if array is larger than 1 then we need to split it
        {
            int e1 = array.length / 2; // lengths used to create temp arrays
            int e2 = array.length - e1;
            int array1[] = new int[e1];
            int array2[] = new int[e2];
            
            for(int i = 0; i < e1; i++) // get the arrays
            {
               array1[i] = array[i];
               //System.out.println("1" + Arrays.toString(array));
            }
            for(int i = e1; i < e1 + e2; i++)
            {
                array2[i - e1] = array[i];
                //System.out.println("2" + Arrays.toString(array));
            }
            
            array1 = aSort(array1); // recursion to sort arrays
            array2 = aSort(array2);
            int x = 0, j = 0, k = 0;
            while(array1.length != j && array2.length != k)
            {
                if(array1[j] < array2[k])
                {
                    array[x] = array1[j];
                    x++;
                    j++;
                    //System.out.println(inversionCount(array)+": :" + Arrays.toString(array));
                }
                else
                {
                    array[x] = array2[k];
                    x++;
                    k++;
                    //System.out.println(inversionCount(array)+": :" + Arrays.toString(array));
                }
            }
            while(array1.length != j)
            {
                array[x] = array1[j];
                x++;
                j++;
                //System.out.println("5" + Arrays.toString(array));
            }
            while(array2.length != k)
            {
                array[x] = array2[k];
                x++;
                k++;
                //System.out.println("6" + Arrays.toString(array));
            }
            //System.out.println("7" + Arrays.toString(array));
        }
        return array;
    }
    
    private int[] bSort(int array[], int inversions)
    {
            int iter, jter, increment, temp,i,j;
            for(increment = array.length/2;increment > 0; increment /= 2)
            {
                    for(i = increment; i<array.length; i++)
                    {
                            temp = array[i];
                            //System.out.println(inversionCount(array)+" : 2 : "+ Arrays.toString(array));
                            for(j = i; j >= increment ;j-=increment)
                            {
                               // while ((inversionCount(array) < inversions) || (inversionCount(array) == inversions))
                                {
                                    if(temp < array[j-increment])
                                    {
                                            array[j] = array[j-increment];
                                            //System.out.println(inversionCount(array)+" : 2 : "+ Arrays.toString(array));
                                    }
                                    else
                                    {
                                            break;
                                    }
                                }
                            }
                            array[j] = temp;
                            //System.out.println(inversionCount(array)+" : 2 : "+ Arrays.toString(array));
                    }
                    //System.out.println(inversionCount(array)+" : : "+ Arrays.toString(array));
            }
            return array; 
    }
    
    private int[] shuffleArray(int[] array)
    {
        Random rnd = new Random();
            for (int i = array.length - 1; i > 0; i--)
            {
                int index = rnd.nextInt(i + 1);
                int value = array[index];
                array[index] = array[i];
                array[i] = value;
            }
        return array; 
    }
     
    private int[] nextPermutation(int[] array)
    {
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i])
        {
            i--;
        }
        if (i <= 0)
        {
            return array;
        }
        int j = array.length - 1;
        while (array[j] <= array[i - 1])
        {
            j--;
        }
        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;
        j = array.length - 1;
        while (i < j) 
        {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }
        return array;
    }
           
    /**
     * Answer to Question 3b: 
     * The inversion Generator A method, generates an arbitrary int array, randomArray, of length, length,
     * and unsorts the array to a specified upfront amount of inversions, inversions. The time complexity 
     * of this implementation reflects the shuffle of all the elements in the array and is proportional to
     * the size of the array and the number of inversions, i.e. exhaustive search that consists of 
     * systematically list all possible items for the solution and check whether each candidate statisfies 
     * the problem statement. Therefore, we can say that the implementation is Linear, O(n), i.e. the time 
     * complexity for searching when compared with data structures such as a sorted array of n element; 
     * its cost is proportional to the number of candidate solutions. (Brute force). 
     * The maximum number of inversions in an array of n * elements is n⋅(n-1)2. This shows that the number 
     * of elements in the array and the number of inversions in the array are both proportional to the time 
     * complexity of the implementation. 
     * 
     * After trying to make the generator more efficient with a quicksort or mergesort, to the split case 
     * the array, I found that the shuffle method was more efficient for generating smaller arrays with 
     * inversions.  
     */
    public int[] inversionGeneratorI(int length, int inversions) throws WrongInversionException
    {
        int max = 100000; 
        //Exceptions for the number of Inversions: throw an exception message if caught. 
        //Checks if there are too few Inversions, i.e. none or null
        if( inversions < 0 )
        {
            throw new TooFewInversionsException();
        }
        //Checks if there are too many inversions (i.e. more than (l*(l-1/2), where l is length))
        
        else if ( inversions > (length * (length - 1) / 2) )
        {
            throw new TooManyInversionsException();
        } 
        else
        {      
            // Create a variable to hold array elements of size length
            int[] randomArray = new int[length]; 
            // initalise random object
            Random r = new Random();
            // initalise random generate array
            for (int i=0; i<randomArray.length; i++) 
            {
                randomArray[i] = r.nextInt(max);
            }
            System.out.println("1.Array before sort : " + Arrays.toString(randomArray));
            // sort array
            randomArray = aSort(randomArray);
            // permutation of array till inversions found
            while (inversionCount(randomArray) != inversions)
            {
                randomArray = nextPermutation(randomArray);
            }
            System.out.println("2.Array after sort and inversions found : " + Arrays.toString(randomArray));
            System.out.println("3.Number of inversions counted by method : " + inversionCount(randomArray));
            return randomArray;
        }
    }
    
    /**
     * given method
     * @param array: any array of integers
     * @return the number of inversions in that array
     */
    private int inversionCount(int[] array) {
        int total;
        if (array.length<=1) return 0;
        int firstval=array[0];
        int smallcount=0;
        for (int i=1; i<array.length; i++) {
            if (array[i]<firstval) smallcount++;
        }
        total=smallcount;
        int[] left, right;
        left=new int[smallcount];
        right=new int[array.length-smallcount-1];
        int d1=0, d2=0;
        for (int i=1; i<array.length; i++) {
            if (array[i]<firstval) {
                left[d1++]=array[i];
                total+=d2;
            } else { right[d2++]=array[i]; }
        }
        return total+inversionCount(left)+inversionCount(right);
    }
    //Q4 put comment on performance of inversionCount here
    //  Add a program comment to the (provided) method inversionCount that explains how efficient 
    //  this method is, in terms of its parameter; you can also compare the method with other
    //  programs you have seen that perhaps have a similar structure. [10 marks]
    /**
     * Answer to Question 4:
     * The Inversion Count method for an array, above, indicates how far or close the given array,
     * array, is from being sorted. If the array is already sorted, e.g. 1, 2, 3, 4, 5, then the
     * inversion count method returns an int 0. If the array is sorted in reverse order then that 
     * inversion count is at the maximum. As the method uses a merge sort, the problem is split 
     * into two halves. The method takes O(n) time. T(n) ≤ 2T(n/2) + cn. Worse case run time is 
     * O(n log n). This is considered efficient as the operations per instance required to 
     * complete decrease with each instance. (Same complexity as a quick sort). 
     */
    
    //Q5 inversionGeneratorD goes here
    //  Write a method int[] inversionGeneratorD(int length,double sortedness) which generates an 
    //  int array of length length whose sortedness is measured by the floating point number 
    //  sortedness. This number should be between -1.0 (reverse order) and 1.0 (sorted order), with 
    //  the numbers in between scaled by the number of inversions the array has (relative to how 
    //  many it could have). Essentially this method should reduce its work to inversionGeneratorI, 
    //  and throw a  WrongInversionException if sortedness is out of bounds. [20 marks]
    /**
     * A method int[] inversionGeneratorD(int length,double sortedness) that generates an int 
     * array of length length whose sortedness is measured by the floating point number 
     * sortedness. This number should be between -1.0 (reverse order) and 1.0 (sorted order), 
     * with the numbers in between scaled by the number of inversions the array has (relative to 
     * how many it could have). Essentially this method should reduce its work to 
     * inversionGeneratorI, and throw a  WrongInversionException if sortedness is out of bounds.
     * Pearson's correlation coefficient:
     * -- r = 1/(n-1) * sumOfEach(x element - x mean)/standardDeviation for X * sumOfEach(y element 
     *                                          - y mean)/y standard deviation 
     * or scale -1 to 1 ... -1 = 0% sorted; 1 = 100% sorted.
     * sortedness: get a ratio for -1:1 and then multiple the max # of inversions to get inversions. 
     */  
    public int[] inversionGeneratorD(int length, double sortedness) throws WrongInversionException
    {
        //Exceptions for the number of Inversions: throw an exception message if caught. 
        //Checks if there are too few Inversions, i.e. none or null

        if( sortedness < -1.0 )
        {
            throw new TooFewInversionsException();
        }
        //Checks if there are too many inversions (i.e. more than (l*(l-1/2), where l is length))
        else if ( sortedness > 1.0 )
        {
            throw new TooManyInversionsException();
        } 
        else
        {
            int[] randomArray = new int[length];
            double ratio = (((sortedness + 1)*50)/100);
            //System.out.println(ratio);
            int maxInversions = (length * (length - 1) / 2);
            //System.out.println(maxInversions);
            double dInversions = ratio * maxInversions;
            //System.out.println(dInversions);
            int inversions = (int)dInversions;
            //System.out.println(inversions);
            randomArray = inversionGeneratorI(length, inversions);
            System.out.println("4.The value of sortedness : " + sortedness +" --- The value of inversions : "+inversions);
            //System.out.println("Array after sort and inversions found : " + Arrays.toString(randomArray));
            return randomArray;
        }
    }
}
