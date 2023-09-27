package funsets

object Main extends App:
  import FunSets.*
  println(contains(singletonSet(1), 1))
  println(contains(singletonSet(90), -1000))
  println(contains(x => false, 10890))
  println(contains(x => true, 10890))
  println(contains(singletonSet(30),2))

  println(union(singletonSet(90), singletonSet(-90)))

