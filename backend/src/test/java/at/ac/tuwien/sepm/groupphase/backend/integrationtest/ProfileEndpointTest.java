package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployeeMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployeeMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProfileEndpointTest implements TestData {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private InterestAreaRepository interestAreaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterEmployeeMapper registerEmployeeMapper;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RegisterEmployerMapper registerEmployerMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployerMapper employerMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Employee_TasksRepository employee_tasksRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Employee employee = Employee.EmployeeBuilder.aEmployee()
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

    private final Employee editEmployee = Employee.EmployeeBuilder.aEmployee()
        .withProfile(Profile.ProfileBuilder.aProfile()
            .isEmployer(false)
            .withEmail(EMPLOYEE_EMAIL)
            .withName(EDIT_EMPLOYEE_LAST_NAME)
            .withForename(EDIT_EMPLOYEE_FIRST_NAME)
            .withPassword(EMPLOYEE_PASSWORD)
            .build())
        .withGender(EMPLOYEE_GENDER)
        .withBirthDate(EMPLOYEE_BIRTH_DATE)
        .build();

    private Employer employer = Employer.EmployerBuilder.aEmployer()
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

    private final Employer editEmployer = Employer.EmployerBuilder.aEmployer()
        .withProfile(Profile.ProfileBuilder.aProfile()
            .isEmployer(true)
            .withEmail(EMPLOYER_EMAIL)
            .withName(EMPLOYER_LAST_NAME)
            .withForename(EMPLOYER_FIRST_NAME)
            .withPassword(EMPLOYER_PASSWORD)
            .build())
        .withCompanyName(EDIT_EMPLOYER_COMPANY_NAME)
        .withDescription(EDIT_EMPLOYER_COMPANY_DESCRIPTION)
        .build();

    private final InterestDto interestDto = InterestDto.InterestDtoBuilder.aInterestDto()
        .withId(null)
        .withName(INTEREST_NAME)
        .withDescription(INTEREST_DESCRIPTION)
        .withSimpleInterestAreaDto(null)
        .build();

    private final SimpleInterestAreaDto simpleInterestAreaDto = SimpleInterestAreaDto.SimpleInterestAreaDtoBuilder.aInterestArea()
        .withId(INTEREST_AREA_ID)
        .withArea(AREA)
        .withDescription(DESCRIPTION)
        .build();

    private final InterestArea interestArea = InterestArea.InterestAreaBuilder.aInterest()
        .withId(INTEREST_AREA_ID)
        .withArea(AREA)
        .withDescription(DESCRIPTION)
        .build();

    private final Time time = Time.TimeBuilder.aTime()
        .withId(TIME_ID)
        .withStart(START_TIME)
        .withEnd(END_TIME)
        .withFinalEndDate(FINAL_END_TIME)
        .withEmployee(EMPLOYEE_TIME)
        .withVisible(VISIBLE)
        .withRef_Id(REF_ID)
        .build();

    private final TimeDto timeDto = TimeDto.TimeDtoBuilder.aTimeDto()
        .withId(TIME_ID)
        .withStart(START_TIME)
        .withEnd(END_TIME)
        .withFinalEndDate(FINAL_END_TIME)
        .withVisible(VISIBLE)
        .withRef_Id(REF_ID)
        .build();

    private final Address address = Address.AddressBuilder.aAddress()
        .withCity(CITY)
        .withState(STATE)
        .withZip(ZIP)
        .withAddressLine(ADDRESS_LINE)
        .withAdditional(ADDITIONAL)
        .build();

    private Event event = Event.EventBuilder.aEvent()
        .withStart(START_OVER)
        .withEnd(END_OVER)
        .withTitle(TITLE_EVENT)
        .withDescription(DESCRIPTION_EVENT)
        .withEmployer(employer)
        .withAddress(address)
        .withTask(TASKS_EVENT)
        .build();

    private final Task task = Task.TaskBuilder.aTask()
        .withDescription(DESCRIPTION_TASK)
        .withEmployeeCount(EMPLOYEE_COUNT)
        .withPaymentHourly(PAYMENT_HOURLY)
        .withEvent(EVENT)
        .withEmployees(EMPLOYEES)
        .withInterestArea(INTEREST_AREA)
        .build();

    @BeforeEach
    public void beforeEach() {
        timeRepository.deleteAll();
        interestRepository.deleteAll();
        interestAreaRepository.deleteAll();
        employee_tasksRepository.deleteAll();
        taskRepository.deleteAll();
        eventRepository.deleteAll();
        employeeRepository.deleteAll();
        employerRepository.deleteAll();
        profileRepository.deleteAll();
        employee = Employee.EmployeeBuilder.aEmployee()
            .withProfile(Profile.ProfileBuilder.aProfile()
                .isEmployer(false)
                .withEmail(EMPLOYEE_EMAIL)
                .withName(EMPLOYEE_LAST_NAME)
                .withForename(EMPLOYEE_FIRST_NAME)
                .withPassword(EMPLOYEE_PASSWORD)
                .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
                .build())
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();
        employer = Employer.EmployerBuilder.aEmployer()
            .withProfile(Profile.ProfileBuilder.aProfile()
                .isEmployer(false)
                .withEmail(EMPLOYER_EMAIL)
                .withName(EMPLOYER_LAST_NAME)
                .withForename(EMPLOYER_FIRST_NAME)
                .withPassword(EMPLOYER_PASSWORD)
                .withPublicInfo(EMPLOYER_PUBLIC_INFO)
                .build())
            .withCompanyName(EMPLOYER_COMPANY_NAME)
            .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
            .build();
        interestDto.setSimpleInterestAreaDto(simpleInterestAreaDto);
        event = Event.EventBuilder.aEvent()
            .withStart(START_OVER)
            .withEnd(END_OVER)
            .withTitle(TITLE_EVENT)
            .withDescription(DESCRIPTION_EVENT)
            .withEmployer(EMPLOYER)
            .withAddress(address)
            .withTask(TASKS_EVENT)
            .build();
    }

    @Test
    public void createValidEmployeeTest() throws Exception {
        String body = objectMapper.writeValueAsString(registerEmployeeMapper.employeeToRegisterEmployeeDto(employee));

        MvcResult mvcResult = this.mockMvc.perform(post(REGISTER_EMPLOYEE_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(employeeRepository.count(), 1);

        assertEquals(profileRepository.count(), 1);
    }

    @Test
    public void createValidEmployerTest() throws Exception {
        String body = objectMapper.writeValueAsString(registerEmployerMapper.employerToRegisterEmployerDto(employer));

        MvcResult mvcResult = this.mockMvc.perform(post(REGISTER_EMPLOYER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(employerRepository.count(), 1);
        assertEquals(profileRepository.count(), 1);
    }

    @Test
    public void tryCreateEmployeeWithoutEmail_LastName_FirstName_Password_Gender_ShouldReturnBadRequest() throws Exception {
        employee.getProfile().setEmail(null);
        employee.getProfile().setPassword(null);
        employee.getProfile().setFirstName(null);
        employee.getProfile().setLastName(null);
        employee.setGender(null);
        String body = objectMapper.writeValueAsString(registerEmployeeMapper.employeeToRegisterEmployeeDto(employee));

        MvcResult mvcResult = this.mockMvc.perform(post(REGISTER_EMPLOYEE_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                String[] errors = content.split(",");
                assertEquals(8, errors.length);
            }
        );
    }

    @Test
    public void tryCreateEmployerWithoutEmail_LastName_FirstName_Password_CompanyName_ShouldReturnBadRequest() throws Exception {
        employer.getProfile().setEmail(null);
        employer.getProfile().setPassword(null);
        employer.getProfile().setFirstName(null);
        employer.getProfile().setLastName(null);
        employer.setCompanyName(null);

        String body = objectMapper.writeValueAsString(registerEmployerMapper.employerToRegisterEmployerDto(employer));

        MvcResult mvcResult = this.mockMvc.perform(post(REGISTER_EMPLOYER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                String[] errors = content.split(",");
                assertEquals(9, errors.length);
            }
        );
    }

    @Test
    public void createEmployeeWithSameEmailsShouldFail() throws Exception {
        String body = objectMapper.writeValueAsString(registerEmployeeMapper.employeeToRegisterEmployeeDto(employee));

        MvcResult mvcResult = this.mockMvc.perform(post(REGISTER_EMPLOYEE_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(employeeRepository.count(), 1);
        assertEquals(profileRepository.count(), 1);

        MvcResult mvcResultFail = this.mockMvc.perform(post(REGISTER_EMPLOYEE_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseFail = mvcResultFail.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), responseFail.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = responseFail.getContentAsString();
                assertEquals("E-Mail Adresse wird bereits verwendet", content);
            }
        );


    }

    @Test
    public void getEmployeeWithNonExistingEmailShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(GET_EMPLOYEE_BASE_URI + EMPLOYEE_EMAIL)
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void getEmployerWithNonExistingEmailShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(GET_EMPLOYER_BASE_URI + EMPLOYER_EMAIL)
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void updateValidEmployeeTest() throws Exception {
        Long id = employeeRepository.save(employee).getId();

        EditEmployeeDto editEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(id)
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployeeDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void updateValidEmployerTest() throws Exception {
        Long id = employerRepository.save(employer).getId();

        EditEmployerDto editEmployerDto = EditEmployerDto.EditEmployerDtoBuilder.aEmployerDto()
            .withProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(id)
                .withEmail(EMPLOYER_EMAIL)
                .withLastName(EMPLOYER_LAST_NAME)
                .withFirstName(EMPLOYER_FIRST_NAME)
                .build())
            .withCompanyName(EDIT_EMPLOYER_COMPANY_NAME)
            .withDescription(EDIT_EMPLOYER_COMPANY_DESCRIPTION)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployerDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYER_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void updateEmployeeWithoutEmail_LastName_FirstName_Password_ShouldReturnBadRequest() throws Exception {
        employee.getProfile().setEmail(null);
        employee.getProfile().setPassword(null);
        employee.getProfile().setFirstName(null);
        employee.getProfile().setLastName(null);

        String body = objectMapper.writeValueAsString(employeeMapper.employeeToEmployeeDto(employee));

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void updateEmployerWithoutEmail_LastName_FirstName_Password_CompanyName_ShouldReturnBadRequest() throws Exception {
        employer.getProfile().setEmail(null);
        employer.getProfile().setPassword(null);
        employer.getProfile().setFirstName(null);
        employer.getProfile().setLastName(null);
        employer.setCompanyName(null);

        String body = objectMapper.writeValueAsString(employerMapper.employerToEmployerDto(employer));

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYER_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void updateProfilePasswordWithNonMatchingPasswords_shouldReturnUnauthorized() throws Exception {
        employeeRepository.save(employee);

        EditPasswordDto editPasswordDto = new EditPasswordDto();
        editPasswordDto.setCurrentPassword(EMPLOYEE_PASSWORD + "wrong");
        editPasswordDto.setNewPassword(EMPLOYEE_PASSWORD + "new");

        String editPasswordBody = objectMapper.writeValueAsString(editPasswordDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_PASSWORD_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editPasswordBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void updateInterestsOfValidEmployeeTest() throws Exception {
        Long id = interestAreaRepository.save(interestArea).getId();
        Long employeeId = employeeRepository.save(employee).getId();

        Set<InterestDto> interestDtoSet = new HashSet<>();
        simpleInterestAreaDto.setId(id);
        interestDto.setSimpleInterestAreaDto(simpleInterestAreaDto);
        interestDtoSet.add(interestDto);

        EditEmployeeDto editEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(employeeId)
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withInterestDtos(interestDtoSet)
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployeeDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(interestRepository.count(), 1);
    }

    @Test
    public void deleteInterestOfValidEmployeeTest() throws Exception {
        Long id = interestAreaRepository.save(interestArea).getId();
        Long employeeId = employeeRepository.save(employee).getId();

        Set<InterestDto> interestDtoSet = new HashSet<>();
        simpleInterestAreaDto.setId(id);
        interestDto.setSimpleInterestAreaDto(simpleInterestAreaDto);
        interestDtoSet.add(interestDto);

        EditEmployeeDto editEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(employeeId)
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withInterestDtos(interestDtoSet)
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployeeDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        // assert that added interest is in db
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(interestRepository.count(), 1);

        EditEmployeeDto newEditEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(employeeId)
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withInterestDtos(null)
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String newEditBody = objectMapper.writeValueAsString(newEditEmployeeDto);

        MvcResult newMvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(newEditBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse newResponse = newMvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), newResponse.getStatus());
        assertEquals(interestRepository.count(), 0);
    }

    @Test
    public void updateTimeOfValidEmployeeTest() throws Exception {
        Long id = employeeRepository.save(employee).getId();

        Set<TimeDto> timeDtoSet = new HashSet<>();
        timeDto.setId(null);
        timeDtoSet.add(timeDto);

        EditEmployeeDto editEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(id)
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withTimes(timeDtoSet)
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployeeDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(timeRepository.count(), 1);
    }

    @Test
    public void deleteTimeOfValidEmployeeTest() throws Exception {
        Long id = employeeRepository.save(employee).getId();

        Set<TimeDto> timeDtoSet = new HashSet<>();
        timeDto.setId(null);
        timeDtoSet.add(timeDto);

        EditEmployeeDto editEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(id)
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withTimes(timeDtoSet)
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployeeDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(timeRepository.count(), 1);

        EditEmployeeDto newEditEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withId(id)
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withTimes(null)
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String newEditBody = objectMapper.writeValueAsString(newEditEmployeeDto);

        MvcResult newMvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(newEditBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse newResponse = newMvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), newResponse.getStatus());
        assertEquals(timeRepository.count(), 0);
    }

    @Test
    public void contactEmployeeWithValidEmail_ShouldReturnOK() throws Exception {
        Profile profile = this.employee.getProfile();
        profile.setEmail("non@existant.address");
        this.employee.setProfile(profile);
        Long id = employeeRepository.save(this.employee).getId();

        ContactMessageDto contactMessageDto = new ContactMessageDto();
        contactMessageDto.setTo(id);
        contactMessageDto.setSubject("testsubject");
        contactMessageDto.setMessage("testmsg");

        String contactBody = objectMapper.writeValueAsString(contactMessageDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CONTACT_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(contactBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deleteEmployerWithEndedEvent_ShouldReturnNoContent() throws Exception {
        Profile profile = profileRepository.save(employer.getProfile());
        employer.setProfile(profile);
        employer.setId(profile.getId());
        Employer e = employerRepository.save(employer);

        Address a = addressRepository.save(address);

        event.setAddress(a);
        event.setEmployer(e);

        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(delete(DELETE_EMPLOYER_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deleteEmployerWithUpcomingEvent_ShouldReturnConflict() throws Exception {
        Profile profile = profileRepository.save(employer.getProfile());
        employer.setProfile(profile);
        employer.setId(profile.getId());
        Employer e = employerRepository.save(employer);

        Address a = addressRepository.save(address);

        event.setAddress(a);
        event.setEmployer(e);
        event.setStart(LocalDateTime.now().plusDays(1));
        event.setEnd(LocalDateTime.now().plusDays(2));

        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(delete(DELETE_EMPLOYER_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }

    @Test
    public void deleteEmployeeWithNoUpcomingTasks_ShouldReturnNoContent() throws Exception {
        Profile profile = profileRepository.save(employee.getProfile());
        employee.setProfile(profile);
        employee.setId(profile.getId());
        Employee employee = employeeRepository.save(this.employee);

        Address a = addressRepository.save(address);
        event.setAddress(a);
        Event event = eventRepository.save(this.event);

        this.task.setEvent(event);
        Task task = taskRepository.save(this.task);

        Employee_Tasks employee_tasks = new Employee_Tasks();
        employee_tasks.setId(1L);
        employee_tasks.setAccepted(true);
        employee_tasks.setEmployee(employee);
        employee_tasks.setTask(task);

        employee_tasksRepository.save(employee_tasks);

        MvcResult mvcResult = this.mockMvc.perform(delete(DELETE_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void deleteEmployeeWithUpcomingTasks_ShouldReturnConflict() throws Exception {
        Profile profile = profileRepository.save(employee.getProfile());
        employee.setProfile(profile);
        employee.setId(profile.getId());
        Employee employee = employeeRepository.save(this.employee);

        Address a = addressRepository.save(address);
        event.setAddress(a);
        event.setStart(LocalDateTime.now().plusDays(1));
        event.setEnd(LocalDateTime.now().plusDays(2));
        Event event = eventRepository.save(this.event);

        this.task.setEvent(event);
        Task task = taskRepository.save(this.task);

        Employee_Tasks employee_tasks = new Employee_Tasks();
        employee_tasks.setId(1L);
        employee_tasks.setAccepted(true);
        employee_tasks.setEmployee(employee);
        employee_tasks.setTask(task);

        employee_tasksRepository.save(employee_tasks);

        MvcResult mvcResult = this.mockMvc.perform(delete(DELETE_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }
}
