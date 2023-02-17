package Tensor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

/**
 * for shape every array is a dimension... ?
 * shape 1,2 would be two-dimensional arrays whereas shape 2,1 or simply 2 would be 1-dimensional array
 * So if i want all the elements of first position of first dimension i.e 0th row then i should give all the elements of that row
 * wether it be an array or single element in case of [2,1]/ [2] array
 *
 *
 */
public class Tensor {
    private double[] data;
    private int[] shape;
    private Tensor grad;

    public Tensor(double[] data, int[] shape) {
        if(data.length != Arrays.stream(shape).reduce(1, (a,b)-> a*b)){
            throw new IllegalArgumentException("Invalid shape for length " + data.length);
        }
        this.data = data;
        this.shape = shape;
    }

    public Tensor(int[] shape) {
        this.shape = shape;
        this.data = new double[]{this.size()};
    }

    public int size () {
        int size = 1;
        for (int i : shape) {
            size *= i;
        }
        return size;
    }

    public Tensor subset(int[] indices) {
        if (indices.length  >= this.shape.length) throw new IllegalArgumentException("Can't subset from same dimensions!");
        // [a,b,c,c](2,2) -> [n] -> return [n*(multiple of other dimensions), n*(multiple of other dimensions) + multiple of others ]
        int first=0;
        for (int i=0; i < indices.length; i++) {
            first += indices[i] * this.shape[i+1];
        }
        System.out.println("first" +first);
        int last = Arrays.stream(Arrays.copyOfRange(this.shape, indices.length, this.shape.length)).reduce(1, (a,b)-> a*b);
        System.out.println("last" +last);
        return new Tensor(Arrays.copyOfRange(this.data, first, last), Arrays.copyOfRange(this.shape, indices.length, this.shape.length));
    }
    public int[] shape() {
        return this.shape;
    }
    public double[] getData() {
        return this.data;
    }

    public Tensor transpose() {
        int [] nshape = this.shape.clone();
        for (int i = 0; i < nshape.length; i++) {
               nshape[i] = this.shape[this.shape.length-i-1];
        }
        return new Tensor(this.data.clone(), nshape);
    }

    public Tensor add(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
            doubles[i] += this.data[i];
        }
        return new Tensor(doubles, this.shape.clone());
    }

    public Tensor sub(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
            doubles[i] -= this.data[i];
        }
        return new Tensor(doubles, this.shape.clone());
    }

    public Tensor mul(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
            doubles[i] *= this.data[i];
        }
        return new Tensor(doubles, this.shape.clone());
    }

    public Tensor div(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
             this.data[i] /= doubles[i];
        }
        return new Tensor(doubles, this.shape.clone());
    }



    public String toString() {
        //TODO
//        SHape -> [1,2] -> ([
//        [a, b]
//        ]), shape-> [2] -> ([1,2])
        String out = "Chibi.Tensor(";
            for (int i : this.shape) {

            }

        out += ")";
        return out;
    }
}
