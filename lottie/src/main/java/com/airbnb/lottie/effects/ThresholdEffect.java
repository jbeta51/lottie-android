package com.airbnb.lottie.effects;

import android.graphics.RenderNode;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.layer.BaseLayer;

public class ThresholdEffect implements LottieEffect{

  private final AnimatableFloatValue level;

  private ThresholdKeyframeAnimation effectKeyframeAnimation = null;
  public ThresholdEffect(AnimatableFloatValue level) {
    this.level = level;
  }

  public AnimatableFloatValue getLevel() {
    return level;
  }

  @Override public void initEffectAnimation(BaseLayer baseLayer) {
    this.effectKeyframeAnimation = new ThresholdKeyframeAnimation(baseLayer, baseLayer, this);
  }

  @Override public void syncEffect() {
    this.effectKeyframeAnimation.syncEffectNode();
  }

  @Override public RenderNode drawEffect(RenderNode renderNode) {
    return this.effectKeyframeAnimation.drawEffect(renderNode);
  }
}
