package com.despegar.ml.classification.bayes

import com.despegar.ml.classification.Preamble.DataSet

/**
 * User: charly
 * Date: 11/08/15
 * Time: 17:17
 * Please add a comment.
 */
object NaiveBayes {

  def apply(dataSet:DataSet,classVec:List[Int])= new NaiveBayes(dataSet,classVec)
}

class NaiveBayes(dataSet:DataSet,classVec:List[Int]){

  lazy val vocabulary:List[String] = dataSet.flatMap( doc => doc.distinct map (el => el.toString))

  def seekTokens(input:List[String]):List[Int] = {
    var range = List.fill(vocabulary.size)(0)

    input.map(word => if (vocabulary.contains(word)) range = range.updated(vocabulary.indexOf(word),1))

    range
  }



}
