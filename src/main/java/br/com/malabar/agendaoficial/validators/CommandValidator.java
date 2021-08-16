package br.com.malabar.agendaoficial.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.malabar.agendaoficial.entity.Commands;
import br.com.malabar.agendaoficial.exceptions.ComandoObrigatorioException;
import br.com.malabar.agendaoficial.exceptions.DataFormatoErradoExcepion;

@Component
public class CommandValidator {
	
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	
	public void validator(Commands commands) throws ComandoObrigatorioException, DataFormatoErradoExcepion {
		
		List<String> datas = new ArrayList<String>();
		
		if(commands.getDataFinal() != null && commands.getDataInicial() == null) {
			throw new ComandoObrigatorioException("Obrigatorio informar dataInicial");
		}
		
		if(commands.getDataInicial() != null) {
			datas.add(commands.getDataInicial());
		}
		
		if(commands.getDataFinal() != null) {
			datas.add(commands.getDataFinal());
		}
		
		for (String item : datas) {
			if(item.split("-").length == 1) {
				throw new DataFormatoErradoExcepion("Formato de data deve ser informada com mm-AAAA");
			}
			if(item.split("-")[0].length() != 2) {
				throw new DataFormatoErradoExcepion("Mês deve ser informado com dois digitos. Siga o formato da mascara mm-AAAA");
			}
			if(item.split("-")[1].length() != 4) {
				throw new DataFormatoErradoExcepion("Ano deve ser informado com quatro digitos. Siga o formato da mascara mm-AAAA");
			}
			
			if(!pattern.matcher(item.split("-")[0]).matches()) {
				throw new DataFormatoErradoExcepion("Mês informado não é um número válido. Verifique se as datas informadas estão usando o padrão mm-AAAA");
			}
			if(!pattern.matcher(item.split("-")[1]).matches()) {
				throw new DataFormatoErradoExcepion("Ano informado não é um número válido. Verifique se as datas informadas estão usando o padrão mm-AAAA");
			}
			
		}
		
	}

}
