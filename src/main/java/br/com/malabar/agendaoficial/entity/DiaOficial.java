package br.com.malabar.agendaoficial.entity;

import java.util.List;

import lombok.Data;

@Data
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

}
