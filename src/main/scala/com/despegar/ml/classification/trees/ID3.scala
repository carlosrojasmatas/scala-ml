package com.despegar.ml.classification.trees

import com.despegar.ml.classification.Preamble._

/**
 * Created by charly on 8/4/15.
 */
class ID3 {

  type DataSet = List[List[Any]]

  private def entropy(dataSet:DataSet): Double = {
    val labels:List[String] = dataSet.map( vec => vec.reverse.head.toString)
    val size = labels.size
    def probability(feature: String): Double = {
      val filtered = labels.filter(c => c.equals(feature))
     filtered.size.toDouble / size.toDouble
    }

    val probs:List[Double]  =  labels.distinct.map(lab => probability(lab))

    probs.foldLeft(0.0)((current, p:Double) => {
      current - p * log(p, 2)
    })

  }

  private def split(dataSet:List[List[Any]],feature:Int,value:Any):List[List[Any]] = {
    val f = dataSet.filter(vect => vect(feature).equals(value))

    f.map(vect => {
      val (left,right) = vect.splitAt(feature)
      left ++ right.tail
    })
  }

  private def pickSplit(dataSet:DataSet):Int = {
    val baseEntropy = entropy(dataSet)
    var bestInfoGain = 0.0
    var bestFeature = -1

    for ( i <- 0 to dataSet(0).length - 2) {
      val labels = dataSet map (vec => vec(i))
      var newEntropy = 0.0
      labels.toSet.foreach{ l:Any =>
        val subDs = split(dataSet,i,l)
        val prob = subDs.size.toDouble/dataSet.size.toDouble
        newEntropy += prob * entropy(subDs)
      }
      val infoGain = baseEntropy - newEntropy
      if(infoGain > bestInfoGain){
        bestInfoGain = infoGain
        bestFeature= i
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
  println(id3.split(createDataset,1,0))
  println(id3.entropy(createDataset))
  println(id3.pickSplit(createDataset))
}
