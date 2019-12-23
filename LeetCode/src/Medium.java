class Solution {
    
    // 71. Simplify Path - https://leetcode.com/problems/simplify-path/

    private void updateStack(String path, int start, int end, Deque<String> stack) {
        if ((start >= end) || ((start == (end - 1)) && (path.charAt(start) == '.'))) {
            // "/." - do nothing
            return;
        } 
        
        if ((start == (end - 2)) && (path.charAt(start) == '.') && (path.charAt(start + 1) == '.')) {
            // "/.."
            stack.pollLast();                    
        } else {
            stack.add(path.substring(start, end));                                
        }                        
    }
    
    public String simplifyPath(String path) {
        if ((path == null) || (path.length() < 1)) {
            return path;
        }    
        
        Deque<String> stack = new ArrayDeque<>();
        int start = 0;
        
        for (int j = 0; j < path.length(); j++) {
            char ch = path.charAt(j);
            if (ch == '/') {
                updateStack(path, start, j, stack);
                start = j + 1;
            }    
        }

        updateStack(path, start, path.length(), stack);
                        
        if (stack.isEmpty()) {
            return "/";
        }
        
        StringBuilder b = new StringBuilder();
        while (! stack.isEmpty()) {
            b.append('/').append(stack.remove());
        }
        return b.toString();
    }

    // 652. Find Duplicate Subtrees - https://leetcode.com/problems/find-duplicate-subtrees/

    int counter = 1;
    Map<String, Integer> trees = new HashMap();
    Map<Integer, Integer> counts = new HashMap();
    List<TreeNode> result = new ArrayList();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        lookup(root);
        return result;
    }

    public int lookup(TreeNode node) {
        if (node == null) { return 0; }
        
        String serial = node.val + ":" + lookup(node.left) + ":" + lookup(node.right);
        int uid = trees.computeIfAbsent(serial, (s) -> counter++);
        
        counts.put(uid, counts.getOrDefault(uid, 0) + 1);
        
        if (counts.get(uid) == 2) {
            result.add(node);            
        }
        
        return uid;
    }

    // 279. Perfect Squares - https://leetcode.com/problems/perfect-squares/
    
    public int numSquares(int n) {
        if (n < 3) {
            return n;
        }
     
        int squares[] = new int[(int) Math.sqrt(n) + 1];
        for (int i = squares.length - 1; i > 0; i--) {
            squares[i] = (i * i);
            if (n == squares[i]) {
                return 1;
            }
        }

        int dp[] = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            for (int j = 1; (j < squares.length) && (squares[j] <= i); j++) {
                dp[i] = Math.min(dp[i], 1 + dp[i - squares[j]]);
            }
        }
      
        return dp[n];
    }

    // 139. Word Break - https://leetcode.com/problems/word-break/

    private Set<String> mismatchesCache = new HashSet<>();
    
    private boolean wordBreak(String s, ArrayList<String> words) {
        
        if (s.length() < 1) {
            return true;
        }
        
        if (mismatchesCache.contains(s)) {
            return false;
        }
        
        for (int j = 0; j < words.size(); j++) {
            String word = words.get(j);
            if (s.startsWith(word) && wordBreak(s.substring(word.length()), words)) {
                return true;
            }
        }
        
        mismatchesCache.add(s);
        return false;
    }
    
    public boolean wordBreak(String s, List<String> words) {
        if ((s == null) || (s.length() < 1) || (words == null) || (words.size() < 1)) {
            return false;
        }       
        
        return wordBreak(s, new ArrayList(words));
    }

    // 655. Print Binary Tree - https://leetcode.com/problems/print-binary-tree/

    public List<List<String>> printTree(TreeNode root) {
        int height = getHeight(root);
        
        String[][] result = new String[height][(1 << height) - 1];
        
        for(String[] a : result){
            Arrays.fill(a, "");            
        }
        
        fill(result, root, 0, 0, result[0].length);
        
        List<List<String>> answer = new ArrayList<>();
        for(String[] a : result) {
            answer.add(Arrays.asList(a));            
        }        
        return answer;
    }
    
    public void fill(String[][] result, TreeNode root, int level, int l, int r) {
        if (root == null){
            return;            
        }
        
        int middle = l + ((r - l) / 2);
        result[level][middle] = String.valueOf(root.val);
        fill(result, root.left,  level + 1, l, middle);
        fill(result, root.right, level + 1, middle + 1, r);
    }
    
    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
            
        return (1 + Math.max(getHeight(root.left), 
                             getHeight(root.right)));
    }

    // 332. Reconstruct Itinerary - https://leetcode.com/problems/reconstruct-itinerary/

    private boolean backtrack(String current, Map<String, List<String>> map, List<String> result, int n) {
        if (result.size() == n) { return true; }
        
        List<String> neibs = map.get(current);
        if ((neibs == null) || neibs.isEmpty()) { return false; }
        
        for (int j = 0; j < neibs.size(); j++) {
            String neib = neibs.remove(j);
            result.add(neib);
            if (backtrack(neib, map, result, n)) { return true; }
            result.remove(result.size() - 1);
            neibs.add(j, neib);
        }
        
        return false;
    }
    
    public List<String> findItinerary(List<List<String>> tickets) {
        
        Map<String, List<String>> map = new HashMap<>();

        for (List<String> fromTo: tickets) {
            String from = fromTo.get(0);
            String to = fromTo.get(1);
            List<String> tos = map.get(from);
            if (tos == null) {
                tos = new ArrayList<>();
                map.put(from, tos);
            }
            tos.add(to);
        }
        
        if (! map.containsKey("JFK")) {
            return Collections.singletonList("JFK");
        }

        for (final String to: map.keySet()) {
            Collections.sort(map.get(to));
        }
        
        List<String> result = new ArrayList();
        result.add("JFK");
        backtrack("JFK", map, result, tickets.size() + 1);
        return result;
    }

    // 34. Find First and Last Position of Element in Sorted Array - https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
    
    private int[] searchRange(int[] nums, int target, int left, int right) {
        
        if ((nums[left] > target) || (nums[right] < target)) {
            return new int[]{-1, -1};
        }
        
        if ((nums[left] == target) && (nums[right] == target)) {
            return new int[]{left, right};
        }
        
        
        int middle = left + ((right - left) / 2);
        
        if (nums[middle] < target) {
            return searchRange(nums, target, middle + 1, right);
        } 
        
        if (nums[middle] > target) {
            return searchRange(nums, target, left, middle - 1);
        }
        
        int start = middle, end = middle;
        
        while ((start >= left) && (nums[start] == target)){ start--; }
        while ((end  <= right) && (nums[end]   == target)){ end++;   }
        
        return new int[]{start + 1, end - 1};
    }
    
    public int[] searchRange(int[] nums, int target) {
        if ((nums == null) || (nums.length < 1)) {
            return new int[]{-1, -1};
        }       
        
        return searchRange(nums, target, 0, nums.length - 1);
    }
    
    
    // 114. Flatten Binary Tree to Linked List - https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
    
    /**
     * Flattens the tree, returning the chain's tail
     */
    private TreeNode helper (TreeNode root) {
        if (root == null) {
            return null;
        }
        
        if ((root.left == null) && (root.right == null)) {
            return root;                        
        }

        TreeNode leftChild  = root.left;
        TreeNode rightChild = root.right;
        TreeNode left       = helper(leftChild);
        TreeNode right      = helper(rightChild);

        root.left = null;
        
        if (leftChild != null) {
            root.right = leftChild;
            left.right = rightChild;
        }

        return (right != null ? right : left);
    }
    
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }       
        
        helper(root);
    }
    
    // 102. Binary Tree Level Order Traversal - https://leetcode.com/problems/binary-tree-level-order-traversal/
    
    private class C {
        private TreeNode node;
        private int level;
        
        private C(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
    
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        
        Queue<C> queue = new LinkedList<>();
        List<Integer> currentLevel = null;
        List<List<Integer>> result = new ArrayList();

        queue.add(new C(root, 1));
        
        while (! queue.isEmpty()) {
            C c = queue.remove();
            
            if (result.size() < c.level) {
                // Starting a new level in the result
                currentLevel = new ArrayList();
                result.add(currentLevel);
            } 
            
            currentLevel.add(c.node.val);
            
            if (c.node.left != null) {
                queue.add(new C(c.node.left, c.level + 1));
            }
            if (c.node.right != null) {
                queue.add(new C(c.node.right, c.level + 1));
            }
        }
        
        return result; 
    }
    
    // 91. Decode Ways - https://leetcode.com/problems/decode-ways/
    
    public int numDecodings(String s) {
        if ((s == null) || (s.length() < 1)) {
            return 0;
        }
        
        int[] d  = new int[s.length()];
        int[] dp = new int[s.length() + 1];
        
        for (int j = 0; j < s.length(); j++) {
            d[j] = s.charAt(j) - '0';
        }
        
        dp[0] = 1;
        
        for (int j = 1; j <= s.length(); j++) {
            int oneDigit = d[j-1];
            
            if (oneDigit > 0) {
                dp[j] += dp[j-1];
            }
            
            if (j > 1) {
                int twoDigit = ((d[j-2] * 10) + d[j-1]);
                if ((twoDigit > 9) && (twoDigit < 27)) {
                    dp[j] += dp[j-2];
                }
            }
        }
        
        return dp[s.length()];
    }    
    
    // 56. Merge Intervals - https://leetcode.com/problems/merge-intervals/
    
    public int[][] merge2(int[][] intervals) {

        if ((intervals == null) || (intervals.length < 1)) {
            return new int[0][];
        }       
        
        if (intervals.length == 1) {
            return intervals;
        }        
        
        Arrays.sort(intervals, (a,b) -> (a[0] - b[0]));

        List<int[]> result = new ArrayList<>();
        
        for (int[] interval : intervals) {
            if (result.isEmpty()) {
                result.add(interval);
                continue;
            }

            int[] prev = result.get(result.size() - 1);
            
            if (interval[0] > prev[1]) {
                // No overlap between prev and interval 
                result.add(interval);
            } else {
                // Overlap between prev and interval - extending prev
                prev[1] = Math.max(prev[1], interval[1]);                
            }            
        }

        return result.toArray(new int[result.size()][]);
    }
    
    public int[][] merge1(int[][] intervals) {
        if ((intervals == null) || (intervals.length < 1)) {
            return new int[0][];
        }       
        
        if (intervals.length == 1) {
            return intervals;
        }
        
        int min = intervals[0][0];
        int max = intervals[0][1];
        
        for (int j = 1; j < intervals.length; j++) {
            min = Math.min(min, intervals[j][0]);
            max = Math.max(max, intervals[j][1]);
        }
        
        int[] a = new int[max - min + 1];

        Set<Integer> emptyIntervals = new HashSet<>();
        
        for (int j = 0; j < intervals.length; j++) {
            if (intervals[j][0] == intervals[j][1]) {
                emptyIntervals.add(intervals[j][0]);
            } else {
                a[intervals[j][0] - min]++;
                a[intervals[j][1] - min]--;                
            }
        }        
        
        List<int[]> result = new ArrayList<>();
        
        // Iterating with a sliding window over a, range is [left, j]
        for (int j = 0, sum = 0, left = 0; j < a.length; j++) {
            if (a[j] > 0) {
                if (sum == 0) {
                    left = j;
                }
                sum += a[j];
            } else if (a[j] < 0) {
                emptyIntervals.remove(j + min);
                sum += a[j];
                if (sum == 0) {
                    result.add(new int[]{left + min, j + min});
                }
            }
            if (sum > 0) {
                emptyIntervals.remove(j + min);
            }            
        }
        
        for (int j : emptyIntervals) {
            result.add(new int[]{j,j});
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    // 253. Meeting Rooms II - https://leetcode.com/problems/meeting-rooms-ii/
    
    public int minMeetingRooms2(int[][] intervals) {
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        PriorityQueue<Integer> ends = new PriorityQueue<>();
        
        for (int[] interval : intervals) {
            // ends.peek() returns the smallest "end"
            if ((! ends.isEmpty()) && (interval[0] >= ends.peek())) {
                ends.remove();
            }
            ends.add(interval[1]);
        }
        
        return ends.size();
    }
    
    public int minMeetingRooms1(int[][] intervals) {
        if ((intervals == null) || (intervals.length < 1)) {
            return 0;
        }
        
        if (intervals.length == 1) {
            return 1;
        }
        
        int min = intervals[0][0];
        int max = intervals[0][1];
        
        for (int j = 1; j < intervals.length; j++) {
            min = Math.min(min, intervals[j][0]);
            max = Math.max(max, intervals[j][1]);            
        }
        
        int[] a = new int[max - min + 1];

        for (int j = 0; j < intervals.length; j++) {
            a[intervals[j][0] - min]++;
            a[intervals[j][1] - min]--;
        }
        
        int result = 0;
        
        for (int j = 0, available = 0; j < a.length; j++) {
            if (a[j] == 0) {
                continue;
            }
            
            if (a[j] > available) {
                result   += (a[j] - available);
                available = 0;                                
            } else {
                available -= a[j];                
            }
        }
        
        return result;
    }
    
    // 78. Subsets - https://leetcode.com/problems/subsets/
    
    public List<List<Integer>> subsets(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return Collections.emptyList();
        }    
        
        if (nums.length == 1) {
            return Arrays.asList(Collections.emptyList(),
                                 Arrays.asList(nums[0]));
        }
        
        if (nums.length == 2) {
            return Arrays.asList(
                Collections.emptyList(),
                Arrays.asList(nums[0]),
                Arrays.asList(nums[1]),
                Arrays.asList(nums[0], nums[1])
            );
        }
        
        List<List<Integer>> result = new ArrayList();
        
        result.add(Collections.emptyList());
        result.add(Arrays.asList(nums[0]));
        result.add(Arrays.asList(nums[1]));
        result.add(Arrays.asList(nums[0], nums[1]));
        
        for (int j = 2; j < nums.length; j++) {
            int n = nums[j];
            int resultSize = result.size();
            
            for (int i = 0; i < resultSize; i++) {
                List<Integer> copy = new ArrayList(result.get(i));
                copy.add(n);
                result.add(copy);
            }
        }
        
        return result;
    }
    
    // 46. Permutations - https://leetcode.com/problems/permutations/
    
    private List<Integer> inject(List<Integer> l, int n, int index) {
        List<Integer> result = new ArrayList(l.size() + 1);
        
        for (int j = 0; j < l.size() + 1; j++) {
            result.add((j == index) ? n :
                       (j < index ) ? l.get(j) :
                                      l.get(j - 1));
        }
        
        return result;
    }
    
    public List<List<Integer>> permute(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return Collections.emptyList();
        }       
        
        if (nums.length == 1) {
            return Arrays.asList(Arrays.asList(nums[0]));
        }

        if (nums.length == 2) {
            return Arrays.asList(Arrays.asList(nums[0], nums[1]),
                                 Arrays.asList(nums[1], nums[0]));
        }
        
        List<List<Integer>> result = new ArrayList<>();

        result.add(Arrays.asList(nums[0], nums[1]));
        result.add(Arrays.asList(nums[1], nums[0]));
 
        for (int j = 2; j < nums.length; j++) {
            int n = nums[j];
            
            List<List<Integer>> tempResult = new ArrayList<>();
            
            for (List<Integer> r : result) {
                for (int i = 0; i < r.size() + 1; i++) {
                    tempResult.add(inject(r, n, i));
                }
            }
            
            result = tempResult;
        }
        
        return result;
    }
    
    // 17. Letter Combinations of a Phone Number - https://leetcode.com/problems/letter-combinations-of-a-phone-number/
    
    public List<String> letterCombinations(String s) {

        if ((s == null) || (s.length() < 1)) {
            return Collections.emptyList();
        }

        char[][] mapping = new char[][]{null, null,
                                        {'a','b','c'},
                                        {'d','e','f'},
                                        {'g','h','i'},
                                        {'j','k','l'},
                                        {'m','n','o'},
                                        {'p','q','r','s'},
                                        {'t','u','v'},
                                        {'w','x','y','z'}};

        List<String> result = new ArrayList<>();

        for (int j = 0; j < s.length(); j++) {

            int ch = s.charAt(j) - '0';

            if ((ch < 2) || (ch > 9)) {
                return Collections.emptyList();
            }

            char[] letters = mapping[ch];

            if (result.isEmpty()) {
               for (char l : letters) {
                   result.add(String.valueOf(l));
               }
            } else {
                List<String> tempResult = new ArrayList<>();
                for (String r : result) {
                    for (char l : letters) {
                        tempResult.add(r + l);
                    }                    
                }
                result = tempResult;
            }
        }

        return result;

    }
    
    private static Map<Integer, List<String>> mapping = new HashMap<>();
    
    static {
        mapping.put(2, Arrays.asList("a", "b", "c"));
        mapping.put(3, Arrays.asList("d", "e", "f"));
        mapping.put(4, Arrays.asList("g", "h", "i"));
        mapping.put(5, Arrays.asList("j", "k", "l"));
        mapping.put(6, Arrays.asList("m", "n", "o"));
        mapping.put(7, Arrays.asList("p", "q", "r", "s"));
        mapping.put(8, Arrays.asList("t", "u", "v"));
        mapping.put(9, Arrays.asList("w", "x", "y", "z"));        
    }
    
    private List<String> letterCombinations(String s, int start) {
        if (start >= s.length()) {
            return Collections.emptyList();
        }

        int digit = s.charAt(start) - '0';
            
        if (! mapping.containsKey(digit)) {
            return null;
        }

        List<String> rest = letterCombinations(s, start + 1);
        
        if (rest == null) {
            return null;
        }
        
        if (rest.isEmpty()) {
            return mapping.get(digit);
        }
        
        List<String> result = new ArrayList<>();

        for (String ds : mapping.get(digit)) {
            for (String rs : rest) {
                result.add(ds + rs);
            }            
        }
        
        return result;
    }  
    
    public List<String> letterCombinations(String s) {
        if ((s == null) || (s.length() < 1)) {
            return Collections.emptyList();
        }       
    
        List<String> result = letterCombinations(s, 0); 
        return (result == null) ? Collections.emptyList() : result;
    }

    // 199. Binary Tree Right Side View - https://leetcode.com/problems/binary-tree-right-side-view/
    
    public List<Integer> rightSideView2(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> q = new ArrayDeque<>();
        
        q.add(root);
        
        for (TreeNode lastNode = null, node = null; ! q.isEmpty(); ) {
            int size = q.size();
            for (int j = 0; j < size; j++) {
                node = q.remove();
                if (node.left  != null) { q.add(node.left);  }
                if (node.right != null) { q.add(node.right); }
                lastNode = node;
            }
            
            result.add(lastNode.val);
        }
        
        return result;
    }
    
    private int rightSideView1(TreeNode node, List<Integer> values) {
        if (node == null) {
            return 0;
        }
        
        values.add(node.val);
        
        int right = rightSideView(node.right, values);
        int left  = 0;
        
        if (node.left != null) {
            List<Integer> leftValues = new ArrayList<>();
            left = rightSideView(node.left, leftValues);
            for (int j = right; j < left; j++) {
                values.add(leftValues.get(j));
            }
        }        
        
        return (1 + Math.max(left, right));
    }
    
    public List<Integer> rightSideView1(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }       
        
        List<Integer> values = new ArrayList<>();
        rightSideView(root, values);
        return values;
    }
    
    // 143. Reorder List - https://leetcode.com/problems/reorder-list/
    
    public void reorderList(ListNode head) {
        if ((head == null) || (head.next == null) || (head.next.next == null)) {
            return;
        }
        
        Deque<ListNode> stack = new ArrayDeque<>();
        
        int size = 0;
        
        for (ListNode p = head; p != null; p = p.next) {
            stack.push(p);
            size++;
        }
        
        ListNode p = head;
        
        for (int j = 0, steps = (size / 2); j < steps; j++) {
            ListNode next = p.next;
            p.next        = stack.pop();
            p.next.next   = next;
            p             = next;
        }
        
        p.next = null;
        return;
    }

    // 138. Copy List with Random Pointer - https://leetcode.com/problems/copy-list-with-random-pointer/

    private Map<Node, Node> nodes = new HashMap<>();
    
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        
        Node n = nodes.get(head);
        
        if (n == null) {
            n = new Node(head.val, null, null);
            nodes.put(head, n);

            n.next   = copyRandomList(head.next);
            n.random = copyRandomList(head.random);     
        }
        
        return n;
    }

    // 560. Subarray Sum Equals K - https://leetcode.com/problems/subarray-sum-equals-k/

    public int subarraySum(int[] nums, int k) {

        for(int i = 1; i < nums.length; i++){
            nums[i] += nums[i-1];
        }

        Map<Integer,Integer> prefix = new HashMap<>();
        prefix.put(0,1);

        int result = 0;

        for(int i = 0; i < nums.length; i++){
            result += prefix.getOrDefault(nums[i] - k, 0);
            prefix.put(nums[i], prefix.getOrDefault(nums[i], 0) + 1);
        }

        return result;
    }

    // 395. Longest Substring with At Least K Repeating Characters - https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/

	public int longestSubstring(String s, int k) {
		return longestSubstring(s, k, 0, s.length());
	}

	private int longestSubstring(String s, int k, int start, int end) {

		if ((end - start) < k) { return 0; }
		
        int[] counters = new int[26];
    
		for (int i = start; i < end; i++) {
			counters[s.charAt(i) - 'a']++;
		}

		int left = start, result = 0;

		for (int i = start; i < end; i++) {
            int counter = counters[s.charAt(i) - 'a']; 
			if (counter < k) {
				result = Math.max(result, longestSubstring(s, k, left, i));
				left = i + 1;
			}
		}
		
		return (left == start) ? (end - start) : 
                                 Math.max(result, longestSubstring(s, k, left, end));
	}

    // 161. One Edit Distance - https://leetcode.com/problems/one-edit-distance/

    public boolean isOneEditDistance(String s, String t) {
        if ((s == null) || (t == null)) {
            return false;
        }       
        
        int sl = s.length();
        int tl = t.length();
        
        if ((Math.abs(sl - tl) > 1) || ((sl + tl) == 0)) {
            return false;
        }
        
        if ((sl + tl) == 1) {
            return true;
        }
        
        String shorter = ((sl < tl) ? s : t);
        String longer  = ((sl < tl) ? t : s);
        int    diff    = 0;
        
        for (int ps = 0, pl = 0; (ps < shorter.length()) && (pl < longer.length());) {
            if (shorter.charAt(ps) == longer.charAt(pl)) {
                ps++;
                pl++;
                continue;
            }
            
            if ((++diff) > 1) { return false; }

            pl++;
            if (sl == tl) { ps++; }
        }
                
        if (sl == tl) {
            return (diff == 1);
        } else {
            return (diff == 0) || 
                   (shorter.charAt(shorter.length() - 1) == longer.charAt(longer.length() - 1));        
        }
    }

    // 43. Multiply Strings - https://leetcode.com/problems/multiply-strings/

    /**
     * Multiples "a" by "n" storing result in "result" starting at index "start"
     * Returns the index of the last stored number in the "result"  
     */
    private int multiply(int[] a, int n, int[] result, int start) {
        int carryOver = 0;
        
        for (int j = a.length - 1; j >= 0; j--, start++) {
            int res       = (a[j] * n) + carryOver + result[start];
            carryOver     = (res > 9) ? (res / 10) : 0;
            result[start] = (res > 9) ? res - (carryOver * 10) : res;
        }
        
        for (; carryOver > 0; start++) {
            int res       = carryOver + result[start];
            carryOver     = (res > 9) ? (res / 10) : 0;
            result[start] = (res > 9) ? res - (carryOver * 10) : res;            
        }
        
        return start - 1;
    }
    
    public String multiply(String s1, String s2) {
        if ((s1 == null) || (s1.length() < 1) || (s2 == null) || (s2.length() < 1)) {
            return null;
        }       
        
        if ("0".equals(s1) || "0".equals(s2)) {
            return "0";
        }
        
        int l1 = s1.length();
        int l2 = s2.length();
        
        String s = ((l1 < l2) ? s1 : s2); // Short
        String l = ((l1 < l2) ? s2 : s1); // Long
        int[]  ld = new int[l.length()];  // Long's digits
        
        for (int j = 0; j < ld.length; j++) {
            ld[j] = l.charAt(j) - '0';
        }
        
        int[] result = new int[(l1 + l2)*2];
        int lastDigit = 0;
        
        for (int j = s.length() - 1, start = 0; j >= 0; j--, start++) {
            lastDigit = multiply(ld, s.charAt(j) - '0', result, start);
        }
        
        // Reversing the result array starting from lastDigit 
        
        StringBuilder sb = new StringBuilder();
        
        for (int j = lastDigit; j >= 0; j--) {
            sb.append(result[j]);
        }
        
        return sb.toString();
    }

    // 31. Next Permutation - https://leetcode.com/problems/next-permutation/

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i]  = nums[j];
        nums[j]  = temp;
    }

    public void nextPermutation(int[] nums) {
        
        // Searching for the first drop (at i), from right to left
        int i = nums.length - 2;
        for (; (i >= 0) && (nums[i] >= nums[i + 1]); i--);
        
        // Searching for the first number larger than drop (at j)
        if (i >= 0) {
            int j = nums.length - 1;
            for (; (j >= 0) && nums[i] >= nums[j]; j--);
            swap(nums, i, j);
        }

        // Reversing between [i+1, nums.length-1]
        for (int j = 0; j < ((nums.length - 1 - i) / 2); j++) {
            swap(nums, i + 1 + j, nums.length - 1 - j);
        }
    }

    // 236. Lowest Common Ancestor of a Binary Tree - https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

    private TreeNode dfs(TreeNode node, TreeNode p, TreeNode q) {
        if ((node.val == p.val) || (node.val == q.val)) {
            return node;
        }
        
        TreeNode left  = (node.left  == null) ? null : dfs(node.left, p, q);
        TreeNode right = (node.right == null) ? null : dfs(node.right, p, q);
        
        return ((left  == null) ? right : 
                (right == null) ? left  :
                                  node);        
    }
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if ((root == null) || (p == null) || (q == null)) {
            return null;
        }
        
        if (p.val == q.val) {
            return p;
        }
        
        return dfs(root, p, q);
    }

    // 200. Number of Islands - https://leetcode.com/problems/number-of-islands/

    void mark(char[][] grid, int row, int column) {
      
        int rows = grid.length;
        int columns = grid[0].length;

        if ((row < 0) || (column < 0) || (row >= rows) || (column >= columns) || (grid[row][column] == '0')) {
          return;
        }

        grid[row][column] = '0';

        mark(grid, row - 1, column);
        mark(grid, row + 1, column);
        mark(grid, row, column - 1);
        mark(grid, row, column + 1);
    }

    public int numIslands(char[][] grid) {
        if ((grid == null) || (grid.length < 1) || (grid[0].length < 1)) {
            return 0;
        }

        int rows = grid.length;
        int columns = grid[0].length;
        int result = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (grid[row][column] == '1') {
                  result++;
                  mark(grid, row, column);
                }
            }
        }

        return result;
    }

    // 133. Clone Graph - https://leetcode.com/problems/clone-graph/

    private Map<Node, Node> nodes = new HashMap<>();
    
    public Node cloneGraph2(Node node) {
        
        if (node == null) {
            return null;
        }
        
        Node n = nodes.get(node);
        
        if (n == null) {
            n = new Node(node.val, new ArrayList());
            nodes.put(node, n);

            for (Node neighbor: node.neighbors) {
                n.neighbors.add(cloneGraph(neighbor));
            }
        }
        
        return n;
    }

    private void updateValues(Node node, Map<Integer, List<Integer>> values) {
        if (values.containsKey(node.val)) {
            return;
        }
        
        List<Integer> nodeValues = new ArrayList(node.neighbors.size());
        values.put(node.val, nodeValues);
        
        for (Node n: node.neighbors) {
            nodeValues.add(n.val);
            updateValues(n, values);
        }
    }
    
    public Node cloneGraph1(Node node) {
        
        if (node == null) {
            return null;
        }
        
        if (node.neighbors.size() < 1) {
            return new Node(node.val, Collections.emptyList());
        }
        
        Map<Integer, List<Integer>> values = new HashMap<>();
        
        updateValues(node, values);
        
        Map<Integer, Node> nodes = new HashMap<>(values.size());
        
        for (int val : values.keySet()) {
            List<Integer> neighborsValues = values.get(val);
            List<Node> neighbors = new ArrayList(neighborsValues.size());
            Node n = nodes.get(val);
            
            if (n == null) {
                n = new Node();
                n.val = val;
                nodes.put(val, n);
            }

            n.neighbors = neighbors;
                                                 
            for (int neighborValue: neighborsValues) {                
                Node neighbor = nodes.get(neighborValue);
                
                if (neighbor == null) {
                    neighbor = new Node();
                    neighbor.val = neighborValue;
                    nodes.put(neighborValue, neighbor);
                }

                neighbors.add(neighbor);
            }
        }
        
        return nodes.get(node.val);
    }


    // 238. Product of Array Except Self - https://leetcode.com/problems/product-of-array-except-self/

    public int[] productExceptSelf(int[] nums) {
        if ((nums == null) || (nums.length < 1)) {
            return nums;
        }
        
        int[] answer = new int[nums.length];
        answer[0]    = 1;
        
        // Accumulate from the left
        for (int i = 1, L = nums[0]; i < nums.length; i++) {
            answer[i] = L;
            L        *= nums[i];
        }

        // Accumulate from the right
        for (int i = nums.length - 1, R = 1; i >= 0; i--) {
            answer[i] *= R;
            R         *= nums[i];
        }

        return answer;
    }

    // 229. Majority Element II - https://leetcode.com/problems/majority-element-ii/

    public List<Integer> majorityElement(int[] nums) {
        
        int a1 = 0, a2 = 0, n1 = 0, n2 = 0;
        
        for (int i : nums) {
            if (((n1 == 0) && (i != a2)) || (a1 == i)) {
                a1 = i;
                n1++;
            } else if ((n2 == 0) || (a2 == i)) {
                a2 = i;
                n2++;
            } else {
                n1--;
                n2--;
            }
        }
        
        List<Integer> result = new ArrayList<>(2);

        if (n1 > (nums.length/3)) { result.add(a1); }
        if (n2 > (nums.length/3)) { result.add(a2); }

        if (result.size() > 1) {
            return result;
        }
        
        n1 = 0;
        n2 = 0;
        
        for (int i : nums) {
            if (a1 == i) {
                n1++;
            } else if (a2 == i) {
                n2++;
            }
        }

        result.clear();

        if (n1 > (nums.length/3)) { result.add(a1); }
        if (n2 > (nums.length/3)) { result.add(a2); }
        
        return result;
    }

    // 54. Spiral Matrix - https://leetcode.com/problems/spiral-matrix/
    
    public List<Integer> spiralOrder(int[][] matrix) {
        if ((matrix == null) || (matrix.length < 1)) {
            return Collections.emptyList();
        }       
        
        int m = matrix.length;
        int n = matrix[0].length;
        boolean isVertical = (m > n);
        int layers = (isVertical ? n/2 : m/2);
        
        List<Integer> result = new ArrayList(m * n);
        
        for (int layer = 0, row = 0, column = 0; layer < layers; layer++) {
            row = layer;
            
            for (column = layer; column < (n - 1 - layer); column++) {
                result.add(matrix[row][column]);
            }
            
            column = n - 1 - layer;
            
            for (row = layer; row < (m - 1 - layer); row++) {
                result.add(matrix[row][column]);
            }

            row = m - 1 - layer;
            
            for (column = n - 1 - layer; column > layer; column--) {
                result.add(matrix[row][column]);
            }
            
            column = layer;
            
            for (row = m - 1 - layer; row > layer; row--) {
                result.add(matrix[row][column]);
            }            
        }
        
        if (isVertical && ((n & 1) == 1)) {
            int column = layers; // n/2 
            for (int row = layers; row <= (m - 1 - layers); row++) {
                result.add(matrix[row][column]);
            }                                                                        
        }
        
        if ((!isVertical) && ((m & 1) == 1)) {
            int row = layers; // m/2 
            for (int column = layers; column <= (n - 1 - layers); column++) {
                result.add(matrix[row][column]);
            }                                                        
        }

        return result;
    }
    
    // 49. Group Anagrams - https://leetcode.com/problems/group-anagrams/submissions/
    
    private int[] primes = new int[]{2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
    
    private Integer signature(String s) {
        int signature = 1;
        
        for (int j = 0; j < s.length(); j++) {
            signature *= primes[s.charAt(j) - 'a'];
        }        

        return signature;
    }
    
    public List<List<String>> groupAnagrams(String[] strs) {
        if ((strs == null) || (strs.length < 1)) {
            return Collections.emptyList();
        }

        Map<Integer, List<String>> map = new HashMap();

        for (String s : strs) {
            Integer signature = signature(s);
            List<String> l = map.get(signature);
            if (l == null) {
                l = new ArrayList<>();
                map.put(signature, l);
            }
            l.add(s);
        }
        
        return new ArrayList(map.values());
    }
 
    // 18. 4Sum - https://leetcode.com/problems/4sum/
 
    private boolean isFound(int[] nums, int start, int end, int num) {
        if (start >= end) {
            return ((nums[start] == num) ? true : false);
        }
        
        int middle = start + ((end - start) / 2);
        return (num == nums[middle]) ? true : 
               (num <  nums[middle]) ? isFound(nums, start, middle - 1, num) : 
                                       isFound(nums, middle + 1, end, num); 
    }
    
    public List<List<Integer>> fourSum2(int[] nums, int target) {
        if ((nums == null) || (nums.length < 4)) {
            return Collections.emptyList();
        }       
        
        Arrays.sort(nums);
        
        List<List<Integer>> result = new ArrayList();
        
        int n = nums.length;
        
        for (int i = 0; i < (n - 3); i++) {
            if ((i > 0) && (nums[i] == nums[i-1])) { continue; }
            
            for (int j = i+1; j < (n - 2); j++) {
                if ((j > (i+1)) && (nums[j] == nums[j-1])) { continue; } 
                
                for (int k = j+1; k < (n - 1); k++) {
                    if ((k > (j+1)) && (nums[k] == nums[k-1])) { continue; }
                    
                    int m = target - nums[i] - nums[j] - nums[k];
                    
                    if ((nums[k+1] <= m) && (nums[n-1] >= m)) {
                        if (isFound(nums, k+1, n-1, m)) {
                            result.add(Arrays.asList(nums[i], nums[j], nums[k], m));                           
                        }
                    }                    
                }
            }
        }
        
        return result;
    }
    
    public List<List<Integer>> fourSum1(int[] nums, int target) {
        if ((nums == null) || (nums.length < 4)) {
            return Collections.emptyList();
        }       
        
        Arrays.sort(nums);
        
        List<List<Integer>> result = new ArrayList();
        
        for (int i = 0; i < (nums.length - 3); i++) {
            if ((i > 0) && (nums[i] == nums[i-1])) { continue; } 
            for (int j = i+1; j < (nums.length - 2); j++) {
                if ((j > (i+1)) && (nums[j] == nums[j-1])) { continue; } 
                
                for (int left = j + 1, right = nums.length - 1; left < right;) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right]; 
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));                           
                        left++;
                        for (; (left < right) && (nums[left] == nums[left-1]); left++);                        
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        
        return result;
    }
    
    // 16. 3Sum Closest - https://leetcode.com/problems/3sum-closest/
    
    public int threeSumClosest(int[] nums, int target) {
        if ((nums == null) || (nums.length < 3)) {
            return 0;
        }
        
        Arrays.sort(nums);
        
        int delta = Integer.MAX_VALUE;
        int result = 0;
        
        for (int j = 0; j < (nums.length - 2); j++) {
            for (int left = j + 1, right = nums.length - 1; left < right; ) {
                int sum = nums[j] + nums[left] + nums[right];
                if (sum == target) {
                    return sum;
                }
                int d = Math.abs(sum - target); 
                if (d < delta) {
                    delta = d;
                    result = sum;
                }
                if (sum > target) {
                    right--;
                } else {
                    left++;
                }
            }
 
        }
        
        return result;
    }
    
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
    
    // 159. Longest Substring with At Most Two Distinct Characters - https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
    
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        
        if (s == null) {
            return 0;
        }
        
        if (s.length() < 3) {
            return s.length();
        }
        
        int[] counters = new int[128];
        int   result   = 0;
        
        for(int size = 0, left = 0, right = 0; right < s.length(); right++){
            int ch = s.charAt(right);
            
            if ((counters[ch]++) == 0) { size++; }
                        
            while (size > 2) {
                ch = s.charAt(left++);
                if((--counters[ch]) == 0) { size--; }
            }
            
            result = Math.max(result, right - left + 1);
        }
        
        return result;
    }
    
    // 3. Longest Substring Without Repeating Characters
    
    public int lengthOfLongestSubstring3(String s) {
        int result = 0;
        int[] index = new int[128]; // Last character's index
        
        // Extendint the [left, right] range
        for (int right = 0, left = 0; right < s.length(); right++) {
            int ch    = s.charAt(right);
            left      = Math.max(index[ch], left);
            result    = Math.max(result, right - left + 1);
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