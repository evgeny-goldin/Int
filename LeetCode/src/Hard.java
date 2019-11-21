class Solution {

    // 76. Minimum Window Substring - https://leetcode.com/problems/minimum-window-substring/
    
    public String minWindow(String s, String t) {
        
        int[] counters = new int[128];
        
        for (char a : t.toCharArray()) {
            counters[a]++;
        }

        int lettersNeeded = t.length(), left = 0, right = 0, start = -1, window = Integer.MAX_VALUE;
        
        while (right < s.length()) {
          if ((--counters[s.charAt(right++)]) > -1) {
              lettersNeeded--;
          }
            
          while (lettersNeeded == 0) {
            if ((right - left) < window) {
              start = left;
              window = right - left;
            }
            if ((++counters[s.charAt(left++)]) == 1) {
                lettersNeeded++;
            }
          }
        }
        
        return ((start == -1) ? "" : s.substring(start, start + window));
    }
}