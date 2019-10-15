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
    def find_number(self, A, k):
        N     = len(A[0])
        left  = 0
        right = (N * N) - 1
        
        while left <= right:
            median = left + int((right - left) / 2)
            number = A[int(median / N)][int(median % N)]
            if number < k:
                left = median + 1
            elif number == k:
                return True
            else:
                right = median - 1
                
        return False
            
        
        
    
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
        self.assertFalse(self.find_number([[1]], 0))
        self.assertTrue(self.find_number([[1]], 1))
        self.assertTrue(self.find_number([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 9))
        self.assertTrue(self.find_number([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 17))
        self.assertTrue(self.find_number([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 1))
        self.assertFalse(self.find_number([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 10))
        self.assertFalse(self.find_number([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 12))
        self.assertFalse(self.find_number([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 100))
        
        
    
if __name__ == '__main__':
    unittest.main()