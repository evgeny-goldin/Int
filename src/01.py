import unittest

class TestBinarySearch(unittest.TestCase):

    def first_appearance(self, A, k, left = 0, right = -1):

      if  right == -1:
        right = len(A) - 1

      median = self.binary_search(A, k, left, right)

      if median < 1 or A[median - 1] != k:
        return median

      return self.first_appearance(A, k, 0, median - 1)

    def binary_search(self, A, k, left, right):

      if  left < 0 or left > right:
        return -1

      while left <= right:
        middle = int(left + ((right - left) / 2))

        if A[middle] < k:
          left = middle + 1
        elif A[middle] == k:
          return middle
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
        self.assertEqual(self.first_appearance([1], 1), 0)
        self.assertEqual(self.first_appearance([1, 1], 1), 0)
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