package br.com.malabar.agendaoficial.entity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiaOficial {
	private Long horasTrabalhadas;
	private Long minutosTrabalhados;
	private Long horasViajadas;
	private Long minutosViajadas;
	private Long totalMinutosEmAgenda;
	private Long totalMinutosViajados;
	private Long totalMinutosCompromissos;
	private String url;
	private List<Compromisso> compromissos;

	public DiaOficial(String url, List<Compromisso> compromissos) {
		this.url = url;
		this.compromissos = compromissos;
	}
}
