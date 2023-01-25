package br.com.events.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.events.models.Evento;
import br.com.events.repository.EventoRepository;



@Controller
public class IndexController {

	@Autowired
	private EventoRepository er;
		@RequestMapping("/")
	public ModelAndView index() {
			ModelAndView mv = new ModelAndView("index");
			Iterable<Evento> eventos = er.findAll();
			mv.addObject("eventos", eventos);
			return mv;
	}
}
