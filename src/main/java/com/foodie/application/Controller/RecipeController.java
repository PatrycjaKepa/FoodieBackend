package com.foodie.application.Controller;

import com.foodie.application.Entity.Recipe;
import com.foodie.application.Entity.User;
import com.foodie.application.Repository.RecipeRepository;
import com.foodie.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/recipe")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeController(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/addRecipe")
    public ResponseEntity<String> addRecipe(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("servings") int servings,
            @RequestParam("duration") int duration,
            @RequestParam("ingredients") String ingredients,
            @RequestParam("steps") String steps
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername());


            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
            // Zapisz zdjęcie na serwerze
            String imagePath = saveImage(image);

            // Utwórz i zapisz obiekt Recipe w bazie
            Recipe recipe = new Recipe();
            recipe.setName(name);
            recipe.setServings(servings);
            recipe.setDuration(duration);
            recipe.setIngredients(ingredients);
            recipe.setSteps(steps);
            recipe.setImagePath(imagePath);
            recipe.setUser(user);

            recipeRepository.save(recipe); // Zapisz do bazy

            return ResponseEntity.ok("Recipe and image uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading recipe");
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        UUID uuid = UUID.randomUUID();

        String fileName = image.getOriginalFilename();
        fileName = uuid + fileName;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.getFileName().toString();
    }

    @GetMapping("/userRecipes")
    public Object getRecipesForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        String isEmpty = "Empty";

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Recipe> userRecipes = recipeRepository.findByUser(user);

        if(userRecipes == null || userRecipes.isEmpty()){
            System.out.println(userRecipes);
            return ResponseEntity.ok(isEmpty);
        }else {
            return ResponseEntity.ok(userRecipes);
        }

    }

    @GetMapping("/recipeDetails/{recipeId}")
    public ResponseEntity<Recipe> getRecipeDetails(@PathVariable Long recipeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return recipeRepository.findByIdAndUser(recipeId, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/wezipobierzmizdjencieproszuje/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
