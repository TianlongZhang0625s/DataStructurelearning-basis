package src.Trees;


/**
 * @author Tianlong Zhang
 * @time 2018/8/5 16:19
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */

// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )                --> Insert x
// void remove( x )             --> Remove x (unimplemented)
// boolean contains( x )      --> Return true if x is present
// boolean remove( x )       --> Return true if x was present
// Comparable findMin( )   --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )         --> Return true if empty; else false
// void makeEmpty( )        --> Remove all items
// void printTree( )             --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate
public class AVLTree <AnyType extends Comparable<? super  AnyType>>{

    private static final int ALLOW_IMBALANCE = 1;

    //定义AVL节点
    private static class AVLNode<AnyType>{
        AnyType element;
        AVLNode<AnyType> left;
        AVLNode<AnyType> right;
        int height ;
        
        AVLNode(AnyType x,AVLNode<AnyType> left, AVLNode<AnyType> right){
            this.element = x;
            this.left = left;
            this.right = right;
            height = 0;
        }
        
        AVLNode(AnyType x){
            this(x,null,null);
        }
    }
    
    //定义节点
    private AVLNode<AnyType> root;

    /**
     * Constructor
     */
    public AVLTree(){
        root = null;
    }
    
    /**
     * 判断树是否为空树
     * @return false / true
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * 将树设置为空
     */
    public void makeEmpty(){
        root = null;
    }

    /**
     * 查找树中最小的元素
     * @return 最小的元素
     */
    public AnyType findMin(){
        if(isEmpty()){
            throw new UnderflowException();
        }
        return findMin(root).element;
    }
    
    /**
     * 查找树中最大的元素
     * @return 最大的元素
     */
    public AnyType findMax(){
        if(isEmpty()){
            throw new UnderflowException();
        }
        return findMax(root).element;
    }

    /**
     * 查询树中是否包含指定的元素
     * @param x   指定元素
     * @return      返回判断结果
     */
    public boolean contains(AnyType x){
        return contains(x,root);
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
     * 插入指定元素
     * @param x 指定元素
     */
    public void insert(AnyType x){
        root = insert(x,root);
    }


    /**
     * 删除指定元素
     * @param x   指定元素
     */
    public void remove(AnyType x){
        root = remove(x, root);
    }

    public void checkBalance(){
        checkBalance(root);
    }

    public int checkBalance(AVLNode<AnyType> root){
        if(root == null){
            return -1;
        }

        int hl = checkBalance(root.left);
        int hr = checkBalance(root.right);
        if(Math.abs(height(root.left) - height(root.right)) > 1 ||
                height(root.left) != hl || height(root.right) != hr){
            System.out.println(" OOPS ! ");
        }
        return height(root);
    }

    /**
     * 从根节点为root的树中删除指定元素
     * @param x       指定元素
     * @param root   根节点
     * @return           经过balance方法调节后的根节点
     */
    public AVLNode<AnyType> remove(AnyType x , AVLNode<AnyType> root){
        if(root == null){
            return root;
        }

        int compareResult = x.compareTo(root.element);

        if(compareResult < 0){
            root.left = remove(x, root.left);
        }else if(compareResult > 0){
            root.right = remove(x , root.right);
        }else if(root.left != null && root.right != null){
            root.element = findMin(root.right).element;
            root.right = remove(root.element,root.right);
        }else {
            root = root.left != null ? root.left : root.right;
        }

        return balance(root);
    }

    /**
     * 向根root的树中插入指定元素x
     * @param x           指定元素
     * @param root       根节点
     * @return               插入节点后的根节点root
     */
    public AVLNode<AnyType> insert(AnyType x ,AVLNode<AnyType> root){
        if(root == null){
            return new AVLNode<>(x,null,null);
        }

        int compareResult = x.compareTo(root.element);

        if(compareResult < 0){
            root.left = insert(x, root.left);
        }else if(compareResult > 0){
            root.right = insert(x, root.right);
        }else
            ;
        return balance(root);
    }

    /**
     * 对根节点为root的树进行平衡，两端差距不超过2个高度
     * @param root   根节点
     * @return           平衡调整后的根节点
     */
    public AVLNode<AnyType> balance(AVLNode<AnyType> root){
        if(root == null){
            return root;
        }

        if(height(root.left) - height(root.right) > ALLOW_IMBALANCE)
            if(height(root.left.left) >= height(root.left.right))
                root = rotateWithLeftChild(root);
            else
                root = doubleWithLeftChild(root);
        else
            if (height(root.right) - height(root.left) > ALLOW_IMBALANCE)
                if(height(root.right.right) >= height(root.right.left))
                    root = rotateWithRightChild(root);
                else
                    root = doubleWithRightChild(root);
        root.height = Math.max(height(root.left),height(root.right)) + 1;

        return root;
    }

    /**
     * 先左旋再右旋，调整情况为右子树中左子树过高的情形
     * @param k1    调节点
     * @return          调整后的根节点
     */
    public AVLNode<AnyType> doubleWithRightChild(AVLNode<AnyType> k1){
        k1.left = rotateWithLeftChild(k1.left);
        return rotateWithRightChild(k1);
    }

    /**
     * 右旋，适合于右子树的右子树过高的情况
     * @param k1   调节点
     * @return         调整后的根节点
     */
    public AVLNode<AnyType> rotateWithRightChild(AVLNode<AnyType> k1){
        AVLNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.right) , height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right) , k1.height) + 1;
        return k2;

    }

    /**
     * 先右旋再左旋，调整情况为左子树中右子树过高的情形
     * @param k3    调节点
     * @return          调整后的根节点
     */
    public AVLNode<AnyType> doubleWithLeftChild(AVLNode<AnyType> k3){
        k3.right = rotateWithRightChild(k3.right);
        return rotateWithLeftChild(k3);
    }

    /**
     * 左旋，适合于左子树的左子树过高的情况
     * @param k2   调节点
     * @return         调整后的根节点
     */
    public AVLNode<AnyType> rotateWithLeftChild(AVLNode<AnyType> k2){
        AVLNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left),height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left),k2.height) + 1;
        return k1;
    }

    /**
     * 求取树的高度的方法
     * @param root  根节点
     * @return          树的高度
     */
    public int height(AVLNode<AnyType> root){
        return root == null ? -1 : root.height;
    }


    /**
     * 打印根节点为root的树
     * @param root   根节点
     */
    private void printTree(AVLNode<AnyType> root) {
        if(root != null){
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }
    }

    /**
     * 查询根节点为root的树中是否包含指定元素
     * @param x       指定的元素
     * @param root   根节点
     * @return           判断结果
     */
    private boolean contains(AnyType x, AVLNode<AnyType> root) {
        
        if(root == null){
            return false;
        }
        
        int compareResult = x.compareTo(root.element);
        
        if(compareResult < 0){
            return contains(x,root.left);
        }else if(compareResult > 0){
            return contains(x,root.right);
        }else {
            //Matchs
            return true;
        }
    }


    /**
     * 查找根节点为root的最大的元素
     * @param root  根节点
     * @return          返回最大元素的节点
     */
    private AVLNode<AnyType> findMax(AVLNode<AnyType> root) {
        if(root == null){
            return root;
        }

        while(root.right != null){
            root = root.right;
        }
        
        return root;
    }

    /**
     * 查找根节点为root的最小的元素
     * @param root  根节点
     * @return          返回最小元素的节点
     */
    private AVLNode<AnyType> findMin(AVLNode<AnyType> root) {

        if(root == null){
            return root;
        }
        while(root.left != null){
            root = root.left;
        }
        return root;
    }




    // Test program
    public static void main( String [ ] args )
    {
        AVLTree<Integer> t = new AVLTree<>( );
        final int SMALL = 40;
        final int NUMS = 1000000;  // must be even
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
        {
            //    System.out.println( "INSERT: " + i );
            t.insert( i );
            if( NUMS < SMALL )
                t.checkBalance( );
        }

        for( int i = 1; i < NUMS; i+= 2 )
        {
            //   System.out.println( "REMOVE: " + i );
            t.remove( i );
            if( NUMS < SMALL )
                t.checkBalance( );
        }
        if( NUMS < SMALL )
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
