package com.kantar.sessionsjob;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // See README.txt for usage example

    public static void main(String[] args) {


        if (args.length < 2) {
            System.err.println("Missing arguments: <input-statements-file> <output-sessions-file>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

//        String path = "src/input-statements.psv";

        List<Stats> sortedStats = importValues(inputFile);
        exportValuesToPsv(sortedStats, outputFile);

    }


    public static List<Stats> importValues(String path){

        String line;

        ArrayList<Stats> statsArrayList = new ArrayList<>();
        List<Stats> sortedStats = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            //skips header line
            br.readLine();

            while((line = br.readLine()) != null){
                String[] homePsv = line.split("\\|");
                statsArrayList.add(new Stats(Integer.parseInt(homePsv[0]), homePsv[1], homePsv[2], homePsv[3]));
            }

        }catch(IOException ex){
            ex.printStackTrace();
        }

        //sorts the arrayList first by HouseNo and then by StartTime
        sortedStats = orderValues(statsArrayList);
        adjustEndTime(sortedStats);

        return sortedStats;

    }


    public static List<Stats> orderValues(ArrayList<Stats> statsArrayList){

        Comparator<Stats> compareThem = Comparator.comparing(Stats::getHouseNumber).thenComparing(Stats::getStartTime);

        List<Stats> sortedStats = statsArrayList.stream().sorted(compareThem).collect(Collectors.toList());

        return sortedStats;

    }


    public static List<Stats> adjustEndTime(List<Stats> sortedStats) {

        ListIterator<Stats> iterator = sortedStats.listIterator();

        int i = 0;

        while (iterator.hasNext()) {

            try {
                if ((i == (sortedStats.size() - 1)) || (sortedStats.get(i).getHouseNumber() != sortedStats.get(i + 1).getHouseNumber())) {
                    sortedStats.get(i).setEndTimeSoloSession();

                } else {
                    sortedStats.get(i).setEndTime(sortedStats.get(i + 1).getStartTime());
                    sortedStats.get(i).correctEndTime();
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            i++;

        }

        return sortedStats;
    }


    private static void exportValuesToPsv(List<Stats> sortedStats, String outputFile){

        try{
            Writer writer = Files.newBufferedWriter(Paths.get(outputFile));

            CSVPrinter printer = CSVFormat.newFormat('|').withHeader("HomeNo","Channel","Starttime","Activity","EndTime","Duration").print(writer);

            for (Stats stat : sortedStats) {
                printer.printRecord("\n");
                printer.printRecord(stat.getHouseNumber(), stat.getChannel(), stat.getStartTime(), stat.getActivity(), stat.getEndTime(), stat.getDuration());
            }

            printer.flush();

            writer.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }


}
