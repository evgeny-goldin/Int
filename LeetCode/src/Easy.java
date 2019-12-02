import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Easy {
    
    // 344. Reverse String - https://leetcode.com/problems/reverse-string/
    
    public void reverseString(char[] s) {
        if ((s == null) || (s.length < 2)) {
            return;
        }
        
        for (int j = 0; j < s.length / 2; j++) {
            char temp = s[j];
            s[j] = s[s.length - 1 - j];
            s[s.length - 1 - j] = temp;
        }           
    }    
    
    // 242. Valid Anagram - https://leetcode.com/problems/valid-anagram/
    
    private int[] primes = new int[]{2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
    
    // ** Overflows for long Strings (larger than ~ 12 chars) ! **
    private long signature(String s) {
        long signature = 1L;
        for (int j = 0; j < s.length(); j++) {
            signature *= primes[s.charAt(j) - 'a'];
        }
        return signature;
    }    

    public boolean isAnagram2(String s, String t) {
        if ((s == null) || (t == null)) {
            return false;
        }
        
        return signature(s) == signature(t);
    }

    public boolean isAnagram1(String s, String t) {
        if ((s == null) || (t == null)) {
            return false;
        }
        if (s.trim().isEmpty() && t.trim().isEmpty()) {
            return true;
        }
        if (s.length() != t.length()) {
            return false;
        }
        
        int[] counters = new int[26];
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        
        for (int j = 0; j < s.length(); j++) {
            counters[sChars[j] - 'a']++;
            counters[tChars[j] - 'a']--;
        }
        
        for (int j = 0; j < counters.length; j++) {
            if (counters[j] != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    // 217. Contains Duplicate - https://leetcode.com/problems/contains-duplicate/
    
    public boolean containsDuplicate2(int[] nums) {
        if ((nums == null) || (nums.length < 2)) {
            return false;
        }
        
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }
        
        return false;
    }
    
    public boolean containsDuplicate1(int[] nums) {
        if ((nums == null) || (nums.length < 2)) {
            return false;
        }
        
        Arrays.sort(nums);
        
        for (int i = 0; i < (nums.length - 1); ++i) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        
        return false;
    }
    
    // 136. Single Number - https://leetcode.com/problems/single-number/
    
    public int singleNumber2(int[] nums) {
        if ((nums == null) || (nums.length < 1) || ((nums.length & 1) == 0)) {
            return -1;
        }
        
        int result = 0;
        
        for (int n: nums) {
            result ^= n;
        }
        
        return result;
    }
    
    public int singleNumber1(int[] nums) {
        if ((nums == null) || (nums.length < 1) || ((nums.length & 1) == 0)) {
            return -1;
        }
        
        int sumAll = 0;
        int sumUnique = 0;
        Set<Integer> set = new HashSet<>();
        
        for (int n: nums) {
            sumAll += n;
            if (set.add(n)) {
                sumUnique += n;
            }
        }
        
        return ((2 * sumUnique) - sumAll);
    }
    
    // 257. Binary Tree Paths - https://leetcode.com/problems/binary-tree-paths/
    
    private void binaryTreePaths(TreeNode node, String nodePath, List<String> result) {
        if (node == null) {
            return;
        }
        
        boolean isLeft  = (node.left  != null); 
        boolean isRight = (node.right != null); 
        
        if (isLeft || isRight) {
            if (isLeft) {
                binaryTreePaths(node.left, nodePath + "->" + node.left.val, result);        
            }

            if (isRight) {
                binaryTreePaths(node.right, nodePath + "->" + node.right.val, result);        
            }
        } else {
            result.add(nodePath);
        }        
    }
    
    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        binaryTreePaths(root, String.valueOf(root.val), result);
        return result;
    }
    
    // 680. Valid Palindrome II - https://leetcode.com/problems/valid-palindrome-ii/
    
    private boolean validPalindrome(String s, int left, int right) {
        for (; left < right; left++, right--) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
        }
        
        return true;
    }
    
    public boolean validPalindrome(String s) {
        if (s == null) {
            return false;
        }
     
        for (int left = 0, right = s.length() - 1; left < right; left++, right--) {
            if (s.charAt(left) != s.charAt(right)) {
                return validPalindrome(s, left, right - 1) || 
                       validPalindrome(s, left + 1, right);
            }
        }
        
        return true;
    }
    
    // 157. Read N Characters Given Read4 - https://leetcode.com/problems/read-n-characters-given-read4/
    
    public int read(char[] buf, int n) {
        if ((buf == null) || (buf.length < 1) || (n < 1)) {
            return 0;
        }    
        
        int p = 0;
        char[] b = new char[4];
        
        for (int r = 1; (n > 0) && (r > 0); n -= r) {
            r = read4(b);
            for (int j = 0; j < Math.min(r, n); j++) {
                buf[p++] = b[j];
            }            
        }
        
        return p;
    }
    
    // 67. Add Binary - https://leetcode.com/problems/add-binary/
    
    public String addBinary2(String s1, String s2) {
        if ((s1 == null) || (s1.length() < 1) || (s2 == null) || (s2.length() < 1)) {
            return null;
        }
        
        if ("0".equals(s1)) { return s2; }
        if ("0".equals(s2)) { return s1; }
        
        int l1 = s1.length();
        int l2 = s2.length();
        int[] result = new int[(l1 < l2 ? l2 : l1) + 1];
        int carryOver = 0, pr = 0;
        
        for (int j1 = l1 - 1, j2 = l2 - 1; (j1 >= 0) || (j2 >= 0); j1--, j2--) {
            int x        = ((j1 >= 0) ? (s1.charAt(j1) - '0') : 0);
            int y        = ((j2 >= 0) ? (s2.charAt(j2) - '0') : 0);
            result[pr++] = (x ^ y) ^ carryOver; 
            carryOver    = ((x == 0) && (y == 0) && (carryOver == 1)) ? 0 : (x & y) | carryOver;
        }
        
        if (carryOver > 0) {
            result[pr++] = 1;
        }    
        
        // Reversing the result
        
        StringBuilder b = new StringBuilder();
        
        for (int j = pr - 1; j >= 0; j--) {
            b.append(result[j]);
        }
        
        return b.toString();
    }
    
    public String addBinary1(String s1, String s2) {
        if ((s1 == null) || (s1.length() < 1) || (s2 == null) || (s2.length() < 1)) {
            return null;
        }
        
        if ("0".equals(s1)) { return s2; }
        if ("0".equals(s2)) { return s1; }
        
        int l1 = s1.length();
        int l2 = s2.length();
        int[] result = new int[(l1 < l2 ? l2 : l1) + 1];
        int carryOver = 0, pr = 0;
        
        for (int j1 = l1 - 1, j2 = l2 - 1; (j1 >= 0) || (j2 >= 0); j1--, j2--) {
            int res      = ((j1 >= 0) ? (s1.charAt(j1) - '0') : 0) + 
                           ((j2 >= 0) ? (s2.charAt(j2) - '0') : 0) + 
                           carryOver;
            carryOver    = (res > 1) ? res / 2 : 0;
            result[pr++] = (res > 1) ? res - (carryOver * 2) : res; 
        }
        
        if (carryOver > 0) {
            result[pr++] = 1;
        }    
        
        // Reversing the result
        
        StringBuilder b = new StringBuilder();
        
        for (int j = pr - 1; j >= 0; j--) {
            b.append(result[j]);
        }
        
        return b.toString();
    }

    // 543. Diameter of Binary Tree - https://leetcode.com/problems/diameter-of-binary-tree/
    
    private int max = 0;
    
    private int depth(TreeNode node) {
        if (node == null) {
            return -1;
        }
        
        int left  = depth(node.left);
        int right = depth(node.right);
        max       = Math.max(max, left + right + 2);
        return (1 + Math.max(left, right));
    }
    
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        depth(root);
        return max;
    }
    
    
    // 235. Lowest Common Ancestor of a Binary Search Tree - https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if ((root == null) || (p == null) || (q == null)) {
            return null;
        }
        
        if (q.val < p.val) {
            TreeNode temp = p;
            p = q;
            q = temp;
        }
        
        if ((p.val <= root.val) && (q.val >= root.val)) {
            return root;
        }
        
        return (((p.val < root.val) && (q.val < root.val)) ? 
                lowestCommonAncestor(root.left,  p, q) :
                lowestCommonAncestor(root.right, p, q));
    }
    
    // 387. First Unique Character in a String - https://leetcode.com/problems/first-unique-character-in-a-string/
    
    public int firstUniqChar(String s) {
        int[] indexes = new int[26];
        
        for (int j = 0; j < s.length(); j++) {
            int i = s.charAt(j) - 'a';
            indexes[i] = (indexes[i] == 0) ? j + 1 : -1; 
        }
        
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < indexes.length; j++) {
            min = (indexes[j] > 0) ? Math.min(min, indexes[j]) : min;
        }
        return ((min == Integer.MAX_VALUE) ? -1 : (min - 1));
    }
    
    // 268. Missing Number - https://leetcode.com/problems/missing-number/
    
    public int missingNumber(int[] nums) { 
        if ((nums == null) || (nums.length < 1)) { 
            return 0; 
        }     
         
        int sum = nums.length; 
         
        for (int j = 0; j < nums.length; j++) { 
            sum += (j - nums[j]); 
        } 
         
        return sum; 
    } 

    // 169. Majority Element - https://leetcode.com/problems/majority-element/
    
    public int majorityElement2(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return 0;
        }       
        
        int candidate = nums[0];
        
        for (int j = 1, counter = 1; j < nums.length; j++) {
            counter += ((nums[j] == candidate) ? 1 : -1); 

            if (counter == 0) {
                candidate = nums[j];
                counter = 1;
            }
        }
        
        return candidate;
    }

    public int majorityElement1(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return 0;
        }       
        
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    
    // 13. Roman to Integer - https://leetcode.com/problems/roman-to-integer/
    
    public int romanToInt2(String s) {
        if ((s == null) || (s.length() < 1)) {
            return 0;
        }       
        
        int sum = 0;
        
        Map<Character, Integer> map = new HashMap();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        
        for (int j = 0; j < s.length(); j++) {
            int n1 = map.get(s.charAt(j));
            int n2 = (j < (s.length() - 1)) ? map.get(s.charAt(j+1)) : 0;
            
            if (n1 < n2) {
                sum -= n1;
            } else {
                sum += n1;
            }
        }
        
        return sum;
    }
    
    public int romanToInt1(String s) {
        if ((s == null) || (s.length() < 1)) {
            return 0;
        }       
        
        int n = 0;
        
        for (int j = 0; j < s.length(); j++) {
            char ch  = s.charAt(j);
            char ch2 = (j < (s.length() - 1)) ? s.charAt(j+1) : ' ';
            if (ch == 'M') {
                n += 1000;
            } else if (ch == 'D') {
                n += 500;
            } else if (ch == 'L') {
                n += 50;
            } else if (ch == 'V') {
                n += 5;
            } else if (ch == 'C') {
                if (ch2 == 'D') {
                    n += 400;
                    j++;
                } else if (ch2 == 'M') {
                    n += 900;
                    j++;
                } else {
                    n += 100;
                }
            } else if (ch == 'X') {
                if (ch2 == 'L') {
                    n += 40;
                    j++;
                } else if (ch2 == 'C') {
                    n += 90;
                    j++;
                } else {
                    n += 10;
                }
            } else if (ch == 'I') {
                if (ch2 == 'V') {
                    n += 4;
                    j++;
                } else if (ch2 == 'X') {
                    n += 9;
                    j++;
                } else {
                    n += 1;
                }
            }
        }
        
        return n;
    }

    // 1. Two Sum (Easy)

    public int[] twoSum(int[] nums, int target) {
        if ((nums == null) || (nums.length < 2)) {
            return new int[0];
        }
        
        // number => index
        Map<Integer, Integer> index = new HashMap<>();
        for (int j = 0; j < nums.length; j++) {
            int number = nums[j];
            Integer otherIndex = index.get(target - number);
            if (otherIndex != null) {
                return new int[]{j, otherIndex};
            }
            index.put(number, j);
        }
        
        return new int[0];
    }
    
    // Fibonacci Sequence:
    // F(0) = 0, F(1) = 1, F(N) = F(N-1) + F(N-2)
    
    // Int  limit - fib(46) = 1836311903 
    // Long limit - fib(92) = 7540113804746346429
    private long fib (int n) {
        if (n < 0) {
            return -1;
        }
    
        if (n < 2) {
            return n;
        }
    
        long s1 = 1, s2 = 0;
    
        for (int j = 1; j < n; j++) {
            long temp = s1;
            if (s1 > (Long.MAX_VALUE - s2)) {
                return -1L;
            }
            s1 += s2;
            s2 = temp;
        }
    
        return s1;
    }    
    
    // 198. House Robber (Easy)
    
    public int rob2(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return 0;
        }    

        // F(N) = Max(X + F(N-2), F(N-1))
        
        int s1 = 0, s2 = 0;
        
        for (int x : nums) {
            int temp = s1;
            s1 = Math.max(x + s2, s1);
            s2 = temp;
        }
        
        return s1;
    }
    
    
    private Map<Integer, Integer> cache = new HashMap<>();
    
    public int rob1(int[] nums, int begin, int end) {
                
        if (begin > end) {
            return 0;
        }
        
        if (begin == end) {
            return nums[begin];
        }
        
        if ((begin + 1) == end) {
            return Math.max(nums[begin], nums[end]);
        }
        
        Integer result = cache.get(begin);
        
        if (result == null) {
            result = Math.max(nums[begin] + rob(nums, begin + 2, end),
                                            rob(nums, begin + 1, end));
            cache.put(begin, result);
        }
        
        return result;
    }
    
    public int rob1(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return 0;
        }    
        
        return rob(nums, 0, nums.length - 1);
    }


    // 53. Maximum Subarray (Easy)    
    
    private int cross(int[] nums, int begin, int end, int p) {
        
        int sum = nums[p];
        int leftMaxSum = sum; 
        
        for (int j = p - 1; j >= begin; j--) {
            sum += nums[j];
            leftMaxSum = Math.max(leftMaxSum, sum);
        }

        sum = nums[p+1];
        int rightMaxSum = sum; 
        
        for (int j = p + 2; j <= end; j++) {
            sum += nums[j];
            rightMaxSum = Math.max(rightMaxSum, sum);
        }
        
        return leftMaxSum + rightMaxSum;

    }
    
    private int maxSubArray2(int[] nums, int begin, int end) {
        if (begin > end) {
            return 0;
        }
        if (begin == end) {
            return nums[begin];
        }        
        if ((begin + 1) == end) {
            return Math.max(nums[begin] + nums[end],
                            Math.max(nums[begin], nums[end]));
        }        
        
        int p     = begin + ((end - begin) / 2);
        int left  = maxSubArray(nums, begin, p);
        int right = maxSubArray(nums, p+1, end);
        int cross = cross(nums, begin, end, p);
        
        return Math.max(cross, Math.max(left, right));
    } 
    
    
    public int maxSubArray2(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return 0;
        } 
        
        return maxSubArray2(nums, 0, nums.length - 1);
    }
    
    
    public int maxSubArray1(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return 0;
        }       
        
        int maxSum = nums[0];
        int sum = (maxSum > 0) ? maxSum : 0;

        for (int j = 1; j < nums.length; j++) {
            if ((sum + nums[j]) > 0) {
                sum += nums[j];
                maxSum = Math.max(maxSum, sum);
            } else {
                sum = 0;
                maxSum = Math.max(maxSum, nums[j]);
            }
        }
        
        return maxSum;
    }
    
    public int maxSubArray(int[] nums) { 
        if ((nums == null) || (nums.length < 1)) { 
            return 0; 
        }         
         
        int sum = nums[0];
        int maxSum = sum;
 
        for (int j = 1; j < nums.length; j++) {
            sum = Math.max(nums[j], nums[j] + sum);
            maxSum = Math.max(maxSum, sum);
        } 
         
        return maxSum; 
    } 

    // 121. Best Time to Buy and Sell Stock (Easy) - https://leetcode.com/problems/best-time-to-buy-and-sell-stock/submissions/
    
    public int maxProfit(int[] prices) {
        if ((prices == null) || (prices.length < 2)) {
            return 0;
        }       
        
        int min = prices[0], profit = 0;
        
        for (int j = 1; j < prices.length; j++) {
            int price = prices[j];
            if (price < min) {
                min = price;
            } else {
                profit = Math.max(profit, price - min);            
            }
        }
        
        return profit;
    }
    
    // 70. Climbing Stairs (Easy)
    
    public int climbStairs(int n) {
        if (n < 3) {
            return n;
        }
        
        if (n > 45) {
            return -1;
        }
        
        int[] cache = new int[n];
        cache[0] = 1;
        cache[1] = 2;
        
        for (int j = 2; j < n; j++) {
            if (cache[j-1] <= Integer.MAX_VALUE - cache[j-2]) {
                cache[j] = cache[j-1] + cache[j-2];    
            } else {
                return -1;
            }
        }
        
        return cache[n-1];        
    }
    
    // 278. First Bad Version (Easy)
    
    public int firstBadVersion(int n) {
        
        if (n < 1) {
            return -1;
        }
        
        int left = 1;
        int right = n;
        
        while (left < right) {
            int middle = left + ((right - left) / 2);
            if (isBadVersion(middle)) {
                right = middle;
            } else {
                left = middle + 1;
            }        
        }
        
        return left;
    }
    
    // 88. Merge Sorted Array (Easy)
    
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if ((nums1 == null) || (nums1.length < 1)) {
            return;
        }       
        if ((nums2 == null) || (nums2.length < 1)) {
            return;
        }       
        if (! ((m >= 0) && (n >= 0) && ((m + n) == nums1.length) && (n == nums2.length))) {
            return;
        }       
        
        m--;
        n--;
        
        for (int j = nums1.length - 1; j >= 0; j--) {
            if (((m > -1) && (n > -1) && (nums2[n] > nums1[m])) || (m < 0)) {
                nums1[j] = nums2[n];
                n--;
            } else {
                nums1[j] = nums1[m];
                m--;                
            }
        }
    }
    
    // 108. Convert Sorted Array to Binary Search Tree (Easy)
    
    public TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (! ((start >= 0) && (start < end) && (end <= nums.length))) {
            return null;
        }
        
        int middle = start + ((int)((end - start) / 2));
        TreeNode t = new TreeNode(nums[middle]);
        t.left  = sortedArrayToBST(nums, start, middle);
        t.right = sortedArrayToBST(nums, middle + 1, end);
        return t;
    }
    
    public TreeNode sortedArrayToBST(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return null;
        }    
    
        return sortedArrayToBST(nums, 0, nums.length);
    }
    
    // 98. Validate Binary Search Tree (Medium)
    
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
    
    
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return visit(root, null) != null;
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

    // 101. Symmetric Tree (Easy)
    
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
    
    // 104. Maximum Depth of Binary Tree (Easy)

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
    
    // 234. Palindrome Linked List (Easy)
    
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
    
    // ...

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
    
    // 38. Count and Say (Easy)

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

    // 28. Implement strStr() (Easy) - https://leetcode.com/problems/implement-strstr/

    private boolean contains(char[] haystackChars, char[] needleChars, int startIndex) {        
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
    
    // 26. Remove Duplicates from Sorted Array (Easy)

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
    
    // 1. Two Sum (Easy)

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

    // 189. Rotate Array (Easy)

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

        // How many chars are left to rotate
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
    
    // 122. Best Time to Buy and Sell Stock II (Easy)

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

    // 349. Intersection of Two Arrays (Easy)

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

    // 350. Intersection of Two Arrays II (Easy)

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
    
    // 66. Plus One (Easy)

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
    
    // 283. Move Zeroes (Easy)

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

    // 36. Valid Sudoku (Medium)

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
    
    // 48. Rotate Image (Medium)

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


    // 7. Reverse Integer

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
    
    // -----------------------------------------------
    // 125. Valid Palindrome
    // -----------------------------------------------
    
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

    // -----------------------------------------------
    // 8. String to Integer (atoi)
    // -----------------------------------------------

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
