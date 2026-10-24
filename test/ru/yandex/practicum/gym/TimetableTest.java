package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Map<TimeOfDay, List<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size());
        Assertions.assertEquals(1, monday.get(new TimeOfDay(13, 0)).size());
        Assertions.assertEquals(singleTrainingSession, monday.get(new TimeOfDay(13, 0)).get(0));

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());

    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Map<TimeOfDay, List<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size());
        Assertions.assertEquals(mondayChildTrainingSession, monday.get(new TimeOfDay(13, 0)).get(0));

        Map<TimeOfDay, List<TrainingSession>> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursday.size());
        List<TimeOfDay> thursdayTimes = new ArrayList<>(thursday.keySet());
        Assertions.assertEquals(new TimeOfDay(13, 0), thursdayTimes.get(0));
        Assertions.assertEquals(new TimeOfDay(20, 0), thursdayTimes.get(1));
        Assertions.assertEquals(thursdayChildTrainingSession, thursday.get(thursdayTimes.get(0)).get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, thursday.get(thursdayTimes.get(1)).get(0));

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());

    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> at13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, at13.size());
        Assertions.assertEquals(singleTrainingSession, at13.get(0));

        Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeSeveralSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Семёнов", "Виктор", "Константинович");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession first = new TrainingSession(groupChild, coach1, DayOfWeek.FRIDAY, new TimeOfDay(18, 0));
        TrainingSession second = new TrainingSession(groupAdult, coach2, DayOfWeek.FRIDAY, new TimeOfDay(18, 0));

        timetable.addNewTrainingSession(first);
        timetable.addNewTrainingSession(second);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.FRIDAY, new TimeOfDay(18, 0));
        Assertions.assertEquals(2, sessions.size());
        Assertions.assertTrue(sessions.contains(first));
        Assertions.assertTrue(sessions.contains(second));
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).isEmpty());
        Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0)).isEmpty());

    }

    @Test
    void testSessionsAddedInRandomOrderAreSortedAndDaysDoNotMix() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession evening = new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(19, 30));
        TrainingSession morning = new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));
        TrainingSession noon = new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 15));
        TrainingSession sundayMorning = new TrainingSession(group, coach, DayOfWeek.SUNDAY, new TimeOfDay(9, 0));

        timetable.addNewTrainingSession(evening);
        timetable.addNewTrainingSession(morning);
        timetable.addNewTrainingSession(noon);
        timetable.addNewTrainingSession(sundayMorning);

        List<TimeOfDay> times = new ArrayList<>(timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).keySet());
        Assertions.assertEquals(3, times.size());
        Assertions.assertEquals(new TimeOfDay(9, 0), times.get(0));
        Assertions.assertEquals(new TimeOfDay(12, 15), times.get(1));
        Assertions.assertEquals(new TimeOfDay(19, 30), times.get(2));

        List<TrainingSession> sunday = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.SUNDAY, new TimeOfDay(9, 0));
        Assertions.assertEquals(1, sunday.size());
        Assertions.assertEquals(sundayMorning, sunday.get(0));
    }
}


