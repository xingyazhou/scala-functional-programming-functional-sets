package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*
  import munit.Clue.generate

  test("contains is implemented") {
    assert(contains(x => true, 100))
    assert(contains((x:Int) => x < 0, -100))
    assert(!contains((x:Int) => x < 0, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val s4 = Set(1, 2, 3, 4, 5)
    val s5 = Set(3, 5, 6, 7, 8)
    val s6 = Set(6, 8, 9, 1000, 2000)

    val s7 = Set(-1, -2, -3, -4, -5)
    val s8 = Set(-3, -2, -1, 1, 2, 3)

    val s9 = Set(1, 2, 3, 4, 5)
    val s10 = Set(2, 4, 6, 8, 10)
    val s11 = Set(1, 4, 9, 16, 25)

  /**
   * This test is currently disabled (by using .ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */

  test("singleton set one contains one") {
      {
        /**
         * We create a new instance of the "TestSets" trait, this gives us access
         * to the values "s1" to "s3".
         */
        new TestSets:
          /**
           * The string argument of "assert" is a message that is printed in case
           * the test fails. This helps identifying which assertion failed.
           */
          assert(contains(s1, 1), "Singleton one set contains 1")
          assert(!contains(s1, 2), "Singleton set one not contains 2")
      }
    }

  /**
   * Test: union
   */
  test("union contains all elements of each set") {
    new TestSets:
      val su1 = union(s1, s2)
      assert(contains(su1, 1), "Union of {1} and {2} contains {1}")
      assert(contains(su1, 2), "Union of {1} and {2} contains {2}")
      assert(!contains(su1, 3), "Union of {1} and {2} NOT contains {3}")

      val su2 = union(s3, s4)
      assert(contains(su2, 5), "Union of {3} and {1, 2, 3, 4, 5} contains {5}")
      assert(contains(su2, 3), "Union of {3} and {1, 2, 3, 4, 5} contains {3}")
      assert(!contains(su2, 6), "Union of {3} and {1, 2, 3, 4, 5} NOT contains {5}")
  }

  /**
   * Test: intersect
   */
  test("intersect contains elements in both sets") {
    new TestSets:
      val si1 = intersect(s1,s4)
      assert(contains(si1, 1), "Intersect of {1} and {1,2,3,4,5} contains {1}")
      assert(!contains(si1, 2), "Intersect of {1} and {1,2,3,4,5} NOT contains {2}")

      val si2 = intersect(s4,s5)
      assert(contains(si2, 3), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} contains {3}")
      assert(contains(si2, 5), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} contains {5}")
      assert(!contains(si2, 1), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {1}")
      assert(!contains(si2, 2), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {2}")
      assert(!contains(si2, 4), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {4}")
      assert(!contains(si2, 6), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {6}")
      assert(!contains(si2, 7), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {7}")
      assert(!contains(si2, 8), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {8}")
      assert(!contains(si2, 9), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {9}")
  }

  /**
   * Test: diff
   */
  test("diff contains elements in first Set not in second Set") {
    new TestSets:
      val d1 = diff(s4,s5)
      assert(contains(d1, 1), "Diff of {1,2,3,4,5} and {3,5,6,7,8} contains {1}")
      assert(contains(d1, 2), "Diff of {1,2,3,4,5} and {3,5,6,7,8} contains {2}")
      assert(contains(d1, 4), "Diff of {1,2,3,4,5} and {3,5,6,7,8} contains {4}")
      assert(!contains(d1, 3), "Diff of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {3}")
      assert(!contains(d1, 5), "Diff of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {5}")
      assert(!contains(d1, 6), "Diff of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {6}")
      assert(!contains(d1, 9), "Diff of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {9}")
  }

  /**
   * Test: filter
   *
   */
  test("filter contains elements in both sets") {
    new TestSets:
      val sf1 = filter(s1, s4)
      assert(contains(sf1, 1), "Intersect of {1} and {1,2,3,4,5} contains {1}")
      assert(!contains(sf1, 2), "Intersect of {1} and {1,2,3,4,5} NOT contains {2}")

      val sf2 = intersect(s4, s5)
      assert(contains(sf2, 3), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} contains {3}")
      assert(contains(sf2, 5), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} contains {5}")
      assert(!contains(sf2, 1), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {1}")
      assert(!contains(sf2, 2), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {2}")
      assert(!contains(sf2, 4), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {4}")
      assert(!contains(sf2, 6), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {6}")
      assert(!contains(sf2, 7), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {7}")
      assert(!contains(sf2, 8), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {8}")
      assert(!contains(sf2, 9), "Intersect of {1,2,3,4,5} and {3,5,6,7,8} NOT contains {9}")
  }

  /**
   * Test: forall
   */
  test("forall test suit") {
    new TestSets {
      assert(forall(s4,  (a: Int) => a > 0 ), "forall of {1, 2, 3, 4, 5} satisfy condition x > 0")
      assert(!forall(s4,  (a: Int) => a > 1 ), "forall of {1, 2, 3, 4, 5} NOT satisfy condition x > 1")
      assert(forall(s7,  (a: Int) => a < 0 ), "forall of {-1, -2, -3, -4, -5} satisfy condition x < 0")
      assert(!forall(s7,  (a: Int) => a > -5 ), "forall of {-1, -2, -3, -4, -5} NOT satisfy condition x > -5")
    }
  }

  /**
   * Test: exists
   */
  test("exists test suit") {
    new TestSets {
      assert(exists(s4,  (a: Int) => a > 4 ), "exists of {1,2,3,4,5} satisfy condition x > 4")
      assert(!exists(s4,  (a: Int) => a > 5 ), "exists of {1,2,3,4,5} NOT satisfy condition x > 5")
      assert(exists(s7, (a: Int) => a < 0), "exists of {-1, -2, -3, -4, -5} satisfy condition x < 0")
      assert(exists(s7, (a: Int) => a < -4), "exist of {-1, -2, -3, -4, -5} satisfy condition x < -4")
      assert(!exists(s7, (a: Int) => a < -5), "exist of {-1, -2, -3, -4, -5} NOT satisfy condition x < -5")
    }
  }

  /**
   * Test: map
   */
  test("map test suit") {
    new TestSets {
      val ms = map(s9, x => x * 3 )
      assert(exists(s9, ms), "Map valid since 2 and 4 in {1, 2, 3, 4, 5} exists in {2, 4, 6, 8, 10}")
      assert(!forall(s9, ms), "Map valid since 1, 3, 5 {1, 2, 3, 4, 5} NOT in in {2, 4, 6, 8, 10}")
      assert(exists(ms, (a: Int) => a % 2 == 0))
      assert(!forall(ms, (a: Int) => a % 2 == 0),"forall ...")

      assert(forall(Set(3,6,9,12,15), ms), "Map valid since {3,6,9,12,15} is in {3,6,9,12,15}")
      assert(!exists(Set(11), ms), "Map valid since {3,6,9,12,15} is in {3,6,9,12,15}")
    }
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
