package com.github.potan.trn

import scala.util.Random
import org.scaloid.common._
import android.graphics.Color

class Shulte extends SActivity {
  var xsize = 6
  var ysize = 6
  var bwait = 0;

  def initTbl(x:Int, y:Int) {
    bwait = 0
    val m = x*y

    contentView = new SVerticalLayout {
      this += new SLinearLayout {
                SButton("run!").onClick(initTbl(xsize, ysize))
              }

      for(row <- Random.shuffle((0 to (x*y-1)).toList).grouped(x)) {
        this += new SLinearLayout {
          for(n <- row) {
            SButton(s"${n}").<<.fill.Weight(1).>>.onClick({
                              if(n == bwait) {
                                bwait += 1
                              } else {
                                toast("Fail!")
                              }
                              if(m == bwait) {
                                toast("Win!")
                              }
                             })
          }
        }
      }
    }
  }

  onCreate {
    initTbl(xsize, ysize)
  }
}
