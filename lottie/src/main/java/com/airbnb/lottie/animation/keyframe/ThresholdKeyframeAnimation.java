package com.airbnb.lottie.animation.keyframe;

import android.annotation.TargetApi;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.os.Build;
import androidx.annotation.Nullable;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.parser.ThresholdEffect;
import org.intellij.lang.annotations.Language;

@TargetApi(Build.VERSION_CODES.TIRAMISU)
public class ThresholdKeyframeAnimation implements BaseKeyframeAnimation.AnimationListener {
  private final BaseKeyframeAnimation.AnimationListener listener;
  private final BaseKeyframeAnimation<Float, Float> level;

  private final RuntimeShader thresholdShader;

  @Language("AGSL") private static final String THRESHOLD_SHADER_SRC =
    "uniform shader u_layer;" +
    "uniform float u_level;" +

    "half4 main(float2 fragCoord) {" +
      "half4 c = u_layer.eval(fragCoord);" +
      "half lum = dot(c.rgb, half3(0.2126, 0.7152, 0.0722));" +
      "half bw = step(u_level, lum);" +

      "return bw.xxx1 * c.a;" +
    "}";

  private static RuntimeShader makeShader() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      return new RuntimeShader(THRESHOLD_SHADER_SRC);
    }
    return null;
  }

  public ThresholdKeyframeAnimation(BaseKeyframeAnimation.AnimationListener listener, BaseLayer baseLayer, ThresholdEffect effect) {
    this.listener = listener;

    level = effect.getLevel().createAnimation();
    level.addUpdateListener(this);
    baseLayer.addAnimation(level);

    thresholdShader = makeShader();
  }

  @Override public void onValueChanged() {
    listener.onValueChanged();
  }

  public RuntimeShader getThresholdShader() {
    thresholdShader.setFloatUniform("u_level", level.getValue());
    return this.thresholdShader;
  }
}
