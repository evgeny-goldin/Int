class Solution {

    // 76. Minimum Window Substring - https://leetcode.com/problems/minimum-window-substring/
    
    public String minWindow(String s, String t) {
        
        int[] count = new int[128];
        
        for (char a : t.toCharArray()) {
            count[a]++;
        }

        int counter = t.length(), left = 0, right = 0;
        int head = -1, window = Integer.MAX_VALUE;
        
        while (right < s.length()) {
          if ((count[s.charAt(right++)]--) > 0) {
              counter--;
          }
            
          while (counter == 0) {
            if ((right - left) < window) {
              head = left;
              window = right - left;
            }
            if ((count[s.charAt(left++)]++) == 0) {
                counter++;
            }
          }
        }
        
        return ((head == -1) ? "" : s.substring(head, head + window));
    }
}