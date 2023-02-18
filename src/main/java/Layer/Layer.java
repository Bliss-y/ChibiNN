package Layer;

import Tensor.Tensor;

public interface Layer {

    Tensor forward(Tensor g);
    Tensor backward(Tensor g);
}
