import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Easy {
    
    
    // In-Order traversal - returns last visited value or null if recursion should stop
    // ** Every previously visited node (lastVisited) should be less than the following one **
    private Integer visit(TreeNode root, Integer lastVisited) {
    
        if (root == null) {
            return lastVisited;
        }

        // Visiting left child 
        if (root.left != null) {
            lastVisited = visit(root.left, lastVisited);
            if (lastVisited == null) {
                return null; 
            }
        }
        
        // Visiting Node
        if ((lastVisited != null) && (lastVisited >= root.val)) {
            return null;
        }
        
        // Visiting right child
        return visit(root.right, root.val);
    }
    
    
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return visit(root, null) != null;
    }    
    
    private boolean isMirror (TreeNode t1, TreeNode t2) {
        if ((t1 == null) && (t2 == null)) {
            return true;
        }
        if ((t1 == null) || (t2 == null)) {
            return false;
        }
        
        return (t1.val == t2.val) && 
               isMirror(t1.left, t2.right) && 
               isMirror(t1.right, t2.left); 
    }    
    
    public boolean isSymmetric2(TreeNode root) {       
       return isMirror(root, root); 
    }
    
    private void traverse(TreeNode root, StringBuilder b, boolean isLeft) {
        if (root == null) {
            b.append("[]");    
            return;
        }
        
        b.append("[" + root.val + "]");
        
        if (isLeft) {
            traverse(root.left,  b, isLeft);
            traverse(root.right, b, isLeft);
        } else {
            traverse(root.right, b, isLeft);
            traverse(root.left,  b, isLeft);
        }
    }
    
    
    public boolean isSymmetric1(TreeNode root) {
       if (root == null) {
           return true;
       } 
        
       StringBuilder left  = new StringBuilder();
       StringBuilder right = new StringBuilder();
        
       traverse(root.left,  left, true);
       traverse(root.right, right, false);
        
       return left.toString().equals(right.toString()); 
    }

    private boolean isValidBST1(TreeNode root, Integer min, Integer max) {
        boolean isValid = true;
        
        if (root.left != null) {
            isValid = isValid && 
                      (root.val > root.left.val) && 
                      ((min == null) || (root.left.val > min)) && 
                      ((max == null) || (root.left.val < max)) && 
                      isValidBST(root.left, min, root.val); 
        }
        
        if (root.right != null) {
            isValid = isValid && 
                      (root.val < root.right.val) && 
                      ((min == null) || (root.right.val > min)) && 
                      ((max == null) || (root.right.val < max)) && 
                      isValidBST(root.right, root.val, max);             
        }
        
        return isValid;
    }
    
    public boolean isValidBST1(TreeNode root) {
 
        if (root == null) {
            return true;
        }
        
        boolean isValid = true;
        
        if (root.left != null) {
            isValid = isValid && (root.val > root.left.val) && isValidBST(root.left, null, root.val); 
        }
        
        if (root.right != null) {
            isValid = isValid && (root.val < root.right.val) && isValidBST(root.right, root.val, null);             
        }
        
        return isValid;
    }
    
    private class C {
        TreeNode node;
        int level;
        
        private C(TreeNode node, int level) {
            this.node  = node;
            this.level = level;
        }
    }
    
    private Queue<C> q = new LinkedList<>();
    
    // BFS
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int depth = -1;
        
        q.add(new C(root, 1));
        
        while (! q.isEmpty()) {
            C c = q.remove();
            depth = c.level;
            if (c.node.left != null) {
                q.add(new C(c.node.left, depth + 1));
            }
            if (c.node.right != null) {
                q.add(new C(c.node.right, depth + 1));
            }
        }
        
        return depth;
    }
    
    // DFS
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        return 1 + Math.max(maxDepth(root.left), 
                            maxDepth(root.right));
    }
    
    
    public boolean isPalindrome(ListNode head) {
        if ((head == null) || (head.next == null)) {
            return true;
        }
        
        // Count list size
        int size = 0;
        for (ListNode p = head; p != null; p = p.next, size++);
        
        int halfSize  = size / 2;
        int p1Steps   = ((size % 2) == 0) ? halfSize : halfSize + 1;
        int p2Steps   = halfSize - 1;  
        ListNode p1   = head;
        ListNode p2   = head;

        // Position p1 to walk the right half
        for (int j = 0; j < p1Steps; j++, p1 = p1.next);
                
        // Reverse the left half and position p2 to walk it in reverse order
        ListNode temp1 = p2.next;
        p2.next = null;
        for (int j = 0; j < p2Steps; j++) {
            ListNode temp2 = temp1.next;
            temp1.next = p2;
            p2 = temp1;
            temp1 = temp2;
        }
        
        // Walk p1 and p2 until mismatch or end of list
        for (int j = 0; j < halfSize; j++) {
            if (p1.val != p2.val) {
                return false;
            } 
            p1 = p1.next;
            p2 = p2.next;            
        }
                    
        // We could also reverse the left half links back
        return true;
    }
    

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }    

        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }


    public ListNode reverseList2(ListNode head) {
        if ((head == null) || (head.next == null)) {
            return head;
        }
        
        ListNode p = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    public ListNode reverseList1(ListNode head) {
        if ((head == null) || (head.next == null)) {
            return head;
        }
        
        ListNode p1 = head;
        ListNode p2 = head.next;
        
        head.next = null;
        
        while (p2 != null) {
            ListNode temp = p2.next;
            p2.next = p1;
            p1 = p2;
            p2 = temp;
        }
        
        return p1;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        if ((head == null) || (n < 1)) {
            return head;
        }
                
        int size = 0;
        for (ListNode p = head; p != null; p = p.next, size++);
        
        if (n < size) {
            ListNode p = head;
            for (int j = 0; j < (size - n - 1); p = p.next, j++);
            p.next = p.next.next;
            return head;
        } else {
            return head.next;
        }        
    }


    private int removeNode(ListNode head, int n) {

        if (head.next == null) {
            return 1; 
        } 
        
        int nodeOrder = removeNode(head.next, n);

        if (nodeOrder == n) {
            head.next = head.next.next;
        } 

        return nodeOrder + 1;
    }
    
    public ListNode removeNthFromEnd1(ListNode head, int n) {
        if ((head == null) || (n < 1)) {
            return head;
        }
        
        int nodeOrder = removeNode(head, n);
        return (nodeOrder <= n) ? head.next : head;
    }

    public void deleteNode(ListNode node) {
        if ((node == null) || (node.next == null)) {
            return;
        }
        
        node.val = node.next.val;
        node.next = node.next.next;
    }

    public String longestCommonPrefix(String[] strs) {
        if ((strs == null) || (strs.length < 1)) {
            return "";
        }       
        
        String shortestStr = strs[0];
        
        for (int j = 1; j < strs.length; j++) {
            if (strs[j].length() < shortestStr.length()) {
                shortestStr = strs[j]; 
            }
        }
        
        for (int j = 0; j < shortestStr.length(); j++) {
            char ch = shortestStr.charAt(j);
            for (int n = 0; n < strs.length; n++) {
                if (strs[n].charAt(j) != ch) {
                    return shortestStr.substring(0, j);
                }
            }
        }
        
        return shortestStr;
    }

    public String countAndSay(int n) {
        if (n < 1) {
            return "";
        }
        
        String s = "1";
        
        if (n == 1) {
            return s;
        }
        
        StringBuilder b = new StringBuilder();
        
        for (int j = 2; j <= n; j++) {
            String nextS = generateNextS(s, b);
            
            if (j == n) {
                return nextS;
            } else {
                s = nextS;
            }
        }
        
        return "";
    }
    
    
    private String generateNextS(String s, StringBuilder b) {
        int[] numbers = new int[s.length()];
        for (int j = 0; j < numbers.length; j++) {
            numbers[j] = (int)s.charAt(j) - '0';
        }
        
        int currentNumber = numbers[0];
        int currentRepeats = 1;
        b.setLength(0);
        
        for (int j = 1; j < numbers.length; j++) {
            int n = numbers[j];
            if (n == currentNumber) {
                currentRepeats++;
            } else {
                b.append(currentRepeats).append(currentNumber);
                currentNumber = n;
                currentRepeats = 1;                
            }
        }
        
        b.append(currentRepeats).append(currentNumber);
        return b.toString();
    }


    private boolean contains(char[] haystackChars, char[] needleChars, int startIndex) {
        
        if ((startIndex + needleChars.length) > haystackChars.length) {
            return false;
        }
        
        // Assuming first letter is already checked
        for (int j = 1; j < needleChars.length; j++) {
            if (needleChars[j] != haystackChars[startIndex + j]) {
                return false;
            }    
        }
        
        return true;
    }
    
    public int strStr(String haystack, String needle) {
        if ((haystack == null) || (needle == null)) {
            return -1;
        }
        if (needle.length() < 1) {
            return 0;
        }
        
        char[] haystackChars = haystack.toCharArray();
        char[] needleChars = needle.toCharArray();        
        
        for (int j = 0; j <= (haystackChars.length - needleChars.length); j++) {
            if ((haystackChars[j] == needleChars[0]) && contains(haystackChars, needleChars, j)) {
                return j;
            }
        }
        
        return -1;
    }


    public int removeDuplicates(final int[] nums) {

        if (nums == null) { return 0; }
        if (nums.length < 2) { return 1; }

        int pointer = 1;
        int currentNumber = nums[0];

        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != currentNumber) {
                nums[pointer++] = nums[j];
                currentNumber = nums[j];
            }
        }

        return pointer;
    }

    public int[] twoSum(int[] nums, int target) {

        if ((nums == null) || (nums.length < 2)) { return new int[0]; }

        Map<Integer, Integer> numsSet = new HashMap<>();

        for (int j = 0; j < nums.length; j++) {
            numsSet.put(nums[j], j);
        }

        for (int j = 0; j < nums.length; j++ ) {
            int number = nums[j];
            int otherNumber = target - number;

            if ((number == otherNumber) && (j != numsSet.get(number))) {
                return new int[]{j, numsSet.get(number)};
            }

            if ((number != otherNumber) && numsSet.containsKey(otherNumber)) {
                return new int[]{j, numsSet.get(otherNumber)};
            }
        }

        return new int[0];
    }

    private void swap(int[] nums, int start1, int start2, int length) {
        for (int j = 0; j < length; j++) {
            int temp = nums[start1 + j];
            nums[start1 + j] = nums[start2 + j];
            nums[start2 + j] = temp;
        }
    }

    private void rotate(int[] nums, int start, int k) {
        if ((start < 0) || (start >= nums.length) || (k < 1)) {
            return;
        }

        // k < nums.length

        int n = nums.length - start - k;

        if (k < n) {
            swap(nums, start, nums.length - k, k);
            rotate(nums, start + k, k);
        } else if (k == n) {
            swap(nums, start, start + k, k);
        }
        else {
            // k > n
            swap(nums, start, start + n, n);
            rotate(nums, start + n, k - n);
        }
    }

    public void rotate(int[] nums, int k) {
        if ((nums == null) || (nums.length < 2) || (k < 1)) {
            return;
        }

        if (k >= nums.length) {
            k = k % nums.length;
            if (k == 0) {
                return;
            }
        }

        // k < nums.length

        rotate(nums, 0, k);
    }

    public int maxProfit(int[] prices) {
        if ((prices == null) || (prices.length < 1)) {
            return 0;
        }

        int min = prices[0];
        int total = 0;

        for (int j = 1; j < prices.length; j++) {
            int price = prices[j];
            if (price < min) {
                min = price;
            } else if (price >= prices[j-1]) {
                boolean isPeak = (j < (prices.length - 1)) ? price > prices[j+1] : true;

                if (isPeak) {
                    total += (price - min);
                    min = (j < (prices.length - 1)) ? prices[j+1] : -1;
                    j++;
                }
            }
        }

        return total;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        if ((nums1 == null) || (nums1.length < 1)) {
            return new int[]{};
        }
        if ((nums2 == null) || (nums2.length < 1)) {
            return new int[]{};
        }

        int[] smallerArray = nums1.length < nums2.length ? nums1 : nums2;
        int[] largerArray  = nums1.length < nums2.length ? nums2 : nums1;

        Set<Integer> largerSet = new HashSet<>();
        Set<Integer> result = new HashSet<>();

        for (int n: largerArray) {
            largerSet.add(n);
        }

        for (int n: smallerArray) {
            if (largerSet.contains(n)) {
                result.add(n);
            }
        }

        int[] resultArray = new int[result.size()];
        int j = 0;

        for (int n: result) {
            resultArray[j++] = n;
        }

        return resultArray;
    }

    // j => counter of appearances
    public Map<Integer, Integer> indexArray(int[] nums) {

        if ((nums == null) || (nums.length < 1)) {
            return Collections.emptyMap();
        }

        Map<Integer, Integer> result = new HashMap<>();

        for (int j = 0; j < nums.length; j++) {
            result.put(nums[j], result.getOrDefault(nums[j], 0) + 1);
        }

        return result;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        if ((nums1 == null) || (nums1.length < 1)) {
            return new int[]{};
        }
        if ((nums2 == null) || (nums2.length < 1)) {
            return new int[]{};
        }

        Map<Integer, Integer> map1 = indexArray(nums1);
        Map<Integer, Integer> map2 = indexArray(nums2);
        Map<Integer, Integer> smallerMap = map1.size() < map2.size() ? map1 : map2;
        Map<Integer, Integer> largerMap  = map1.size() < map2.size() ? map2 : map1;
        List<Integer> result = new ArrayList<>();

        for (int n: smallerMap.keySet()) {
            if (largerMap.containsKey(n)) {
                for (int j = 0; j < Math.min(smallerMap.get(n), largerMap.get(n)); j++) {
                    result.add(n);
                }
            }
        }

        int[] resultArray = new int[result.size()];
        int j = 0;

        for (int n: result) {
            resultArray[j++] = n;
        }

        return resultArray;
    }

    public int[] plusOne(int[] digits) {
        if ((digits == null) || (digits.length < 1)) {
            return new int[0];
        }

        if (digits[digits.length - 1] < 9) {
            digits[digits.length - 1]++;
            return digits;
        }

        int[] result = new int[digits.length + 1];
        int carryOver = 1;

        for (int j = digits.length - 1; j >= 0; j--) {
            int num = digits[j];
            if (num < (10 - carryOver)) {
                result[j + 1] = num + carryOver;
                carryOver = 0;
            } else {
                result[j + 1] = num + carryOver - 10;
                carryOver = 1;
            }
        }

        if (carryOver == 1) {
            result[0] = 1;
            return result;
        } else {
            return Arrays.copyOfRange(result, 1, result.length);
        }
    }

    public void moveZeroes(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return;
        }

        int zeroes = 0;

        for (int j = 0; j < nums.length; j++) {
            int num = nums[j];
            if (num == 0) {
                zeroes++;
            } else {
                nums[j - zeroes] = num;
            }
        }

        for (int j = 0; j < zeroes; j++) {
            nums[nums.length - 1 - j] = 0;
        }
    }

    private int toInt(char ch) {
        return (ch == '.' ? 0 : ch - '0');
    }

    private short checkBit(short bitSet, int num) {
        if (num > 0) {
            if ((bitSet << ~num) < 0) {
                // Bit is already set
                return -1;
            } else {
                // Setting the bit
                bitSet |= 1 << num;
            }
        }
        return bitSet;
    }

    public boolean isValidSudoku(char[][] board) {
        int N = 9;
        int SMALLN = N / 3;
        if ((board == null) || (board.length != N)) {
            return false;
        }

        for (int row = 0; row < N; row++) {
            short bitSet = 0;
            for (int column = 0; column < N; column++) {
                int num = toInt(board[row][column]);
                // System.out.println("[" + row + "][" + column + "][" + num + "][" + bitSet + "]");
                bitSet = checkBit(bitSet, num);
                if (bitSet < 0) {
                    return false;
                }
            }
        }

        for (int column = 0; column < N; column++) {
            short bitSet = 0;
            for (int row = 0; row < N; row++) {
                int num = toInt(board[row][column]);
                bitSet = checkBit(bitSet, num);
                if (bitSet < 0) {
                    return false;
                }
            }
        }

        for (int row = 0; row < SMALLN; row++) {
            for (int column = 0; column < SMALLN; column++) {
                short bitSet = 0;
                for (int cell = 0; cell < N; cell++) {
                    int cellRow    = (row    * SMALLN) + (cell / 3);
                    int cellColumn = (column * SMALLN) + (cell % 3);
                    int num = toInt(board[cellRow][cellColumn]);
                    bitSet = checkBit(bitSet, num);
                    if (bitSet < 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void rotate(int[][] matrix) {
        if ((matrix == null) || (matrix.length < 1)) {
            return;
        }

        int n = matrix.length;

        // Iterating over all layers
        for (int layer = 0; layer < (n / 2); layer++) {
            // Iterating over cells in a row, rotating them 90 degress
            for (int cell = 0; cell < (n - 1 - (layer * 2)); cell++) {
                int temp = matrix[layer + cell][n - 1 - layer];
                // Top => Right
                matrix[layer + cell][n - 1 - layer] = matrix[layer][layer + cell];
                // Left => Top
                matrix[layer][layer + cell] = matrix[n - 1 - layer - cell][layer];
                // Bottom => Left
                matrix[n - 1 - layer - cell][layer] = matrix[n - 1 - layer][n - 1 - layer - cell];
                // Right => Bottom
                matrix[n - 1 - layer][n - 1 - layer - cell] = temp;
            }
        }
    }


    public int reverse(int x) {
        if ((x == 0) || ((x > -10) && (x < 10))) {
            return x;
        }

        int result = 0;

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            if ((result > Integer.MAX_VALUE/10) || ((result == Integer.MAX_VALUE/10) && digit >  7)) return 0;
            if ((result < Integer.MIN_VALUE/10) || ((result == Integer.MIN_VALUE/10) && digit < -8)) return 0;

            result = (result * 10) + digit;
        }

        return result;
    }
    
    private boolean isDigit (char ch) {
        int codePoint = (int) ch;
        return ((codePoint >= '0') && (codePoint <= '9')); 
    }    
    
    private boolean isAlphanumeric(char ch) {
        int codePoint = (int) ch;
        return ((codePoint >= 'a') && (codePoint <= 'z')) || 
               ((codePoint >= 'A') && (codePoint <= 'Z')) ||
               isDigit(ch);
    }
    
    private boolean isEqual(char ch1, char ch2) {
        if (ch1 == ch2) {
            return true;
        }        
        
        if (isDigit(ch1) || isDigit(ch2)) {
            return false;
        }

        int codePoint1 = (int) ch1;
        int codePoint2 = (int) ch2;
        int largerPoint = (codePoint2 > codePoint1) ? codePoint2 : codePoint1;
        int smallerPoint = (codePoint2 > codePoint1) ? codePoint1 : codePoint2;
        
        return  ((largerPoint - 'a') == (smallerPoint - 'A'));
    }

    public boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }       
        
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            while ((left < right) && (left < (chars.length-1)) && (! isAlphanumeric(chars[left]))) {
                left++;
            }
            while ((right > left) && (right > -1) && (! isAlphanumeric(chars[right]))) {
                right--;
            }
            
            if (left == right) {
                return true;
            }
            if (! isEqual(chars[left], chars[right])) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }


    private static final int MAX_VALUE_TENTH = Integer.MAX_VALUE / 10;
    private static final int MIN_VALUE_TENTH = Integer.MIN_VALUE / 10;
    
    private boolean isNumber(char ch) {
        return (((int)ch >= '0') && ((int)ch <= '9'));
    }
    
    public int myAtoi(String s) {
        if ((s == null) || (s.length() < 1)) {
            return 0;
        }    
        
        char[] chars = s.toCharArray();
        boolean isFirstNonWhitespace = true;
        int result = 0;
        boolean isPositive = true;
        
        for (int j = 0; j < chars.length; j++) {
            char ch = chars[j];
            
            if (isFirstNonWhitespace && (ch == ' ')) {
                continue;
            }
            
            boolean isNumber = isNumber(ch);
            int chNum = isNumber ? (((int) ch) - '0') : -1;
            
            if (isFirstNonWhitespace && (ch != ' ')) {
                isFirstNonWhitespace = false;
                if (isNumber) {
                    result = chNum;
                } else {
                    boolean isContinue = ((ch == '+') || (ch == '-')) &&
                                         (j < (chars.length - 1))     && 
                                         isNumber(chars[j+1]);
                    if (! isContinue){
                        return 0;
                    }
                    
                    isPositive = (ch == '+');
                }
            } else if (isNumber) {

                boolean isPositiveOverflow = 
                    isPositive && 
                    ((result > MAX_VALUE_TENTH) || ((result == MAX_VALUE_TENTH) && (chNum > 7)));
                    
                if (isPositiveOverflow){
                    return Integer.MAX_VALUE;                     
                }    

                boolean isNegativeOverflow = 
                    (! isPositive) && 
                    ((-result < MIN_VALUE_TENTH) || ((-result == MIN_VALUE_TENTH) && (chNum > 8)));
                
                if (isNegativeOverflow){
                    return Integer.MIN_VALUE;                     
                }    

                result = (result * 10) + chNum;
            } else {
                break;
            }
        }
        
        return isPositive ? result : -result;
    }
}
