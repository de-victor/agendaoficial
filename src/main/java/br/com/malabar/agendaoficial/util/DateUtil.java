package br.com.malabar.agendaoficial.util;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	public String dateToString(LocalDate hoje) {
		return hoje.getYear()+"-"+
			   StringUtils.leftPad(hoje.getMonthValue()+"", 2, "0")+"-"+
		       StringUtils.leftPad(hoje.getDayOfMonth()+"", 2, "0");
	}

	@Deprecated
	public String todayDateToString() {
		LocalDate hoje = LocalDate.now();
		
		return hoje.getYear()+"-"+
			   StringUtils.leftPad(hoje.getMonthValue()+"", 2, "0")+"-"+
		       StringUtils.leftPad(hoje.getDayOfMonth()+"", 2, "0");
	}
	
	public String dateUsToBr(String date) {
		String[] split = date.split("-");
		
		return split[2]+"-"+split[1]+"-"+split[0];
	}
	
	
	
}
