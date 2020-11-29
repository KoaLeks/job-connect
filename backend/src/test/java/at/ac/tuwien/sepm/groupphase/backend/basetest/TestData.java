package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TestData {

    Long ID = 1L;
    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_TEXT = "TestMessageText";
    LocalDateTime TEST_NEWS_PUBLISHED_AT =
        LocalDateTime.of(2019, 11, 13, 12, 15, 0, 0);

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";
    String EVENTS_BASE_URI = BASE_URI + "/events";
    String ADDRESSES_BASE_URI = BASE_URI + "/addresses";
    String TASKS_BASE_URI = BASE_URI + "/tasks";
    String INTERESTAREAS_BASE_URI = BASE_URI + "/interestareas";

    Long ADDRESS_ID = 1L;
    String CITY = "Linz";
    String STATE = "Upper Austria";
    Integer ZIP = 4020;
    String ADDRESS_LINE = "Baumstraße 5";
    String ADDITIONAL = "Tür 17";

    Long INTEREST_AREA_ID = 1L;
    String AREA = "Promo";
    String DESCRIPTION = "Flyer verteilen";
    Set<Interest> INTERESTS = null;
    Set<Task> TASKS = null;

    Long TASK_ID = 1L;
    String DESCRIPTION_TASK = "Flyer verteilen";
    Integer EMPLOYEE_COUNT = 3;
    Double PAYMENT_HOURLY = 10.0;
    Event EVENT = null;
    Set<Employee> EMPLOYEES = null;
    InterestArea INTEREST_AREA = null;

    Long EVENT_ID = 1L;
    LocalDateTime START = LocalDateTime.of(2019, 11, 13, 12, 0, 0, 0);
    LocalDateTime END = LocalDateTime.of(2019, 11, 13, 18, 0, 0, 0);
    String DESCRIPTION_EVENT = "Flyer verteilen für einen Smoothie Konzern am Karlsplatz";
    Employer EMPLOYER = null;
    Address ADDRESS = null;
    Set<Task> TASKS_EVENT = null;

    String ADMIN_USER = "admin@email.com";
    List<String> ADMIN_ROLES = new ArrayList<>() {
        {
            add("ROLE_ADMIN");
            add("ROLE_USER");
        }
    };
    String DEFAULT_USER = "admin@email.com";
    List<String> USER_ROLES = new ArrayList<>() {
        {
            add("ROLE_USER");
        }
    };

}
