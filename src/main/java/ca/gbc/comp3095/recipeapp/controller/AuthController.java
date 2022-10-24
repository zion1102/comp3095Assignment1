package ca.gbc.comp3095.recipeapp.controller;

/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <All of the app routing and api calls are located in this file. Also provides data flow between backend and frontend.>
 *********************************************************************************/

import ca.gbc.comp3095.recipeapp.dto.UserDto;
import ca.gbc.comp3095.recipeapp.entity.Recipe;
import ca.gbc.comp3095.recipeapp.entity.User;
import ca.gbc.comp3095.recipeapp.repository.RecipeRepository;
import ca.gbc.comp3095.recipeapp.repository.UserRepository;
import ca.gbc.comp3095.recipeapp.service.RecipeService;
import ca.gbc.comp3095.recipeapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    private UserService userService;
    private RecipeService recipeService;
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private static final String VIEWS_RECIPES_CREATE_OR_UPDATE_FORM = "create";

    public AuthController(UserService userService, RecipeService recipeService, RecipeRepository recipeRepository,UserRepository userRepository) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
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


    @GetMapping("/userProfile/{id}")
    public ModelAndView userProfile() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.setViewName("userProfile");
        return modelAndView;
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

    @PostMapping("/recipes/save")
    public String save(@Valid Recipe recipe,BindingResult result, ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());


        recipeRepository.save(recipe);

        user.addRecipe(recipe);
        userRepository.save(user);
        return "redirect:/recipes";
    }


    @GetMapping("/recipes/new")
    public String initCreationForm( ModelMap model) {
        Recipe recipe = new Recipe();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
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
