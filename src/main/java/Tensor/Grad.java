package Tensor;

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

    @Override
    public void backward() {
        res.gradFunc.calculateGrad();
        if(op1 != null && op1.gradFunc != null) op1.gradFunc.calculateGrad();

        if(op2 != null && op2.gradFunc != null) op2.gradFunc.calculateGrad();

    }


}
