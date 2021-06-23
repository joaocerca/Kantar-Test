package com.kantar.sessionsjob;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    private Stats stats;
    private List<Stats> statsList = new ArrayList<>();

    @BeforeEach
    void setUp(){ this.stats = new Stats(800, "42", "20150101080000", "Recording");}

    @ParameterizedTest(name = "houseNumber{0}, channel{1}, startTime{2}, activity{3}")
    @CsvFileSource(delimiter ='|', files = "src/test/resources/input-statements.psv", numLinesToSkip = 1)
    public void testAssertValuesAreExtractedFromFile(int houseNo, String channel, String startTime, String activity) {

        Stats expectedStats = new Stats(houseNo, channel, startTime, activity);

        assertAll(
                () -> assertNotEquals(expectedStats.getHouseNumber(), stats.getHouseNumber()),
                () -> assertNotEquals(expectedStats.getChannel(), stats.getChannel()),
                () -> assertNotEquals(expectedStats.getStartTime(), stats.getStartTime()),
                () -> assertNotEquals(expectedStats.getActivity(), stats.getActivity())

        );

    }

    @Test
    public void testAssertList() {

        //given
        statsList = Arrays.asList(
                new Stats(1234, "101","20200101180000","Live"),
                new Stats(1234,"102","20200101183000","Live"),
                new Stats(1234,"601","20200101203000","PlayBack"),
                new Stats(45678,"103","20200101190000","PlayBack")
        );


    }


}