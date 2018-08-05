package src.Table_Stack_Queue;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Tianlong Zhang
 * @time 2018/8/4 15:19
 * @project DataStructure
 * @Version 1.0.0
 */
public class MyArrayList<AnyType> implements Iterable<AnyType> {
    private int theSize;
    private  AnyType[] theItems;

    private static  final int DEFAULT_CAPACITY = 10;

    public MyArrayList(){
        doClear();
    }

    /**
     * 获取列表的大小
     * @return 列表大小
     */
    public int size(){
        return theSize;
    }

    /**
     * 判断是否是一个空的列表
     * @return false or true
     */
    public boolean isEmpty(){
        return  size() == 0;
    }

    /**\
     * 返回对应index处的AnyType类型的元素
     * @param index 索引值
     * @return 返回对应索引值的Element
     */
    public AnyType get(int index){
        if(index < 0 || index >= size()){
            throw new ArrayIndexOutOfBoundsException( "Index " + index + "; size " + size( ) );
        }

        return theItems[index];
    }

    /**
     * 修改对应位置的值，将其改为新的元素值
     * @param index 索引值
     * @param newVal 设置的新的值
     * @return 返回旧的值
     */
    public AnyType set(int index, AnyType newVal){
        if(index < 0 || index >= size()){
            throw new  ArrayIndexOutOfBoundsException( "Index " + index + "; size " + size( ) );
        }
        AnyType old = theItems[index];
        theItems[index] = newVal;
        return old;
    }

    public boolean add(AnyType x){
        add(size(), x);
        return true;
    }

    public void add(int index, AnyType x) {
        if(theItems.length == size()){
            ensureCapacity(size() * 2 + 1);
        }

        for(int i = theSize; i > index; i--){
            theItems[i] = theItems[i - 1];
        }

        theItems[index] = x;
        theSize ++;
    }

    /**
     * ]删除指定位置的元素
     * @param index 位置
     * @return 被删除的元素
     */
    public AnyType remove(int index){
        AnyType removeItem = theItems[index];

        for(int i = index; i < size() ; i++){
            theItems[i] = theItems[i + 1];
        }

        theSize--;
        return removeItem;
    }

    /**
     * 清理方法，并设置默认的容量大小
     */
    public void clear(){
        doClear();
    }

    private void doClear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }


    /**
     * 扩容方法
     * @param newCapacity 新的容量的大小
      */
    private void ensureCapacity(int newCapacity) {
        if(theSize > newCapacity){
            return;
        }

        AnyType[] old = theItems;
        //NOTE ： 这里需要使用Object创建所需要的泛型类型的数组，
        //因为直接创建泛型数组是不合法的。
        theItems = (AnyType[]) new Object[newCapacity];
        for(int i = 0; i < theSize; i++){
            theItems[i] = old[i];
        }
    }

    /**
     * 重写Iterable的iterator方法
     * @return 返回为一个迭代器类型的对象，在案例里面
     * 我们用ArrayListIterator类实现类Iterator接口
     */
    public Iterator<AnyType> iterator(){
        return new ArrayListIterator();
    }
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * 实现了Iterator泛型接口的Iterator接口
     */
    private class ArrayListIterator implements Iterator<AnyType> {
        private int current = 0;
        private boolean okToRemove = false;

        //重写hasNext，next和remove方法
        public boolean hasNext(){
            return current < size();
        }

        public AnyType next(){
            if(! hasNext()){
                throw new NoSuchElementException();
            }

            okToRemove = true;
            return theItems[current++];
        }

        public void remove(){
            if(!okToRemove){
                throw new IllegalStateException();
            }

            MyArrayList.this.remove(--current);
            okToRemove = false;
        }
    }
}

/**
 * 测试案例
 */
class TestArrayList
{
    public static void main( String [ ] args )
    {
        MyArrayList<Integer> lst = new MyArrayList<>( );

        for( int i = 0; i < 10; i++ )
            lst.add( i );
        for( int i = 20; i < 30; i++ )
            lst.add( 0, i );

        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );

        System.out.println( lst );
    }
}