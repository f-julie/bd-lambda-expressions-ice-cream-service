package com.ata.lambdaexpressions.classroom.dao;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RecipeDao_Factory implements Factory<RecipeDao> {
  private static final RecipeDao_Factory INSTANCE = new RecipeDao_Factory();

  @Override
  public RecipeDao get() {
    return new RecipeDao();
  }

  public static RecipeDao_Factory create() {
    return INSTANCE;
  }
}
