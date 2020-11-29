package at.ac.tuwien.sepm.groupphase.backend.basetest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    String EMPLOYEE_PASSWORD = "password1234";
    String EMPLOYEE_PUBLIC_INFO = "This is the public information of Max Mustermann";

    Long EMPLOYER_ID = 2L;
    String EMPLOYER_LAST_NAME = "Musterfrau";
    String EMPLOYER_FIRST_NAME = "Erika";
    String EMPLOYER_EMAIL = "Erika.Musterfrau@muster.com";
    String EMPLOYER_PASSWORD = "password1234";
    String EMPLOYER_PUBLIC_INFO = "This is the public information of Erika Musterfrau";
    String EMPLOYER_COMPANY_NAME = "Muster Corp.";
    String EMPLOYER_COMPANY_DESCRIPTION = "Muster Corp. Description";

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";
    String REGISTER_EMPLOYEE_BASE_URI = BASE_URI + "/profiles/employee";
    String REGISTER_EMPLOYER_BASE_URI = BASE_URI + "/profiles/employer";

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
