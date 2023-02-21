package Layer;

import Tensor.Tensor;
import Tensor.Grad;

public class Tanh extends Layer {
    public String name = "Tanh";
    @Override
    public Tensor forward(Tensor g) {
        double[] gdata = g.getData().clone();
        for(int i=0; i<g.size(); i++){
            gdata[i] = tanh(gdata[i]);
        }
        Tensor out = new Tensor(gdata, g.shape().clone());
        if(!Tensor.backpropping) {
            out.gradFunc = new Grad(g, null, out){
                @Override
                public Tensor calculateGrad() {
                    this.op1.setGrad(new Tensor(ntanh2(this.res.getData()), res.shape().clone()));
                    return null;
                }
            };
        }
        return out;
    }
    public static double[] ntanh2(double[] d){
        double[] newd = new double[d.length];
        for(int i=0; i < d.length; i++){
            newd[i] = -1*Math.pow(d[i], 2);
        }
        return newd;
    }

    public static double tanh(double d){
        double d2 = Math.exp(2*d);
        return (d2-1)/(d2+1);
    }

    @Override
    public Tensor[] parameters() {
        return new Tensor[]{};
    }

    @Override
    public void setIndex(int i) {
    }
}
