package org.example;

import Layer.Linear;
import Layer.Tanh;
import Tensor.Tensor;
import Tensor.Val;
import Tensor.CL;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    /*
       ax + b = Tensor.CL;
        x -> input
        t -> list of all the actual answers;
        for :
           Tensor.CL = ax + b; // layer 1
           d = yc + g; // layer 2
           loss = (t - d).avg; -> 10
           loss.backward();
           for all parameters:
                Tensor.CL = grad *
     */

    public static void main(String[] args) {
        test();

    }

    public static void test() {
        Tensor input = new Tensor(new double[]{
                0,0,
                1,1,
                1,0,
                0,1}, new int[]{4,2});
        Tensor output = new Tensor(new double[]{1,0,1,0,0,1,0,1}, new int[]{4,2});
        input.name ="input";
        Linear linear = new Linear(2, 2);// input @ output -> [4,200]
        linear.parameters[0].name = "W1";
        Tanh tanh = new Tanh();

        Linear l2 =new Linear(2, 2); // 200,1 ->
        l2.parameters[0].name = "W2";

        output.name = "output";
        ArrayList<Tensor> params = new ArrayList<>();
        params.add(linear.parameters[0]);
        params.add(l2.parameters[0]);
        for (int i = 0; i < 500; i++) {
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
            Tensor loss_sq = loss.sum().pow(2);
            loss_sq.name = "loss_sq";
//            System.out.println("Loss: " + Arrays.toString(loss.getData()));
            Tensor.backpropping = true;

            linear.parameters[0].setGrad();
            l2.parameters[0].setGrad();
            loss.setGrad();
            loss_sq.setGrad();
            loss_sq.backward();
            loss_sq.printData();

            /*
             *Manual BackPropagating
            Tensor dloss_sq = Tensor.ones(loss_sq.shape()[0], loss_sq.shape()[1]);
            Tensor dloss = loss.mul(2);
            Tensor dout3 = dloss.mul(1); // loss = out3 - output
            Tensor dw2 = out.transpose().multiply(dout3); // out3 = out @ w2 dw2 = out.T @ dout3;
            dw2.name = "w2 expected grad ";
            Tensor dout =  dout3.multiply(l2.parameters[0].transpose()); // dout = dout3 @ w2.T
            Tensor dw1 =  input.transpose().multiply(dout);  //out = input @ w1; //dw1= input.T @ dout
            dw1.name = "w1 expected grad ";



            */

            linear.parameters[0] = linear.parameters[0].sub(linear.parameters[0].getGrad().mul(0.1));
            l2.parameters[0] = l2.parameters[0].sub(l2.parameters[0].getGrad().mul(0.1));
        }
    }

    public static Tensor loss(Tensor T) {
        return  null;
    }
}