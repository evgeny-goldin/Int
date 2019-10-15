import unittest

class Chapter03(unittest.TestCase):

    def find_duplicates_06(self, A):
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
    
    def test_find_duplicates_06(self):
        self.assertEqual(self.find_duplicates_06([]), [])
        self.assertEqual(self.find_duplicates_06([0, 1]), [])
        self.assertEqual(self.find_duplicates_06([0, 0]), [0])
        self.assertEqual(self.find_duplicates_06([3, 0, 2, 1]), [])
        self.assertEqual(self.find_duplicates_06([3, 0, 2, 1, 3]), [3])
        self.assertEqual(self.find_duplicates_06([3, 0, 2, 1, 3, 1]), [1, 3])
        self.assertEqual(self.find_duplicates_06([1, 0, 2, 1, 3, 3]), [1, 3])
        self.assertEqual(self.find_duplicates_06([0, 1, 1, 1, 0, 1]), [0, 1])
    
    
if __name__ == '__main__':
    unittest.main()