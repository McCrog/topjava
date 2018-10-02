package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> filteredMealList = mealList
                .stream()
                .filter(s -> TimeUtil.isBetween(s.getDateTime().toLocalTime(), startTime, endTime))
                .sorted(Comparator.comparing(UserMeal::getDateTime))
                .collect(Collectors.groupingBy(s -> s.getDateTime().toLocalDate()));

        List<UserMealWithExceed> list = new ArrayList<>();
        for (Map.Entry<LocalDate, List<UserMeal>> entry : filteredMealList.entrySet()) {
            int[] calories = {0};
            entry.getValue().forEach(s -> {
                calories[0] += s.getCalories();
                list.add(new UserMealWithExceed(
                        s.getDateTime(),
                        s.getDescription(),
                        s.getCalories(),
                        calories[0] >= caloriesPerDay));
            });
        }

        return list;
    }
}
