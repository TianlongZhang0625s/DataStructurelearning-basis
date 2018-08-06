package src.Trees;


/**
 * @author Tianlong Zhang
 * @time 2018/8/5 9:50
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */


//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )                  --> Insert x
// void remove( x )               --> Remove x
// boolean contains( x )        --> Return true if x is present
// Comparable findMin( )     --> Return smallest item
// Comparable findMax( )    --> Return largest item
// boolean isEmpty( )           --> Return true if empty; else false
// void makeEmpty( )           --> Remove all items
// void printTree( )               --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate



public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    // 定义节点类型
    private static class BinaryNode<AnyType>{
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;

        BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right){
            this.element = element;
            this.left = left;
            this.right = right;
        }

        BinaryNode(AnyType x){
            this(x,null,null);
        }
    }

    //定义根节点
    private BinaryNode<AnyType> root;

    /**
     * Constructor
     */
    public BinarySearchTree(){
        root = null;
    }

    /**
     * 判断二叉树是否为空
     * @return true /false
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * 置空二叉树
     */
    public void makeEmpty(){
        root = null;
    }

    /**
     * 向二叉树中插入元素x
     * @param x 待插入元素
     */
    public void insert(AnyType x){
        root = insert(x,root);
    }

    /**
     * 查找二叉树中最小的元素
     * @return  最小的元素
     */
    public AnyType findMin(){
        if(isEmpty()){
            throw new UnderflowException();
        }
        return findMin(root).element;
    }

    /**
     * 查找二叉树中最大的元素
     * @return  最大的元素
     */
    public AnyType findMax(){
        if(isEmpty()){
            throw new UnderflowException();
        }

        return findMax(root).element;
    }

    /**
     * 查找二叉树中是否包含元素x
     * @param x  待检测的元素
     * @return      false / true
     */
    public boolean contains(AnyType x){
        return contains(x,root);
    }

    /**
     * 删除二叉树中指定的元素
     * @param x  指定元素
     */
    public void remove(AnyType x){
        root = remove(x,root);
    }

    /**
     * printTree,打印整个二叉树
     */
    public void printTree(){
        if(isEmpty()) {
            throw new UnderflowException();
        }else {
            printTree(root);
        }
    }

    /**
     * 求一个子树的高度
     * @param root    根节点
     * @return            树的高度
     */
    private int height(BinaryNode<AnyType> root){
        if( root == null){
            return -1;
        }else {
            return 1 + Math.max(height(root.left),height(root.right));
        }
    }

    /**
     * 打印根节点为root的树
     * @param root   根节点
     */
    private void printTree(BinaryNode<AnyType> root) {
        if(root != null){
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }
    }


    /**
     * 删除根节点为root的二叉树中指定元素x
     * @param x      指定元素
     * @param root  根节点
     * @return          删除节点所在的节点
     */
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> root) {

        if(root == null){
            return null;
        }

        int compareResult = x.compareTo(root.element);

        if(compareResult < 0){
            root.left = remove(x,root.left);
        }else if(compareResult > 0){
            root.right = remove(x,root.right);
        }else if(root.left != null && root.right != null){
            root.element = findMin(root.right).element;
            root.right = remove(root.element,root.right);
        }else
            root = (root.left != null) ? root.left : root.right;
        return root;
    }

    /**
     *  查找根节点为root的二叉树中是否包含元素x
     * @param x         待检测元素
     * @param root     根节点
     * @return             false / true
     */
    private boolean contains(AnyType x, BinaryNode<AnyType> root) {
        if(root == null){
            return false;
        }

        int compareResult = x.compareTo(root.element);

        if(compareResult < 0){
            return contains(x,root.left);
        }else if(compareResult > 0){
            return contains(x,root.right);
        }else
            //Match
            return true;
    }

    /**
     * 从根节点为root的二叉树中查找最大的元素
     * @param root 根节点
     * @return         最大元素所在的节点
     */
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> root) {
        if(root == null){
            return null;
        }else if(root.right == null){
            return root;
        }

        return findMax(root.right);
    }
    /*  while 循环方式实现
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> root){
        if(root == null){
            return  null;
        }

        while (root != null){
            root = root.right;
        }

        return root;
    }
*/

    /**
     * 从根节点为root的二叉树中查找最小的元素
     * @param root 根节点
     * @return         最小元素所在的节点
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> root) {
        if(root == null){
            return null;
        }else if(root.left == null){
            return root;
        }
        return findMin(root.left);
    }


    /**
     * 向根节点为root的二叉树中插入元素x
     * @param x      待插入元素
     * @param root  根节点
     * @return          根节点
     */
    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> root) {
        if(root == null){
            return new BinaryNode<>(x,null,null);
        }

        int compareResult = x.compareTo(root.element);

        if(compareResult < 0){
            root.left = insert(x, root.left);
        }else if(compareResult > 0){
            root.right = insert(x, root.right);
        }else
            ;
        return root;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Test program
    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );
        final int NUMS = 4000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            t.insert( i );

        for( int i = 1; i < NUMS; i+= 2 )
            t.remove( i );

        if( NUMS < 40 )
            t.printTree( );
        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 2; i < NUMS; i+=2 )
            if( !t.contains( i ) )
                System.out.println( "Find error1!" );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( t.contains( i ) )
                System.out.println( "Find error2!" );
        }
    }
}
