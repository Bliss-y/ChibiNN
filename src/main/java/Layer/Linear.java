package Layer;

import Tensor.Tensor;


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
}
