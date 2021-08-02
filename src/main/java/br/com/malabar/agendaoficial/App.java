package br.com.malabar.agendaoficial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.malabar.agendaoficial.service.CommandLineService;



@SpringBootApplication
public class App implements CommandLineRunner {
	
	@Autowired
	private CommandLineService commandLineService;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		commandLineService.commandsProcessor(args);
		
	}
}
