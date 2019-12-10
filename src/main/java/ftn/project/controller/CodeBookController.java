package ftn.project.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ftn.project.dto.CodeBookDto;
import ftn.project.model.CodeBook;
import ftn.project.services.CodeBookService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/administrators/codebook")
public class CodeBookController {

	private final CodeBookService codeBookService;

	@GetMapping
	public ModelAndView getCodeBook(@ModelAttribute("codeBookDto") CodeBookDto codeBookDto, ModelMap model) {
		model.addAttribute("codeBooksDto", codeBookService.allCodeBooks());
		return new ModelAndView("codeBook", "Model", codeBookService.allCodeBooks());
	}

	@PostMapping("/create")
	public String createCodeBook(@Valid @ModelAttribute("codeBookDto") CodeBookDto codeBookDto) {
		codeBookService.createCodeBook(codeBookDto);
		return "redirect:/administrators/codebook";
	}

	@GetMapping("/delete/{idDto}")
	public String deleteCodeBook(@PathVariable("idDto") Long idDto, ModelMap model) {
		codeBookService.deleteCodeBook(idDto);
		return "redirect:/administrators/codebook";
	}

	@GetMapping("/edit/{idDto}")
	public String getEditPage(@PathVariable("idDto") Long id, ModelMap model) {
		model.addAttribute("codeBookDto", codeBookService.editCodeBook(id));
		return "codeBookEdit";
	}

	@PostMapping("/edit/create")
	public String editCodeBook(@Valid @ModelAttribute("codeBookDto") CodeBookDto codeBookDto ,ModelMap model) {
		codeBookService.createCodeBook(codeBookDto);
		return "redirect:/administrators/codebook";
	}

}