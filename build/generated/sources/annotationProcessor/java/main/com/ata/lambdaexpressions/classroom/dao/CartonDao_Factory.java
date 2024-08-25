package com.ata.lambdaexpressions.classroom.dao;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CartonDao_Factory implements Factory<CartonDao> {
  private static final CartonDao_Factory INSTANCE = new CartonDao_Factory();

  @Override
  public CartonDao get() {
    return new CartonDao();
  }

  public static CartonDao_Factory create() {
    return INSTANCE;
  }
}
