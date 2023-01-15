package br.com.malabar.agendaoficial.service;

import br.com.malabar.agendaoficial.entity.Compromisso;
import br.com.malabar.agendaoficial.exceptions.SemCompromissoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiaOficialServiceTest {

    @Autowired
    private DiaOficialService diaOficialService;

    @MockBean
    private CompromissoService compromissoService;


    @Test
    void getDiaOficialCompromissos_ShouldReturnSemCompromissoException(){
        when(compromissoService.getCompromissos()).thenReturn(new ArrayList<Compromisso>());
        SemCompromissoException semCompromissoException = assertThrows(SemCompromissoException.class, () -> diaOficialService.getDiaOficialCompromissos());
        assertNotNull(semCompromissoException);
    }
}