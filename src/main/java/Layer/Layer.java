package Layer;

import Tensor.Tensor;

public abstract class Layer {

    public Tensor[] parameters;

    public Tensor forward(Tensor g){
        return null;
    };
    public Tensor[] parameters(){
        return null;
    }

    public void backProp(double multiplier){

    }
    public abstract void setIndex(int i);
}
