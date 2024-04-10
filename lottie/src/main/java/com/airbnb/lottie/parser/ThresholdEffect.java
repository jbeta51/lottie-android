package com.airbnb.lottie.parser;

import android.graphics.RuntimeShader;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;

public class ThresholdEffect {

  private final AnimatableFloatValue level;

  ThresholdEffect(AnimatableFloatValue level) {
    this.level = level;
  }

  public AnimatableFloatValue getLevel() {
    return level;
  }
}
