package com.ata.lambdaexpressions.classroom;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class IceCreamMaker_Factory implements Factory<IceCreamMaker> {
  private static final IceCreamMaker_Factory INSTANCE = new IceCreamMaker_Factory();

  @Override
  public IceCreamMaker get() {
    return new IceCreamMaker();
  }

  public static IceCreamMaker_Factory create() {
    return INSTANCE;
  }
}
