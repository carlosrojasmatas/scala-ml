package com.despegar.ml.classification.bayes

import org.scalatest.{Matchers, FlatSpec}

/**
 * User: charly
 * Date: 11/08/15
 * Time: 17:29
 * Please add a comment.
 */
class BayesSpec extends FlatSpec with Matchers {

  val dataSet = List(

    List("my", "dog", "has", "flea", "problems", "help", "please"),
    List("maybe", "not", "take", "him", "to", "dog", "park", "stupid"),
    List("my", "dalmation", "is", "so", "cute", "I", "love", "him"),
    List("stop", "posting", "stupid", "worthless", "garbage"),
    List("mr", "licks", "ate", "my", "steak", "how", "to", "stop", "him"),
    List("quit", "buying", "worthless", "dog", "food", "stupid")
  )

  val classification = List(0, 1, 0, 1, 0, 1)

  val bayesClass = NaiveBayes.apply(dataSet, classification)

  "Naive Bayes " should "merge all documents" in {
    val voc = bayesClass.vocabulary
    voc should be(List("my", "dog", "has", "flea", "problems", "help",
      "please", "maybe", "not", "take", "him", "to", "dog", "park", "stupid",
      "my", "dalmation", "is", "so", "cute", "I", "love", "him",
      "stop", "posting", "stupid", "worthless", "garbage",
      "mr", "licks", "ate", "my", "steak", "how", "to", "stop", "him",
      "quit", "buying", "worthless", "dog", "food", "stupid"
    ))
  }

  it  should " seek for words in vocabulary " in {
    val result  = bayesClass.seekTokens(List("my","dog","so","fasdf","gohd"))
    println(result)
   true


  }


}
