package org.pitest.quickbuilder;

import java.util.List;

public interface SequenceBuilder<T> extends Builder<T> {

  List<T> build(int number);
  
}
