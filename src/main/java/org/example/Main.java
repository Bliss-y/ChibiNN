package org.example;

import Tensor.Tensor;

import java.util.Arrays;

public class Main {

    /*
       ax + b = c;
        x -> input
        t -> list of all the actual answers;
        for :
           c = ax + b; // layer 1
           d = yc + g; // layer 2
           loss = (t - d).avg; -> 10
           loss.backward();
           for all parameters:
                c = grad *
     */

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
        Tensor t3 = t1.transpose();
        linear.backward();
        System.out.println("Expected grad of T2: " + Arrays.toString((t3.multiply(Tensor.ones(linear.shape()[0], linear.shape()[1]))).getData()));

        System.out.println(Arrays.toString(t1.getGrad().getData()));
        System.out.println(Arrays.toString(t2.getGrad().getData()));


    }
}