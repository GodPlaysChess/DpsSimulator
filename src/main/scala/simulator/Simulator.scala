package simulator

import scalaz._
import Scalaz._


/**
 * Not clear:
 * 1) should I pass along the state "Alive check"?
 * 2) should I pass along the state "Timepool" ?
 * 3) Shoould I pass with the enemy "time spent" ? think
 * no, cause it can be calculated by folding the sequence of spells by casttime
 */

object Simulator {
  type StateEnemy[A] = State[Enemy, A]

  implicit object Conjunction extends Monoid[Boolean] {
    override def zero: Boolean = true

    override def append(f1: Boolean, f2: => Boolean): Boolean = f1 && f2
  }

  // tag as hitpoins
  def findBest(hp: Long): Seq[Action] = {
    ???
  }

  def simulate(input: List[Spell]): State[Enemy, Boolean] = {
    val sts: List[State[Enemy, Boolean]] = input.map(applySpell)
    val b: StateEnemy[List[Boolean]] = sts.sequence[StateEnemy, Boolean]
    b.map(_.suml)
  }

  def tick: StateEnemy = for {
    m <- init
    t <- modify[Enemy](e => {
      val dmg = e.effects.map(_.dmg).sum
      val effects = e.effects
      e.copy(e.hp - dmg, effects.map(eff => Effect(eff.timeleft - 1)))
    }
    )
  }
    /*

    Effect => (damage, Effect) is a state itself
    *
    *
    *
    *
    * */
    // seq[obj] => seq [obj] with changed 1 values

  //  def tick: State[Enemy, Unit] = for {
  //    e <- get[Enemy]
  //    Enemy(hp, effects) = e
  //    n <- put(Enemy(hp - sp.dmg, effects ++ sp.effects))
  //  } yield put(Enemy(hp - e.dmg, effects ++ e.effects))

  // neeed to apply spell using State.modify
  def applySpell(sp: Spell): State[Enemy, Boolean] = for {
    s <- get[Enemy]
    alive = if (s.hp - sp.dmg > 0) true else false
    enemy <- put(Enemy(s.hp - sp.dmg, s.effects ++ sp.effects)) // here we're chaining the state
  } yield alive // this value we're returning.


}
