package com.ata.lambdaexpressions.classroom;

import com.ata.lambdaexpressions.classroom.converter.RecipeConverter;
import com.ata.lambdaexpressions.classroom.dao.CartonDao;
import com.ata.lambdaexpressions.classroom.dao.RecipeDao;
import com.ata.lambdaexpressions.classroom.exception.CartonCreationFailedException;
import com.ata.lambdaexpressions.classroom.exception.RecipeNotFoundException;
import com.ata.lambdaexpressions.classroom.model.Carton;
import com.ata.lambdaexpressions.classroom.model.Ingredient;
import com.ata.lambdaexpressions.classroom.model.Recipe;
import com.ata.lambdaexpressions.classroom.model.Sundae;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Provides Ice Cream Parlor functionality.
 */
public class IceCreamParlorService {
    private final RecipeDao recipeDao;
    private final CartonDao cartonDao;
    private final IceCreamMaker iceCreamMaker;

    /**
     * Constructs service with the provided DAOs.
     * @param recipeDao the RecipeDao to use for accessing recipes
     * @param cartonDao the CartonDao to use for accessing ice cream cartons
     */
    @Inject
    public IceCreamParlorService(RecipeDao recipeDao, CartonDao cartonDao, IceCreamMaker iceCreamMaker) {
        this.recipeDao = recipeDao;
        this.cartonDao = cartonDao;
        this.iceCreamMaker = iceCreamMaker;
    }

    /**
     * Creates and returns the sundae defined by the given ice cream flavors.
     * If a flavor is not found or we have none of that flavor left, the sundae
     * is returned, but without that flavor. (We'll only charge the customer for
     * the scoops they are returned)
     * @param flavorNames List of flavors to include in the sundae
     * @return The newly created Sundae
     */
    public Sundae getSundae(List<String> flavorNames) {
        // This does the filtering out of any unknown flavors, so only
        // Cartons of known flavors will be returned.
        List<Carton> cartons = cartonDao.getCartonsByFlavorNames(flavorNames);

        // PHASE 1: Use removeIf() to remove any empty cartons from cartons
        // Pass each carton to a Lambda expression to see if it's empty

        cartons.removeIf((carton) -> carton.isEmpty());

        // Alternative using method reference instead of Lambda expression
        //cartons.removeIf(Carton::isEmpty);

        return buildSundae(cartons);
    }

    @VisibleForTesting
    Sundae buildSundae(List<Carton> cartons) {
        Sundae sundae = new Sundae();

        // PHASE 2: Use forEach() to add one scoop of each flavor
        // remaining in cartons

        // Go through the list of Cartons we are given and get one scoop for the sundae
        // Since cartons is a List we can use the List forEach instead of Stream forEach

        cartons.forEach((aCarton) -> {sundae.addScoop(aCarton.getFlavor());});

        return sundae;
    }

    /**
     * Prepares the specified flavors, creating 1 carton of each provided
     * flavor.
     *
     * A flavor name that doesn't correspond
     * to a known recipe will result in CartonCreationFailedException, and
     * no Cartons will be created.
     *
     * @param flavorNames List of names of flavors to create new batches of
     * @return the number of cartons produced by the ice cream maker
     */
    public int prepareFlavors(List<String> flavorNames) {
        List<Recipe> recipes = map( // This is a helper method called map provided in the App - NOT the Stream map()
            flavorNames, // will be called 'input' in the helper method
            (flavorName) -> { // will be called 'converter' in the helper method
                // trap the checked exception, RecipeNotFoundException, and
                // wrap in a runtime exception because our lambda can't throw
                // checked exceptions
                try {
                    return recipeDao.getRecipe(flavorName);
                } catch (RecipeNotFoundException e) {
                    throw new CartonCreationFailedException("Could not find recipe for " + flavorName, e);
                }
            }
        );

        // PHASE 3: Replace right hand side: use map() to convert List<Recipe> to List<Queue<Ingredient>>

        // Replaced by using a Stream map() method
        //List<Queue<Ingredient>> ingredientQueues = new ArrayList<>();
        //
        // Since all we are doing in the Lambda expression is calling a static method in a class
        // we can use a method reference for our Lambda expression

        // Using a method reference
        //List<Queue<Ingredient>> ingredientQueues = recipes.stream().map(RecipeConverter::fromRecipeToIngredientQueue)
        //    .collect(Collectors.toList());

        // Alternative using a Lambda expression
        List<Queue<Ingredient>> ingredientQueues = recipes.stream().map((aRecipe) ->
                        RecipeConverter.fromRecipeToIngredientQueue(aRecipe))
                .collect(Collectors.toList());

        return makeIceCreamCartons(ingredientQueues);
    }

    @VisibleForTesting
    int makeIceCreamCartons(List<Queue<Ingredient>> ingredientQueues) {
        // don't change any of the lines that touch cartonsCreated.
        int cartonsCreated = 0;
        for (Queue<Ingredient> ingredients : ingredientQueues) {

            // PHASE 4: provide Supplier to prepareIceCream()
            // Use a no-arg Lambda expression to get elements out of the Queue
            if (iceCreamMaker.prepareIceCreamCarton(() -> ingredients.poll())) {
                cartonsCreated++;
            }
        }

        return cartonsCreated;
    }

    /**
     * Converts input list of type T to a List of type R, where each entry in the return
     * value is the output of converter applied to each entry in input.
     *
     * (We will get to Java streams in a later lesson, at which point we won't need a helper method
     * like this.)
     *
     * @param List
     * @param Functional Reference - named method or Lambda expression
     *
     * Generic data types are used when methods are needed to do the same processing on different data types
     *
     * <T, R> - indicates that two generic data types will be referenced
     *                   the 1st we'll call 'T'
     *                   the 2nd we'll call 'R'
     *
     * List<T> - indicates a List of the 1st generic data type
     * List<R> - indicates a List of the 2nd generic data type
     *
     * Function<T, R> - indicates a method that has two parameters, 1st is type 'T',
     *                   2nd is type 'R'
     */
    // This method will return a List of 2nd generic data type
    // and receive a List of the 1st generic data type
    // and a method that receives two parameters one of the 1st generic type and one of the 2nd generic type
    private <T, R> List<R> map(List<T> input, Function<T, R> converter) {
        // Use the Stream interface map() method to run the converter method (2nd parameter) passed to it
        return input.stream()
            .map(converter) // pass the function we received to the Stream map() method
            .collect(Collectors.toList());
    }
}
