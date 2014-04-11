package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface ArrayBeanBuilder extends Builder<ArrayBean> {

  ArrayBeanBuilder withBytes(byte[] bs);

  ArrayBeanBuilder withStrings(String[] strings);

  ArrayBeanBuilder withDoubles(double[] doubles);

  ArrayBeanBuilder withBools(boolean[] bools);

  ArrayBeanBuilder withMulti(boolean[][][][][] multi);

}
