package src.Heaps;

/**
 * @author Tianlong Zhang
 * @time 2018/11/20 19:30
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class QuickSort {

    public static void QuickSort(int[] array, int left, int right){

        int par_vaule;      //基准值

        if( left < right){
            par_vaule = Partition(array,left,right);
            QuickSort(array,left,par_vaule - 1);
            QuickSort(array,par_vaule + 1, right);
        }

    }

    public static int Partition(int[] array, int left, int right){

        int x, i , j , temp;
        //在这里假设以最后一个值为基准值

        x = array[right];
        //将指针i移动到外面
        i = left - 1;

        for(j = left; j <= right - 1; j++){
            if(array[j] >= x) {

                i += 1;
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        temp = array[i + 1];
        array[i + 1] = array[right];
        array[right] = temp;

        return i + 1;
    }

    public static void print (int[] array){
        for (int i = 0 ; i < array.length; i++){
            System.out.println(array[i]);
        }
    }
    public static void main(String[] args){
        int [] array={5,3,5,8,2,9,12,78,9,2};
        QuickSort(array,0,array.length - 1);
        print(array);
    }
}
