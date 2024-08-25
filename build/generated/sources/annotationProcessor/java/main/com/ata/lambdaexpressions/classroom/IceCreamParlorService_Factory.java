package com.ata.lambdaexpressions.classroom;

import com.ata.lambdaexpressions.classroom.dao.CartonDao;
import com.ata.lambdaexpressions.classroom.dao.RecipeDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class IceCreamParlorService_Factory implements Factory<IceCreamParlorService> {
  private final Provider<RecipeDao> recipeDaoProvider;

  private final Provider<CartonDao> cartonDaoProvider;

  private final Provider<IceCreamMaker> iceCreamMakerProvider;

  public IceCreamParlorService_Factory(
      Provider<RecipeDao> recipeDaoProvider,
      Provider<CartonDao> cartonDaoProvider,
      Provider<IceCreamMaker> iceCreamMakerProvider) {
    this.recipeDaoProvider = recipeDaoProvider;
    this.cartonDaoProvider = cartonDaoProvider;
    this.iceCreamMakerProvider = iceCreamMakerProvider;
  }

  @Override
  public IceCreamParlorService get() {
    return new IceCreamParlorService(
        recipeDaoProvider.get(), cartonDaoProvider.get(), iceCreamMakerProvider.get());
  }

  public static IceCreamParlorService_Factory create(
      Provider<RecipeDao> recipeDaoProvider,
      Provider<CartonDao> cartonDaoProvider,
      Provider<IceCreamMaker> iceCreamMakerProvider) {
    return new IceCreamParlorService_Factory(
        recipeDaoProvider, cartonDaoProvider, iceCreamMakerProvider);
  }
}
