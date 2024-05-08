package com.airbnb.lottie.effects;

import android.graphics.RenderNode;

public interface LottieEffect {
  void syncEffectNode();

  RenderNode drawEffect(RenderNode renderNode);
}
