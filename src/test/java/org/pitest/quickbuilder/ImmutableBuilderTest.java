package org.pitest.quickbuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.pitest.quickbuilder.builders.QB;
import org.pitest.quickbuilder.common.ConstantBuilder;
import org.pitest.quickbuilder.common.ElementSequence;
import org.pitest.quickbuilder.common.NonBuilder;
import org.pitest.quickbuilder.common.Sequences;

import com.example.beans.ArrayBean;
import com.example.beans.ArrayBeanBuilder;
import com.example.beans.ByteArrayBean;
import com.example.beans.ByteArrayBeanBuilder;
import com.example.beans.ChildBean;
import com.example.beans.ChildBeanBuilder;
import com.example.beans.CompositeBean;
import com.example.beans.CompositeBeanBuilder;
import com.example.beans.FruitBean;
import com.example.beans.FruitBuilder;
import com.example.beans.GenericPropertiesBean;
import com.example.beans.GenericPropertiesBeanBuilder;
import com.example.beans.PropertyOverridenByUnderscore;
import com.example.beans.StatelessBeanBuilder;
import com.example.beans.StringBean;
import com.example.beans.StringBeanBuilder;
import com.example.beans.generics.BuilderDeclaringBaseBuilderProperty;
import com.example.beans.generics.BuilderDeclaringBoundedWildcardProperty;
import com.example.beans.misuse.BuilderDeclaringNonExistingProperty;
import com.example.beans.misuse.BuilderWithParameterisedUnderscoreMethod;
import com.example.beans.misuse.BuilderWithPropertyReturningWrongType;
import com.example.beans.misuse.BuilderWithPropertyWithTooManyParameters;
import com.example.beans.misuse.BuilderWithTypeMismatchInUnderscoreMethod;
import com.example.beans.primitives.BooleanBean;
import com.example.beans.primitives.BooleanBeanBuilder;
import com.example.beans.primitives.ByteBean;
import com.example.beans.primitives.ByteBeanBuilder;
import com.example.beans.primitives.CharBean;
import com.example.beans.primitives.CharBeanBuilder;
import com.example.beans.primitives.DoubleBean;
import com.example.beans.primitives.DoubleBeanBuilder;
import com.example.beans.primitives.FloatBean;
import com.example.beans.primitives.FloatBeanBuilder;
import com.example.beans.primitives.IntBean;
import com.example.beans.primitives.IntBeanBuilder;
import com.example.beans.primitives.LongBean;
import com.example.beans.primitives.LongBeanBuilder;
import com.example.beans.primitives.PrimitiveBeanBuilder;
import com.example.beans.primitives.ShortBean;
import com.example.beans.primitives.ShortBeanBuilder;
import com.example.example.Apple;
import com.example.example.FruitBuilders;
import com.example.immutable.IntegerValue;
import com.example.immutable.IntegerValueBuilder;
import com.example.immutable.MixedValue;
import com.example.immutable.MixedValueBuilder;
import com.example.immutable.MixedValueGenerator;

public class ImmutableBuilderTest {

  @Test
  public void shouldCreateABuilder() {
    final StatelessBeanBuilder testee = QB.builder(StatelessBeanBuilder.class);
    assertThat(testee).isNotNull();
  }

  @Test
  public void shouldBuildBeans() {
    final StatelessBeanBuilder builder = QB.builder(StatelessBeanBuilder.class);
    assertThat(builder.build()).isNotNull();
  }

  @Test
  public void shouldImplementWithMethodsThatReturnCopyOfTheBuilder() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class);
    assertThat(builder.withName("foo")).isNotSameAs(builder);
  }

  @Test
  public void shouldNotChangeStateInBuilderWhenWithMethodCalled() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class);
    final FruitBuilder original = builder.withName("original");
    assertThat(builder.withName("foo").build().getName()).isEqualTo("foo");
    assertThat(original.build().getName()).isEqualTo("original");
  }

  @Test
  public void shouldSupportAnyLowerCasePrefixForBuilderMethods() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class);
    assertThat(builder.andColour("foo")).isNotSameAs(builder);
  }

  @Test
  public void shouldSetStateOnBeansForStringProperty() {
    final StringBeanBuilder builder = QB.builder(StringBeanBuilder.class);
    assertThat(builder.withName("foo").build().getName()).isEqualTo("foo");
  }

  @Test
  public void shouldSetStateOnBeansForMultipleStringProperties() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class);
    final FruitBean fruit = builder.withName("foo").andColour("foo").build();
    assertThat(fruit.getName()).isEqualTo("foo");
    assertThat(fruit.getColour()).isEqualTo("foo");
  }

  @Test
  public void shouldSetStateOnBeansForIntProperty() {
    final IntBeanBuilder builder = QB.builder(IntBeanBuilder.class);
    final IntBean b = builder.withI(42).build();
    assertThat(b.getI()).isEqualTo(42);
  }

  @Test
  public void shouldSetStateOnBeansForLongProperty() {
    final LongBeanBuilder builder = QB.builder(LongBeanBuilder.class);
    final LongBean b = builder.withL(42l).build();
    assertThat(b.getL()).isEqualTo(42);
  }

  @Test
  public void shouldSetStateOnBeansForFloatProperty() {
    final FloatBeanBuilder builder = QB.builder(FloatBeanBuilder.class);
    final FloatBean b = builder.withF(42f).build();
    assertThat(b.getF()).isEqualTo(42f);
  }

  @Test
  public void shouldSetStateOnBeansForDoubleProperty() {
    final DoubleBeanBuilder builder = QB.builder(DoubleBeanBuilder.class);
    final DoubleBean b = builder.withD(42d).build();
    assertThat(b.getD()).isEqualTo(42d);
  }

  @Test
  public void shouldSetStateOnBeansForBooleanProperty() {
    final BooleanBeanBuilder builder = QB.builder(BooleanBeanBuilder.class);
    final BooleanBean b = builder.withB(true).build();
    assertThat(b.isB()).isEqualTo(true);
  }

  @Test
  public void shouldSetStateOnBeansForShortProperty() {
    final ShortBeanBuilder builder = QB.builder(ShortBeanBuilder.class);
    final ShortBean b = builder.withS((short) 3).build();
    assertThat(b.getS()).isEqualTo((short) 3);
  }

  @Test
  public void shouldSetStateOnBeansForCharProperty() {
    final CharBeanBuilder builder = QB.builder(CharBeanBuilder.class);
    final CharBean b = builder.withC('a').build();
    assertThat(b.getC()).isEqualTo('a');
  }

  @Test
  public void shouldSetStateOnBeansForByteProperty() {
    final ByteBeanBuilder builder = QB.builder(ByteBeanBuilder.class);
    final ByteBean b = builder.withBy((byte) 1).build();
    assertThat(b.getBy()).isEqualTo((byte) 1);
  }

  @Test
  public void shouldSetStateOnBeansForByteArrayProperty() {
    final ByteArrayBeanBuilder builder = QB.builder(ByteArrayBeanBuilder.class);
    final byte[] bs = new byte[] {};
    final ByteArrayBean b = builder.withBytes(bs).build();
    assertThat(b.getBytes()).isSameAs(bs);
  }

  @Test
  public void shouldSetStateOnBeansForStringArrayProperty() {
    final ArrayBeanBuilder builder = QB.builder(ArrayBeanBuilder.class);
    final String[] s = new String[] {};
    final ArrayBean b = builder.withStrings(s).build();
    assertThat(b.getStrings()).isSameAs(s);
  }

  @Test
  public void shouldSetStateOnBeansForDoubleArrayProperty() {
    final ArrayBeanBuilder builder = QB.builder(ArrayBeanBuilder.class);
    final double[] d = new double[] {};
    final ArrayBean b = builder.withDoubles(d).build();
    assertThat(b.getDoubles()).isSameAs(d);
  }

  @Test
  public void shouldSetStateOnBeansForBooleanArrayProperty() {
    final ArrayBeanBuilder builder = QB.builder(ArrayBeanBuilder.class);
    final boolean[] d = new boolean[] {};
    final ArrayBean b = builder.withBools(d).build();
    assertThat(b.getBools()).isSameAs(d);
  }

  @Test
  public void shouldSetStateOnBeansForMultiDimensionalArrayProperty() {
    final ArrayBeanBuilder builder = QB.builder(ArrayBeanBuilder.class);
    final boolean[][][][][] multi = new boolean[][][][][] {};
    final ArrayBean b = builder.withMulti(multi).build();
    assertThat(b.getMulti()).isSameAs(multi);
  }

  @Test
  public void shouldUseCorrectMethodsInHierarchy() {
    final ChildBeanBuilder builder = QB.builder(ChildBeanBuilder.class);
    final ChildBean bean = builder.withFoo("foo").withBar("bar").build();
    assertThat(bean.getBar()).isEqualTo("bar");
    assertThat(bean.getFoo()).isEqualTo("modifiedbychild_foo");

  }

  @Test
  public void shouldSetStateOnBeansForGenericListProperty() {
    final GenericPropertiesBeanBuilder builder = QB
        .builder(GenericPropertiesBeanBuilder.class);
    final List<String> expected = Collections.emptyList();
    final GenericPropertiesBean b = builder.withS(expected).build();
    assertThat(b.getS()).isSameAs(expected);
  }

  @Test
  public void shouldSetStateOnBeansForBoundedWildCardGenericListProperty() {
    final GenericPropertiesBeanBuilder builder = QB
        .builder(GenericPropertiesBeanBuilder.class);
    final List<? extends Number> expected = Collections.emptyList();
    final GenericPropertiesBean b = builder.withN(expected).build();
    assertThat(b.getN()).isSameAs(expected);
  }

  @Test
  public void shouldImplementUnderscoreAccessorsForInteger() {
    final IntegerValueBuilder builder = QB.builder(IntegerValueBuilder.class,
        intGenerator());
    assertThat(builder.withI(42)._I()).isEqualTo(42);
  }

  @Test
  public void shouldContructImmutableValueTypesWithInteger() {
    final IntegerValueBuilder builder = QB.builder(IntegerValueBuilder.class,
        intGenerator());
    assertThat(builder.withI(42).build().i()).isEqualTo(42);
  }

  public interface MaybeStringBeanBuilder extends Builder<StringBean> {

    MaybeStringBeanBuilder withName(String name);

    Maybe<String> __Name();

  }

  @Test
  public void shouldImplementUnderscoreAccessorsForMaybes() {
    final MaybeStringBeanBuilder builder = QB
        .builder(MaybeStringBeanBuilder.class);
    assertThat(builder.__Name().hasNone()).isTrue();
    assertThat(builder.withName("foo").__Name().value()).isEqualTo("foo");
  }

  @Test
  public void shouldImplementUnderscoreAccessorsForPrimitiveMaybes() {
    final PrimitiveBeanBuilder builder = QB.builder(PrimitiveBeanBuilder.class);
    assertThat(builder.__I().hasNone()).isTrue();
    assertThat(builder.__D().hasNone()).isTrue();
    assertThat(builder.withI(42).__I().value()).isEqualTo(42);
    assertThat(builder.withD(1).__D().value()).isEqualTo(1);
  }

  @Test
  public void shouldConstructImmutableMixedValueTypes() {
    final MixedValueBuilder builder = QB.builder(MixedValueBuilder.class,
        new MixedValueGenerator());
    final double[] ds = new double[] { 1.0d, 2.2d };
    final float f = 1.3f;

    final MixedValue actual = builder.withDs(ds).withF(f).withS("S")
        .withSs("SS").withLs(null).build();
    assertThat(actual.getDs()).isSameAs(ds);
    assertThat(actual.getF()).isEqualTo(f);
    assertThat(actual.getS()).isEqualTo("S");
    assertThat(actual.getSs()).isEqualTo("SS");
  }

  private Generator<IntegerValueBuilder, IntegerValue> intGenerator() {
    return new Generator<IntegerValueBuilder, IntegerValue>() {
      @Override
      public IntegerValue generate(final IntegerValueBuilder builder) {
        return new IntegerValue(builder._I());
      }
    };
  }

  @Test(expected = QuickBuilderError.class)
  public void shouldErrorWhenNoSetterForDeclaredPropery() {
    QB.builder(BuilderDeclaringNonExistingProperty.class);
  }

  @Test
  public void shouldNotErrorWhenNoSetterForDeclaredProperyButUnderscoreMethodExists() {
    try {
      QB.builder(PropertyOverridenByUnderscore.class);
      // pass
    } catch (final Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void shouldAcceptBuilderInPlaceOfPropertyType() {
    final CompositeBeanBuilder builder = QB.builder(CompositeBeanBuilder.class);
    final FruitBuilder fb = QB.builder(FruitBuilder.class).withName("foo");
    final CompositeBean actual = builder.withFruit(fb).build();
    assertThat(actual.getFruit().getName()).isEqualTo("foo");
  }

  @Test
  public void shouldAllowPropertyToBeOverriddenByBuilderAndType() {
    final CompositeBeanBuilder builder = QB.builder(CompositeBeanBuilder.class);
    final FruitBuilder fb = QB.builder(FruitBuilder.class).withName("foo");
    final CompositeBean actual = builder.withFruit(fb)
        .withFruit(new FruitBean()).build();
    // last call should take precedence
    assertThat(actual.getFruit().getName()).isNull();
  }

  @Test
  public void shouldAcceptBaseBuilderInterfaceInPlaceOfBuiltType() {
    final BuilderDeclaringBaseBuilderProperty builder = QB
        .builder(BuilderDeclaringBaseBuilderProperty.class);
    final FruitBuilder fb = QB.builder(FruitBuilder.class).withName("foo");
    final CompositeBean bean = builder.withMoreFruit(fb).build();
    assertThat(bean.getMoreFruit().getName()).isEqualTo("foo");
  }

  @Test
  public void shouldCreateBridgeMethodsForErasedReturnTypes() {
    final Apple a = FruitBuilders.anApple().withRipeness(2d).withLeaves(2)
        .build();
    assertEquals(a.numberOfLeaves(), 2);
  }

  @Test
  public void doesNotSupportWildCards() {
    try {
      QB.builder(BuilderDeclaringBoundedWildcardProperty.class);
      fail("expected an error");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining("wildcards not currently supported");
    }
  }

  @Test
  public void shouldThrowErrorWhenUnderScoreMethodHasParameter() {
    try {
      QB.builder(BuilderWithParameterisedUnderscoreMethod.class);
      fail("expected an error");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining("_Foo should not have parameters");
    }
  }

  @Test
  public void shouldThrowErrorWhenTypeOfUnderscoreMethodDoesNotMatchProperty() {
    try {
      QB.builder(BuilderWithTypeMismatchInUnderscoreMethod.class);
      fail("expected an error");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining("No setter found for Foo of type");
      assertThat(e).hasMessageContaining(BuilderWithTypeMismatchInUnderscoreMethod.class.getName());
      assertThat(e).hasMessageContaining(String.class.getName());
    }
  }

  @Test
  public void shouldThrowErrorWhenWithMethodDoesNotReturnBuilder() {
    try {
      QB.builder(BuilderWithPropertyReturningWrongType.class);
      fail("expected an error");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining(
          "should declare return type as "
              + BuilderWithPropertyReturningWrongType.class.getName());
    }
  }

  @Test
  public void shouldThrowErrorWhenWithMethodHasWrongNumberOfParameters() {
    try {
      QB.builder(BuilderWithPropertyWithTooManyParameters.class);
      fail("expected an error");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining("should take exactly one parameter");
    }
  }

  @Test
  public void shouldThrowErrorWhenPropertyAccessedWithoutAValueBeingSet() {
    final MixedValueBuilder builder = QB.builder(MixedValueBuilder.class,
        new MixedValueGenerator());
    try {
      builder.withF(1.0f).build();
      fail("expected an error");
    } catch (final NoValueAvailableError e) {
      assertThat(e).hasMessageContaining("no value");
    }
    // fail
  }

  public static abstract class InvalidClass implements Builder<String> {

  }

  @Test
  public void shouldThrowErrorWhenAskedToImplementAClass() {
    try {
      QB.builder(InvalidClass.class);
      fail("expected an exception");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining("not an interface");
      assertThat(e).hasMessageContaining(InvalidClass.class.getName());
    }

  }

  interface Inaccessible extends Builder<String> {

  }

  @Test
  public void shouldThrowErrorWhenAskedToImplementInaccessibleInterface() {
    try {
      QB.builder(Inaccessible.class);
      fail("expected an error");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining(
          "Cannot implement the interface " + Inaccessible.class.getName());
    }
  }

  @Test
  public void shouldReturnConstantNextWhenNoChildrenSet() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class);
    assertThat(builder.next().hasSome()).isTrue();
  }

  @Test
  public void shouldReturnConstantNextWhenNoChildrenAreConstants() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId("foo");
    assertThat(builder.next().hasSome()).isTrue();
    assertThat(builder.next().value().build().getId()).isEqualTo("foo");
  }

  @Test
  public void shouldReturnLimitedNextWhenChildrenAreLimited() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        new NonBuilder<String>());
    assertThat(builder.next().hasSome()).isFalse();
  }

  @Test
  public void shouldReturnNextStateWhenChildrenHaveTransistionableState() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("a", "b")));
    assertThat(builder.build().getId()).isEqualTo("a");
    assertThat(builder.next().value().build().getId()).isEqualTo("b");
  }

  @Test
  public void shouldReturnNoneWhenTransistionsExhausted() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("a", "b")));
    final Maybe<Builder<FruitBean>> oneTransistion = builder.next();
    final Maybe<Builder<FruitBean>> twoTransistions = oneTransistion.value()
        .next();
    assertThat(twoTransistions.hasSome()).isEqualTo(false);
  }
  
  public static class ABean {
    private String foo;

    public String getFoo() {
      return foo;
    }

    public void setFoo(String foo) {
      this.foo = foo;
    }
    
  }
  
  public interface ABeanBuilder extends Builder<ABean> {
    ABeanBuilder withFoo(Builder<String> b);
  }
  
  @Test
  public void shouldDetectPropertiesWhenOnlyBuilderWithMethodDeclared() {
    ABeanBuilder builder = QB.builder(ABeanBuilder.class).withFoo(new ConstantBuilder<String>("foo"));
    assertThat(builder.build().getFoo()).isEqualTo("foo");
  }
  
  public interface ABeanBuilderWithUnderScore extends Builder<ABean> {
    ABeanBuilderWithUnderScore withFoo(Builder<String> b);
    String _Foo();
  }
  
  @Test
  public void shouldImplementUnderscoreMethodDeclaredForBuildeOnlyProperty() {
    ABeanBuilderWithUnderScore builder = QB.builder(ABeanBuilderWithUnderScore.class).withFoo(new ConstantBuilder<String>("foo"));
    assertThat(builder._Foo()).isEqualTo("foo");
    assertThat(builder.build().getFoo()).isNull();
  }
  
  public interface ABeanBuilderWithMaybeUnderScore extends Builder<ABean> {
    ABeanBuilderWithMaybeUnderScore withFoo(Builder<String> b);
    Maybe<String> __Foo();
  }
  
  @Test
  public void shouldImplementMaybeUnderscoreMethodDeclaredForBuilderOnlyProperty() {
    ABeanBuilderWithMaybeUnderScore builder = QB.builder(ABeanBuilderWithMaybeUnderScore.class).withFoo(new ConstantBuilder<String>("foo"));
    assertThat(builder.__Foo().value()).isEqualTo("foo");
  }
  
  @Test
  public void shouldDisableSettersForMaybeMethodForBuilderOnlyProperty() {
    ABeanBuilderWithMaybeUnderScore builder = QB.builder(ABeanBuilderWithMaybeUnderScore.class).withFoo(new ConstantBuilder<String>("foo"));
    assertThat(builder.build().getFoo()).isNull();
  }
  
  @Test
  public void shouldConsumeAllOfLimitedSequences() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("one", "two")));
    final List<FruitBean> actual = Sequences.buildAll(builder);
    assertThat(actual).hasSize(2);
  }

  @Test
  public void shouldBuildListsOfRequestedSize() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("repeated", "two", "repeated")));
    final List<FruitBean> expected = Sequences.build(builder, 2);
    assertThat(expected.size()).isEqualTo(2);
    assertThat(expected).haveExactly(1, fruitWithId("repeated"));
    assertThat(expected).haveExactly(1, fruitWithId("two"));
  }

  @Test
  public void shouldBuildListsOfRequestedSizeViaInstanceMethod() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class);
    final List<FruitBean> expected = builder.build(2);
    assertThat(expected.size()).isEqualTo(2);
  }

  @Test
  public void shouldConsumeAllOfSmallestSequencesWhenBuidAllCalledViaInstanceMethod() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("one", "two")));
    final List<FruitBean> actual = builder.buildAll();
    assertThat(actual).hasSize(2);
  }
  
  @Test
  public void shouldLimitSequences() {
    final SequenceBuilder<FruitBean> builder = QB.builder(FruitBuilder.class).limit(12);
    assertThat(builder.buildAll()).hasSize(12);
  }
  
  @Test
  public void shouldImplementIteratorMethod() {
    final SequenceBuilder<FruitBean> builder = QB.builder(FruitBuilder.class);
    assertThat(builder.iterator().next()).isNotNull();
  }
  
  
  public static interface MistypedBuilder extends Builder<FruitBean> {
    MistypedBuilder withName(Builder<Integer> name);
  }
  
  @Test
  public void shouldReportMistypedBuilderParams() {
    try {
      QB.builder(MistypedBuilder.class);
      fail("Expected an error");
    } catch (final QuickBuilderError e) {
      assertThat(e).hasMessageContaining("No setter found for ");
    }
  }
  
  private Condition<FruitBean> fruitWithId(final String id) {
    return new Condition<FruitBean>() {
      @Override
      public boolean matches(final FruitBean arg0) {
        return arg0.getId().equals(id);
      }

    };
  }

  
}
