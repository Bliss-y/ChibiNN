package Tensor;

public class Tensor {
    private double[] data;
    private int[] shape;
    private Tensor grad;

    public Tensor(double[] data, int[] shape) {
        this.data = data;
        this.shape = shape;
    }

    public int[] shape() {
        return this.shape;
    }

    public double[] getData() {
        return this.data;
    }

    public String show() {
        String out = "Chibi.Tensor(";
            for (int i : this.shape) {
                
            }

        out += ")";
        return
    }






}
