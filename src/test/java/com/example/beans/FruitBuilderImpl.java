package com.example.beans;

import java.util.List;

import org.pitest.quickbuilder.SequenceBuilder;
import org.pitest.quickbuilder.internal.BuilderImplementation;

import com.example.example.Fruit;

public class FruitBuilderImpl implements SequenceBuilder<Fruit> {

  @Override
  public Fruit build() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Fruit> build(int size) {
    return (List<Fruit>) BuilderImplementation.buildSequence(this, size);
  }

}
