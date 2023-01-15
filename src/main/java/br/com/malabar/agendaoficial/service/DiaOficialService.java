package br.com.malabar.agendaoficial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.malabar.agendaoficial.agendaenum.TipoCompromissoEnum;
import br.com.malabar.agendaoficial.entity.Compromisso;
import br.com.malabar.agendaoficial.entity.DiaOficial;
import br.com.malabar.agendaoficial.exceptions.SemCompromissoException;

@Service
public class DiaOficialService {
	

	private final CompromissoService compromissoService;

	public DiaOficialService(CompromissoService compromissoService){
		this.compromissoService = compromissoService;
	}

	@Deprecated
	public DiaOficial getDiaOficial() throws SemCompromissoException  {
		
		List<Compromisso> compromissos = compromissoService.getCompromissos();
		
		if(compromissos.isEmpty()) {
			throw new SemCompromissoException();
		}
		
		return loadCompromissosToDiaOficial(compromissos);
	}

	public List<Compromisso> getDiaOficialCompromissos() throws SemCompromissoException {
		return Optional.ofNullable(compromissoService.getCompromissos()).orElseThrow(SemCompromissoException::new);
	}

	@Deprecated
	private DiaOficial loadCompromissosToDiaOficial(List<Compromisso> compromissos)  {
		
		DiaOficial diaOficial = new DiaOficial();
		
		diaOficial.setCompromissos(compromissos);
		diaOficial.setUrl(diaOficial.getCompromissos().get(0).getUrl());
		
		
		diaOficial.setTotalMinutosEmAgenda(diaOficial.getCompromissos().stream()
																	   .map(item ->{return item.getTempoMinutos();})
																	   .reduce(Long.valueOf(0), (a,b) -> a+b));
		
		diaOficial.setTotalMinutosViajados(diaOficial.getCompromissos().stream()
																	   .filter(item -> item.getTipoCompromisso().equals(TipoCompromissoEnum.Viagem))
																	   .map(item ->{return item.getTempoMinutos();})
																	   .reduce(Long.valueOf(0), (a,b)-> a+b));
		
		diaOficial.setTotalMinutosCompromissos(diaOficial.getCompromissos().stream()
																	   	   .filter(item -> item.getTipoCompromisso().equals(TipoCompromissoEnum.Compromisso))
																	   	   .map(item ->{return item.getTempoMinutos();})
																	   	   .reduce(Long.valueOf(0), (a,b)-> a+b));
		
		if(diaOficial.getTotalMinutosCompromissos() != null && diaOficial.getTotalMinutosCompromissos() > 0) {
			diaOficial.setHorasTrabalhadas(diaOficial.getTotalMinutosCompromissos()/60);
			diaOficial.setMinutosTrabalhados(diaOficial.getTotalMinutosCompromissos()%60);
		}
		else {
			diaOficial.setHorasTrabalhadas(Long.valueOf(0));
			diaOficial.setMinutosTrabalhados(Long.valueOf(0));
		}

		if(diaOficial.getTotalMinutosViajados() != null && diaOficial.getTotalMinutosViajados() > 0) {
			diaOficial.setHorasViajadas(diaOficial.getTotalMinutosViajados()/60);
			diaOficial.setMinutosViajadas(diaOficial.getTotalMinutosViajados()%60);
		}
		else {
			diaOficial.setHorasViajadas(Long.valueOf(0));
			diaOficial.setMinutosViajadas(Long.valueOf(0));
		}

		return diaOficial;
	}
}
