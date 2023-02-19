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
        Tensor t3 = new Tensor(new double[] {0,3,1,0,4,4,4,2,4,2,5,1},new int []{3,4});
        Tensor t2 = new Tensor(new double[] {3,0,2,2,3,0}, new int []{3,2});

//        System.out.println(Arrays.toString(t3.add(t3).getData()));
//
//
//        Tensor linear = t1.multiply(t2);
//        System.out.println(Arrays.toString(linear.getData()));
//        System.out.println(Arrays.toString(linear.shape()));
//
//        t1.setGrad();


//        t2.setGrad();

//        linear.grad = Tensor.ones(linear.shape()[0], linear.shape()[1]);

        Tensor f = t1.transpose(); // 0 0 4 2 3 4 2 5 1 4 4 1

        System.out.println(Arrays.toString(f.getData()));
//        System.out.println(Arrays.toString(f.multiply(linear.grad).getData()));
//        System.out.println(Arrays.toString(t3.multiply(linear.grad).getData()));
//
//
//        linear.gradFunc.calculateGrad();
//
//        System.out.println(Arrays.toString(t1.grad.getData()));
//        System.out.println(Arrays.toString(t2.grad.getData()));

    }
}