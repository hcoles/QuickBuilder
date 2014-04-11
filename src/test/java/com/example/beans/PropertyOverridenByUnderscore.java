package com.example.beans;

import com.example.beans.misuse.BuilderDeclaringNonExistingProperty;

public interface PropertyOverridenByUnderscore extends BuilderDeclaringNonExistingProperty {
  int _DoesNotExist();
}
