package com.despegar.ml.classification.trees

import com.despegar.ml.classification.Preamble._

/**
 * Created by charly on 8/4/15.
 */
class ID3 {

  type DataSet = List[List[Any]]

  trait Element {
    val isLeaf: Boolean = childs.size == 0
    val childs: Map[Any,Element]
  }

  case class Node(val label: String, val childs: Map[Any,Element]) extends Element

  case class Leaf(val value: Any, childs: Map[Any,Element] = Map.empty) extends Element

  case class Tree(val root: Element)

  def createTree(dataSet: DataSet, labels: List[String]): Tree = {

    def diggInto(subData: DataSet,currLabels:List[String]): Element = {
      val classes = subData.map(row => row.last).distinct

      if (classes.size == 1) Leaf(classes.head) //first exit case. All labels are equals.

      else if (subData.size == 1) Leaf(mayority(classes)) //second case. No more features but no labels equals.

      else {
        val splitAt = bestSplit(subData)
        val values = domain(subData, splitAt)

        Node(currLabels(splitAt),
          (for {
            v <- values
          } yield {
            var newLabels = labels.filterNot(l => l.equals(labels(splitAt)))
            (v -> diggInto(split(subData, splitAt, v),newLabels))
          }).toMap)
      }
    }


    Tree(diggInto(dataSet,labels))
  }

  private def entropy(dataSet: DataSet): Double = {
    val labels: List[String] = dataSet.map(vec => vec.reverse.head.toString)
    val size = labels.size
    def probability(feature: String): Double = {
      val filtered = labels.filter(c => c.equals(feature))
      filtered.size.toDouble / size.toDouble
    }

    val probs: List[Double] = labels.distinct.map(lab => probability(lab))

    probs.foldLeft(0.0)((current, p: Double) => {
      current - p * log(p, 2)
    })

  }

  private def split(dataSet: DataSet, feature: Int, value: Any): DataSet = {
    val f = dataSet.filter(vect => vect(feature).equals(value))

    f.map(vect => {
      val (left, right) = vect.splitAt(feature)
      left ++ right.tail
    })
  }

  private def domain(dataSet: DataSet, featureIdx: Int): List[Any] = {
    val featureVec = dataSet.map(vec => vec(featureIdx))
    featureVec.distinct
  }

  private def mayority(labels: List[Any]): Any = {
    val counts = labels.distinct.map(lab => (lab, labels.count(l => l.equals(lab))))
    counts.sortWith(_._2 > _._2).head._1
  }

  private def bestSplit(dataSet: DataSet): Int = {
    val baseEntropy = entropy(dataSet)
    var bestInfoGain = 0.0
    var bestFeature = -1

    for (i <- 0 to dataSet(0).length - 2) {
      val labels = domain(dataSet, i)
      var newEntropy = 0.0
      labels.toSet.foreach { l: Any =>
        val subDs = split(dataSet, i, l)
        val prob = subDs.size.toDouble / dataSet.size.toDouble
        newEntropy += prob * entropy(subDs)
      }
      val infoGain = baseEntropy - newEntropy
      if (infoGain > bestInfoGain) {
        bestInfoGain = infoGain
        bestFeature = i
      }
    }

    bestFeature
  }


}

object ID3 extends App {


  def createDataset: List[List[Any]] = {
    List(
      List(1, 1, "yes"),
      List(1, 1, "yes"),
      List(1, 0, "no"),
      List(0, 1, "no"),
      List(0, 1, "no")
    )
  }


  val id3 = new ID3

  println(id3.mayority(List("a", "a", "a", "b", "b", "c")))
  println(id3.entropy(createDataset))
  println(id3.bestSplit(createDataset))
  println(id3.createTree(createDataset, List("No surfacing", "Flippers", "Fish")))


}
