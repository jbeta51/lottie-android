package com.airbnb.lottie.effects;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.RuntimeShader;
import android.os.Build;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.layer.BaseLayer;
import org.intellij.lang.annotations.Language;

@TargetApi(Build.VERSION_CODES.TIRAMISU)
public class ThresholdKeyframeAnimation implements BaseKeyframeAnimation.AnimationListener {
  private final BaseKeyframeAnimation.AnimationListener listener;
  private final BaseKeyframeAnimation<Float, Float> level;

  private final RuntimeShader thresholdShader;

  private final RenderNode effectRenderNode;
  private final Rect effectBounds;

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

    effectRenderNode = new RenderNode("threshold_effect");
    this.effectBounds = baseLayer.getDrawableBounds();
    effectRenderNode.setPosition(0, 0, effectBounds.width(), effectBounds.height());
    effectRenderNode.setRenderEffect(RenderEffect.createRuntimeShaderEffect(this.thresholdShader,
        "u_layer"));
  }

  @Override public void onValueChanged() {
    listener.onValueChanged();
  }

  public void syncEffectNode() {
    thresholdShader.setFloatUniform("u_level", level.getValue());
    effectRenderNode.setPosition(0, 0, effectBounds.width(), effectBounds.height());
    effectRenderNode.setRenderEffect(RenderEffect.createRuntimeShaderEffect(this.thresholdShader,
        "u_layer"));
  }

  public RenderNode drawEffect(RenderNode renderNode) {
    RecordingCanvas effectCanvas = effectRenderNode.beginRecording();
    effectCanvas.drawRenderNode(renderNode);
    effectRenderNode.endRecording();
    return effectRenderNode;
  }
}
