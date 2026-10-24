package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        timetable.computeIfAbsent(day, k -> new TreeMap<>())
                .computeIfAbsent(time, k -> new ArrayList<>())
                .add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessions = timetable.get(dayOfWeek);
        if (sessions == null) {
            return new TreeMap<>();
        }
        return sessions;
    }


    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessions = timetable.get(dayOfWeek);
        if(sessions == null) {
            return new ArrayList<>();
        }
        List<TrainingSession> result = sessions.get(timeOfDay);
        if (result == null) {
            return new ArrayList<>();
        }
        return result;
    }
}
