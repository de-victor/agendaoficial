package br.com.malabar.agendaoficial.service;

import br.com.malabar.agendaoficial.exceptions.ComandoObrigatorioException;
import br.com.malabar.agendaoficial.exceptions.DataFormatoErradoExcepion;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommandLineServiceTest {


    @Autowired
    private CommandLineService commandLineService;

    @Test
    void commandsProcessor_ShouldWithOnlyDataFinalCommandWillThrowComandoObrigatorioException() throws ComandoObrigatorioException, DataFormatoErradoExcepion, IOException, InterruptedException {
        String[] commands = {"dataFinal=01-2023"};
        ComandoObrigatorioException comandoObrigatorioException = assertThrows(ComandoObrigatorioException.class, () -> commandLineService.commandsProcessor(commands));
        assertNotNull(comandoObrigatorioException);
    }

    @Test
    void commandsProcessor_ShouldThrowDataNaoValidaExecptionWithWrongDate() throws ComandoObrigatorioException, DataFormatoErradoExcepion, IOException, InterruptedException {
        String[] commands = {"dataInicial=a1-23"};
        DataFormatoErradoExcepion comandoObrigatorioException = assertThrows(DataFormatoErradoExcepion.class, () -> commandLineService.commandsProcessor(commands));
        assertNotNull(comandoObrigatorioException);
    }
}