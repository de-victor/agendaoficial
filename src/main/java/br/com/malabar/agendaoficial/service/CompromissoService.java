package br.com.malabar.agendaoficial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.malabar.agendaoficial.entity.Compromisso;
import br.com.malabar.agendaoficial.scraper.AgendaScraper;
import lombok.extern.java.Log;

@Service
@Log
public class CompromissoService {
	
	@Autowired
	private AgendaScraper agendaScraper;
	
	
	public void compromissoEmMinutos() {
		agendaScraper = new AgendaScraper();
		List<Compromisso> lista = agendaScraper.scrapAgenda();
		
		if(!lista.isEmpty()) {
			Long minutosTrabalhados = lista.stream()
					.map(item ->{
							return item.getTempoMinutos();
					}).reduce(new Long(0), (a,b) -> a+b);
			
			log.info("Minutos trabalhados pelo presidente hoje: "+minutosTrabalhados);
		}
		else {
			log.info("Presidente não trabalhou hoje");
		}
		
	}
	
	public List<Compromisso> getCompromissos(){
		return agendaScraper.scrapAgenda();
	}
	
}
