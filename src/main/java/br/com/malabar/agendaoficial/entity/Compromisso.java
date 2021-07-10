package br.com.malabar.agendaoficial.entity;

import br.com.malabar.agendaoficial.agendaenum.TipoCompromissoEnum;
import lombok.Data;

@Data
public class Compromisso {
	private String descricao;
	private String local;
	private String url;
	private String horaInicio;
	private String horaFim;
	private Long tempoMinutos;
	private TipoCompromissoEnum tipoCompromisso;
	
	public Compromisso() {}
	
	public Compromisso(String horaInicio, String horaFim) {
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
	}
	
	public Compromisso(String local, String descricao, String horaInicio, String horaFim, String url) {
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.local = local;
		this.descricao = descricao;
		this.url = url;
	}

}
