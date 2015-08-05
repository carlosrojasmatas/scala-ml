package com.despegar.ml.classification

import scala.collection.immutable.ListMap
import scala.io.Source
import Preamble._

/**
 * Created by charly on 7/16/15.
 */
class kNN {


  private val testCoef = 0.1



  def classify0(inX: DoubleVector, data: DoubleMatrix, labels: List[String], k: Int): String = {
    val dist = euclidianDistance(inX, data)
    val sorted = dist.toArray.zip(labels).sorted.take(k)
    ListMap(sorted.groupBy(_._2).mapValues(_.size).toSeq.sortWith(_._2 > _._2): _*).head._1
  }

  def normalize(dataSet: DoubleMatrix): DoubleMatrix = {
    val mins = dataSet.minPerFeature()
    val max = dataSet.maxPerFeature()
    val ranges = max.substract(mins)
    dataSet substract (fillMatrix(ranges, dataSet.size)) divide fillMatrix(ranges, dataSet.size)
  }

  private var dataSet: DoubleMatrix = List.empty[DoubleVector]
  private var labels: List[String] = List.empty[String]

  def loadData(fName: String): Unit = {

    val src = Source.fromFile(fName)
    src.getLines() foreach {
      rawLine =>
        val line = rawLine.split('\t')
        labels = labels ::: List(line.reverse.head)
        dataSet = dataSet.append(line.take(line.size - 1))
    }
    dataSet = normalize(dataSet)
  }

  def testClassifier() = {
    val testSize = (dataSet.size * testCoef).toInt
    val testData = dataSet.take(testSize)
    val trainingData = dataSet.slice(testSize + 1, dataSet.size)
    val testLabels = labels.slice(testSize + 1, labels.size)
    var numErr = 0
    for (i <- 0 to testSize) {
      val testVals = dataSet(i)
      val expRs = labels(i)
      val rs = classify0(testVals, trainingData, testLabels, 3)
      if (!rs.equals(expRs)) {
        println(s"error detected. Expected:$expRs -- Actual: $rs")
        numErr += 1
      }
    }

    if (numErr > 0) {
      val ratio: Double = numErr.toDouble / testSize.toDouble
      println(s"$numErr detected for number of tests $testSize. Error ratio:$ratio")
    }


  }

}

object kNN extends App {
  val knn = new kNN
  knn.loadData("etc/datingTestSet2.txt")
  knn.testClassifier()
}
