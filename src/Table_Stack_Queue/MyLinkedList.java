package src.Table_Stack_Queue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Tianlong Zhang
 * @time 2018/8/4 17:21
 * @project DataStructure
 * @Version 1.0.0
 */
public class MyLinkedList <AnyType> implements Iterable<AnyType> {

    //定义节点
    private static class Node<AnyType>{
        public AnyType data ;
        public Node<AnyType> prev;
        public Node < AnyType> next;

        public Node(AnyType data, Node<AnyType> prev, Node<AnyType> next){
            this.data =data;
            this.next = next;
            this.prev = prev;
        }
    }

    //定义所使用的字段
    private Node<AnyType> beginMarker;   //头结点
    private Node<AnyType> endMarker;      //尾节点
    private int theSize;                                    //链表大小
    private int modCount;                              //自构造之后所改变的次数

    public MyLinkedList(){
        doClear();
    }

    /**
     * 初始化清理链表方法，此时仅有头指针和尾指针
     */
    private void doClear() {
        beginMarker = new Node<>(null, null, null);
        endMarker = new Node<>(null, beginMarker, null);
        beginMarker.next = endMarker;
        theSize = 0;
        //初始也改变了链表的结构故加1
        modCount ++;
    }

    /**
     * 获取链表的长度
     * @return 返回链表的长度
     */
    public int size(){
        return  theSize;
    }

    /**
     * 判断链表是否为空
     * @return true / false
     */
    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * get方法，查询在索引index处的节点
     * @param index  索引
     * @param lowe   链表的最小程度
     * @param upper  链表的最大长度
     * @return  返回查询到链表中的index处的节点
     */
    private Node<AnyType> getNode(int index, int lowe, int upper){
        if(index < lowe || index > upper){
            throw new IndexOutOfBoundsException("getNode index: " + index + "; size: " + size( ) );
        }

        Node<AnyType> p;

        if(index < size() /2){
            //注意这里开始寻求的是从第一个节点开始，而不是从beginMarker开始
            p = beginMarker.next;
            for (int i = 0; i < index; i++){
                p = p.next;
            }
        }else {
            p = endMarker;
            for (int i = size(); i >  index; i--){
                p = p.prev;
            }
        }
        return p;
    }


    /**
     * 重写的getNode方法，默认从开始查询到结尾
     * @param index  索引值
     * @return  返回索引处的节点
     */
    public Node<AnyType> getNode(int index){
        return getNode(index,0,size() - 1);
    }

    /**
     * get 方法，返回索引节点处的数据值，即，方法
     * 获取的getNode找到的节点的data值
     * @param index  索引值，位置
     * @return   索引位置处的data值
     */
    public AnyType get(int index){
        return getNode(index).data;
    }

    /**
     * 实现增加元素的过程，此方法将元素插入到指定的
     * 元素的前面，实现其移动过程
     * 例如，当前位置为 p节点，新的节点为：newNode
     * 所以：newNode节点的prev为 p的前一个元素，newNode的
     * 后一个节点即为p节点，即：
     * newNode(data, p.prev, p);
     * 到这里更新了新加入的newNode的节点的指向，此时应该更新
     * p.prev的next和p.prev的指向，即：
     * p.prev.next = newNode;
     * p.prev = newNode;
     * @param p 当前节点
     * @param x 节点的数据，储存在data字段中
     */
    public void addBefore(Node<AnyType> p, AnyType x){
        Node<AnyType> newNode = new Node<>(x, p.prev,p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }

    /**
     * 通过addBefore方法实现在指定位置处插入数据X
     * @param index 索引位置
     * @param x 待插入数据
     */
    public void  add(int index, AnyType x){
        addBefore(getNode(index,0,size()),x);
    }

    /**
     * add方法，默认方式为在尾部插入数据
     * @param x  待插入数据
     * @return   返回特定的插入的结果成功与否
     */
    public boolean add(AnyType x){
        add(size(),x);
        return true;
    }

    /**
     * Set 方法修改索引地址处的data的值
     * @param index 索引地址处
     * @param newVal  需要修改的新的值
     * @return 返回原来的旧值
     */
    public AnyType set(int index, AnyType newVal){
        Node<AnyType> p = getNode(index);
        AnyType oldVal = p.data;

        p.data = newVal;
        return  oldVal;
    }

    /**
     * 删除指定的节点
     * @param p 待删除节点
     * @return  f返回修改前的数据
     */
    public AnyType remove(Node<AnyType> p){
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;

        return p.data;
    }


    /**
     * 根据节点的索引值返回要删除的对象
     * @param index 索引值
     * @return 删除的元素的值
     */
    public AnyType remove( int index )
    {
        return remove( getNode( index ) );
    }

    /**
     * 修改默认的toString方法
     * @return 返回列表中元素的内容
     */
    public String toString(){
        StringBuffer sb = new StringBuffer("[ ");

        for(AnyType x : this){
            sb.append(x + " ");
        }

        sb.append(" ]");

        return new String( sb );
    }

    /**
     * 实现迭代器，使得MyLinkedList类可以称为一个能使用
     * 迭代器加快效率的类
     * @return
     */
    public Iterator<AnyType> iterator(){
        return  new LinkedListIterator();
    }


    /**
     * 实现 Iterator方法中返回的LinkedListIterator类
     */
    private class LinkedListIterator implements Iterator<AnyType> {
        //current 指向第一个节点
        private Node<AnyType> current = beginMarker.next;
        //初始化修改次数
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext(){
            //不进行操作次数的检查
            return current != endMarker;
        }

        public AnyType next(){
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if(!hasNext()){
                throw new NoSuchElementException();
            }

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove(){
            if(modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }
            if(!okToRemove){
                throw new IllegalStateException();
            }
            //一般的指内部类对象访问外部类对象时，可以采用外部类
            //类名.this.方法来实现
            MyLinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove =false;
        }
    }
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );

        for( int i = 0; i < 10; i++ )
            lst.add( i );
        for( int i = 20; i < 30; i++ )
            lst.add( 0, i );

        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );

        System.out.println( lst );

        Iterator<Integer> itr = lst.iterator( );
        while( itr.hasNext( ) )
        {
            itr.next( );
            itr.remove( );
            System.out.println( lst );
        }
    }
}
