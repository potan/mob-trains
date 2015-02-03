package com.github.potan.trn

import scala.annotation.tailrec

import scala.util.Random
import android.text.{SpannableString,Spanned}
import android.text.style.UnderlineSpan
import org.scaloid.common._
import android.graphics.Color

class Shulte extends SActivity {
  var xsize = 6
  var ysize = 6
  var bwait = 0;

  var base = 9
  var symBase = true

  def divmod(n:Int) = {
    val r = n%base
    val m = if(symBase && 2*r>base) {
              r-base
            } else {
              r
            }
    (m,((n-m)/base)) 
  }

  @tailrec final def digits(n:Int, l:List[Int] = Nil):List[Int] = {
    (n,l) match {
      case (0, Nil) => List(0)
      case (0, l)   => l
      case (n,l)    => divmod(n) match { case (m,n) => digits(n, m::l) }
    }
  }

  val ds = (('0' to '9') ++ ('a' to 'z')) map {_.toString()}

  def iShow(n:Int) = {
    val d = digits(n)
    val s = d.foldLeft("")({(s,n) => s+ds(math.abs(n))});
    ((Stream.from(0) zip d) flatMap {case (n,v) if(v<0) => Some(n) case _ => None }).foldLeft(new SpannableString(s))({(s,p) =>
                        s.setSpan(new UnderlineSpan(), p, p+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); s})
  }

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
            SButton(iShow(n)).<<.fill.Weight(1).>>.onClick({
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
