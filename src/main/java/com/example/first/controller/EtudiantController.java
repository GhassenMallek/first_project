package com.example.first.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.expression.Mvc;

import com.example.first.entities.Etudiant;

@RequestMapping("/etudiant")
@Controller
public class EtudiantController {
	List<Etudiant> etudiants = new ArrayList<>();
	{
		etudiants.add(new Etudiant(1, "ghassen", "aaa@gmail.com"));
		etudiants.add(new Etudiant(2, "haboub", "aaa@gmail.com"));
		etudiants.add(new Etudiant(3, "Ghazi", "aaa@gmail.com"));
	}

	@RequestMapping("/home")
	public String message(Model model) {
		System.out.println("Hello world ! ");
		String formation = "fullstack";
		model.addAttribute("training", formation);
		return "info";
	}

	@RequestMapping("/products")
	// public String listProduct(Model model){
	public ModelAndView listProduct() {

		ModelAndView mv = new ModelAndView();

		List<String> products = new ArrayList<>();
		products.add("Ghassen");
		products.add("Haboub");
		// System.out.println(products);
		// model.addAttribute("test", products);
		mv.addObject("test", products);
		mv.setViewName("products");
		return mv;

		// return"products";

	}

	@RequestMapping("/students")
	public ModelAndView listStudents() {

		ModelAndView mv = new ModelAndView();
		System.out.println(etudiants);
		mv.addObject("students", etudiants);
		mv.setViewName("listStudents");
		return mv;
	}

	// methode ajouter
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addStudentForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addStudent");
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addStudent(@RequestParam("id") int id, @RequestParam("nomEtudiant") String nom,
			@RequestParam("email") String email) {

		Etudiant e = new Etudiant(id, nom, email);
		etudiants.add(e);

		return "redirect:students";
	}

	// supprimer
	@GetMapping("/delete/{id}")
	public String suppression(@PathVariable("id") int id) {

		System.out.println("id =" + id);
		// code suppression

		Etudiant e = null;
		e = recherche(etudiants, id);
		etudiants.remove(e);
		return "redirect:../students";
	}

	// updaate
	@GetMapping("/update/{id}")
	public ModelAndView getUpdateForm(@PathVariable("id") int id) // id = ide
	{
		System.out.println("id = " + id);
		// on va supprimer ici
		Etudiant e = null;
		e = recherche(etudiants, id);
		// etudiants.remove(e);
		ModelAndView mv = new ModelAndView();
		mv.addObject("etudiant", e);
		mv.setViewName("updateStudent");
		return mv;
	}

	@PostMapping("/update")
	public String updateEtudiant(Etudiant etudiant) // id = ide
	{
		// System.out.println(etudiant);
		int index = rechercheIndex(etudiants, etudiant);
		etudiants.set(index, etudiant);
		return "redirect:students";
	}

	private Etudiant recherche(List<Etudiant> le, int index) {
		Etudiant temp = null;
		for (Etudiant e : le) {
			if (e.getId() == index) {
				temp = e;
				return e;
			}
		}
		return temp;
	}

	private int rechercheIndex(List<Etudiant> le, Etudiant e) {
		int compteur = -1;
		for (Etudiant temp : le) {
			compteur++;
			if (temp.getId() == e.getId()) {
				return compteur;
			}

		}
		return compteur;
	}

}
