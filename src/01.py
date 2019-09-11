import unittest

class TestBinarySearch(unittest.TestCase):

    # 1.2
    def first_appearance(self, A, k, left = 0, right = -1):

      if  right == -1:
        right = len(A) - 1

      median = self.binary_search(A, k, left, right)

      if median < 1 or A[median - 1] != k:
        return median

      return self.first_appearance(A, k, 0, median - 1)

    # 1.3
    def first_larger(self, A, k, left = 0, right = -1):

      if  right == -1:
        right = len(A) - 1

      median = self.binary_search(A, k, left, right)

      if median < 0 or median == right:
        return -1

      if A[median + 1] != k:
        return median + 1

      return self.first_larger(A, k, median + 1, right)

    # 1.4 - finds j such that A[j] = j
    def find_equal_index(self, A):

      left  = 0
      right = len(A) - 1

      while left <= right:
        if A[left] > right or A[right] < left:
          return -1
        median = int(left + ((right - left) / 2))
        if A[median] < median:
          left = median + 1
        elif A[median] == median:
          return median
        else:
          right = median - 1

      return -1


    # Finds an index of the k in A, or -1
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

    def test_first_appearance_missing(self):
        self.assertEqual(self.first_appearance([], 0), -1)
        self.assertEqual(self.first_appearance([], 10), -1)
        self.assertEqual(self.first_appearance([1, 2, 3], 4), -1)
        self.assertEqual(self.first_appearance([1, 2, 3, 3, 4], 5), -1)
        self.assertEqual(self.first_appearance([1, 2, 3, 3, 4], -5), -1)

    def test_first_larger_missing(self):
        self.assertEqual(self.first_larger([], 0), -1)
        self.assertEqual(self.first_larger([], 10), -1)
        self.assertEqual(self.first_larger([0], 0), -1)
        self.assertEqual(self.first_larger([0], 1), -1)
        self.assertEqual(self.first_larger([1, 2, 3, 4], 4), -1)
        self.assertEqual(self.first_larger([1, 2, 3, 3, 4], 5), -1)
        self.assertEqual(self.first_larger([1, 2, 3, 4, 5], 5), -1)
        self.assertEqual(self.first_larger([1, 2, 3, 3, 4, 5], 5), -1)
        self.assertEqual(self.first_larger([1, 2, 3, 3, 4], -5), -1)

    def test_find_equal_index_missing(self):
        self.assertEqual(self.find_equal_index([]), -1)
        self.assertEqual(self.find_equal_index([1]), -1)
        self.assertEqual(self.find_equal_index([10]), -1)
        self.assertEqual(self.find_equal_index([-1]), -1)
        self.assertEqual(self.find_equal_index([-1, 3, 5, 10]), -1)

    def test_find_equal_index(self):
        self.assertEqual(self.find_equal_index([0]), 0)
        self.assertEqual(self.find_equal_index([-1, 1, 2]), 1)
        self.assertEqual(self.find_equal_index([-1, 0, 2]), 2)
        self.assertEqual(self.find_equal_index([-1, 0, 1, 3]), 3)
        self.assertEqual(self.find_equal_index([-1, 0, 1, 3, 5, 6]), 3)
        self.assertEqual(self.find_equal_index([-1, 0, 1, 2, 4]), 4)
        self.assertEqual(self.find_equal_index([-1, 0, 1, 2, 4, 7]), 4)
        self.assertEqual(self.find_equal_index([-10, -5, -3, -2, 0, 2, 4, 7]), 7)
        self.assertEqual(self.find_equal_index([-10, -5, -3, -2, 0, 2, 4, 7, 10, 15, 25]), 7)

    def test_first_appearance_single(self):
        self.assertEqual(self.first_appearance([0], 0), 0)
        self.assertEqual(self.first_appearance([1], 1), 0)
        self.assertEqual(self.first_appearance([0, 1], 0), 0)
        self.assertEqual(self.first_appearance([0, 1], 1), 1)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 1), 0)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 2), 1)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 3), 2)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 4), 3)
        self.assertEqual(self.first_appearance([1, 2, 3, 4, 5], 5), 4)

    def test_first_larger_single(self):
        self.assertEqual(self.first_larger([0, 1], 0), 1)
        self.assertEqual(self.first_larger([1, 2], 1), 1)
        self.assertEqual(self.first_larger([1, 2, 3, 4, 5], 1), 1)
        self.assertEqual(self.first_larger([1, 2, 3, 4, 5], 2), 2)
        self.assertEqual(self.first_larger([1, 2, 3, 4, 5], 3), 3)
        self.assertEqual(self.first_larger([1, 2, 3, 4, 5], 4), 4)

    def test_first_appearance_multiple(self):
        self.assertEqual(self.first_appearance([0, 0, 0, 1], 0), 0)
        self.assertEqual(self.first_appearance([0, 0, 0, 1, 1, 1, 1], 1), 3)
        self.assertEqual(self.first_appearance([0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2], 1), 3)
        self.assertEqual(self.first_appearance([0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2], 2), 7)
        self.assertEqual(self.first_appearance([1, 1, 1, 2, 3, 4, 5], 1), 0)
        self.assertEqual(self.first_appearance([1, 2, 2, 2, 2, 2, 2, 3, 4, 5], 2), 1)
        self.assertEqual(self.first_appearance([1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 4, 5], 2), 5)
        self.assertEqual(self.first_appearance([1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 5], 4), 12)

    def test_first_larger_multiple(self):
        self.assertEqual(self.first_larger([0, 0, 0, 1], 0), 3)
        self.assertEqual(self.first_larger([0, 0, 0, 1, 1, 1, 1], 1), -1)
        self.assertEqual(self.first_larger([0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2], 1), 7)
        self.assertEqual(self.first_larger([0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2], 2), -1)
        self.assertEqual(self.first_larger([1, 1, 1, 2, 3, 4, 5], 1), 3)
        self.assertEqual(self.first_larger([1, 2, 2, 2, 2, 2, 2, 3, 4, 5], 2), 7)
        self.assertEqual(self.first_larger([1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 4, 5], 2), 11)
        self.assertEqual(self.first_larger([1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 5], 4), 17)

if __name__ == '__main__':
    unittest.main()