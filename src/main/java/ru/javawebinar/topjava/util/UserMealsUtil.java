package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList,
                                                                        LocalTime startTime,
                                                                        LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dates = new HashMap<>();
        for (UserMeal um : mealList) {
            LocalDate umDate = um.getDateTime().toLocalDate();
            if (dates.containsKey(umDate)) {
                dates.put(umDate, dates.get(umDate) + um.getCalories());
            } else {
                dates.put(umDate, um.getCalories());
            }
        }
        List<UserMealWithExceed> withExceeds = new LinkedList<>();
        for (UserMeal um : mealList) {
            if (TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)) {
                if (dates.get(um.getDateTime().toLocalDate()) > caloriesPerDay) {
                    withExceeds.add(
                            new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), true)
                    );
                } else {
                    withExceeds.add(
                            new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), false)
                    );
                }
            }
        }
        return withExceeds;
    }
}
