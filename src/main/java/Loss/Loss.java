package Loss;

import Tensor.Tensor;

public class Loss {
    public  Tensor input;
    public Tensor loss;
    Loss(Tensor input){
        this.input =input;
    }

    public void forward() {
//        this.loss = input.sum()
    }

}
