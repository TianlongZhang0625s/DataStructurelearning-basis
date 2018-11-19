package src.hashing;

/**
 * @author Tianlong Zhang
 * @time 2018/8/7 15:09
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class QuadraticProbingHashTable <AnyType> {

    private static class HashEntry<AnyType>{
        public AnyType element;
        public boolean isActive;

        public HashEntry(AnyType e, boolean i){
            element = e;
            isActive = i;
        }

        public HashEntry(AnyType e){
            this(e,true);
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private HashEntry<AnyType> [] array;
    private int occupied;
    private int theSize;

    public QuadraticProbingHashTable(int size){
        allocateArray(size);
        doClear();
    }

    public QuadraticProbingHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }

    public boolean insert(AnyType x){
        
        int currentPos = findPos(x);
        if(isActive(currentPos)){
            return false;
        }
        
        if(array[currentPos] == null){
            ++occupied;
        }
        
        array[currentPos] = new HashEntry<>(x , true);
        theSize++;
        
        if(occupied > array.length /2){
            rehash();
        }
        
        return true;
    }

    private void rehash() {
        HashEntry<AnyType> [] oldArray = array;
        allocateArray(2 * oldArray.length);
        occupied = 0;
        theSize = 0;

        for(HashEntry<AnyType> entry : oldArray){
            if(entry != null && entry.isActive){
                insert(entry.element);
            }
        }
    }

    public boolean remove(AnyType x){
        int currentPos = findPos(x);
        if(isActive(currentPos)){
            array[currentPos].isActive = false;
            theSize--;
            return true;
        }else
            return false;
    }

    public int size(){
        return theSize;
    }
    
    public int capacity(){
        return array.length;
    }
    
    public boolean contains(AnyType x){
        int currentPos = findPos(x);
        return isActive(currentPos);
    }
    
    
    public boolean isActive(int currentPos){
        return array[currentPos] != null && array[currentPos].isActive;
    }

    public void makeEmpty(){
        doClear();
    }


    public int findPos(AnyType x){
        int offset = 1;
        int currentPos = myhash(x);

        while (array[currentPos] != null &&
                !array[currentPos].element.equals(x)){
            currentPos += offset;
            offset += 2;
            if(currentPos >= array.length){
                currentPos -= array.length;
            }
        }

        return currentPos;
    }

    private int myhash(AnyType x) {
        int hashVal = x.hashCode();


        hashVal %= array.length;
        if(hashVal < 0){
            hashVal += array.length;
        }
        return hashVal;
    }

    private void doClear() {
        occupied = 0;
        for(int i = 0; i < array.length; i++){
            array[i] = null;
        }
    }

    private void allocateArray(int size) {
        array = new HashEntry[nextPrime(size)];
    }

    private int nextPrime(int n) {
        if(n % 2 == 0){
            n++;
        }

        for( ; !isPrime(n); n += 2)
            ;
        return n;
    }

    private boolean isPrime(int n) {
        if(n ==2 || n == 3){
            return true;
        }

        if(n ==1  || n % 2 == 0){
            return false;
        }

        for (int i = 3 ; i * i <= n; i ++){
            if(n % i == 0){
                return false;
            }
        }

        return true;
    }




    // Simple main
    public static void main( String [ ] args )
    {
        QuadraticProbingHashTable<String> H = new QuadraticProbingHashTable<>( );


        long startTime = System.currentTimeMillis( );

        final int NUMS = 2000000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );


        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            H.insert( ""+i );
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            if( H.insert( ""+i ) )
                System.out.println( "OOPS!!! " + i );
        for( int i = 1; i < NUMS; i+= 2 )
            H.remove( ""+i );

        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( ""+i ) )
                System.out.println( "Find fails " + i );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( H.contains( ""+i ) )
                System.out.println( "OOPS!!! " +  i  );
        }

        long endTime = System.currentTimeMillis( );

        System.out.println( "Elapsed time: " + (endTime - startTime) );
    }
}
