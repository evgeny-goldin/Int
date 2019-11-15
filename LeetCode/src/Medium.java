class Solution {
    
    // 2. Add Two Numbers
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }    
        if (l2 == null) {
            return l1;
        }        
        
        ListNode dummy = new ListNode(-1);
        ListNode l = dummy;
        
        for (int carryOver = 0; (l1 != null) || (l2 != null) || (carryOver > 0);) {
            boolean isL1Over = (l1 == null);
            boolean isL2Over = (l2 == null);
            
            int sum   = carryOver + (isL1Over ? 0 : l1.val) + (isL2Over ? 0 : l2.val);
            int value = (sum > 9) ? sum - 10 : sum;
            carryOver = (sum > 9) ? 1 : 0;
            
            l.next = new ListNode(value);
            l  = l.next;
            l1 = isL1Over ? null : l1.next;
            l2 = isL2Over ? null : l2.next;

            if ((isL1Over || isL1Over) && (! (isL1Over && isL2Over)) && (carryOver == 0)) {
                l.next = (isL1Over ? l2 : l1);
                break;
            }
        }
        
        return dummy.next;
    }
}