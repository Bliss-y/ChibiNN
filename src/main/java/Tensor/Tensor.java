package Tensor;

import utils.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * for shape every array is a dimension... ?
 * shape 1,2 would be two-dimensional arrays whereas shape 2,1 or simply 2 would be 1-dimensional array
 * So if i want all the elements of first position of first dimension i.e 0th row then i should give all the elements of that row
 * wether it be an array or single element in case of [2,1]/ [2] array
 *
 *
 *
 */
public class Tensor {
    private double[] data;
    private int[] shape;
    private Tensor grad;
    public Grad gradFunc;
    public boolean requires_grad = true;
    private boolean isLeaf = true;
    public static boolean backpropping = false;

    public Tensor(double[] data, int[] shape) {
        if(data.length != Arrays.stream(shape).reduce(1, (a,b)-> a*b)){
            throw new IllegalArgumentException("Invalid shape for length " + data.length);
        }

        this.data = data;
        this.shape = shape;
    }

    public Tensor(double[] data, int[] shape, boolean requires_grad) {
        if(data.length != Arrays.stream(shape).reduce(1, (a,b)-> a*b)){
            throw new IllegalArgumentException("Invalid shape for length " + data.length);
        }
        this.requires_grad = requires_grad;
        this.data = data;
        this.shape = shape;
    }

    private Tensor() {
        this.data = null;
        this.shape =null;
    }

    public Tensor getGrad() {
        return this.grad;
    }

    // isGradable is used to see if the grad needs to be stored or not!
    public boolean isGradable() {
        return this.size() > 0 && requires_grad;
    }
    public Tensor empty() {
        return new Tensor();
    }
    public Tensor(int[] shape) {
        this.shape = shape;
        this.data = new double[this.size()];
    }

    public void setGrad(Tensor T) {
        if(!isGradable())return;
        if(this.grad == null) {
            this.grad = Tensor.zero();
        }
        System.out.println(T.size());;
        this.grad = this.grad.add(T);
    }


    public int size () {
        int size = 1;
        for (int i : shape) {
            size *= i;
        }
        return size;
    }

    // only works in 2X2 matrix for now
    public Tensor inSum(int axis) {
        double[] d = new double[this.shape[axis]];
        int non = axis == 0 ? 1 : 0;
        for (int i=0; i < this.shape[non]; i++) {
            for(int j =0; j < this.shape[axis]; j++) {
                d[i*non] += this.data[i*non + j];
            }
        }
        return new Tensor(d, new int[]{this.shape[non]});
    }

    /**
     * Returns a subset of a Tensor!
     * @param indices
     * @return
     */
    public Tensor subset(int[] indices) {
        if (indices.length  >= this.shape.length) throw new IllegalArgumentException("Can't subset from same dimensions!");
        // [a,b,c,c](2,2) -> [n] -> return [n*(multiple of other dimensions), n*(multiple of other dimensions) + multiple of others ]
        int first=0;
        for (int i=0; i < indices.length; i++) {
            first += indices[i] * this.shape[i+1];
        }
        int last = Arrays.stream(Arrays.copyOfRange(this.shape, indices.length, this.shape.length)).reduce(1, (a,b)-> a*b);
        return new Tensor(Arrays.copyOfRange(this.data, first, last), Arrays.copyOfRange(this.shape, indices.length, this.shape.length));
    }
    public int[] shape() {
        return this.shape;
    }
    public double[] getData() {
        return this.data;
    }

    public void setGrad() {
        this.grad = zero();
    }

    public static Tensor zero() {
        return new Tensor(new double[]{0}, new int[]{1});
    }

    public Tensor transpose() {
        int [] nshape = this.shape.clone();
        double[] doubles = this.data.clone();
        int columnCounter = -1;
        int rowCounter = 0;
        for (int i = 0; i < nshape.length; i++) {
               nshape[i] = this.shape[this.shape.length-i-1];
        }
        for(int i =0; i < doubles.length; i++) {
            if(i%(this.shape[0]-1) == 0) {
                columnCounter ++;
                rowCounter = 0;
            }
            int curIndex = rowCounter*nshape[1] + columnCounter;
            // i=0 row = 0; column = 0; 0)) i=1 -> increase row
            doubles[curIndex] = this.data[i];
            rowCounter++;
        }


        return new Tensor(doubles, nshape);
    }

    /**
     *TODO: probably doesn't work right now . make it so that the remaining matrices gets sent to transpose() and transpose that way!
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

    public static boolean isBroadCastable(Tensor t, Tensor t2) {
        if (Arrays.equals(t.shape, t2.shape)) return true;

        if(t.shape.length > t2.shape.length && (t2.shape[0] == 1 || t2.shape[0] == t.shape[0])) return true;
        if(t.shape.length < t2.shape.length && (t.shape[0] == 1|| t2.shape[0] == t.shape[0])) return true;
        return false;
    }

    public Tensor add(Tensor t) {
        if(!Arrays.equals(t.shape,this.shape) && !isBroadCastable(this, t)){
            System.out.println("this shape" + Arrays.toString(this.shape));
            System.out.println("other shape "+Arrays.toString(t.shape));
            throw new IllegalArgumentException("Shapes donot match for the given tensors");
        }

        double [] doubles = new double[1];
        int [] newShape = this.shape.clone();
        if(t.size() == 1) {
            doubles = this.getData().clone();
            newShape = this.shape.clone();
            doubles = Array.add(this.data, t.data[0]);
        } else if (this.size() == 1) {
            newShape = t.shape.clone();
            doubles = t.getData().clone();
            doubles = Array.add(t.data, this.data[0]);
        }
        else {
            for (int i =0; i < this.data.length; i++) {
                doubles = t.getData().clone();
                doubles[i] += this.data[i];
            }
        }

        Tensor out = new Tensor(doubles, newShape);
        if(!backpropping){
            out.gradFunc = new Grad(this, t, out) {
                @Override
                public Tensor calculateGrad() {
                    this.op1.grad = this.op1.add(res);
                    this.op2.grad = this.op1.add(res);
                    return null;
                }
            };
        }
        return out;
    }

    /**
     * raises the power of the tensor data by given amount
     * @param i
     * @return
     */
    public Tensor pow(double i) {
        Tensor out = new Tensor(Array.pow(this.data, i), this.shape.clone());
        out.gradFunc = new Grad(this, null, out) {
            @Override
            public Tensor calculateGrad() {
                op1.grad = op1.grad.add(op1.mul(i));

                return null;
            }
        };

        return out;
    }

    /**
     * Element-wise subtraction of two tensors.
     * @param t
     * @return
     */
    public Tensor sub(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
            doubles[i] -= this.data[i];
        }
        return new Tensor(doubles, this.shape.clone());
    }

    /**
     * Element wise multiplication of two tensors
     * @param t
     * @return
     */
    public Tensor mul(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
            doubles[i] *= this.data[i];
        }
        Tensor out = new Tensor(doubles, this.shape.clone());
        return out;
    }

    /**
     * Scaling a Tensor by a double m
     * @param m
     * @return
     */
    public Tensor mul(double m) {
        double [] doubles = new double[this.data.length];
        for (int i =0; i < this.data.length; i++) {
            doubles[i] = this.data[i]*m;
        }
        Tensor out = new Tensor(doubles, this.shape.clone());
        return out;
    }



    /**
     * Multiplies 2 Tensors and Sets their gradFunc
     * @param T
     * @return matrix multiplication/vector dot product of this with other
     */
    public Tensor multiply(Tensor T) {
        if (T.shape.length != this.shape.length) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        Tensor out = T.shape.length ==3 ? this.Mul3d(T) : this.Mul2d(T);
        out.isLeaf = false;

        // only set gradFunc when not backPropagating.
        if(!backpropping) {
            out.gradFunc = new Grad(this, T, out) {
                @Override
                public Tensor calculateGrad() {
                    // op2grad[3,2] = op2grad[3,2] + ((op1grad[2,3].T)[3,2] @ out.grad[2,2])[3,2]
                    this.op2.setGrad(op2.grad.add((op1.transpose()).multiply(out.grad)));
                    this.op1.setGrad(op1.grad.add(out.grad.multiply(op2.transpose())));
                    return null;
                }
            };
        }
        return out;
    }

    /**
     * Returns a Tensor of given dimension filled with random doubles
     * @param x
     * @param y
     * @return
     */
    public static Tensor rand(int x, int y) {
        Random random = new Random();
        double[] d = new double[x*y];
        for(int i=0; i<d.length; i++) {
            d[i] = random.nextDouble();
        }
        return new Tensor(d, new int[]{x,y});
    }

    /**
     * Returns a tensor of all ones in given dimensions
     * @param x
     * @param y
     * @return
     */
    public static Tensor ones(int x, int y) {
        double[] d = new double[x*y];
        for(int i=0; i<d.length; i++) {
            d[i] = 1;
        }
        return new Tensor(d, new int[]{x,y});
    }

    public boolean isLeaf() {
        isLeaf = this.gradFunc == null ? false : true;
        return isLeaf;
    }

    /**
     * Divide the tensor data with another tensor
     * This is an element wise operation!
     * TODO: change it to use power so that gradiation is simple
     * @param t
     * @return
     */
    public Tensor div(Tensor t) {
        if(!t.shape.equals(this.shape)) throw new IllegalArgumentException("Shapes donot match for the given tensors");
        double [] doubles = t.getData().clone();
        for (int i =0; i < this.data.length; i++) {
             this.data[i] /= doubles[i];
        }
        return new Tensor(doubles, this.shape.clone());
    }

    /**
     *
     * @param  t -> another tensor of multiply-able 3- dimensions
     * @return resulting Tensor of the multiplication
     */
    private Tensor Mul3d(Tensor t) {
        if (t.shape[0] != this.shape[0] || this.shape[2] != t.shape[1]) throw new IllegalArgumentException("dimensions do not match for multiplication");
        double[] tmpdata= {};
        for (int i =0; i < this.shape[0]; i++) {
            Tensor tmp1 = copyRange(this, i*this.shape[1]*this.shape[2] , i*(this.shape[1]*this.shape[2]) +this.shape[1] *this.shape[2], new int[]{this.shape[1], this.shape[2]});
            Tensor tmp2 = copyRange(this, i*t.shape[1]*t.shape[2] ,i*(t.shape[1]*t.shape[2]) + t.shape[1] * t.shape[2], new int[]{t.shape[1], t.shape[2]});
            tmpdata = Array.concat(tmpdata, tmp1.Mul2d(tmp2).data);
        }
        return new Tensor(tmpdata, new int[]{this.shape[0], this.shape[1], t.shape[2]});
    }

    public static Tensor copyRange(Tensor t, int s, int e, int[] shape) {
        return new Tensor(Array.subset(t.data, s, e),shape);
    }

    /**
     *
     * @param  t -> another tensor of multiply-able 2-d dimensions
     * @return resulting Tensor of the multiplication
     */
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
    public Tensor sum() {
        Tensor out = new Tensor(new double[]{Arrays.stream(this.data).sum()}, new int[] {1});
        out.gradFunc = new Grad(this, null, out){
            @Override
            public Tensor calculateGrad() {
                this.op1.setGrad(res.grad);
                return null;
            }
        };
        return out;
    }

    /**
     * CALLS BACKWARD ON THE TENSOR AND CALCULATES IT'S GRADIATION
     */
    public void backward() {
        this.setGrad(Tensor.ones(this.shape()[0], this.shape()[1]));
        ArrayList<Tensor> graph = buildTopo(this);
        for (Tensor t : graph)
        {
            t.gradFunc.calculateGrad();
        }
    }

    /**
     * BUILDS A TOPOLOGICAL GRAPH OF TENSOR WITH THEIR PARENT OPERANDS
     * @param t TENSOR TO START THE TOPOLOGY
     * @return ArrayList<Tensor> GRAPH that STOREs THE TENSORS IN THE TOPOLOGICAL ORDER
     */
    private static ArrayList<Tensor> buildTopo(Tensor t) {
        ArrayList<Tensor> graph = new ArrayList<>();
       ArrayList<Tensor> visited = new ArrayList<>();
       buildTopo(t, graph, visited);
       return graph;
    }

    private static void buildTopo(Tensor t, ArrayList<Tensor> graph, ArrayList<Tensor> visited) {
        if( visited.contains(t)) return;
        if(!t.isLeaf()){
        if(t.gradFunc.op1 != null) buildTopo(t.gradFunc.op1, graph, visited);
        if(t.gradFunc.op2 != null) buildTopo(t.gradFunc.op2, graph, visited);
        // freeing the memory of the grad and gradfunc !
            t.grad = null;
            t.gradFunc = null;
        }
        graph.add(t);
    }

    /**
     *
     * @return String of the display of tensorss
     */
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
