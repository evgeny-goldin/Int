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

    
if __name__ == '__main__':
    unittest.main()