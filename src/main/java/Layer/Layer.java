package Layer;

import Tensor.Tensor;

import java.util.ArrayList;

public interface Layer {

    Tensor forward(Tensor g);
    Tensor[] parameters();
}
