package br.com.malabar.agendaoficial.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.malabar.agendaoficial.entity.Commands;
import br.com.malabar.agendaoficial.entity.DiaOficial;
import br.com.malabar.agendaoficial.exceptions.ComandoObrigatorioException;
import br.com.malabar.agendaoficial.exceptions.DataFormatoErradoExcepion;
import br.com.malabar.agendaoficial.exceptions.SemCompromissoException;
import br.com.malabar.agendaoficial.validators.CommandValidator;
import lombok.extern.java.Log;

@Log
@Service
public class CommandLineService {
	
	private DiaOficialService diaOficialService;
	private CompromissoCsvService compromissoCsvService;
	private CommandValidator commandValidator;
	
	@Autowired
	public CommandLineService(DiaOficialService diaOficialService, CompromissoCsvService compromissoCsvService, CommandValidator commandValidator) {
		this.diaOficialService = diaOficialService;
		this.compromissoCsvService = compromissoCsvService;
		this.commandValidator = commandValidator;
	}
	
	
	private String dataInicial = "dataInicial";
	private String dataFinal = "dataFinal";
	
	public void commandsProcessor(String[] args) throws InterruptedException, IOException, ComandoObrigatorioException, DataFormatoErradoExcepion {
		List<String> list = Arrays.asList(args);
		if(list.isEmpty()) {
			noCommandInput();
		}
		else {
			execCommandInputs(list);
		}
	}
	
	private void execCommandInputs(List<String> list) throws InterruptedException, IOException, ComandoObrigatorioException, DataFormatoErradoExcepion {
		Commands commands = loadCommandsInObj(list);
		
		if(commands.getDataInicial() != null && commands.getDataFinal() == null) {
			compromissoCsvService.agendaToCsv(commands.getDataInicial());
		}
		
		if(commands.getDataInicial() != null && commands.getDataFinal() != null) {
			compromissoCsvService.agendaToCsv(commands.getDataInicial(), commands.getDataFinal());
		}
		
	}
	
	private Commands loadCommandsInObj(List<String> list) throws ComandoObrigatorioException, DataFormatoErradoExcepion {
		Commands commands = new Commands();
		list.forEach(item -> {
			if(item.contains(dataInicial)) {
				String data = item.split("=")[1];
				commands.setDataInicial(data);
			}
			if(item.contains(dataFinal)) {
				String data = item.split("=")[1];
				commands.setDataFinal(data);
			}
		});
		
		commandValidator.validator(commands);
		
		return commands;
	}

	private void noCommandInput() {
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
