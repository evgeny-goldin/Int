import unittest

class Chapter03(unittest.TestCase):

    # Question 6
    def find_duplicates(self, A):
        duplicates = set([])
        for j in range(0, len(A)):
            duplicate_found = False
            while (not duplicate_found) and (A[j] != j):
                current = A[j]
                if A[current] == current:
                    duplicate_found = True
                    duplicates.add(current)
                else:
                    A[j] = A[current]
                    A[current] = current
        return sorted(list(duplicates))
        
    # Question 7    
    def find_number1(self, A, k):
        columns = len(A[0])
        rows    = len(A)
        left    = 0
        right   = (rows * columns) - 1
        
        while left <= right:
            median = left + int((right - left) / 2)
            number = A[int(median % rows)][int(median % columns)]
            if number < k:
                left = median + 1
            elif number == k:
                return True
            else:
                right = median - 1
                
        return False

    # Question 8
    def find_number2(self, A, k, y, x):
        if (x < 0) or (y >= len(A)):
            return False

        corner_number = A[y][x]

        if corner_number < k:
            return self.find_number2(A, k, y + 1, x)
        elif corner_number == k:
            return True
        else:
            return self.find_number2(A, k, y, x - 1)

    # Question 9
    def replace_blanks(self, A):
        blanks = 0

        for j in range(0, len(A)):
            if A[j] == ' ':
                blanks += 1

        if blanks == 0:
            return A

        pointer = len(A) - 1 - (blanks * 2)
        while pointer > -1:
            if A[pointer] == ' ':
                A[pointer + (blanks * 2)]     = '0'
                A[pointer + (blanks * 2) - 1] = '2'
                A[pointer + (blanks * 2) - 2] = '%'
                blanks -= 1
                if blanks == 0:
                    return A
            else:
                A[pointer + (blanks * 2)] = A[pointer]
            pointer -= 1

    # Question 10
    def merge_arrays(self, A1, A2):
        a1 = len(A1) - 1
        a2 = len(A2) - 1
        current = a1

        for j in reversed(range(0, len(A1))):
            if A1[j] == -1:
                a1 -= 1

        while (a1 >= 0) or (a2 >= 0):
            if  (a1 < 0) or (a2 < 0):
                A1[current] = A2[a2] if (a1 < 0) else A1[a1]
                a1 -= 1
                a2 -= 1
                current -= 1
            elif A1[a1] > A2[a2]:
                A1[current] = A1[a1]
                a1 -= 1
                current -= 1
            elif A1[a1] == A2[a2]:
                A1[current]     = A1[a1]
                A1[current - 1] = A2[a2]
                a1 -= 1
                a2 -= 1
                current -= 2
            else:
                A1[current] = A2[a2]
                a2 -= 1
                current -= 1

        if current != -1:
            raise Exception("Current: {} != -1".format(current))

        return A1

    # Question 12
    def is_number(self, S, allowed):

        if  len(S) < 1:
            return True

        char = S[0]

        if char not in allowed:
            return False

        reminder = S[1:]

        if (char == '+') or (char == '-'):
            allowed.remove('+')
            allowed.remove('-')
            return self.is_number(reminder, allowed)

        if char == '.':
            allowed.remove('.')
            return self.is_number(reminder, allowed)

        if (char == 'e') or (char == 'E'):
            allowed.remove('e')
            allowed.remove('E')
            allowed.remove('+')
            allowed.remove('.')
            allowed.append('-')
            return False if len(reminder) == 0 else self.is_number(reminder, allowed)

        # A digit
        return self.is_number(reminder, allowed)

    def test_find_duplicates(self):
        self.assertEqual(self.find_duplicates([]), [])
        self.assertEqual(self.find_duplicates([0, 1]), [])
        self.assertEqual(self.find_duplicates([0, 0]), [0])
        self.assertEqual(self.find_duplicates([3, 0, 2, 1]), [])
        self.assertEqual(self.find_duplicates([3, 0, 2, 1, 3]), [3])
        self.assertEqual(self.find_duplicates([3, 0, 2, 1, 3, 1]), [1, 3])
        self.assertEqual(self.find_duplicates([1, 0, 2, 1, 3, 3]), [1, 3])
        self.assertEqual(self.find_duplicates([0, 1, 1, 1, 0, 1]), [0, 1])
    
    def test_find_number(self):
        self.assertFalse(self.find_number1([[1]], 0))
        
        self.assertTrue(self.find_number1([[1]], 1))
        self.assertTrue(self.find_number1([[1], [2], [3]], 1))
        self.assertTrue(self.find_number1([[1], [2], [3]], 2))
        self.assertTrue(self.find_number1([[1], [2], [3]], 3))
        
        self.assertTrue(self.find_number1([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 1))
        self.assertTrue(self.find_number1([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 9))
        self.assertTrue(self.find_number1([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 17))
        
        self.assertTrue(self.find_number1([[1, 3], [7, 9], [13, 15]], 1))
        self.assertTrue(self.find_number1([[1, 3], [7, 9], [13, 15]], 9))
        self.assertTrue(self.find_number1([[1, 3], [7, 9], [13, 15]], 15))
        
        self.assertFalse(self.find_number1([[1], [7], [13]], 10))
        self.assertFalse(self.find_number1([[1, 3], [7, 9], [13, 15]], 12))
        self.assertFalse(self.find_number1([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 100))
        
    def test_find_number2(self):
        matrix1 = [[1, 2], [2, 3]]
        matrix2 = [[1, 2, 8, 9], [2, 4, 9, 12], [4, 7, 10, 13], [6, 8, 11, 15]]

        self.assertFalse(self.find_number2([[1]], 0, 0, 0))
        self.assertFalse(self.find_number2(matrix1, 0, 0, 1))
        self.assertFalse(self.find_number2(matrix1, 5, 0, 1))

        self.assertTrue(self.find_number2(matrix1, 1, 0, 1))
        self.assertTrue(self.find_number2(matrix1, 2, 0, 1))
        self.assertTrue(self.find_number2(matrix1, 3, 0, 1))

        self.assertTrue(self.find_number2(matrix2, 1, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 2, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 4, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 6, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 7, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 8, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 9, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 10, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 11, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 12, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 13, 0, 3))
        self.assertTrue(self.find_number2(matrix2, 15, 0, 3))

        self.assertFalse(self.find_number2(matrix2, 0, 0, 3))
        self.assertFalse(self.find_number2(matrix2, 3, 0, 3))
        self.assertFalse(self.find_number2(matrix2, 5, 0, 3))
        self.assertFalse(self.find_number2(matrix2, 14, 0, 3))
        self.assertFalse(self.find_number2(matrix2, 20, 0, 3))
        self.assertFalse(self.find_number2(matrix2, 100, 0, 3))

    def test_replace_blanks(self):
        self.assertEqual(self.replace_blanks([]), [])
        self.assertEqual(self.replace_blanks([' ', '', '']), ['%', '2', '0'])
        self.assertEqual(self.replace_blanks([' ', ' ', '', '', '', '']), ['%', '2', '0', '%', '2', '0'])
        self.assertEqual(self.replace_blanks(['A']), ['A'])
        self.assertEqual(self.replace_blanks(['A', ' ', 'B', '', '']), ['A', '%', '2', '0', 'B'])
        self.assertEqual(self.replace_blanks([' ', 'A', ' ', 'B', ' ', '', '', '', '', '', '']),
                                             ['%', '2', '0', 'A', '%', '2', '0', 'B', '%', '2', '0'])
        self.assertEqual(self.replace_blanks(['W', 'E', ' ', 'A', 'R', 'E', ' ', 'H', 'A', 'P', 'P', 'Y', '', '', '', '']),
                                             ['W', 'E', '%', '2', '0', 'A', 'R', 'E', '%', '2', '0', 'H', 'A', 'P', 'P', 'Y'])

    def test_merge_arrays(self):
        self.assertEqual(self.merge_arrays([], []), [])
        self.assertEqual(self.merge_arrays([0], []), [0])
        self.assertEqual(self.merge_arrays([10], []), [10])
        self.assertEqual(self.merge_arrays([-1], [11]), [11])
        self.assertEqual(self.merge_arrays([1, -1], [8]), [1, 8])
        self.assertEqual(self.merge_arrays([8, -1], [1]), [1, 8])
        self.assertEqual(self.merge_arrays([1, 3, 7, -1], [2]), [1, 2, 3, 7])
        self.assertEqual(self.merge_arrays([1, 3, 7, -1], [10]), [1, 3, 7, 10])
        self.assertEqual(self.merge_arrays([1, 3, 7, -1, -1, -1, -1], [0, 2, 5, 100]), [0, 1, 2, 3, 5, 7, 100])
        self.assertEqual(self.merge_arrays([1, -1, -1, -1, -1], [0, 2, 3, 7]), [0, 1, 2, 3, 7])
        self.assertEqual(self.merge_arrays([1, 7, 9, 10, -1, -1, -1, -1], [2, 3, 5, 8]), [1, 2, 3, 5, 7, 8, 9, 10])

    def test_is_number(self):
        allowed = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '.', 'e', 'E']
        self.assertTrue(self.is_number("0", allowed.copy()))
        self.assertTrue(self.is_number("012345", allowed.copy()))
        self.assertTrue(self.is_number("012345.4567", allowed.copy()))
        self.assertTrue(self.is_number("100.", allowed.copy()))
        self.assertTrue(self.is_number("+100.", allowed.copy()))
        self.assertTrue(self.is_number("-100.", allowed.copy()))
        self.assertTrue(self.is_number("-100.23", allowed.copy()))
        self.assertTrue(self.is_number("5e2", allowed.copy()))
        self.assertTrue(self.is_number(".123", allowed.copy()))
        self.assertTrue(self.is_number("-.123", allowed.copy()))
        self.assertTrue(self.is_number("3.1416", allowed.copy()))
        self.assertFalse(self.is_number("12e", allowed.copy()))
        self.assertFalse(self.is_number("1a3.14", allowed.copy()))
        self.assertFalse(self.is_number("1.2.3", allowed.copy()))
        self.assertFalse(self.is_number("+-5", allowed.copy()))
        self.assertFalse(self.is_number("12e+5.4", allowed.copy()))

    
if __name__ == '__main__':
    unittest.main()