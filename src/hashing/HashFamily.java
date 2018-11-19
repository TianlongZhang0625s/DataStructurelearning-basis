package src.hashing;

/**
 * @author Tianlong Zhang
 * @time 2018/8/21 16:59
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public interface HashFamily<AnyType> {
    int hash(AnyType x, int which);
    int getNumberOfFunctions();
    void generateNewFunctions();
}
