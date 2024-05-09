package com.airbnb.lottie.effects;

import android.graphics.RenderNode;
import com.airbnb.lottie.model.layer.BaseLayer;

public interface LottieEffect {

  /**
   * Constructs the Effect's held KeyframeAnimation, setting up effect property listeners
   * @param baseLayer The layer the effect is attached to
   */
  void initEffectAnimation(BaseLayer baseLayer);

  /**
   * Updates property values of the LottieEffect's KeyframeAnimation
   */
  void syncEffect();

  /**
   * Draws the contents of the RenderNode passed into the LottieEffect's own RenderNode
   * <p>
   * In the case that an effect isn't supported by the current SDK, it will return
   * contentNode
   *
   * @param contentNode Node with content to be drawn
   * @return The RenderNode the content was drawn to
   */
  RenderNode drawEffect(RenderNode contentNode);
}
