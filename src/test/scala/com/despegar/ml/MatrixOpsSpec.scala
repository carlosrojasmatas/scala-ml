package com.despegar.ml

import com.despegar.ml.classification.Preamble
import org.scalatest._

/**
  * Created by charly on 7/29/15.
 */
class MatrixOpsSpec extends FlatSpec with Matchers{
  import Preamble._

  val X:DoubleMatrix = List(List(4d,8d,12d),List(3d,4d,9d),List(20d,8d,14d))
  val Y:DoubleMatrix = List(List(9d,18d,142d),List(34d,49d,94d),List(20d,81d,4d))
  val Z:DoubleMatrix = List(List(8d,12d),List(4d,9d),List(8d,14d))
  val K:DoubleMatrix = List(List(18d,142d),List(49d,94d),List(81d,4d))

  "A square matrix " should "substract another matrix"  in{

    val E:DoubleMatrix = List(List(-5d,-10d,-130d),List(-31d,-45d,-85d),List(0d,-73d,10d))
    val R = X.substract(Y)
    R should be (E)

    val E2 = List(List(5d,10d,130d),List(31d,45d,85d),List(0d,73d,-10d))
    val R2 = Y.substract(X)
    R2 should be (E2)
  }

  it should "divide another matrix" in {
    val E:DoubleMatrix = List(List(4d/9d,8d/18d,12d/142d),List(3d/34d,4d/49d,9d/94d),List(1d,8d/81d,14d/4d))
    val R = X.divide(Y)
    R should be (E)

    val E2 = List(List(9d/4d,18d/8d,142d/12d),List(34d/3d,49d/4d,94d/9d),List(1d,81d/8d,4d/14d))
    val R2 = Y.divide(X)
    R2 should be (E2)

  }

  it should "retrieve the max value per feature" in {
    X maxPerFeature() should be(List(20d,8d,14d))
    Y maxPerFeature() should be(List(34d,81d,142d))
  }

  it should "retrieve the min value per feature" in {
    X minPerFeature() should be(List(3d,4d,9d))
    Y minPerFeature() should be(List(9d,18d,4d))
  }


  "A non square matrix " should "substract another matrix"  in{

    val E:DoubleMatrix = List(List(-10d,-130d),List(-45d,-85d),List(-73d,10d))
    val R = Z.substract(K)
    R should be (E)

    val E2 = List(List(10d,130d),List(45d,85d),List(73d,-10d))
    val R2 = K.substract(Z)
    R2 should be (E2)
  }

  it should "divide another matrix" in {
    val E:DoubleMatrix = List(List(8d/18d,12d/142d),List(4d/49d,9d/94d),List(8d/81d,14d/4d))
    val R = Z.divide(K)
    R should be (E)

    val E2 = List(List(18d/8d,142d/12d),List(49d/4d,94d/9d),List(81d/8d,4d/14d))
    val R2 = K.divide(Z)
    R2 should be (E2)

  }

  "A fill operation " should " create a matrix with the same row from a give vector" in {
    val v:DoubleVector = List(4d,8d,7d)
    val v2:DoubleVector = List(4d,8d)
    val k  = 3
    val R:DoubleMatrix = List(List(4d,8d,7d),List(4d,8d,7d),List(4d,8d,7d))
    val R2:DoubleMatrix = List(List(4d,8d),List(4d,8d),List(4d,8d))

    fillMatrix(v,k) should be (R)
    fillMatrix(v2,k) should be (R2)
  }

  "euclideanDistance() " should " compute the distance betwee a vector and a dataset" in {
    val obs = List(5d,18d,9d)
    val rs = euclidianDistance(obs,X)
    println(rs)
  }


}
