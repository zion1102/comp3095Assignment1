package com.example.registrationlogindemo.controller;

import static javax.swing.JOptionPane.showMessageDialog;
import com.example.registrationlogindemo.dto.RecipeDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Recipe;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RecipeRepository;
import com.example.registrationlogindemo.service.RecipeService;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private RecipeRepository recipeRepository;
    private static final String VIEWS_RECIPES_CREATE_OR_UPDATE_FORM = "create";

    public AuthController(UserService userService, RecipeService recipeService, RecipeRepository recipeRepository) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
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

    @GetMapping("/recipes")
    public ModelAndView listRegisteredUsers() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("recipes", recipeRepository.findAll());
        modelAndView.setViewName("recipes");
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

        return "redirect:/recipes";
    }


    @GetMapping("/recipes/new")
    public String initCreationForm(User user, ModelMap model) {
        Recipe recipe = new Recipe();
        user.addRecipe(recipe);
        model.put("recipe", recipe);
        return VIEWS_RECIPES_CREATE_OR_UPDATE_FORM;
    }


    @RequestMapping("/recipes/show/{id}")
    public ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("recipe", recipeRepository.findById(id).orElse(null));
        modelAndView.setViewName("show");
        return modelAndView;
    }

    @GetMapping("/recipes/search")
    public String processFindForm(@RequestParam("name") String name) {


        if (recipeRepository.findByName(name) == null) {
            // no owners found
            return "redirect:/recipes";
        }

        Recipe recipe = recipeRepository.findByName(name);

            // 1 owner found
        return "redirect:/recipes/show/" + recipe.getId();

    }




    @RequestMapping("/recipes/delete")
    public String delete(@RequestParam Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        recipeRepository.delete(recipe);

        return "redirect:/recipes";
    }

    @RequestMapping("/recipes/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("recipe", recipeRepository.findById(id).orElse(null));
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @RequestMapping("/recipes/update")
    public String update(@RequestParam Long id, @RequestParam String name, @RequestParam String ingredients, @RequestParam String instructions, @RequestParam Long prepTime, @RequestParam Long cookTime) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        recipe.setName(name);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipeRepository.save(recipe);

        return "redirect:/recipes";
    }

}
