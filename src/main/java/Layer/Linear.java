package Layer;

import Tensor.Tensor;


public class Linear extends Layer{
    Tensor output = null;

    // output_size would be number of parameter
    public Linear(int input_size, int output_size) {
        Tensor weight = Tensor.rand(input_size, output_size);
        this.parameters = new Tensor[]{weight};
    }
    @Override
    public void setIndex(int i) {
        this.parameters[0].name = "weight: " + i;
    }

    @Override
    public Tensor[] parameters() {
        return this.parameters;
    }

    @Override
    public void backProp(double multiplier) {

    }


    @Override
    public Tensor forward(Tensor input) {
        output = input.multiply(this.parameters[0]);
        return output;
    }
}
