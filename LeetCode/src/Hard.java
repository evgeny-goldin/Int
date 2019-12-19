class Solution {
    
            // 23. Merge k Sorted Lists - https://leetcode.com/problems/merge-k-sorted-lists/
    
    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode h = new ListNode(0);
        ListNode result = h;

        while ((l1 != null) && (l2 != null)) {
            if (l1.val < l2.val) {
                h.next = l1;
                h      = h.next;
                l1     = l1.next;
            } else {
                h.next = l2;
                h      = h.next;
                l2     = l2.next;
            }
        }

        if(l1 == null) {
            h.next = l2;
        }

        if(l2 == null) {
            h.next = l1;
        } 

        return result.next;
    }
    
    public ListNode mergeKLists(ListNode[] lists) {
        if ((lists == null) || (lists.length < 1)) {
            return null;
        }
                
        for (int interval = 1; interval < lists.length; interval *= 2){
            for (int j = 0; (j + interval) < lists.length; j += (interval * 2)) {
                lists[j] = merge(lists[j], lists[j + interval]);            
            }
        }

        return lists[0];
    }
    
    // 297. Serialize and Deserialize Binary Tree - https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
    
    // -- DFS -- 
    
    private final int Bump               = 1000; // A number we bump up every node value by to escape negative values 
    private final int NullNodeNumber     = 1010; // Max node value (that we never meet), used as a Null character 
    private final Character NullNodeChar = Character.toChars(NullNodeNumber + Bump)[0];    
    
    private void serialize(TreeNode node, StringBuilder b) {
        if (node == null) {
            b.append(NullNodeChar);
        } else {
            b.append(Character.toChars(node.val + Bump)[0]);
            serialize(node.left,  b);
            serialize(node.right, b);            
        }        
    }
    
    // Encodes a tree to a single String by encoding each node's value to it's Unicode Character as if it was a codepoint. 
    // Since the max node value is 1000 - we only need one Character (see Character.toChars())    
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        
        StringBuilder b = new StringBuilder();
        serialize(root, b);
        return b.toString(); // ϩϪϫϭϯߚߚϰϴ..
    }
    
    private TreeNode deserialize(String data, int[] j) {
        int n = ((j[0] < data.length()) ? data.charAt(j[0]++) - Bump : NullNodeNumber);
        
        if (n == NullNodeNumber) {
            return null;
        }
        
        TreeNode node = new TreeNode(n);
        node.left     = deserialize(data, j);
        node.right    = deserialize(data, j);
        return node;
    }
    
    // Decodes your encoded data to tree by taking each character in a String and decoding it back to its codepoint (a node.val)
    public TreeNode deserialize(String data) {
        
        if ((data == null) || (data.length() < 1)) {
            return null;
        }
        
        return deserialize(data, new int[]{0});
    }    
    
    // -- BFS -- 
    
    private final TreeNode Null          = new TreeNode(-1);
    private final int Bump               = 1000; // A number we bump up every node value by to escape negative values 
    private final int NullNodeNumber     = 1010; // Max node value (that we never meet), used as a Null character 
    private final Character NullNodeChar = Character.toChars(NullNodeNumber + Bump)[0];
    
    // Calculates the height of the tree: single node is 1, a node with children is 2 and so on.
    private int height(TreeNode node) {
        return ((node == null) ? 0 : 1 + Math.max(height(node.left), height(node.right)));
    }
    
    // Encodes a tree to a single String by encoding each node's value to it's Unicode Character as if it was a codepoint. 
    // Since the max node value is 1000 - we only need one Character (see Character.toChars())
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        
        int height = height(root);
        StringBuilder b = new StringBuilder();
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        
        int level = 1;

        while (! q.isEmpty()) {
            for (int queueSize = q.size(); queueSize > 0; queueSize--) {
                TreeNode node = q.remove();
                if (node == Null) {
                    b.append(NullNodeChar);
                } else {
                    b.append(Character.toChars(node.val + Bump)[0]);
                    // When last level (level = height) - we don't look at children, this avoids trailing nulls from last level leaves  
                    if (level < height) {
                      q.add(node.left  == null ? Null : node.left);
                      q.add(node.right == null ? Null : node.right);
                    }
                }
            }
            
            level++;
        }
        
        return b.toString(); // ϩߚϪߚϫߚϬ..
    }
    
    // Decodes your encoded data to tree by taking each character in a String and decoding it back to its codepoint (which is a node.val)
    // Each node is added to and removed from the queue (BFS), while pulling for new chars from the data.
    public TreeNode deserialize(String data) {
        
        if ((data == null) || (data.length() < 1)) {
            return null;
        }
        
        int j = 0; // Pointer to data chars
        int n = data.charAt(j++) - Bump;
        
        if (n == NullNodeNumber) {
            return null;
        }
        
        // Since we know how many characters we expect - we can build our own queue using an array and use two pointers to "add" and "remove" from it 
        int add          = 0;
        int remove       = 0;
        int dataLength   = data.length();
        TreeNode[] queue = new TreeNode[dataLength];
        queue[add++]     = new TreeNode(n);
                
        // While queue is not empty
        while (add > remove) { 
            // queue.remove()
            TreeNode parent = queue[remove++];
            
            int left  = ((j < dataLength) ? data.charAt(j++) - Bump : NullNodeNumber);
            int right = ((j < dataLength) ? data.charAt(j++) - Bump : NullNodeNumber);
            
            if (left != NullNodeNumber) {
                parent.left  = new TreeNode(left);
                // queue.add()
                queue[add++] = parent.left;
            }
            
            if (right != NullNodeNumber) {
                parent.right = new TreeNode(right);
                // queue.add()
                queue[add++] = parent.right;
            }
        }
        
        return queue[0];
    }    
    
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