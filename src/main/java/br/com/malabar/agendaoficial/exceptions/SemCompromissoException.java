package br.com.malabar.agendaoficial.exceptions;

public class SemCompromissoException extends Exception {

	private static final long serialVersionUID = -7925492012138888038L;
	private final static String msg = "Presidente não trabalhou hoje";
	
	public SemCompromissoException() {
		super(msg);
	}

}
