package src.Trees;

// SplayTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )                --> Insert x
// void remove( x )             --> Remove x
// boolean contains( x )      --> Return true if x is found
// Comparable findMin( )   --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )         --> Return true if empty; else false
// void makeEmpty( )         --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * @author Tianlong Zhang
 * @time 2018/8/6 20:53
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class SplayTree <AnyType extends Comparable<? super  AnyType>>{
    //定义节点
    private static class BinaryNode<AnyType>{
        AnyType elements;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;

        BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right){
            this.elements = element;
            this.left = left;
            this.right = right;
        }

        BinaryNode(AnyType elements){
            this(elements, null,null);
        }
    }

    //定义根节点
    private BinaryNode<AnyType> root;
    private BinaryNode<AnyType> nullNode;
    private BinaryNode<AnyType> newNode = null;
    private BinaryNode<AnyType> header = new BinaryNode<AnyType>( null ); // For splay

    public SplayTree(){
        nullNode = new BinaryNode<AnyType>(null);
        nullNode.left = nullNode.right = nullNode;
        root = nullNode;
    }

    public void insert(AnyType x){
        if(newNode == null){
            newNode = new BinaryNode<AnyType>(null);
        }

        newNode.elements = x;

        if(root == nullNode){
            newNode.left = newNode.right = nullNode;
            root = newNode;
        }
        else
        {
            root = splay(x , root);

            int compareResult = x.compareTo(root.elements);

            if(compareResult < 0){
                newNode.left = root.left;
                newNode.right = root;
                root.left = nullNode;
                root = newNode;
            }
            else
                if(compareResult > 0){
                     newNode.right = root.right;
                     newNode.left = root;
                     root.right = nullNode;
                     root = newNode;
                }
                else
                    return;
        }
        newNode = null;
    }

    public void remove(AnyType x){
        if(! contains(x)){
            return;
        }

        BinaryNode<AnyType> newTree;

        if(root.left == nullNode)
            newTree = root.right;
        else
        {
            newTree = root.left;
            newTree = splay(x, newTree);
            newTree.right = root.right;
        }

        root = newTree;
    }
    public boolean contains( AnyType x )
    {
        if( isEmpty( ) )
            return false;

        root = splay( x, root );

        return root.elements.compareTo( x ) == 0;
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = nullNode;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == nullNode;
    }


    public AnyType findMin(){
        if(isEmpty()){
            throw new UnderflowException();
        }

        BinaryNode<AnyType>  ptr = root;
        while (ptr.left != nullNode){
            ptr = ptr.left;
        }
        root = splay(ptr.elements, root);
        return ptr.elements;
    }

    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        BinaryNode<AnyType> ptr = root;

        while( ptr.right != nullNode )
            ptr = ptr.right;

        root = splay( ptr.elements, root );
        return ptr.elements;
    }

    private BinaryNode<AnyType> splay(AnyType x, BinaryNode<AnyType> root){
        BinaryNode<AnyType> leftTreeMax, rightTreeMin;

        header.left = header.right = nullNode;
        leftTreeMax = rightTreeMin = header;

        nullNode.elements = x;

        for( ; ; ){
            int compareResult = x.compareTo(root.elements);

            if(compareResult < 0){
                if(x.compareTo(root.left.elements) < 0)
                    root = rotateWithLeftChild(root);
                if (root.left == nullNode){
                    break;
                }

                rightTreeMin.left = root;
                rightTreeMin = root;
                root = root.left;
            }else if(compareResult > 0){
                if(x.compareTo(root.right.elements) > 0){
                    root = rotateWithRightChild(root);
                }
                if (root.right == nullNode)
                    break;

                leftTreeMax.right = root;
                leftTreeMax = root;
                root = root.right;
            }else
                break;
        }

        leftTreeMax.right = root.left;
        rightTreeMin.left = root.right;
        root.left = header.right;
        root.right = header.left;
        return root;
    }

    private static <AnyType> BinaryNode<AnyType> rotateWithLeftChild(BinaryNode<AnyType> k2){
        BinaryNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    private static <AnyType> BinaryNode<AnyType> rotateWithRightChild(BinaryNode<AnyType> k1){
        BinaryNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }


    // Test program; should print min and max and nothing else
    public static void main( String [ ] args )
    {
        SplayTree<Integer> t = new SplayTree<Integer>( );
        final int NUMS = 40000;
        final int GAP  =   307;

        System.out.println( "Checking... (no bad output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            t.insert( i );
        System.out.println( "Inserts complete" );

        for( int i = 1; i < NUMS; i += 2 )
            t.remove( i );
        System.out.println( "Removes complete" );

        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 2; i < NUMS; i += 2 )
            if( !t.contains( i ) )
                System.out.println( "Error: find fails for " + i );

        for( int i = 1; i < NUMS; i += 2 )
            if( t.contains( i ) )
                System.out.println( "Error: Found deleted item " + i );
    }
}
