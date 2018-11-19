package src.hashing;

// SeparateChaining Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

/**
 * Separate chaining table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author Mark Allen Weiss
 */

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tianlong Zhang
 * @time 2018/8/7 9:53
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class SeparateChainHashingTable <AnyType> {

    /*important*/
    private List<AnyType>[] theList; // to define a array as the address find

    /*to record the number of the element in the hash table*/
    private int currentSize;
    private static final int DEFAULT_TABLE_SIZE = 101;

    public SeparateChainHashingTable(){
        this(DEFAULT_TABLE_SIZE);
    }

    //insert
    public void insert(AnyType x){
        List<AnyType> whichList = theList[myhash(x)];
        if(!whichList.contains(x)){
            whichList.add(x);

            //当插入成功的次数超过分离表的长度时，
            //需要rehash()方法重新hash，一般扩大为原来的两倍
            if(++ currentSize > theList.length){
                rehash();
            }
        }
    }

    //remove
    public void remove(AnyType x){
        List<AnyType> whichList = theList[myhash(x)];
        if(whichList.contains(x)){
            whichList.remove(x);
            currentSize--;
        }
    }

    public boolean contains(AnyType x){
        List<AnyType> whichList = theList[myhash(x)];
        return whichList.contains(x);
    }

    public void makeEmpty(){
        for (int i = 0; i < theList.length; i++){
            theList[i].clear();
        }
        currentSize = 0;
    }
    private void rehash() {
        List<AnyType> [] oldList = theList;

        theList = new List[nextPrime(2 * theList.length)];
        for (int j = 0; j < theList.length; j++){
            theList[j] = new LinkedList<>();
        }

        currentSize = 0;
        for(List<AnyType> list : oldList){
            for (AnyType item : list){
                insert(item);
            }
        }

    }

    private int myhash(AnyType x) {
        int hashVal = x.hashCode();

        hashVal %= theList.length;

        if(hashVal < 0){
            hashVal += theList.length;
        }
        return hashVal;
    }

    public SeparateChainHashingTable(int size){
        theList = new LinkedList[nextPrime(size)];
        for(int i = 0; i < theList.length; i++){
            theList[i] = new LinkedList<>();
        }
    }

    private int nextPrime(int n) {
        if(n % 2 == 0)
            n++;
        for(; !isPrime(n) ; n += 2)
            ;
        return n;
    }

    private boolean isPrime(int n) {
        if(n == 2 || n == 3){
            return true;
        }

        if(n == 1 || n % 2 == 0){
            return false;
        }

        for(int i = 3; i * i <= n; i+=2){
            if (n % i == 0){
                return false;
            }
        }
        return true;
    }

    public static int hash(String key, int tableSize){
        int hashVal = 0;

        for (int i = 0; i < key.length(); i++){
            hashVal = 37 * hashVal + key.charAt(i);
        }

        hashVal %= tableSize;
        if(hashVal < 0){
            hashVal += tableSize;
        }
        return hashVal;
    }


    public static void main( String [ ] args )
    {
        SeparateChainHashingTable<Integer> H = new SeparateChainHashingTable<>( );

        long startTime = System.currentTimeMillis( );

        final int NUMS = 2000000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            H.insert( i );
        for( int i = 1; i < NUMS; i+= 2 )
            H.remove( i );

        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( i ) )
                System.out.println( "Find fails " + i );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( H.contains( i ) )
                System.out.println( "OOPS!!! " +  i  );
        }

        long endTime = System.currentTimeMillis( );

        System.out.println( "Elapsed time: " + (endTime - startTime) );
    }
}
