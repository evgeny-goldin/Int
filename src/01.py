import unittest

class TestBinarySearch(unittest.TestCase):

    @staticmethod
    def first_appearance(A, k):
      left  = 0
      right = len(A) - 1

      while left <= right:
        middle = int(left + ((right - left) / 2))

        if A[middle] < k:
          left = middle + 1
        elif A[middle] == k:
          while middle > -1 and A[middle] == k:
            middle = middle - 1
          return middle + 1
        else:
          right = middle - 1

      return -1

    def test_missing(self):
        self.assertEqual(self.first_appearance([], 0), -1)
        self.assertEqual(self.first_appearance([], 10), -1)
        self.assertEqual(self.first_appearance([1, 2, 3], 4), -1)
        self.assertEqual(self.first_appearance([1, 2, 3, 3, 4], 5), -1)
        self.assertEqual(self.first_appearance([1, 2, 3, 3, 4], -5), -1)

    def test_single_appearance(self):
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 1), 0)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 2), 1)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 3), 2)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 4), 3)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 5), 4)

    def test_multiple_appearances(self):
        self.assertEqual(self.first_appearance([1, 1, 1, 2, 3, 4, 5], 1), 0)
        self.assertEqual(self.first_appearance([1, 2, 2, 2, 2, 2, 2, 3, 4, 5], 2), 1)
        self.assertEqual(self.first_appearance([1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 4, 5], 2), 5)
        self.assertEqual(self.first_appearance([1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 5], 4), 12)

if __name__ == '__main__':
    unittest.main()