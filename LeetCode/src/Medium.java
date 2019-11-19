class Solution {
    
    // 15. 3Sum - https://leetcode.com/problems/3sum/
    // https://leetcode.com/problems/3sum/discuss/432592/Simple-and-short-Java-solution
    
    public List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> solutionSet = new ArrayList<List<Integer>>();        
        Arrays.sort(nums);
        
        for(int a = 0; a < nums.length - 2; a++) {
            if((a > 0) && (nums[a] == nums[a - 1])) continue;
            int b = a + 1;
            int c = nums.length - 1;
            while (b < c) {
                int sum = nums[a] + nums[b] + nums[c]; 
                if(sum == 0) {
                    solutionSet.add(Arrays.asList(nums[a], nums[b], nums[c]));
                    b++;
                    while((b < c) && (nums[b] == nums[b - 1])) b++;
                } else if (sum < 0) {
                    b++;
                } else {
                    c--;
                }
            }
        }

        return solutionSet;
    }
    
    private List<List<Integer>> twoSum(Map<Integer, List<Integer>> index, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        for (int num: index.keySet()) {
            int otherNum = (target - num);
            if (index.containsKey(otherNum)) {
                for (int numIndex : index.get(num)) {
                    for (int otherIndex: index.get(otherNum)) {
                        if (numIndex < otherIndex) {
                            result.add(Arrays.asList(num, numIndex, otherNum, otherIndex));
                        }
                    }
                }
            }                
        }
        
        return result;
    }
    
    public List<List<Integer>> threeSum1(int[] nums) {
        
        if ((nums == null) || (nums.length < 3)) {
            return Collections.emptyList();
        }
        
        // N => list of indexes
        Map<Integer, List<Integer>> index = new HashMap<>();
        
        for (int j = 0; j < nums.length; j++) {
            List<Integer> indexes = index.get(nums[j]);
            if (indexes == null) {
                indexes = new ArrayList<>();
                index.put(nums[j], indexes);
            }
            indexes.add(j);
        } 
        
        if ((index.size() == 1) && index.containsKey(0)) {
            return Arrays.asList(Arrays.asList(0, 0, 0));
        }
        
        List<List<Integer>> result = new ArrayList<>();
        
        Set<String> set = new HashSet<>();
        
        for (int n1: index.keySet()) {
            long t = System.currentTimeMillis();
            
            List<List<Integer>> twoSumPairs = twoSum(index, -n1);
            
            for (int n1Index: index.get(n1)) {
                for (List<Integer> pair: twoSumPairs) {
                    int n2Index = pair.get(1);
                    int n3Index = pair.get(3);

                    if (n1Index < n2Index) {
                        int n2   = pair.get(0);
                        int n3   = pair.get(2);
                        int tn1  = Math.min(Math.min(n1, n2), n3);
                        int tn3  = Math.max(Math.max(n1, n2), n3);
                        int tn2  = n1 + n2 + n3 - tn1 - tn3;
                        String s = tn1 + ":" + tn2 + ":" + tn3;

                        if (set.add(s)) {
                            result.add(Arrays.asList(tn1, tn2, tn3));
                        }
                    }             
                }                   
            }
        }
        
        return result;
    }

    // 12. Integer to Roman - https://leetcode.com/problems/integer-to-roman/
    
    public String intToRoman(int n) {
        StringBuilder b = new StringBuilder();
        
        for (int j = 0; j < (n / 1000); j++) {
            b.append("M");
        }
        
        n = n % 1000;
        
        if (n >= 900) {
            b.append("CM");
            n -= 900;
        } else if (n >= 500) {
            b.append("D");
            n -= 500;
        } else if (n >= 400) {
            b.append("CD");
            n -= 400;
        } 
        
        for (int j = 0; j < (n / 100); j++) {
            b.append("C");
        }

        n = n % 100;
        
        if (n >= 90) {
            b.append("XC");
            n -= 90;
        } else if (n >= 50) {
            b.append("L");
            n -= 50;
        } else if (n >= 40) {
            b.append("XL");
            n -= 40;
        } 
        
        for (int j = 0; j < (n / 10); j++) {
            b.append("X");
        }
        
        n = n % 10;

        if (n >= 9) {
            b.append("IX");
            n -= 9;
        } else if (n >= 5) {
            b.append("V");
            n -= 5;
        } else if (n >= 4) {
            b.append("IV");
            n -= 4;
        } 

        for (int j = 0; j < n; j++) {
            b.append("I");
        }
        
        return b.toString();
    }
    
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
    
    // 384. Shuffle an Array
    
    private final int[] origin;
    private final int[] array;
    private final Random R = new Random();
    
    public Solution(int[] nums) {
        if (nums == null) {
            throw new NullPointerException();
        }
        
        origin = Arrays.copyOfRange(nums, 0, nums.length);
        array = new int[nums.length];
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return origin;
    }
    
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle2() {       
        System.arraycopy(origin, 0, array, 0, origin.length);
        for (int j = 0; j < array.length; j++) {
            swap(j, j + R.nextInt(array.length - j));
        }
        return array;
    }    
    
    private final int[] counters;
    private final int[] origin;
    private final int[] array;
    private final Set<Integer> set = new HashSet<>();
    
    public Solution(int[] nums) {
        if (nums == null) {
            throw new NullPointerException();
        }
        
        origin = Arrays.copyOfRange(nums, 0, nums.length);
        counters = new int[nums.length];
        array = new int[nums.length];
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return origin;
    }
    
    private boolean isValidCounters() {
        set.clear();
        for (int j = 0; j < counters.length; j++) {
            int index = j + counters[j];
            if (index >= counters.length) {
                index -= counters.length;
            }
            int number = origin[index];
            if (set.add(number)) {
                array[j] = number;
            } else {
                return false;
            }
        }
        return true;
    }
    
    private void incrementCounters() {
        do {
            for (int j = 0; j < counters.length; j++) {            
                counters[j] = (counters[j] < (counters.length - 1)) ? counters[j] + 1 : 0;
                if (counters[j] != 0) {
                    break;
                }
            }            
        } while (! isValidCounters());
    }
        
    /** Returns a random shuffling of the array. */
    public int[] shuffle1() {       
        incrementCounters();
        return array;
    }    
    
    
    // 5. Longest Palindromic Substring
    
    // Container for returning expansion results
    private class C {
        private int size;
        private int start;
        private int end;
    } 
    
    private void expand(String s, int start, int end, C c) {
        if (s.charAt(start) != s.charAt(end)) {
            return;
        }
        
        start--;
        end++;            
        
        while((start >= 0) && (end < s.length()) && (s.charAt(start) == s.charAt(end))) {
            start--;
            end++;
        }

        int size = end - start - 1; 
        
        if (size > c.size) {
            c.size  = size;
            c.start = start + 1; 
            c.end   = end - 1;
        }
    }
    
    public String longestPalindrome(String s) {
        if ((s == null) || (s.length() < 2)) {
            return s;
        }
        
        C c     = new C();
        c.size  = (s.charAt(0) == s.charAt(1)) ? 2 : 1;
        c.start = 0;
        c.end   = c.size - 1;
        
        for (int j = 2; j < s.length(); j++) {
            // Trying both expansion options: "xx" and "x?x"
            expand(s, j - 1, j, c);
            expand(s, j - 2, j, c);
        }
                
        return (c.size < s.length()) ? s.substring(c.start, c.end + 1) : s;
    }   
    
    // 3. Longest Substring Without Repeating Characters
    
    public int lengthOfLongestSubstring3(String s) {
        int n = s.length(), result = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int right = 0, left = 0; right < n; right++) {
            int ch = s.charAt(right);
            left   = Math.max(index[ch], left);
            result = Math.max(result, right - left + 1);
            index[ch] = right + 1;
        }
        return result;
    }
    
    private boolean isSet(long bitSet, int bit) {
        return ((bitSet << ~bit) < 0);
    }
    
    private long set(long bitSet, int bit) {
        return (bitSet | (1L << bit));
    }

    private long clear(long bitSet, int bit) {
        return (bitSet & ~(1L << bit));
    }
    
    public int lengthOfLongestSubstring2(String s) {
        if ((s == null) || (s.length() < 1)) {
            return 0;
        }

        int result = 0, left = 0, right = 0, length = s.length();
        long bitSetHigh = 0, bitSetLow = 0;
        
        while ((left < length) && (right < length)) {
            int ch = (int) s.charAt(right);
            if (ch > 127) {
                throw new IllegalArgumentException(String.valueOf(ch));
            }
            boolean isBitAlreadySet = (ch < 64) ? isSet(bitSetLow, ch): 
                                                  isSet(bitSetHigh, ch - 64);
            if (isBitAlreadySet) {
                ch = (int) s.charAt(left++);
                // Clear "ch" bit
                if (ch < 64) {
                    bitSetLow  = clear(bitSetLow, ch);
                } else {
                    bitSetHigh = clear(bitSetHigh, ch - 64);
                }
            } else {
                right++;
                // Set "ch" bit
                if (ch < 64) {
                    bitSetLow  = set(bitSetLow, ch);
                } else {
                    bitSetHigh = set(bitSetHigh, ch - 64);
                }
                result = Math.max(result, right - left);
            }
        }

        return result;
    }
    
    public int lengthOfLongestSubstring1(String s) {
        if ((s == null) || (s.length() < 1)) {
            return 0;
        }

        int result = 0, left = 0, right = 0;
        boolean[] set = new boolean[128];
        
        while ((left < s.length()) && (right < s.length())) {
            int ch = (int)s.charAt(right++);
            if (! set[ch]) {
                set[ch] = true;
                result = Math.max(result, right - left);
            } else {
                right--;
                set[(int)s.charAt(left++)] = false;
            }
        }

        return result;
    }
    
    // 445. Add Two Numbers II
    
    private final C EMPTY_C = new C(0, null);
    
    // Container for recursion returns
    private class C {
        private int carryOver;
        private ListNode node;
        private C (int carryOver, ListNode node) {
            this.carryOver = carryOver;
            this.node = node;
        }
    }
    
    // Adds two lists, *both pointers are equaly distant from their ends*
    // Returns a new container with a carry over and a new list
    private C add(ListNode l1, ListNode l2) {
        C c           = ((l1.next == null) && (l2.next == null)) ? EMPTY_C : add(l1.next, l2.next);        
        int sum       = l1.val + l2.val + c.carryOver;
        int carryOver = (sum > 9) ? 1 : 0;
        sum           = (sum > 9) ? sum - 10 : sum;
        ListNode l    = new ListNode(sum);
        l.next        = c.node;
        
        return new C(carryOver, l);
    }
    
    // Calcualtes list's length
    private int listLength (ListNode l) {
        int length = 0;
        for (; l != null; l = l.next){ length++; };
        return length;
    }
    
    // Applies carry over to the list (modifying it), returns a new carry over
    private C addCarryOver(ListNode start, ListNode end, int carryOver) {
        C c = (start == end) ? new C(carryOver, null) : addCarryOver(start.next, end, carryOver);
        if (c.carryOver == 0) {
            return EMPTY_C;            
        } 
        
        start.val += c.carryOver;
        int carry  = (start.val > 9) ? 1 : 0;
        start.val  = (start.val > 9) ? start.val - 10 : start.val;
        
        return new C(carry, null);
    }
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if ((l1 == null) || (l2 == null)) {
            return (l1 == null) ? l2 : l1;
        }
        
        int length1 = listLength(l1);
        int length2 = listLength(l2);
        
        // Two lists are of identical length - easy case
        if (length1 == length2) {
            C c = add(l1, l2);

            if (c.carryOver == 0) {
                return c.node;
            } 
            
            ListNode root = new ListNode(c.carryOver);
            root.next = c.node;
            return root;                
        }
        
        ListNode longerList  = length1 > length2 ? l1 : l2;
        ListNode shorterList = length1 > length2 ? l2 : l1;
        ListNode l = longerList;
        int steps = Math.abs(length1 - length2) - 1;
    
        // Moving l forward so that l.next and shorterList pointers are equally distant from the end
        for (int j = 0; j < steps; j++) { l = l.next; }
        C c = add(l.next, shorterList);
        
        // Attaching the result to the longer list, modifying it
        l.next = c.node;
        
        if ((l.val + c.carryOver) < 10) {
            l.val += c.carryOver;
            return longerList;
        }
        
        c = addCarryOver(longerList, l, c.carryOver); 
        
        if (c.carryOver == 0) {
            return longerList;
        } 
        
        ListNode root = new ListNode(c.carryOver);
        root.next = longerList;
        return root;                
    }    
    
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
            carryOver = (sum > 9) ? 1 : 0;
            sum       = (sum > 9) ? sum - 10 : sum; 
             
            l.next = new ListNode(sum); 
            l  = l.next; 
            l1 = isL1Over ? null : l1.next; 
            l2 = isL2Over ? null : l2.next; 
 
            // One of the lists is over while the other one isn't
            if ((isL1Over || isL2Over) && (! (isL1Over && isL2Over)) && (carryOver == 0)) { 
                l.next = (isL1Over ? l2 : l1); 
                break; 
            } 
        } 
         
        return dummy.next; 
    }
}