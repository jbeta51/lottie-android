package com.airbnb.lottie.parser;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;

public class ThresholdEffectParser {

  private static final JsonReader.Options THRESHOLD_EFFECT_NAMES = JsonReader.Options.of(
      "ef"
  );
  private static final JsonReader.Options INNER_EFFECT_NAMES = JsonReader.Options.of(
      "nm",
      "v"
  );

  private AnimatableFloatValue level;

  @Nullable
  ThresholdEffect parse(JsonReader reader, LottieComposition composition) throws IOException {
    while (reader.hasNext()) {
      switch (reader.selectName(THRESHOLD_EFFECT_NAMES)) {
        case 0:
          reader.beginArray();
          while (reader.hasNext()) {
            maybeParseInnerEffect(reader, composition);
          }
          reader.endArray();
          break;
        default:
          reader.skipName();
          reader.skipValue();
      }
    }
    if (level != null) {
      return new ThresholdEffect(level);
    }
    return null;
  }

  private void maybeParseInnerEffect(JsonReader reader, LottieComposition composition) throws IOException {
    String currentEffectName = "";
    reader.beginObject();
    while (reader.hasNext()) {
      switch (reader.selectName(INNER_EFFECT_NAMES)) {
        case 0:
          currentEffectName = reader.nextString();
          break;
        case 1:
          switch (currentEffectName) {
            case "Level":
              level = AnimatableValueParser.parseFloat(reader, composition);
              break;
            default:
              reader.skipValue();
              break;
          }
          break;
        default:
          reader.skipName();
          reader.skipValue();
      }
    }
    reader.endObject();
  }
}
