package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public DataLoader(RecipeRepository recipeRepository,
                      CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository,
                      IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = recipeRepository.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        Set<Recipe> recipes = recipeRepository.findAll();
        Set<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findAll();
        Set<Category> categories = categoryRepository.findAll();

        Category mexican = categoryRepository.findByDescription("Mexican").get();

        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        UnitOfMeasure dash = unitOfMeasureRepository.findByDescription("Dash").get();
        UnitOfMeasure item = unitOfMeasureRepository.findByDescription("Item").get();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();

        Ingredient avacado = new Ingredient();
        avacado.setAmount(BigDecimal.valueOf(2));
        avacado.setDescription("ripe avocados");
        avacado.setUom(item);

        Ingredient salt = new Ingredient();
        salt.setAmount(BigDecimal.valueOf(0.5));
        salt.setDescription("kosher salt");
        salt.setUom(teaspoon);

        Ingredient juice = new Ingredient();
        juice.setUom(tablespoon);
        juice.setDescription("fresh lime juice or lemon juice");
        juice.setAmount(BigDecimal.valueOf(1));

        Ingredient onion = new Ingredient();
        onion.setUom(tablespoon);
        onion.setAmount(BigDecimal.valueOf(2));
        onion.setDescription("minced red onion or thinly sliced green onion");

        Ingredient chilies = new Ingredient();
        chilies.setUom(item);
        chilies.setAmount(BigDecimal.valueOf(1));
        chilies.setDescription("serrano chiles, stems and seeds removed, minced");

        Ingredient cilantro = new Ingredient();
        cilantro.setUom(tablespoon);
        cilantro.setAmount(BigDecimal.valueOf(2));
        cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");

        Ingredient tomato = new Ingredient();
        tomato.setUom(item);
        tomato.setAmount(BigDecimal.valueOf(0.5));
        tomato.setDescription("ripe tomato");

        Set<Category> mexicanCategories = new HashSet<>();
        mexicanCategories.add(mexican);
        Set<Ingredient> tacoIngredients = new HashSet<>();
        tacoIngredients.add(avacado);
        tacoIngredients.add(salt);
        tacoIngredients.add(juice);
        tacoIngredients.add(onion);
        tacoIngredients.add(chilies);
        tacoIngredients.add(cilantro);
        tacoIngredients.add(tomato);

        Recipe tacoRecipe = new Recipe();

        avacado.setRecipe(tacoRecipe);
        tacoRecipe.setCategories(mexicanCategories);
        tacoRecipe.setIngredients(tacoIngredients);
        tacoRecipe.setDescription("Guacamole");
        tacoRecipe.setCookTime(120);
        tacoRecipe.setDifficulty(Difficulty.EASY);
        tacoRecipe.setDirections("1. Cut avocado, remove flesh\n2. Mash with as fork\n3.Add salt, lime juice, and the rest\n4.Cover with plastic and chilll to store");

        Recipe savedTacoRecipe = recipeRepository.save(tacoRecipe);

        System.out.println(savedTacoRecipe.toString());
    }
}
