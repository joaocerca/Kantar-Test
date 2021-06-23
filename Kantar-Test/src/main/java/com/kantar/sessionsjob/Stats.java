package com.kantar.sessionsjob;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Stats{

    private int houseNumber;
    private String channel;
    private String startTime;
    private String activity;
    private String endTime;
    private String duration;


    public Stats(int houseNumber, String channel, String startTime, String activity) {
        this.houseNumber = houseNumber;
        this.channel = channel;
        this.startTime = startTime;
        this.activity = activity;

    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    @Override
    public String toString() {
        return houseNumber + " - " + channel + " - "
                + startTime + " - " + activity;
    }

    public String getDuration(){
        return duration;
    }

    public void setDuration(String startTime, String endTime){

        this.duration = calculateDuration(startTime, endTime);

    }

    public String calculateDuration(String startTime, String endTime){
        //gets the input format of the timestamps
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date1 = null;
        Date date2 = null;

        try{
            date1 = format.parse(startTime);
            date2 = format.parse(endTime);

            DateTime dt1 = new DateTime(date1);
            DateTime dt2 = new DateTime(date2);

            this.duration = String.valueOf((Minutes.minutesBetween(dt1, dt2).getMinutes() * 60) + 60);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return this.duration;

    }


    public void setEndTimeSoloSession(){

        String dateSub = startTime.substring(0,8);
        endTime = dateSub + "235959";

    }

    public void correctEndTime(){

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        DateTime dt = fmt.parseDateTime(endTime);
        endTime = fmt.print(dt.minusSeconds(1));

    }

}
