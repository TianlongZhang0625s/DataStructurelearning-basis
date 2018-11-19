package src.Heaps;

/**
 * @author Tianlong Zhang
 * @time 2018/11/19 12:14
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */

// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate


import src.Trees.UnderflowException;

/**
 * Implements a binary heap
 * Note that all "matching" is based on the compareTo method
 * @Author Zhang Tianlong
 * @param <AnyType>
 */
public class BinaryHeap<AnyType extends Comparable <? super AnyType>> {

    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;
    private AnyType[] array;
    /**
     * Construct the binary heap
     */
    public BinaryHeap(){
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct the binary heap
     * @Param capacity the capacity of the binary heap
     */

    public BinaryHeap(int capacity){
        currentSize = 0;
        array = (AnyType[]) new Comparable[capacity + 1];
    }

    /**
     * Construct the binary heap given an array of items
     */
    public BinaryHeap(AnyType[] items){
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];
        int i = 1;
        for (AnyType item : items){
            array[i++] = item;
        }

        buildHeap(currentSize);
    }

    /**
     * Insert into the priority queue, maintaining heap order
     * Duplicates are allowed
     * @Param x the item to insert
     */

    public void insert(AnyType x){
        if(currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        int hole = ++currentSize;
        for(array[0] = x; x.compareTo(array[hole / 2]) < 0; hole /= 2){
            array[hole] = array[hole / 2];
        }
        array[hole] = x;
    }

    private void enlargeArray(int newSize) {
        AnyType[] old = array;
        array = (AnyType[]) new Comparable[newSize];
        for(int i = 0; i < old.length; i++){
            array[i] = old[i];
        }

    }

    /**
     * Find the smallest item in the priority queue
     * @return the smallest item, or throw an UnderflowException if empty
     */
    public AnyType findMin(){
        if(isEmpty()){
            throw new UnderflowException();
        }
        return array[1];
    }

    /**
     * Remove the smallest item form priority queue
     * @Param the samllest item, or throw an UnderflowException if empty
     */

    public AnyType deleteMin(){
        if(isEmpty()){
            throw new UnderflowException();
        }

        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);
        return minItem;
    }

    /**
     * Test if the priority queue is logically empty
     * @reutrn true is empty,false otherwise
     */

    public boolean isEmpty(){
        return currentSize == 0;
    }

    /**
     * Make the priority empty logically .
     */
    public void makeEmpty(){
        currentSize = 0;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items Runs in linear time
     */
    private void buildHeap(int currentSize) {
        for(int i = currentSize / 2; i > 0; i--){
            percolateDown(i);
        }
    }

    /**
     * Internal method to percolate down in the heap
     * @Param hole the index at which the percolate begins
     */
    private void percolateDown(int hole) {
        int child;
        AnyType temp = array[hole];

        for(; 2 * hole <= currentSize; hole =  child){
            child = 2 * hole;

            if(child != currentSize && array[child + 1].compareTo(array[child]) < 0){
                child++;
            }
            if(array[child].compareTo(temp) < 0){
                array[hole] = array[child];
            }else
                break;
        }

        array[hole] = temp;
    }

    public void percolateDown1(int hole){
        int left = 2 * hole;
        int right = 2 * hole + 1;

        int min = hole;

        if(min <= currentSize / 2){
            if(left <= currentSize && array[left].compareTo(array[min]) < 0){
                min = left;
            }

            if(right <= currentSize && array[right].compareTo(array[min]) < 0){
                min = right;
            }

            if(min != hole){
                AnyType temp = array[hole];
                array[hole] = array[min];
                array[min] = temp;

                percolateDown(min);
            }
        }

    }



//Test Programs
    public static void main(String[] args) {
        int numItems = 10000;
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>();

        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % numItems) {
            heap.insert(i);
        }

        for (i = 1; i < numItems; i++) {
            if (heap.deleteMin() != i) {
                System.out.println("oopS");
            }
        }
    }
}
