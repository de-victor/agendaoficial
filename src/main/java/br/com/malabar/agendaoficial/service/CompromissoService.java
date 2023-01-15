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

	private final AgendaScraper agendaScraper;

	public CompromissoService(AgendaScraper agendaScraper){
		this.agendaScraper = agendaScraper;
	}
	
	public List<Compromisso> getCompromissos(){
		return agendaScraper.scrapAgenda();
	}
	
}
