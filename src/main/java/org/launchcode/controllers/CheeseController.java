package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;
    
    @Autowired
	private CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {
    	
    	model.addAttribute("cheeses", cheeseDao.findAll());
    	model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese, @RequestParam int categoryId,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            return "cheese/add";
        }
        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";
    }
    
    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId) {
    	
    	model.addAttribute("cheese", cheeseDao.findOne(cheeseId));
    	model.addAttribute("categories", categoryDao.findAll());
    	model.addAttribute("title", "Edit the "+ cheeseDao.findOne(cheeseId).getName());
    	
    	return "cheese/edit";
    }
    
    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(@PathVariable int cheeseId, int categoryId, String name, String description, Model model) {
    	
    	Cheese theCheese = cheeseDao.findOne(cheeseId);
    	theCheese.setName(name);
    	theCheese.setDescription(description);
    	theCheese.setCategory(categoryDao.findOne(categoryId));
    	cheeseDao.save(theCheese);

    	return "redirect:/cheese";
    }
    
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value="category/{catId}", method=RequestMethod.GET)
	public String category(Model model, @PathVariable int catId) {
    	List<Cheese> results = new ArrayList<>();
    	
    	for (Cheese cheese : cheeseDao.findAll()) {
    		if (cheese.getCategory().equals(categoryDao.findOne(catId))) {
    			results.add(cheese);
    		}
    	}
    	
    	model.addAttribute("cheeses", results);
    	model.addAttribute("title", "Results for Category: "+categoryDao.findOne(catId).getName());
    	
    	return "cheese/index";
    }
}
