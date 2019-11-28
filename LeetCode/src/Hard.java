class Solution {
    
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