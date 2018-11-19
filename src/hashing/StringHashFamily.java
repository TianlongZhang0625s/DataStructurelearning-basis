package src.hashing;

import java.util.Random;

/**
 * @author Tianlong Zhang
 * @time 2018/8/21 19:53
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */
public class StringHashFamily implements HashFamily<String> {

    private final int [] MULTIPLIERS;
    private final Random random = new Random();

    public StringHashFamily(int d){
        MULTIPLIERS = new int[d];
        generateNewFunctions();
    }

    public int getNumberOfFunctions(){
        return MULTIPLIERS.length;
    }
    public void generateNewFunctions(){
        for (int i = 0; i <MULTIPLIERS.length;i++){
            MULTIPLIERS[i] = random.nextInt();
        }
    }

    public int hash(String x, int which){
        final int multiper = MULTIPLIERS[which];
        int hashVal = 0;

        for(int i = 0; i < x.length(); i++){
            hashVal = multiper * hashVal + x.charAt(i);
        }
        return hashVal;
    }
}
