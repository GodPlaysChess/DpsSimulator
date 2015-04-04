package simulator

/**
 * Created by Gleb on 3/13/2015.
 */
object Main {

  def main(args: Array[String]) {
    val en = Enemy(100)
    println(Simulator.applySpell(ShadowBolt()).eval(en))
    println(Simulator.simulate(List(ShadowBolt(), ShadowBolt())).run(en))
    println(Simulator.simulate(List(SearingPain(), SearingPain())).run(en))
  }

}
