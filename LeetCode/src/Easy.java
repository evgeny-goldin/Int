import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Easy {

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
    

}
