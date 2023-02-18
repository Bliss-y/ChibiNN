package org.example;

import Tensor.Tensor;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) {

        Tensor t1 = new Tensor(new double[] {1,2,3,4,5,6,1,2,3,4,5,6},new int []{2,2,3});
        Tensor t2 = new Tensor(new double[] {1,2,3,4,5,6,1,2,3,4,5,6}, new int []{2,3,2});


        double[] d = {1,2,3,4,5,6,1,2,3,4,5,6};

        System.out.println(Arrays.toString(Arrays.copyOfRange(d, 6, 12)));

        System.out.println(Arrays.toString(t1.multiply(t2).getData()));
        System.out.println(Arrays.toString(t1.multiply(t2).shape()));

    }
}