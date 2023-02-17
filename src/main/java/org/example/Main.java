package org.example;

import Tensor.Tensor;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        Tensor tensor = new Tensor((new double[]{1,2,3,4,5,6}), new int[]{2,3});

        Tensor t2 = tensor.transpose();
        double[] doubles = t2.add(tensor).getData();

        System.out.println(Arrays.toString(tensor.getData()));
        System.out.println(Arrays.toString(t2.shape()));
        System.out.println(Arrays.toString(tensor.shape()));

    }
}