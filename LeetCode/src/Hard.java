class Solution {

    // 76. Minimum Window Substring - https://leetcode.com/problems/minimum-window-substring/
    
    public String minWindow(String s, String t) {
        
        int[] count = new int[128];
        
        for (char a : t.toCharArray()) {
            count[a]++;
        }

        int lettersNeeded = t.length(), left = 0, right = 0, start = -1, window = Integer.MAX_VALUE;
        
        while (right < s.length()) {
          if ((--count[s.charAt(right++)]) > -1) {
              lettersNeeded--;
          }
            
          while (lettersNeeded == 0) {
            if ((right - left) < window) {
              start = left;
              window = right - left;
            }
            if ((++count[s.charAt(left++)]) == 1) {
                lettersNeeded++;
            }
          }
        }
        
        return ((start == -1) ? "" : s.substring(start, start + window));
    }
}