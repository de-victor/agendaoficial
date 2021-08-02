package br.com.malabar.agendaoficial.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.malabar.agendaoficial.entity.Compromisso;
import br.com.malabar.agendaoficial.scraper.AgendaScraper;
import br.com.malabar.agendaoficial.util.DateUtil;
import lombok.extern.java.Log;

@Service
@Log
public class CompromissoCsvService {
	
	@Autowired
	private AgendaScraper agendaScraper;
	
	@Autowired
	private DateUtil dateUtil;
	
	private final String file = "agenda_oficial.csv";
	private final Long sleepTime = new Long(2000);
	private final LocalDate today = LocalDate.now();
	
	public void agendaToCsv() throws IOException, InterruptedException {
		
		log.info("Criando arquivo CSV");
		csvHeader();
		
		csvBuild(agendaScraper.scrapAgenda(dateUtil.dateToString(LocalDate.now())));
		
		log.info("Arquivo "+file+" criado");
		
	}
	
	public void agendaToCsv(String startDate, String endDate) throws InterruptedException, IOException {
		LocalDate dataInicialConvertida = LocalDate.of(new Integer(startDate.split("-")[1]), new Integer(startDate.split("-")[0]), 1);
		LocalDate dataFinalConvertida = LocalDate.of(new Integer(endDate.split("-")[1]), new Integer(endDate.split("-")[0]), 1);
		
		dataFinalConvertida = dataFinalConvertida.plusMonths(1).minusDays(1);
		
		log.info("Criando arquivo CSV");
		csvHeader();
		
		scrapFromDates(dataInicialConvertida, dataFinalConvertida);
		
		log.info("Arquivo "+file+" criado");
		
	}
	
	public void agendaToCsv(String startDate) throws InterruptedException, IOException {
		Integer month = new Integer(startDate.split("-")[0]);
		Integer year = new Integer(startDate.split("-")[1]);
		
		log.info("Criando arquivo CSV");
		csvHeader();
		
		scrapFromDates(LocalDate.of(year, month, 1), today);
		
		log.info("Arquivo "+file+" criado");
	}
	
	private void scrapFromDates(LocalDate startDate, LocalDate endDate) throws InterruptedException, IOException {
		
		while(startDate.toEpochDay() <= endDate.toEpochDay()) {
			List<Compromisso> listCompromisso = agendaScraper.scrapAgenda(dateUtil.dateToString(startDate));
			csvBuild(listCompromisso);
			startDate = startDate.plusDays(1);
			
			Thread.sleep(sleepTime);
		}
	}
	
	private void csvBuild(List<Compromisso> lista) throws IOException {
		
		
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file, true));
		
		lista.forEach(item ->{
			try {
				buffWrite.append(objectToCsvLine(item) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		buffWrite.close();
		
	}
	
	private void csvHeader() throws IOException {
		
		File arqCheck = new File(file);
		if(arqCheck.exists()) {
			arqCheck.delete();
		}
		
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arqCheck, true));
		buffWrite.append("initial_time;end_time;desc;location;comp_type;data;minutes;url" + "\n");
		buffWrite.close();
	}
	
	
	private String objectToCsvLine(Compromisso compromisso) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(compromisso.getHoraInicio()+";");
		builder.append(compromisso.getHoraFim()+";");
		builder.append(compromisso.getDescricao()+";");
		builder.append(compromisso.getLocal()+";");
		builder.append(compromisso.getTipoCompromisso()+";");
		builder.append(compromisso.getData()+";");
		builder.append(compromisso.getTempoMinutos()+";");
		builder.append(compromisso.getUrl());
		
		
		return builder.toString();
	}

}
