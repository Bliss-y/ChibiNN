package Tensor;

public class Grad implements GradI{
    Tensor op1, op2, res;
    Grad(Tensor op1, Tensor op2, Tensor res) {

    }
    @Override
    public Tensor calculateGrad() {
        return null;
    }
}
