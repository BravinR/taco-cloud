package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.data.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo) {
		super();
		this.ingredientRepo = ingredientRepo;
	}
	@GetMapping
	public String showDesignForm(Model model){
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			  model.addAttribute(type.toString().toLowerCase(),
			      filterByType(ingredients, type));
			}
		model.addAttribute("design", new Taco());
		return "design";
			
	}
	  @PostMapping
	  public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
	    if (errors.hasErrors()) {
	      return "design";
	    }

	    // Save the taCo design...
	    // We'll do this in chapter 3
	    log.info("Processing design: " + design);

	    return "redirect:/orders/current";
	  }
	
//	@PostMapping
//	public String processDesign(Design design) {
//	 // Save the TaCo design...
//	 // We'll do this in chapter 3
//	 log.info("Processing design: " + design);
//	 return "redirect:/orders/current";
//	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

    return ingredients.stream()
            .filter(x -> x.getType().equals(type))
            .collect(Collectors.toList());

}
	

}
