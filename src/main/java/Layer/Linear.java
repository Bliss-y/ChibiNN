package Layer;

import Tensor.Tensor;

import java.util.ArrayList;
import java.util.Dictionary;

public class Linear implements Layer{
    Tensor weight;
    Tensor bias;
    Tensor output = null;
    public Linear(int input_size, int output_size) {
        this.weight = Tensor.rand(input_size, output_size);
        this.bias = new Tensor(new int[] {output_size});
    }

    public Tensor[] parameters() {
        return new Tensor[]{weight, bias};
    }

    @Override
    public Tensor forward(Tensor input) {
        output = input.mul(weight).add(bias);
        return output;
    }

    //TODO: IMPLEMENT A CALLBACK SYSTEM SO TENSORS/PARAMS HOLD THEIR OWN GRADS
    @Override
    public Tensor backward(Tensor grad) {
        bias.grad = grad.inSum(0);
//        weight.grad = this.
        return null;
    }
}
