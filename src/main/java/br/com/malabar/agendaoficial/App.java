package br.com.malabar.agendaoficial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.malabar.agendaoficial.entity.DiaOficial;
import br.com.malabar.agendaoficial.exceptions.SemCompromissoException;
import br.com.malabar.agendaoficial.service.DiaOficialService;
import lombok.extern.java.Log;



@SpringBootApplication
@Log
public class App implements CommandLineRunner {
	
	@Autowired
	DiaOficialService diaOficialService;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			DiaOficial diaOficial = diaOficialService.getDiaOficial();
			log.info(diaOficial.getUrl());
			log.info("Presidente trabalhou "+diaOficial.getHorasTrabalhadas()+" hora(s) e "+diaOficial.getMinutosTrabalhados()+" minuto(s)");
			log.info("Presidente viajou "+diaOficial.getHorasViajadas()+" hora(s) e "+diaOficial.getMinutosViajadas()+" minuto(s)");
			
		} catch (SemCompromissoException e) {
			log.info(e.getMessage());
		}
		
	}
}
