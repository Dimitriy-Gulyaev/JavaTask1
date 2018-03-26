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

        @Override
        public int hashCode() {

            return Objects.hash(name, departure);
        }

        final String name;
        final int departure;
        final String endStation;
        ArrayList<String> stations;

        /**
         * Constructor for Train object.
         * Hours and minutes are converted into one value for simplified comparison of Train objects.
         */
        Train(String s, int hours, int minutes, String e) {
            if (s == null || s == "") throw new IllegalArgumentException("Wrong name format");
            if (e == null || e == "") throw new IllegalArgumentException("Wrong end station's name format");
            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59)
                throw new IllegalArgumentException("Wrong time format");
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
            if (station == null || station == "") throw new IllegalArgumentException("Invalid station name");
            if (this.stations.contains(station)) throw new IllegalArgumentException("Train already has this station");
            else this.stations.add(station);
        }

        public boolean deleteStation(String station) {
            if (station == null || station == "") throw new IllegalArgumentException("Invalid station name");
            if (this.stations.contains(station)) this.stations.remove(station);
            else return false;
            return true;
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
     *                                  and departure time.
     */
    public void addTrain(String name, int hours, int minutes, String endSt) {
        Train temp = new Train(name, hours, minutes, endSt);
        trains.forEach(train -> {
            if (train.equals(temp))
                throw new IllegalArgumentException(
                        "An entry of such train already exists, please delete old entry first");
        });
        trains.add(temp);
    }

    /**
     * Deletes train by name and departure time
     */
    public boolean deleteTrain(String name, int hours, int minutes) {
        int i = 0;
        Train temp = new Train(name, hours, minutes, "Any Street");
        while (i < trains.size()) {
            Train train = trains.get(i);
            if (train.equals(temp)) {
                trains.remove(i);
                return true;
            } else i++;
        }
        return false;
    }

    /**
     * Seeks for train by station and current time.
     *
     * @param destination - needed station.
     * @param hours       - current hours.
     * @param minutes     - current minutes. These two parameters computed into one which is compared to each train's
     *                    departure time.
     * @return Train object which departure time is the closest to current time or null if such train doesn't exist.
     */
    public Train findTrain(String destination, int hours, int minutes) {
        int currentTime = 60 * hours + minutes;
        int min = currentTime;
        Optional<Train> wanted = Optional.empty();
        for (int i = 0; i < trains.size(); i++) {
            Train temp = trains.get(i);
            if (temp.departure - currentTime > 0
                    && temp.departure - currentTime < min
                    && (temp.stations.contains(destination) || temp.endStation.equals(destination))) {
                min = temp.departure - currentTime;
                wanted = Optional.of(temp);
            }
        }
        return wanted.orElseThrow(() -> new NoSuchElementException("No such train found"));
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
