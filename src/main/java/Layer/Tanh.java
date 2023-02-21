package Layer;

import Tensor.Tensor;
import Tensor.Grad;

import java.util.ArrayList;

public class Tanh implements Layer {
    @Override
    public Tensor forward(Tensor g) {
        double[] gdata = g.getData().clone();
        for(int i=0; i<g.size(); i++){
            gdata[i] = tanh(gdata[i]);
        }
        Tensor out = new Tensor(gdata, g.shape().clone());
        if(Tensor.backpropping) {
            out.gradFunc = new Grad(g, null, out){
                @Override
                public Tensor calculateGrad() {
                    return null;
                }
            };
        }

        return out;
    }

    public double tanh(double d){
        double d2 = Math.exp(2*d);
        return (d2-1)/(d2+1);
    }

    @Override
    public Tensor[] parameters() {
        return new Tensor[]{};
    }
}
