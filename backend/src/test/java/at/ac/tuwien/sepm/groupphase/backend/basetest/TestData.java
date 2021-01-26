package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TimeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.Employee_TasksRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;

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

    Long EMPLOYEE_ID = 1L;
    String EMPLOYEE_LAST_NAME = "Mustermann";
    String EMPLOYEE_FIRST_NAME = "Max";
    String EMPLOYEE_EMAIL = "Max.Mustermann@muster.com";
    String EMPLOYEE_EMAIL_2 = "erika.musterfrau@muster.com";
    String EMPLOYEE_PASSWORD = "password1234";
    String EMPLOYEE_PUBLIC_INFO = "This is the public information of Max Mustermann";
    Gender EMPLOYEE_GENDER = Gender.MALE;
    LocalDateTime EMPLOYEE_BIRTH_DATE = LocalDateTime.of(2000, 12, 4, 0, 0, 0, 0);
    Set<TimeDto> EMPLOYEE_TIMESET = null;

    String EDIT_EMPLOYEE_LAST_NAME = "Musterfrau";
    String EDIT_EMPLOYEE_FIRST_NAME = "Erika";

    Long EMPLOYER_ID = 2L;
    String EMPLOYER_LAST_NAME = "Musterfrau";
    String EMPLOYER_FIRST_NAME = "Erika";
    String EMPLOYER_EMAIL = "Erika.Musterfrau@muster.com";
    String EMPLOYER_EMAIL_2 = "max.mustermann@muster.com";
    String EMPLOYER_PASSWORD = "password1234";
    String EMPLOYER_PUBLIC_INFO = "This is the public information of Erika Musterfrau";
    String EMPLOYER_COMPANY_NAME = "Muster Corp.";
    String EMPLOYER_COMPANY_DESCRIPTION = "Muster Corp. Description";

    String EDIT_EMPLOYER_COMPANY_NAME = "Musterfrau Corp.";
    String EDIT_EMPLOYER_COMPANY_DESCRIPTION = "Musterfrau Corp. Description";

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";
    String EVENTS_BASE_URI = BASE_URI + "/events";
    String INTERESTAREAS_BASE_URI = BASE_URI + "/interestareas";

    Long ADDRESS_ID = 1L;
    String CITY = "Linz";
    String STATE = "Upper Austria";
    Integer ZIP = 4020;
    String ADDRESS_LINE = "Baumstrasse 5";
    String ADDITIONAL = "Tuer 17";

    Long INTEREST_AREA_ID = 1L;
    String AREA = "Promo";
    String DESCRIPTION = "Flyer verteilen";
    Set<Interest> INTERESTS = null;
    Set<Task> TASKS = null;

    Long INTEREST_ID = 1L;
    String INTEREST_NAME = "Fotografie";
    String INTEREST_DESCRIPTION = "fotografieren";
    Employee INTEREST_EMPLOYEE = null;

    Long TASK_ID = 1L;
    String DESCRIPTION_TASK = "Flyer verteilen";
    Integer EMPLOYEE_COUNT = 3;
    Double PAYMENT_HOURLY = 10.0;
    Event EVENT = null;
    Set<Employee_Tasks> EMPLOYEES = null;
    InterestArea INTEREST_AREA = null;

    Long EVENT_ID = 1L;
    LocalDateTime START = LocalDateTime.of(2022, 11, 13, 12, 0, 0, 0);
    LocalDateTime END = LocalDateTime.of(2022, 11, 13, 18, 0, 0, 0);
    LocalDateTime START_OVER = LocalDateTime.of(2020, 11, 13, 12, 0, 0, 0);
    LocalDateTime END_OVER = LocalDateTime.of(2020, 11, 13, 18, 0, 0, 0);
    String TITLE_EVENT = "Flyer verteilen";
    String DESCRIPTION_EVENT = "Flyer verteilen fuer einen Smoothie Konzern am Karlsplatz";
    Employer EMPLOYER = null;
    Address ADDRESS = null;
    Set<Task> TASKS_EVENT = null;
    String REGISTER_EMPLOYEE_BASE_URI = BASE_URI + "/profiles/employee/register";
    String REGISTER_EMPLOYER_BASE_URI = BASE_URI + "/profiles/employer/register";
    String GET_EMPLOYEE_BASE_URI = BASE_URI + "/profiles/employee";
    String GET_ALL_EMPLOYEES_BASE_URI = BASE_URI + "/profiles/employees";
    String GET_EMPLOYER_BASE_URI = BASE_URI + "/profiles/employer";
    String GET_ALL_EMPLOYERS_BASE_URI = BASE_URI + "/profiles/employers";
    String EDIT_EMPLOYEE_BASE_URI = BASE_URI + "/profiles/employee";
    String EDIT_EMPLOYER_BASE_URI = BASE_URI + "/profiles/employer";
    String GET_INTERESTS_BASE_URI = BASE_URI + "/interests";
    String EDIT_PASSWORD_BASE_URI = BASE_URI + "/profiles/updatePassword";
    String CONTACT_BASE_URI = BASE_URI + "/profiles/contact";
    String DELETE_EMPLOYER_BASE_URI = BASE_URI + "/profiles/employer";
    String DELETE_EMPLOYEE_BASE_URI = BASE_URI + "/profiles/employee";
    String NOTIFICATION_BASE_URI = BASE_URI + "/notifications";
    String CHANGE_FAVORITE_NOTIFICATION_BASE_URI = BASE_URI + "/notifications/changeFavorite";
    String FILTER_EMPLOYEES_SMART_BASE_URI = BASE_URI + "/profiles/employee/filter/smart";
    String FILTER_EMPLOYEES_BASE_URI = BASE_URI +  "/profiles/employee/filter";

    Long TIME_ID = 1L;
    LocalDateTime START_TIME = LocalDateTime.of(2022, 11, 13, 12, 0, 0, 0);
    LocalDateTime END_TIME = LocalDateTime.of(2022, 11, 13, 18, 0, 0, 0);
    LocalDateTime FINAL_END_TIME = LocalDateTime.of(2022, 11, 13, 18, 0, 0, 0);
    Employee EMPLOYEE_TIME = null;
    Boolean VISIBLE = false;
    Long REF_ID = 1L;

    static Employee getNewEmployee() {
        return Employee.EmployeeBuilder.aEmployee()
            .withProfile(Profile.ProfileBuilder.aProfile()
                .isEmployer(false)
                .withEmail(EMPLOYEE_EMAIL)
                .withName(EMPLOYEE_LAST_NAME)
                .withForename(EMPLOYEE_FIRST_NAME)
                .withPassword(EMPLOYEE_PASSWORD)
                .build())
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();
    }

    static Employer getNewEmployer() {
        return Employer.EmployerBuilder.aEmployer()
            .withProfile(Profile.ProfileBuilder.aProfile()
                .isEmployer(true)
                .withEmail(EMPLOYER_EMAIL)
                .withName(EMPLOYER_LAST_NAME)
                .withForename(EMPLOYER_FIRST_NAME)
                .withPassword(EMPLOYER_PASSWORD)
                .build())
            .withCompanyName(EMPLOYER_COMPANY_NAME)
            .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
            .build();
    }

    static Address getNewAddress() {
        return Address.AddressBuilder.aAddress()
            .withCity(CITY)
            .withState(STATE)
            .withZip(ZIP)
            .withAddressLine(ADDRESS_LINE)
            .withAdditional(ADDITIONAL)
            .build();
    }


    static InterestArea getNewInterestArea() {
        return InterestArea.InterestAreaBuilder.aInterest()
            .withArea(AREA)
            .withDescription(DESCRIPTION)
            .withInterests(INTERESTS)
            .withTasks(TASKS)
            .build();
    }

    static Interest getNewInterest() {
        return Interest.InterestBuilder.aInterest()
            .withId(INTEREST_ID)
            .withName(INTEREST_NAME)
            .withDescription(INTEREST_DESCRIPTION)
            .withInterestArea(getNewInterestArea())
            .withEmployee(INTEREST_EMPLOYEE)
            .build();
    }

    static Task getNewTask() {
        return Task.TaskBuilder.aTask()
            .withDescription(DESCRIPTION_TASK)
            .withEmployeeCount(EMPLOYEE_COUNT)
            .withPaymentHourly(PAYMENT_HOURLY)
            .withEvent(EVENT)
            .withEmployees(EMPLOYEES)
            .withInterestArea(INTEREST_AREA)
            .build();
    }

    static Event getNewEvent() {
        return Event.EventBuilder.aEvent()
            .withStart(START_TIME)
            .withEnd(END_TIME)
            .withTitle(TITLE_EVENT)
            .withDescription(DESCRIPTION_EVENT)
            .withEmployer(getNewEmployer())
            .withAddress(getNewAddress())
            .withTask(TASKS_EVENT)
            .build();
    }

    static Time getNewTime() {
        return Time.TimeBuilder.aTime()
            .withId(TIME_ID)
            .withStart(START_TIME)
            .withEnd(END_TIME)
            .withFinalEndDate(FINAL_END_TIME)
            .withEmployee(EMPLOYEE_TIME)
            .withVisible(VISIBLE)
            .withRef_Id(REF_ID)
            .build();
    }

    static Notification getNewNotification() {
        return Notification.NotificationBuilder.aNotification()
            .withId(null)
            .withMessage("Notification message")
            .withType(NotificationType.APPLICATION.name())
            .withSeen(false)
            .withEvent(null)
            .withRecipient(null)
            .withSender(null)
            .withTask(null)
            .withFavorite(false)
            .build();
    }

    String ADMIN_USER = "admin@email.com";
    List<String> ADMIN_ROLES = new ArrayList<>() {
        {
            add("ROLE_ADMIN");
            add("ROLE_USER");
            add("ROLE_EMPLOYER");
            add("ROLE_EMPLOYEE");
        }
    };
    String DEFAULT_USER = "admin@email.com";
    List<String> USER_ROLES = new ArrayList<>() {
        {
            add("ROLE_USER");
            add("ROLE_EMPLOYEE");
        }
    };

}
