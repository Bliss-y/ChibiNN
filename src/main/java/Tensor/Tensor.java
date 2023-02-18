package Tensor;

import utils.Array;

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
            System.out.println("Tensor attempted: " +data.length);
            System.out.println(Arrays.toString(shape));
            System.out.println(Arrays.toString(data));
            throw new IllegalArgumentException("Invalid shape for length " + data.length);
        }

        this.data = data;
        this.shape = shape;
    }

    private Tensor() {
        this.data = null;
        this.shape =null;
    }

    public Tensor empty() {
        return new Tensor();
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

    /**
     *
     * @param n the dimensions until which the dimension is not to be touched! (don't use 0 indexing here !)
     * @return a transposed matrix where the dimensions are revers after nth dimensions not touching dimensions until n
     */
    public Tensor transpose(int n) {
        if(n >= this.shape.length) throw  new IllegalArgumentException("maximum dimension is given as pivot of transpose");
        int [] nshape = Arrays.copyOfRange(this.shape, n+1, this.shape.length);
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

    /**
     *
     * @param T
     * @return matrix multiplication/vector dot product of this with other
     */
    public Tensor multiply(Tensor T) {
        if (T.shape.length != this.shape.length) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        return T.shape.length ==3 ? this.Mul3d(T) : this.Mul2d(T);
    }



    public Tensor div(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
             this.data[i] /= doubles[i];
        }
        return new Tensor(doubles, this.shape.clone());
    }

    private Tensor Mul3d(Tensor t) {
        if (t.shape[0] != this.shape[0] || this.shape[2] != t.shape[1]) throw new IllegalArgumentException("dimensions do not match for multiplication");
        double[] tmpdata= {};
        for (int i =0; i < this.shape[0]; i++) {
            System.out.println(i*this.shape[1]*this.shape[2]);
            System.out.println(i*(this.shape[1]*this.shape[2]) +this.shape[1] *this.shape[2]);
            Tensor tmp1 = copyRange(this, i*this.shape[1]*this.shape[2] , i*(this.shape[1]*this.shape[2]) +this.shape[1] *this.shape[2], new int[]{this.shape[1], this.shape[2]});
            Tensor tmp2 = copyRange(this, i*t.shape[1]*t.shape[2] ,i*(t.shape[1]*t.shape[2]) + t.shape[1] * t.shape[2], new int[]{t.shape[1], t.shape[2]});
            tmpdata = Array.concat(tmpdata, tmp1.Mul2d(tmp2).data);
        }
        return new Tensor(tmpdata, new int[]{this.shape[0], this.shape[1], t.shape[2]});
    }

    public static Tensor copyRange(Tensor t, int s, int e, int[] shape) {
        return new Tensor(Array.subset(t.data, s, e),shape);
    }

    // works tested alongside pytorch results :)
    private Tensor Mul2d(Tensor t) {
        if(t.shape[0] != this.shape[1]) throw new IllegalArgumentException("The column of first and row of second doesnot match");
        double[] newdata = new double[this.shape[0] * t.shape[1]];

        int counter = 0;
        for (int i=0; i < this.shape[0]; i++) {
            double[] tdata = t.getData();
            for (int j =0; j < t.shape[1]; j++) {
                // cij = ai1.b11+ aij.bj1
                double sum = 0;
                for(int k = 0; k < t.shape[0]; k++) {
                    sum += this.data[i*this.shape[1] + k] * tdata[k*t.shape()[1] + j];
                }
                newdata[counter] = sum;
                counter++;
            }
        }
        return new Tensor(newdata, new int[]{this.shape[0], t.shape[1]});
    }

    private class TensorTraverse {
        private Tensor tensor;
        private int[] traversal_indices;
        private int currentIndex = -1;
        public TensorTraverse(Tensor tensor) {
            this.tensor = tensor;
        }

        /*
            BASIC PREMISE:- GIVEN THE AXIS OF ANY (N1, N2...N(n-1) ) FOR A TENSOR OF SHAPE (N1,N2,...Nn)
            THIS CLASS WILL GENERATE AND  RETURN THE INDICES OF ELEMENTS OF THAT DIMENSIONS
            ?? how would you get the next element of the dimension ? ?
         */

        public void generateIndices(int[] axis) {

        }



    }



    public String toString() {
        //TODO
//         SHape -> [1,2] -> ([
//        [a, b]
//        ]), shape-> [2] -> ([1,2])
        String out = "Chibi.Tensor(";
            for (int i : this.shape) {

            }

        out += ")";
        return out;
    }
}
