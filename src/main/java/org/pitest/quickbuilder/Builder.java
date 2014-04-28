package org.pitest.quickbuilder;

/**
 * Builder interface which must be extended by all QuickBuilder interfaces.
 * It should not be implemented by concrete classes, instead the extended
 * interface should be passed to the {@link QB} class which will generate
 * an implementation at runtime. 
 *
 * @param <T> Type to be built.
 */
public interface Builder<T> {

  /**
   * Constructs a T using state within the builder
   * @return a T
   */
  T build();
  
  /**
   * The limit to the number of values that can be produced by this builder.
   * This will generally be -1 (representing no limit i.e. infinite values), but could be 0 (no values available)
   * or a positive integer when working with sequences.
   * 
   * @return A positive integer or Null if builder can produce infinite values
   */
  int valueLimit();  
    
}