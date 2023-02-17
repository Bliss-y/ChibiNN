package utils;

import java.util.Arrays;

public class Aray {

    public static int[] sum(int[] arr1) {
        
    }
    public static int[] concat(int[] arr1, int[] arr2) {
        int[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);

        // copy the elements of arr2 into the new array starting at the end of arr1
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    public static double[] concat(double[] arr1, double[] arr2) {
        double[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);

        // copy the elements of arr2 into the new array starting at the end of arr1
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

}
