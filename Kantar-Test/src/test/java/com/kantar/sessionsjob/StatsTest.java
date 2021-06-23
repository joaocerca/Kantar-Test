package com.kantar.sessionsjob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    public Stats stats = new Stats(1234, "101", "20200101180000", "Live");

    @Test
    public void testConstructor(){

        assertEquals(1234, stats.getHouseNumber());
        assertEquals("101", stats.getChannel());
        assertEquals("20200101180000", stats.getStartTime());
        assertEquals("Live", stats.getActivity());

    }


    @Test
    public void should_CheckDurationTime(){

        stats.setEndTime("20200101203000");

        String recommended = stats.calculateDuration("20200101180000", "20200101203000");

        assertEquals("9060", "9060");

    }

}