package com.example.example;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Generator;
import org.pitest.quickbuilder.builders.QB;

public class FruitBuilders {

  static interface FruitBuilder<T extends Fruit, B extends Builder<? extends Fruit>>
      extends Builder<T> {
    B withRipeness(double ripeness);

    double _Ripeness();
  }

  public interface AppleBuilder extends FruitBuilder<Apple, AppleBuilder> {
    AppleBuilder withLeaves(int leaves);

    int _Leaves();
  }

  interface BananaBuilder extends FruitBuilder<Banana, BananaBuilder> {
    BananaBuilder withCurve(int curve);

    int _Curve();
  }

  public static AppleBuilder anApple() {
    return QB.builder(AppleBuilder.class, appleSeed());
  }

  public static BananaBuilder aBanana() {
    return QB.builder(BananaBuilder.class, bananaSeed());
  }

  private static Generator<AppleBuilder, Apple> appleSeed() {
    return new Generator<AppleBuilder, Apple>() {
      @Override
      public Apple generate(final AppleBuilder builder) {
        return new Apple(builder._Leaves());
      }
    };
  }

  private static Generator<BananaBuilder, Banana> bananaSeed() {
    return new Generator<BananaBuilder, Banana>() {
      @Override
      public Banana generate(final BananaBuilder builder) {
        return new Banana(builder._Curve());
      }
    };
  }

}
