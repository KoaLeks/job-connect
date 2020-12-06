package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EditEmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EditEmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EditProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployeeMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployeeMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployerRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
            .isEmployer(false)
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

    @BeforeEach
    public void beforeEach() {
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
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
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
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
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
                assertEquals("Email address already in use", content);
            }
        );


    }

    @Test
    public void getEmployeeWithBlankEmailShouldReturnBadRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(GET_EMPLOYEE_BASE_URI+"   ")
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void getEmployerWithBlankEmailShouldReturnBadRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(GET_EMPLOYER_BASE_URI+"  ")
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void updateValidEmployeeTest() throws Exception {
        employeeRepository.save(employee);

        EditEmployeeDto editEmployeeDto = EditEmployeeDto.EditEmployeeDtoBuilder.aEmployeeDto()
            .withEditProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withEmail(EMPLOYEE_EMAIL)
                .withFirstName(EDIT_EMPLOYEE_LAST_NAME)
                .withLastName(EDIT_EMPLOYEE_FIRST_NAME)
                .build())
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployeeDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYEE_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updateValidEmployerTest() throws Exception {
        employerRepository.save(employer);

        EditEmployerDto editEmployerDto = EditEmployerDto.EditEmployerDtoBuilder.aEmployerDto()
            .withProfileDto(EditProfileDto.EditProfileDtoBuilder.aEditProfileDto()
                .withEmail(EMPLOYER_EMAIL)
                .withLastName(EMPLOYER_LAST_NAME)
                .withFirstName(EMPLOYER_FIRST_NAME)
                .build())
            .withCompanyName(EDIT_EMPLOYER_COMPANY_NAME)
            .withDescription(EDIT_EMPLOYER_COMPANY_DESCRIPTION)
            .build();

        String editBody = objectMapper.writeValueAsString(editEmployerDto);

        MvcResult mvcResult = this.mockMvc.perform(put(EDIT_EMPLOYER_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(editBody))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
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

}
