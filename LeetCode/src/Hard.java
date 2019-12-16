class Solution {
    
    // 42. Trapping Rain Water - https://leetcode.com/problems/trapping-rain-water/
    
    public int trap(int[] a) {
        if ((a == null) || (a.length < 3)) { 
            return 0;
        }
          
        int result = 0;
        
        for (int left = 0, right = a.length - 1, tallestLeftBar = -1, tallestRightBar = -1;
            left < right;) {
            
            tallestLeftBar  = Math.max(tallestLeftBar, a[left]); 
            tallestRightBar = Math.max(tallestRightBar, a[right]); 
            
            if (tallestLeftBar < tallestRightBar) {
                result += tallestLeftBar - a[left]; 
                left++;
            } else {
                result += tallestRightBar - a[right]; 
                right--;
            }            
        }
        
        return result;
    }
    
    // 269. Alien Dictionary - https://leetcode.com/problems/alien-dictionary/

    private final         int ABC_SIZE = 26; 
    private Deque<Character> stack     = new ArrayDeque<>(); 
    private boolean[]        visited   = new boolean[ABC_SIZE]; 
    private boolean[]        isLoop    = new boolean[ABC_SIZE]; 
 
    private class Node { 
        private char c; 
        private List<Node> neighbours = new ArrayList<>(); 
 
        Node (char c) { 
            this.c = c; 
        } 
    } 
 
    private int i(char ch) { return (ch - 'a'); } 
    
    /** 
     * Visits node neighbours recursively, returns whether or not the loop is found 
     * See https://www.geeksforgeeks.org/topological-sorting/ 
     */ 
    private boolean isLoopFound(Node node) { 
 
        int n = i(node.c); 
 
        if (isLoop[n])  { return true;  } 
        if (visited[n]) { return false; } 
 
        visited[n] = isLoop[n] = true; 
 
        for (Node neighbour : node.neighbours){ 
            if (isLoopFound(neighbour)) { 
                return true;    
            } 
        } 
 
        isLoop[n] = false; 
        stack.push(node.c);         
        return false; 
    }     
 
    public String alienOrder(String[] words) { 
 
        if ((words == null) || (words.length < 1)) { 
            return ""; 
        } 
 
        Node[] nodes    = new Node[ABC_SIZE]; 
        String prevWord = null; 
 
        for (String word : words){ 
 
            // Adding each new letter to the nodes array 
            for (int j = 0; j < word.length(); j++){ 
                char letter = word.charAt(j);  
                if (nodes[i(letter)] == null) { 
                    nodes[i(letter)] = new Node(letter); 
                } 
            } 
 
            // Checking the previous word (if exists) for the first different letter and making it the parent of the current word letter  
            if (prevWord != null) { 
                for (int j = 0; (j < word.length()) && (j < prevWord.length()); j++){ 
                    char prevLetter = prevWord.charAt(j); 
                    char letter     = word.charAt(j);  
                    if (letter != prevLetter) { 
                        nodes[i(prevLetter)].neighbours.add(nodes[i(letter)]); 
                        break; 
                    } 
                } 
            } 
 
            prevWord = word; 
        } 
 
        // Iterating though all letters, checking for loops, and adding letters to the stack 
        for (int j = 0; j < ABC_SIZE; j++){ 
            char letter = (char)('a' + j); 
            if ((! visited[j]) && (nodes[i(letter)] != null) && isLoopFound(nodes[i(letter)])){ 
                return ""; 
            } 
        } 
 
        // Emptying the Stack into result 
        StringBuilder b = new StringBuilder(); 
        while (! stack.isEmpty()) { 
            b.append(stack.pop()); 
        } 
        return b.toString(); 
    } 
    
    // 124. Binary Tree Maximum Path Sum - https://leetcode.com/problems/binary-tree-maximum-path-sum/
    
    int maxSum = Integer.MIN_VALUE;

    public int maxSum(TreeNode node) {
        if (node == null) { return 0; }

        int leftGain  = Math.max(maxSum(node.left), 0);
        int rightGain = Math.max(maxSum(node.right), 0);
        maxSum        = Math.max(maxSum, node.val + leftGain + rightGain);

        return node.val + Math.max(leftGain, rightGain);
    }

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        maxSum(root);
        return maxSum;
    }
    
    private class C {
        private int left;
        private int middle;
        private int right;
        
        private C (int left, int middle, int right) {
            this.left   = left;
            this.middle = middle;
            this.right  = right;
        }
    }
    
    private C maxSum1(TreeNode node) {

        if ((node.left == null) && (node.right == null)) {
            return new C(node.val, node.val, node.val);
        }
        
        C left  = (node.left  == null) ? null : maxSum1(node.left);
        C right = (node.right == null) ? null : maxSum1(node.right);
        
        int l = (left == null)  ? node.val : Math.max(node.val, node.val + Math.max(left.left, left.right));
        int r = (right == null) ? node.val : Math.max(node.val, node.val + Math.max(right.left, right.right));
        int m = (left  == null) ? Math.max(right.middle, l + r - node.val) :
                (right == null) ? Math.max(left.middle,  l + r - node.val) :
                                  Math.max(Math.max(left.middle, right.middle),
                                           l + r - node.val);
        return new C(l, m, r);
    }
    
    public int maxPathSum1(TreeNode root) {
        if (root == null) {
            return 0;
        }       
        
        C c = maxSum(root);
        return Math.max(Math.max(c.left, c.middle), c.right);
    }
    
    // 340. Longest Substring with At Most K Distinct Characters - https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
    
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if ((s == null) || (s.length() < 1) || (k < 1)) {
            return 0;
        }       
        
        if (s.length() <= k) {
            return s.length();
        }
        
        int result = 0; 
        int[] counters = new int[128];
        
        for (int left = 0, right = 0, size = 0; right < s.length(); right++) {
            int ch = s.charAt(right);
            
            if ((counters[ch]++) == 0) {
                size++;
            }
            
            while (size > k) {
                ch = s.charAt(left++);
                if ((--counters[ch]) == 0) {
                    size--;
                }
            }
            
            result = Math.max(result, right - left + 1);
        }
        
        return result;
    }
    
    // 273. Integer to English Words - https://leetcode.com/problems/integer-to-english-words/
    
    private int BILLION  = 1000000000;
    private int MILLION  = 1000000;
    private int THOUSAND = 1000;
    private int HUNDRED  = 100;
    
    private String[] TWENTY = new String[]{
        null, "One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ", "Eight ", "Nine ", "Ten ",
        "Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Seventeen ", "Eighteen ", "Nineteen "
    };
    
    private String[] TENS = new String[]{
        null, null, "Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ", "Seventy ", "Eighty ", "Ninety "
    }; 
    
    private void add(StringBuilder b, int n, String title) {
        if (n < 1) {
            return;
        }
        
        // [1..999]
        
        if (n >= HUNDRED) {
            int hundreds = n / HUNDRED;
            b.append(TWENTY[hundreds]).append("Hundred ");
            n -= (hundreds * HUNDRED);            
        }

        // [1..99]
        
        if (n > 19) {
            // [20..99]
            int tens = n / 10;
            b.append(TENS[tens]);
            n -= (tens * 10);
        } 
        
        if (n > 0) {
            // [1..19]
            b.append(TWENTY[n]);            
        }
        
        if (title != null) {
            b.append(title);
        }
    }
    
    public String numberToWords(int n) {
        
        if (n < 0) {
            return null;
        }
        
        if (n == 0) {
            return "Zero";
        }
        
        StringBuilder b = new StringBuilder();
        
        if (n >= BILLION) {
            int billions = n / BILLION;
            add(b, billions, "Billion ");
            n -= (billions * BILLION);            
        }
           
        if (n >= MILLION) {
            int millions = n / MILLION;
            add(b, millions, "Million ");
            n -= millions * MILLION;
        }

        if (n >= THOUSAND) {
            int thousands = n / THOUSAND;
            add(b, thousands, "Thousand ");
            n -= thousands * THOUSAND;
        }
        
        if (n > 0) {
            add(b, n, null);        
        }
        
        return b.toString().trim();
    }

    // 158. Read N Characters Given Read4 II - Call multiple times - https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/
    
    private int    lStart    = 0; // Leftovers start, inclusive
    private int    lEnd      = 0; // Leftovers end, exclusive
    private char[] leftovers = new char[4];
    
    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    public int read(char[] buf, int n) {
        if ((buf == null) || (buf.length < 1) || (n < 1)) { 
            return 0; 
        }     
        
        int p = 0; 
        
        // Trying leftovers first
        for (int j = 0, times = Math.min(lEnd - lStart, n); j < times; j++, n--) {
            buf[p++] = leftovers[lStart++];
        }     

        while (n > 0) {    
            
            int r = read4(leftovers);
            
            // No more data left to read
            if (r < 1) { break; }
            
            // Consuming as much as we need, potentially less than what we've just read
            for (int j = 0, times = Math.min(r, n); j < times; j++) { 
                buf[p++] = leftovers[j]; 
            }      
            
            // Setting leftovers boundaries
            lStart = Math.min(r, n);
            lEnd   = r;            
            n     -= r;
        } 

        return p;        
    }
    
    // 329. Longest Increasing Path in a Matrix - https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
    
    private int[][] cache;
    
    private int dfs(int[][] m, int row, int column) {
        
        if (cache[row][column] > 0) {
            return cache[row][column];
        }

        int rows = m.length;
        int columns = m[0].length;
        
        int n  = m[row][column]; 
        int n1 = (column > 0) ? m[row][column-1]           : 0;
        int n2 = (row    > 0) ? m[row-1][column]           : 0;
        int n3 = (column < (columns-1)) ? m[row][column+1] : 0;
        int n4 = (row    < (rows-1)) ? m[row+1][column]    : 0;
        
        int result = 1;

        if (n < n1) {
            result = Math.max(result, 1 + dfs(m, row, column-1));
        }
        
        if (n < n2) {
            result = Math.max(result, 1 + dfs(m, row-1, column));
        }
        
        if (n < n3) {
            result = Math.max(result, 1 + dfs(m, row, column+1));
        }

        if (n < n4) {
            result = Math.max(result, 1 + dfs(m, row+1, column));
        }

        cache[row][column] = result;
        return result;
    }
    
    public int longestIncreasingPath(int[][] m) {
        if ((m == null) || (m.length < 1) || (m[0].length < 1)) {
            return 0;
        }       
        
        int rows = m.length;
        int columns = m[0].length;
        int result = 0;
        cache = new int[rows][columns];
        
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                result = Math.max(result, dfs(m, row, column));
            }
        }
        
        return result;
    }
    

    // 76. Minimum Window Substring - https://leetcode.com/problems/minimum-window-substring/
    
    public String minWindow(String s, String t) {
        
        int[] counters = new int[128];
        
        for (char a : t.toCharArray()) {
            counters[a]++;
        }

        int missingLetters = t.length(), left = 0, right = 0, start = -1, window = Integer.MAX_VALUE;
        
        while (right < s.length()) {
          // Moving "right" until we find all letters and "missingLetters" drops to zero   
          if ((--counters[s.charAt(right++)]) > -1) {
              missingLetters--;
          }
            
          while (missingLetters == 0) {
            // Checking the current window (from "left" to "right")  
            if ((right - left) < window) {
              start = left;
              window = right - left;
            }
            // Moving "left" as long as we don't lose any letter by doing so ("missingLetters" stays zero)
            if ((++counters[s.charAt(left++)]) == 1) {
                missingLetters++;
            }
          }
        }
        
        return ((start == -1) ? "" : s.substring(start, start + window));
    }
}