class Solution {
    
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