package br.com.malabar.agendaoficial.scraper;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.malabar.agendaoficial.agendaenum.TipoCompromissoEnum;
import br.com.malabar.agendaoficial.entity.Compromisso;
import br.com.malabar.agendaoficial.util.DateUtil;
import lombok.extern.java.Log;

@Log
@Component
public class AgendaScraper {

	private final String url = "https://www.gov.br/planalto/pt-br/acompanhe-o-planalto/agenda-do-presidente-da-republica/";
	private final String linha = "item-compromisso";
	private final String compromissoTitulo = "compromisso-titulo";
	private final String compromissoLocal = "compromisso-local";
	private final String horaInicio = "compromisso-inicio";
	private final String horaFim = "compromisso-fim";
	private final String viagem = "Partida de";
	
	@Autowired
	private DateUtil dateUtil;
	
	
	
	public List<Compromisso> scrapAgenda(String data){
		List<Compromisso> lista = new ArrayList<Compromisso>();
		
		try {
			log.info("Processando URL: "+url+data);
			Document doc = Jsoup.connect(url+data).get();
			
			Elements linhasCompromisso = doc.getElementsByClass(linha);
			
			if(!linhasCompromisso.isEmpty()) {
				
				for(Element linhaCompromisso : linhasCompromisso) {
					Compromisso compromisso = new Compromisso(linhaCompromisso.getElementsByClass(compromissoLocal).text().trim().replace(";", " "), 
															  linhaCompromisso.getElementsByClass(compromissoTitulo).text().trim().replace(";", " "), 
															  linhaCompromisso.getElementsByClass(horaInicio).text().trim(), 
															  linhaCompromisso.getElementsByClass(horaFim).text().trim(),
															  url+data,
															  dateUtil.dateUsToBr(data));
					
					compromisso.setUrl(url+data);
					loadDataToCompromisso(compromisso);
					
					if(compromisso.getDescricao().contains(viagem)) {
						compromisso.setTipoCompromisso(TipoCompromissoEnum.Viagem);
					}
					else {
						compromisso.setTipoCompromisso(TipoCompromissoEnum.Compromisso);
					}
					
					
					log.info("Objeto extraido: "+compromisso);
					lista.add(compromisso);
				}
	
			}
			
			
		} catch (IOException e) {
			log.severe("Erro ao processar p??gina de destino");
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public List<Compromisso> scrapAgenda(){
		List<Compromisso> lista = this.scrapAgenda(this.buildToDayDateUrl());
		
		return lista;
	}
	
	
	private String buildToDayDateUrl() {
		LocalDate hoje = LocalDate.now();
		
		return hoje.getYear()+"-"+
			   StringUtils.leftPad(hoje.getMonthValue()+"", 2, "0")+"-"+
		       StringUtils.leftPad(hoje.getDayOfMonth()+"", 2, "0");
	}
	
	private void loadDataToCompromisso(Compromisso compromisso) {
		LocalDate hoje = LocalDate.now();
		String[] inicioHoraMinuto = compromisso.getHoraInicio().split("h");
		String[] fimHoraMinuto = compromisso.getHoraFim().split("h");
		
		LocalDateTime agora = LocalDateTime.of(hoje.getYear(), hoje.getMonthValue(), hoje.getDayOfMonth(), new Integer(inicioHoraMinuto[0]), new Integer(inicioHoraMinuto[1]));
		LocalDateTime depois = LocalDateTime.of(hoje.getYear(), hoje.getMonthValue(), hoje.getDayOfMonth(), new Integer(fimHoraMinuto[0]), new Integer(fimHoraMinuto[1]));
		
		Duration between = Duration.between(agora, depois);
		
		compromisso.setTempoMinutos(between.toMinutes());
	}
	
	
}
