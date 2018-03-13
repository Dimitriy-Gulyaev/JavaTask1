package com.gulyaev.trainschedule;

import java.util.*;

public final class TrainSchedule {

    ArrayList<Train> trains;

    @Override
    public int hashCode() {

        return Objects.hash(trains);
    }

    TrainSchedule() {
        this.trains = new ArrayList<>();
    }

    public static final class Train {

        final String name;

        @Override
        public int hashCode() {

            return Objects.hash(name);
        }

        final int departure;
        final String endStation;
        ArrayList<String> stations;

        /**
         * Constructor for Train object.
         * Hours and minutes are converted into one value for simplified comparison of Train objects.
         */
        Train(String s, int hours, int minutes, String e) {
            this.name = s;
            this.departure = 60 * hours + minutes;
            this.endStation = e;
            this.stations = new ArrayList<>();
        }

        String getName() {
            return this.name;
        }

        int getDeparture() {
            return this.departure;
        }

        String getEndStation() {
            return this.endStation;
        }

        ArrayList<String> getStations() {
            return this.stations;
        }

        public void addStation(String station) {
            this.stations.add(station);
        }

        public void deleteStation(String station) {
            this.stations.remove(station);
        }

        /**
         * Trains considered equal if their names and departure times are equal.
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || object.getClass() != this.getClass()) return false;
            if (object instanceof Train) {
                Train other = (Train) object;
                return this.name.equals(other.name) && this.departure == other.departure;
            }
            return false;
        }
    }

    /**
     * Simply adds new Train object to trains with all parameters required by constructor.
     *
     * @throws IllegalArgumentException if time format is invalid or in case of existence of a train with the same name
     * and departure time.
     */
    public void addTrain(String name, int hours, int minutes, String endSt) {
        if (hours < 0 || hours > 24 || minutes < 0 || minutes > 60)
            throw new IllegalArgumentException("Wrong time format");
        trains.forEach(train -> {
            if (train.name.equals(name) && train.departure == hours * 60 + minutes)
                throw new IllegalArgumentException(
                        "An entry of such train already exists, please delete old entry first");
        });
        trains.add(new Train(name, hours, minutes, endSt));
    }

    /**
     * Deletes train by name and departure time
     */
    public void deleteTrain(String name, int hours, int minutes) {
        int i = 0;
        while (i < trains.size()) {
            if (trains.get(i).name.equals(name) && trains.get(i).departure == 60 * hours + minutes) {
                trains.remove(i);
                break;
            } else i++;
        }
    }

    /**
     * Seeks for train by station and current time.
     *
     * @param destination - needed station.
     * @param hours       - current hours.
     * @param minutes     - current minutes. These two parameters computed into one which is compared to each train's
     * departure time.
     * @return Train object which departure time is the closest to current time or null if such train doesn't exist.
     */
    public Train findTrain(String destination, int hours, int minutes) {
        int currentTime = 60 * hours + minutes;
        int min = currentTime;
        Train wanted = null;
        for (int i = 0; i < trains.size(); i++) {
            if (trains.get(i).departure - currentTime > 0
                    && trains.get(i).departure - currentTime < min
                    && (trains.get(i).stations.contains(destination) || trains.get(i).endStation.equals(destination))) {
                min = trains.get(i).departure - currentTime;
                wanted = trains.get(i);
            }
        }
        return wanted;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || object.getClass() != this.getClass()) return false;
        if (object instanceof TrainSchedule) {
            TrainSchedule other = (TrainSchedule) object;
            return this.trains.equals(other.trains);
        }
        return false;
    }
}
