package trainschedule;

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

    static final class Train {

        final String name;

        @Override
        public int hashCode() {

            return Objects.hash(name);
        }

        final int departure;
        final String endStation;
        ArrayList<String> stations;

        Train(String s, int hours, int minutes, String e) {
            this.name = s;
            this.departure = 60 * hours + minutes;
            this.endStation = e;
            this.stations = new ArrayList<>();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || object.getClass() != this.getClass()) return false;
            if (object instanceof Train) {
                Train other = (Train) object;
                return this.name.equals(other.name);
            }
            return false;
        }
        /**
         * Предполагается, что имя каждого поезда уникально. Иначе класс станет неудобным для пользователя.
         * Если сравнивать поезда по всем параметрам, для удаления поезда придётся подавать на вход массив промежуточных
         * станций, что крайне неудобно.
         */
    }

    public void addStation(Train tr, String station) {
        tr.stations.add(station);
    }

    public void deleteStation(Train tr, String station) {
        tr.stations.remove(station);
    }

    public void addTrain(String name, int hours, int minutes, String endSt) {
        if (hours < 0 || hours > 24 || minutes < 0 || minutes > 60)
            throw new IllegalArgumentException("Wrong time format");
        trains.forEach(train -> {
            if (train.name.equals(name))
                throw new IllegalArgumentException(
                        "An entry of a train with such name already exists, please delete old entry first");
        });
        trains.add(new Train(name, hours, minutes, endSt));
    }

    public void deleteTrain(String name) {
        int i = 0;
        while (i < trains.size()) {
            if (trains.get(i).name.equals(name)) {
                trains.remove(i);
                break;
            } else i++;
        }
    }

    public Train findTrain(String destination, int hours, int minutes) {
        int currentTime = hours * 60 + minutes;
        int min = currentTime;
        Train wanted = trains.get(0);
        for (int i = 0; i < trains.size(); i++) {
            if (trains.get(i).departure - currentTime > 0
                    && trains.get(i).departure - currentTime < min
                    && (trains.get(i).stations.contains(destination) || trains.get(i).endStation.equals(destination))) {
                min = trains.get(i).departure - currentTime;
                wanted = trains.get(i);
            }
        }
        if (min != currentTime) {
            return wanted;
        } else {
            throw new NoSuchElementException("No suitable trains found");
        }
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
