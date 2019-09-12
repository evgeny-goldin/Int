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

    def intersect_arrays(self, A, B):
      a_length = len(A)
      b_length = len(B)

      a = 0
      b = 0
      C = []

      while a < a_length and b < b_length:
        while a < a_length and A[a] < B[b]:
          a += 1
        while a < a_length and b < b_length and B[b] < A[a]:
          b += 1
        if  a < a_length and b < b_length and A[a] == B[b]:
          C.append(A[a])
          a += 1
          b += 1

      return C

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

    def check_arrays_intersect(self, A, B, C):
      self.assertEqual(self.intersect_arrays(A, A), A)
      self.assertEqual(self.intersect_arrays(B, B), B)
      self.assertEqual(self.intersect_arrays(C, C), C)
      self.assertEqual(self.intersect_arrays(A, B), C)
      self.assertEqual(self.intersect_arrays(B, A), C)
      self.assertEqual(self.intersect_arrays(A, C), C)
      self.assertEqual(self.intersect_arrays(B, C), C)

    def test_intersect_arrays(self):
        self.check_arrays_intersect([], [], [])
        self.check_arrays_intersect([0], [1], [])
        self.check_arrays_intersect([0, 1], [-1, 10], [])
        self.check_arrays_intersect([-100, 1, 100], [], [])
        self.check_arrays_intersect([], [-113, 34, 56, 78], [])
        self.check_arrays_intersect([23, 35, 60], [-113, 34, 56, 78], [])
        self.check_arrays_intersect([23, 35, 60], [-113, 34, 35, 56, 60, 78], [35, 60])
        self.check_arrays_intersect([1, 2, 3, 4, 7], [1, 4, 5, 6, 7], [1, 4, 7])
        self.check_arrays_intersect([-1], [-1], [-1])
        self.check_arrays_intersect([0], [0], [0])
        self.check_arrays_intersect([1], [1], [1])
        self.check_arrays_intersect([1, 2, 3], [1, 2], [1, 2])
        self.check_arrays_intersect([1, 2], [1, 2, 3], [1, 2])
        self.check_arrays_intersect([1, 2, 100, 4000], [1, 2, 100, 4000], [1, 2, 100, 4000])
        self.check_arrays_intersect([1, 2, 3, 4000], [1, 2, 100, 4000], [1, 2, 4000])
        self.check_arrays_intersect([1, 2, 100, 4000], [1, 2, 3, 4000], [1, 2, 4000])
        self.check_arrays_intersect([1, 2, 1000, 2000, 3500, 4001, 5005, 10000, 10001], [1, 2, 3, 4000, 10001], [1, 2, 10001])

if __name__ == '__main__':
    unittest.main()
