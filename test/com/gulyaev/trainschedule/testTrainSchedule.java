package com.gulyaev.trainschedule;

import org.junit.Test;

import static org.junit.Assert.*;

import com.gulyaev.trainschedule.TrainSchedule.Train;

import java.util.ArrayList;

/**
 * @author Dmitriy
 */
public class testTrainSchedule {

    public testTrainSchedule() {
    }

    /**
     * Test of addStation method, of class TrainSchedule.
     */
    @Test
    public void testAddStation() {
        Train tr = new Train("Sample", 0, 0, "End Station");
        String station1 = "Sample Town 1";
        String station2 = "Sample Town 2";
        TrainSchedule instance = new TrainSchedule();
        tr.addStation(station1);
        tr.addStation(station2);
        ArrayList<String> ar = new ArrayList<>();
        ar.add("Sample Town 1");
        ar.add("Sample Town 2");
        assertEquals(ar, tr.stations);
    }

    /**
     * Test of deleteStation method, of class TrainSchedule.
     */
    @Test
    public void testDeleteStation() {
        Train tr = new Train("Sample", 0, 0, "End Station");
        String station1 = "Sample Town 1";
        String station2 = "Sample Town 2";
        TrainSchedule instance = new TrainSchedule();
        tr.addStation(station1);
        tr.addStation(station2);
        tr.deleteStation(station1);
        ArrayList<String> ar = new ArrayList<>();
        ar.add("Sample Town 2");
        assertEquals(ar, tr.stations);
    }

    /**
     * Test of addTrain method, of class TrainSchedule.
     */
    @Test
    public void testAddTrain() {
        String name = "";
        int hours = 0;
        int minutes = 0;
        String endSt = "";
        TrainSchedule instance = new TrainSchedule();
        instance.addTrain(name, hours, minutes, endSt);
        ArrayList<Train> trSc = new ArrayList<>();
        trSc.add(new Train(name, hours, minutes, endSt));
        assertEquals(trSc, instance.trains);
        try {
            instance.addTrain("Bombom", 27, 61, "Station");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong time format", e.getMessage());
        }
        try {
            instance.addTrain(name, 1, 1, "Station");
        } catch (IllegalArgumentException e) {
            assertEquals("An entry of such train already exists, please delete old entry first",
                    e.getMessage());
        }
    }

    /**
     * Test of deleteTrain method, of class TrainSchedule.
     */
    @Test
    public void testDeleteTrain() {
        String name = "Sample1";
        TrainSchedule instance = new TrainSchedule();
        instance.addTrain("Sample1", 0, 0, "Sample Town1");
        instance.addTrain("Sample2", 0, 0, "Sample Town2");
        instance.deleteTrain(name, 0, 0);
        ArrayList<Train> ar = new ArrayList<>();
        ar.add(new Train("Sample2", 0, 0, "Sample Town1"));
        assertEquals(ar, instance.trains);
    }

    /**
     * Test of findTrain method, of class TrainSchedule.
     */
    @Test
    public void testFindTrain() {
        String destination = "Sample Town";
        int hours = 15;
        int minutes = 30;
        TrainSchedule instance = new TrainSchedule();
        instance.addTrain("Bombom1", 15, 45, "Station1");
        instance.trains.get(0).stations.add("Sample Town");
        instance.addTrain("Bombom2", 16, 30, "Sample Town");
        instance.addTrain("Bombom3", 15, 29, "Station3");
        TrainSchedule.Train result = instance.findTrain(destination, hours, minutes);
        assertEquals(instance.trains.get(0), result);
        instance = new TrainSchedule();
        instance.addTrain("Bombom", 15, 29, "Station");
        result = instance.findTrain(destination, hours, minutes);
        assertEquals(null, result);
    }

}
