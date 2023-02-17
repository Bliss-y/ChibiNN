package org.example;

import Tensor.Tensor;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        Tensor tensor = new Tensor((new double[]{1,2,3,4,5,6}), new int[]{2,3});

        Tensor t2 = tensor.subset(new int[]{0});
        double[] doubles = t2.add(t2).getData();
        for(double d : doubles) {
            System.out.print(d+", ");
        }

    }
}