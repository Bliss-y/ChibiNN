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


    }

    public static void test() {
        Tensor input = new Tensor(new double[]{
                0,0,
                1,1,
                1,0,
                0,1}, new int[]{4,2});
        Tensor output = new Tensor(new double[]{0, 0, 1,1}, new int[]{4});
        input.name ="input";
        Linear linear = new Linear(2, 200);// input @ output -> [4,200]
        linear.parameters[0].name = "W1";
        Tanh tanh = new Tanh();

        Linear l2 =new Linear(200, 1);
        l2.parameters[0].name = "W2";

        output.name = "output";
        ArrayList<Tensor> params = new ArrayList<>();
        params.add(linear.parameters[0]);
        params.add(l2.parameters[0]);
        for (int i = 0; i < 1; i++) {
            Tensor.backpropping = false;


            Tensor X = new Tensor(input.getData().clone(), input.shape().clone());
            X.name = "X";
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
            linear.parameters[0].printData();
            linear.parameters[0] = linear.parameters[0].add(linear.parameters[0].getGrad().mul(0.1*-1));
            linear.parameters[1] = linear.parameters[0].add(linear.parameters[0].getGrad().mul(0.1*-1));
        }
    }

    public static Tensor loss(Tensor T) {
        return  null;
    }
}