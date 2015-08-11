package com.despegar.ml.classification

/**
 * Created by charly on 7/17/15.
 */

object Preamble {

  type DataSet = List[List[Any]]
  type DoubleVector = List[Double]
  type DoubleMatrix = List[DoubleVector]

  def zipAndMap(x: DoubleVector, y: DoubleVector, op: (Double, Double) => Double): DoubleVector = {
    x zip y map { case (elx, ely) => op(elx, ely) }
  }

  def log(value:Double,base:Int): Double ={
    Math.log(value)/Math.log(base)
  }

  def fillMatrix(in: DoubleVector, rows: Int): DoubleMatrix = {
    Array.fill(rows)(in) toList
  }

  private def sqrDistance(x: Double, y: Double): Double = Math.pow(x - y, 2)

  def euclidianDistance(obs: DoubleVector, dataSet: DoubleMatrix): DoubleVector = {
    val refMatrix = fillMatrix(obs, dataSet.size)
    val coMatrix = refMatrix zip dataSet
    val sqrDistances = coMatrix map { case (vx, vy) => zipAndMap(vx, vy, sqrDistance).sum }
    sqrDistances map (dist => Math.sqrt(dist))
  }
  

  implicit class VectorExtensions(vec: DoubleVector) {
    def substract(that:DoubleVector):DoubleVector = vec zip that map {case(lf,rg) => lf - rg}
  }

  implicit class MatrixExtensions(mat:DoubleMatrix) {

    private def opOver(that:DoubleMatrix,op:(Double,Double) => Double):DoubleMatrix = {
      require(mat.size == that.size)
      mat zip that map {case(vn,vc) => vn zip vc map {case(n,c) => op(n,c)}}
    }

    def maxPerFeature():DoubleVector = {
      mat.transpose map(fVect => fVect.max)
    }

    def minPerFeature():DoubleVector = {
      mat.transpose map( vect => vect.min)
    }

    def divide(that:DoubleMatrix):DoubleMatrix = {
      opOver(that,(l:Double,r:Double) => l / r)
    }

    def substract(that:DoubleMatrix) = {
      opOver(that,(l:Double,r:Double) => l - r)
    }

    def append[T](that:Array[T]):DoubleMatrix = {
      val converted:DoubleVector = that.map(x => x.toString.toDouble).toList
      mat ::: List(converted)
    }
  }

  //  def foldByColumn(): RealVector = {
  //    val result = MatrixUtils.createRealVector(Array.fill(m.getRowDimension)(0))
  //    val j = m.getColumnDimension
  //
  //    for (i <- 0 to m.getRowDimension - 1) {
  //      for (j <- 0 to m.getColumnDimension - 1) {
  //        result.addToEntry(i, m.getEntry(i, j))
  //      }
  //    }
  //    result
  //  }
}
