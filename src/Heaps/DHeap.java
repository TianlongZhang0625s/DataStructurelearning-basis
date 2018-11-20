package src.Heaps;

import src.Trees.UnderflowException;

/**
 * @author Tianlong Zhang
 * @time 2018/11/20 14:26
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class DHeap <AnyType extends Comparable<? super AnyType>>{

    private AnyType[] mArray;
    private int currentSize;
    private static final int  DEFAULT_CAPACITY = 10;
    private final int D_ary;

    public DHeap(int D_ary){
        this.D_ary = D_ary;
        mArray = (AnyType[]) new Comparable[DEFAULT_CAPACITY];
        currentSize = 0;
    }

    //获取父节点
    public int Parent(int hole){
        return (hole - 2  + D_ary) / D_ary;
    }

    //获取子节点
    public int Child(int parent, int index_child){
        return (D_ary * (parent - 1) + 2 + index_child);
    }

    //下滤过程，注意，这个为确定好父节点，子节点之后
    //通过寻找子节点中最小的下标，然后进行交换（小顶堆）
    public void perlocateDown(int hole){
        int largest = hole;
        for(int k = 0; k < D_ary; k++){
            int child = Child(hole,k);
            if(child < currentSize && mArray[child].compareTo(mArray[largest]) < 0){
                largest = child;
            }
            }
        if(largest != hole){
            AnyType temp = mArray[hole];
            mArray[hole] = mArray[largest];
            mArray[largest] = temp;

            perlocateDown(largest);
        }
    }

    //在插入的时候不使用mArray[0],此位置作为插入元素缓冲区
    public void insert(AnyType x){
        if(currentSize == mArray.length - 1){
            enlargeArray(mArray.length * 2 + 1);
        }

        int hole = ++currentSize;
        for(mArray[0] = x;x.compareTo(mArray[Parent(hole)]) < 0; hole = Parent(hole)){
            mArray[hole] = mArray[Parent(hole)];
        }

        mArray[hole] = x;
    }

    //扩充容量
    private void enlargeArray(int newSize) {
        AnyType[] old = mArray;
        mArray = (AnyType []) new Comparable[newSize];

        for(int i = 0; i < old.length; i++){
            mArray[i] = old[i];
        }
    }

    //获取小顶堆的最小值
    public AnyType findMin(){
        if (isEmpty())
            throw new UnderflowException();
        return mArray[1];
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    //删除小顶堆的最小值
    public AnyType deleteMin(){
        if(isEmpty())
            throw new UnderflowException();

        AnyType maxItem = findMin();
        //此处相当于将currentSize大小的最后一个元素移动到mArray[0]
        //位置覆盖掉原来的最小值，然后将currentSize大小减一，将尾部的
        //最后一个元素舍弃，然后重新进行下滤
        mArray[1] = mArray[currentSize -- ];
        //重新调整，使得符合堆的性质
        perlocateDown(1);
        return maxItem;
    }

    public static void main(String[] args) {
        int numItems = 100;
        DHeap<Integer> heap = new DHeap<Integer>(2);

        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % numItems) {
            heap.insert(i);
        }

        for (i =1; i  < numItems; i++) {
                System.out.println(i  + "     delete  " + heap.deleteMin());
        }
    }
}
