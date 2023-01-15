package br.com.malabar.agendaoficial.util;

import org.apache.commons.lang3.StringUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DateUtilTest {

    @Autowired
    private DateUtil dateUtil;

    private String dateToString;

    @BeforeEach
    void init(){
        dateToString = dateUtil.dateToString(LocalDate.now());
    }

    @Test
    void dateToString_ShouldBeAStringDateFormated(){
        assertInstanceOf(String.class, dateToString);
        itShouldContains2Dash();
    }
    @Test
    void dateToString_ShouldContain4DigitsYear(){
        assertEquals(4, dateToString.split("-")[0].length());
    }

    @Test
    void dateToString_ShouldMonthLessThan2DigistsBeginsWith0(){
        String month = dateUtil.dateToString(LocalDate.of(2023, 1, 1)).
                split("-")[1];
        assertTrue(month.startsWith("0"));
    }
    @Test
    void dateToString_ShouldDayLessThan2DigistsBeginsWith0(){
        String month = dateUtil.dateToString(LocalDate.of(2023, 1, 1)).
                split("-")[2];
        assertTrue(month.startsWith("0"));
    }

    @Test
    void dateUsToBr_ShouldEndWithYear(){
        String ano = dateUtil.dateUsToBr("2023-01-01").split("-")[2];
        assertEquals("2023", ano);
    }

    void itShouldContains2Dash(){
        assertEquals(2, StringUtils.countMatches(dateToString, "-"));
        assertEquals(3, dateToString.split("-").length);
    }
}