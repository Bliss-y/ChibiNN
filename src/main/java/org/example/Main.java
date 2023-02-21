package org.example;

import Layer.Layer;
import Layer.Linear;
import Layer.Tanh;
import Tensor.Tensor;
import utils.Array;

import java.util.ArrayList;
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
        Tensor input = new Tensor(new double[]{
                0,0,
                1,1,
                1,0,
                0,1}, new int[]{4,2});
        Tensor output = new Tensor(new double[]{0, 0, 1,1}, new int[]{4});
        input.name ="input";
        Linear linear = new Linear(2, 200);// input @ output -> [4,200]
        Tanh tanh = new Tanh();
        Linear l2 =new Linear(200, 1);
        output.name = "output";

        for (int i = 0; i < 1; i++) {
            Tensor.backpropping = false;

            Tensor X = new Tensor(input.getData().clone(), input.shape().clone());
            Tensor out = linear.forward(X);
            out.name = "l1";
            Tensor out2 = tanh.forward(out);
            out2.name = "h";
            Tensor out3 = l2.forward(out2);
            out3.name = "l2";
            Tensor loss = out3.sub(output);
            loss.name = "loss";
            Tensor loss_sq = loss.pow(2);
            loss_sq.name = "loss_sq";
            System.out.println();
            System.out.println("Loss: " + Arrays.toString(loss_sq.getData()));
            Tensor.backpropping = true;
            loss_sq.backward();

//            for( int j =0; j < params.length; i++) {
//                params[j]= params[j].add(params[j].getGrad().mul(0.1*-1));
//            }
        }

    }

    public static Tensor loss(Tensor T) {
        return  null;
    }
}