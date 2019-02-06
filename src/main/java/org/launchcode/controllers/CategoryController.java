package org.launchcode.controllers;

import javax.validation.Valid;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("category")
public class CategoryController {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@RequestMapping(value="")
	public String index(Model model) {
		
		model.addAttribute("categories", categoryDao.findAll());
		model.addAttribute("title", "My Categories");
		
		return "category/index";
	}
	
	@RequestMapping(value="add", method= RequestMethod.GET)
	public String add(Model model) {
		
		model.addAttribute("title", "Add Category");
		model.addAttribute(new Category());
		
		return "category/add";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String add(Model model, @ModelAttribute @Valid Category category, Errors errors) {
		
		if (errors.hasErrors()) {
			return "category/add";
		}else {
			categoryDao.save(category);
			return "redirect:";
		}
	}
	
}
