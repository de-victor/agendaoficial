package br.com.malabar.agendaoficial.exceptions;

public class ComandoObrigatorioException extends Exception {

	private static final long serialVersionUID = -3559245572695649518L;
	
	public ComandoObrigatorioException(String msg) {
		super(msg);
	}

}
