package br.com.events.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.events.models.Convidados;
import br.com.events.models.Evento;
import br.com.events.repository.ConvidadosRepository;
import br.com.events.repository.EventoRepository;

@Controller
public class EventoControllers {

	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadosRepository cr;
	
	
	@RequestMapping(value="/cadastrar", method=RequestMethod.GET)
	public String form() {
		return "formEvento";
	}
	
	@RequestMapping(value="/cadastrar", method=RequestMethod.POST)
	public String form(Evento evento) {
		
		er.save(evento);
		
		return "redirect:/listar";
	}
	
	@RequestMapping("/listar")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("detalhesEvento");
		mv.addObject("evento", evento);
		
		
		Iterable<Convidados> convidado = cr.findByEvento(evento);
		mv.addObject("convidados", convidado);
		return mv;
		
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		
		
		return "redirect:/listar";
	}
	
	
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidados convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long cod = evento.getCodigo();
		String codigo = "" + cod;
		
		
		return "redirect:/" + codigo;
	}
	
	
	
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidados convidado, 
			BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("msg", "Verifique os campos!");
			return "redirect:/{codigo}";
		}else {
			Evento evento = er.findByCodigo(codigo);
			convidado.setEvento(evento);
			cr.save(convidado);
			attributes.addFlashAttribute("msg", "Convidado adicionado com sucesso");
			return "redirect:/{codigo}" ;
		}
		
	}
}
