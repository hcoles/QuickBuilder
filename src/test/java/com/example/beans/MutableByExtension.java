package com.example.beans;

import org.pitest.quickbuilder.MutableBuilder;

public interface MutableByExtension extends FruitBuilder, MutableBuilder<FruitBean> {

}
