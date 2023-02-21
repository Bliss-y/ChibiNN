package Tensor;

import java.util.Arrays;

public class Grad implements GradI{
    public Tensor op1, op2, res;
    protected Grad(Tensor op1, Tensor op2, Tensor res) {
        this.op1 = op1;
        this.op2 = op2;
        this.res = res;
    }
    @Override
    public Tensor calculateGrad() {
        return null;
    }




}
