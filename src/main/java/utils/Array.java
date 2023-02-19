package utils;

import java.util.Arrays;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * Basic Array utility class for basic numerical operation when the time may come :)
 */

public class Array {

    public static int sum(int[] arr) {
        LongAdder sum = new LongAdder();
        Arrays.stream(arr).forEach(sum::add);
        return sum.intValue();
    }

    public static double[] subset(double[] arr, int s, int e) {
        return Arrays.copyOfRange(arr, s, e);
    }
    public static int[] subset(int[] arr, int s, int e) {
        return Arrays.copyOfRange(arr, s, e);
    }

    public static int[] sum(int[] arr, int[] arr2) {
        if(arr.length != arr2.length) throw new IllegalArgumentException("The arrays lengths donot match");
        int sum[] = new int[arr.length];
        for(int i=0; i < arr.length; i++) {
            sum[i] = arr[i] + arr2[i];
        }
        return sum;
    }

    public static int[] product(int[] arr, int[] arr2) {
        if(arr.length != arr2.length) throw new IllegalArgumentException("The arrays lengths donot match");
        int sum[] = new int[arr.length];
        for(int i=0; i < arr.length; i++) {
            sum[i] = arr[i] * arr2[i];
        }
        return sum;
    }

    public static double[] mult(double [] arr, double m) {
        double sum[] = new double[arr.length];
        for(int i=0; i < arr.length; i++) {
            sum[i] = arr[i] * m;
        }
        return sum;
    }

    public static double[] pow(double [] arr, double m) {
        double sum[] = new double[arr.length];
        for(int i=0; i < arr.length; i++) {
            sum[i] = Math.pow(arr[i], m);
        }
        return sum;
    }


    public static int product(int[] arr) {
        LongAccumulator product = new LongAccumulator((a, b) -> a * b, 1);
        Arrays.stream(arr).forEach(product::accumulate);
        return (int) product.get();
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
