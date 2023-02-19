package org.example;

import Tensor.Tensor;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) {

        Tensor t1 = new Tensor(new double[] {
                0,3,1, // 4 4
                0,4,4, // 8 8
                4,2,4, //
                2,5,1
        },new int []{4,3});
        Tensor t2 = new Tensor(new double[] {3,0,2,2,3,0}, new int []{3,2});
        Tensor linear = t1.multiply(t2);
        t1.setGrad();
        System.out.println("initial t1 grad: "+Arrays.toString(t1.getGrad().getData()));

        t2.setGrad();
        linear.setGrad(Tensor.ones(linear.shape()[0], linear.shape()[1]));
        Tensor t3 = t1.transpose();
        Tensor expectedGrad = t3.multiply(linear.getGrad());
        System.out.println("expected gradiation: "+Arrays.toString(expectedGrad.getData()));
        linear.gradFunc.calculateGrad();
        System.out.println(Arrays.toString(t1.getGrad().getData()));
        System.out.println(Arrays.toString(t2.getGrad().getData()));


    }
}