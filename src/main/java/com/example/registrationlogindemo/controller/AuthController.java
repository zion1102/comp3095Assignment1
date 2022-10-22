package com.example.registrationlogindemo.controller;


import com.example.registrationlogindemo.dto.RecipeDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Recipe;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.RecipeService;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;
    private RecipeService recipeService;
    private static final String VIEWS_RECIPES_CREATE_OR_UPDATE_FORM = "createOrUpdatePetForm";
    public AuthController(UserService userService,  RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public ModelAndView listRegisteredUsers(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("recipes", recipeService.findAllRecipes());
        modelAndView.setViewName("users");
        return modelAndView;
    }
    @RequestMapping("/recipe/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.setViewName("createOrUpdatePetForm");
        return modelAndView;
    }
    @RequestMapping("/recipes/save")
    public String save(@RequestParam String name, @RequestParam String ingredients, @RequestParam String instructions, @RequestParam Long prepTime, @RequestParam Long cookTime) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(name);
        recipeDto.setIngredients(ingredients);
        recipeDto.setInstructions(instructions);
        recipeDto.setPrepTime(prepTime);
        recipeDto.setCookTime(cookTime);
        recipeService.saveRecipe(recipeDto);

        return "redirect:/users" ;
    }

    @GetMapping("/recipes/new")
    public String initCreationForm(User user, ModelMap model) {
        Recipe recipe = new Recipe();
        user.addRecipe(recipe);
        model.put("recipe", recipe);
        return VIEWS_RECIPES_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/recipes/new")
    public String processCreationForm(User user, @Valid Recipe recipe, BindingResult result, ModelMap model) {
        if (StringUtils.hasLength(recipe.getName()) && recipe.isNew() && user.getRecipe(recipe.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }

        user.addRecipe(recipe);
        if (result.hasErrors()) {
            model.put("recipe", recipe);
            return VIEWS_RECIPES_CREATE_OR_UPDATE_FORM;
        }

        return "redirect:/users";
    }
}
