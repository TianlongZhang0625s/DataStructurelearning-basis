package src.Heaps;

/**
 * @author Tianlong Zhang
 * @time 2018/11/19 20:42
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class HeapSort {
    public static void HeapAdjust(int[] array,int hole, int length){
        int left = 2 * hole;
        int right = 2 * hole + 1;
        //min
         //int min = hole;
        int max = hole;

         if(max < length / 2){
             if(left <= length && array[left] > array[max]){
                 max = left;
             }

             if (right <= length && array[right] > array[max]) {
                 max = right;
             }

             if(max != hole){
                 int temp = array[hole];
                 array[hole] = array[max];
                 array[max] = temp;

                 HeapAdjust(array,max,length);
             }
         }
    }

    public static void HeapAdjust2(int[] array,int hole, int length){
        int child;
        int temp = array[hole];

        for(; 2 * hole < length; hole = child){
            child = 2 * hole;
            //控制右边节点不要越界
            //可以修改为MaxHeap  >  ----->    <
            if(child + 1 != length && array[child + 1] > array[child]){
                child++;
            }
            if(array[child] > temp){
                array[hole] = array[child];
            }else
                break;
        }

        array[hole] = temp;
    }


    public static void heapSort (int[] array){
        for(int i = array.length / 2; i >= 0; i--){
            HeapAdjust2(array,i,array.length);
        }

        for(int i = array.length - 1; i > 0; i--){

            int temp = array[i];
            array[i] =array[0];
            array[0] = temp;

            HeapAdjust2(array,0,i);
        }
    }

    public static void print(int[] array){
        for(int i = 0; i< array.length; i++){
            System.out.println("element : " + array[i]);
        }
    }

    public static void main(String[] args){
        int [] array={5,3,5,8,2,9,12,78,9,2};
        heapSort(array);
        print(array);
    }
}
