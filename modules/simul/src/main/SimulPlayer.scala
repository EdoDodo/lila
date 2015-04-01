package lila.simul

import chess.variant.Variant
import lila.game.PerfPicker
import lila.rating.Perf
import lila.user.{ User, Perfs }

private[simul] case class SimulPlayer(
    user: String,
    variant: Variant,
    rating: Int) {

  def is(userId: String): Boolean = user == userId
  def is(other: SimulPlayer): Boolean = is(other.user)
}

private[simul] object SimulPlayer {

  private[simul] def apply(user: User, variant: Variant): SimulPlayer = new SimulPlayer(
    user = user.id,
    variant = variant,
    rating = {
      if (variant == chess.variant.Standard) user.perfs.classical
      else Perfs.variantLens(variant).fold(user.perfs.standard)(_(user.perfs))
    }.intRating
  )
}
