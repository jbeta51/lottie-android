package com.airbnb.lottie.effects;

import android.graphics.RenderNode;

import com.airbnb.lottie.model.layer.BaseLayer;

import java.util.ArrayList;

public class EffectManager {
  private final ArrayList<LottieEffect> effects;

  public EffectManager() {
    this.effects = new ArrayList<>();
  }

  public void addEffect(LottieEffect e) {
    effects.add(e);
  }

  public boolean hasEffects() {
    return !effects.isEmpty();
  }

  /**
   * This init function will take every Effect object saved and initialize its
   * keyframe animation. This happens separately from when the effect is made when parsed
   * due to needing the base layer for callbacks.
   *
   * @param baseLayer The layer the effect is attached to
   */
  public void initEffectAnimations(BaseLayer baseLayer) {
    for (LottieEffect effect : effects) {
      effect.initEffectAnimation(baseLayer);
    }
  }

  /**
   * Calls syncEffect on each tracked LottieEffect
   */
  public void syncEffects() {
    for (LottieEffect effect : effects) {
      effect.syncEffect();
    }
  }

  /**
   * Draw effects goes over each effect in order and draws the previous effect's content node
   * into the next.
   *
   * @param contentNode The starting content node
   * @return The final effect's content node.
   */
  public RenderNode drawEffects(RenderNode contentNode) {
    return effects.get(0).drawEffect(contentNode);
  }
}
