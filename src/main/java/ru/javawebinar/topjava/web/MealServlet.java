package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.DataUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("redirect to meals");
            List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(DataUtil.getInstance().getMealStorage().getAll(), LocalTime.MIDNIGHT, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealsWithExceeded);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }

        Meal meal;

        switch (action) {
            case "add":
                log.debug("add meal");
                meal = Meal.EMPTY;
                break;
            case "edit":
                log.debug("edit meal");
                meal = DataUtil.getInstance().getMealStorage().read(Integer.parseInt(id));
                break;
            case "delete":
                log.debug("delete and redirect to users");
                DataUtil.getInstance().getMealStorage().delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");

        }

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String description = request.getParameter("description");
        String caloriesParam = request.getParameter("calories");

        int id = Integer.parseInt(idParam);
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
        int calories = Integer.parseInt(caloriesParam);

        final boolean isNotCreated = (id == Integer.MAX_VALUE);

        Meal meal;
        if (isNotCreated) {
            meal = new Meal(localDateTime, description, calories);
        } else {
            meal = DataUtil.getInstance().getMealStorage().read(id);
            meal.setDateTime(localDateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
        }

        if (isNotCreated) {
            log.debug("create meal");
            DataUtil.getInstance().getMealStorage().create(meal);
        } else {
            log.debug("update meal");
            DataUtil.getInstance().getMealStorage().update(meal);
        }

        log.debug("redirect to meals");
        response.sendRedirect("meals");
    }
}
