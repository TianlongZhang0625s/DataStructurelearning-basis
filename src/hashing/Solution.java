package src.hashing;

import java.util.List;

/**
 * @author Tianlong Zhang
 * @time 2018/9/11 19:57
 * @project DataStructurelearning-basis
 * @Version 1.0.0
 */



public class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;

        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2;

        while(p != null && q != null){
            if(p.val < q.val){
                dummyHead.next = p;
                p = p.next;
            }else{
                dummyHead.next = q;
                q = q.next;
            }

            dummyHead = dummyHead.next;
        }

        dummyHead.next = (p == null) ? q : p;
        return dummyHead.next;
    }


    public  class ListNode{
        public ListNode(int val){
            this.val = val;
        }

        public ListNode next;
        int val;
    }

    public static void main(String[] args){

    }
}
