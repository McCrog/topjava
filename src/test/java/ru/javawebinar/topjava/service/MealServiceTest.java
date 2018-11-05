package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_MEAL_1_ID, USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() throws Exception {
        service.get(USER_MEAL_1_ID, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_MEAL_1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEAL_6, USER_MEAL_5, USER_MEAL_4, USER_MEAL_3, USER_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherUserMeal() throws Exception {
        service.delete(USER_MEAL_1_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        assertMatch(service.getBetweenDates(USER_MEAL_1.getDate(), USER_MEAL_1.getDate(), USER_ID), USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        assertMatch(service.getBetweenDateTimes(USER_MEAL_1.getDateTime(), USER_MEAL_2.getDateTime(), USER_ID), USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), USER_MEAL_6, USER_MEAL_5, USER_MEAL_4, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_1_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(USER_MEAL_1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUserMeal() throws Exception {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(
                LocalDateTime.of(2015, 6, 30, 10, 0,0),
                "Завтрак", 500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, USER_MEAL_6, USER_MEAL_5, USER_MEAL_4, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }
}