package src.Heaps;

/**
 * @author Tianlong Zhang
 * @time 2018/11/19 21:30
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class MergeSort {
    public static void mergeSort(int[] array, int left, int right){
        if(right - left < 2) return ;
        int mid = (left + right) >> 1;

        mergeSort(array,left,mid);
        mergeSort(array,mid , right);
        merge(array,left,mid,right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int i = left, j = mid, k = 0;
        int [] temp = new int [right - left];

        for(; i < mid && j < right; k++){
            if(array[i] < array[j]){
                temp[k] = array[i++];
            }else{
                temp[k] = array[j++];
            }
        }

        for(; i < mid; k++){
            temp[k] = array[i++];
        }

        for(; j < right; k++){
            temp[k] = array[j++];
        }

        for (i = left, k = 0; i < right; i++){
            array[i] = temp[k++];
        }
    }

    public static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("element : " + array[i]);
        }
    }


    public static void main(String[] args){
        int [] array={5,3,5,8,2,9,12,78,9,2};
        mergeSort(array,0,array.length );
        print(array);
    }
}
