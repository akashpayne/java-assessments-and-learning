import java.util.Arrays;
public class Exercise17 {
  public static void main(String[] args) {

		int[] my_array = {
            1789, 2035, 1899, 1456, 2013, 
            1458, 2458, 1254, 1472, 2365, 
            1456, 2165, 1457, 2456};

		int max = my_array[0];
		int second_max = my_array[0];
		
	    System.out.println("Original numeric array : "+Arrays.toString(my_array));
	
		for (int i = 0; i < my_array.length; i++) {

			if (my_array[i] > max) {
				second_max = max;
				max = my_array[i];

			} else if (my_array[i] > second_max) {
				second_max = my_array[i];

			}
		}

		System.out.println("Second largest number is : " + second_max);

	}
}
