package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.AlreadyHandledException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NoAvailableSpacesException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.Employee_TasksService;
import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
import at.ac.tuwien.sepm.groupphase.backend.util.validator.DateValidator;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Profile("generateData")
@Component
public class TestDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String[] companyNames = {
        "Alladins Lamp",
        "Architectural Genie",
        "Asian Answers",
        "Avant Garde Interior Designs",
        "Back To Basics Chiropractic Clinic",
        "Balanced Fortune",
        "Beasts of Beauty",
        "Belle Ladi",
        "Belle Lady",
        "Benesome",
        "Best Biz Survis",
        "Better Business Ideas and Services",
        "Buena Vista Garden Maintenance",
        "First Choice Yard Help",
        "First Rate Choice",
        "Fit Tonic",
        "Flexus",
        "Formula Grey",
        "Four Leaf Clover",
        "Fragrant Flower Lawn Services",
        "Freedom Map",
        "Fresh Start",
        "Friendly Advice",
        "Full Color",
        "Future Bright",
        "Future Plan",
        "Galaxy Man",
        "Gamma Gas",
        "Gamma Realty",
        "Garden Guru",
        "Listen Up",
        "Locost Accessories",
        "Lone Wolf Wealth Planning",
        "Magna Consulting",
        "Matrix Architectural Service",
        "Magik Lamp",
        "Magna Wealth",
        "ManPower",
        "Manu Connection",
        "Maxaprofit"
    };

    String[] names = {
        "Lucy Bauer",
        "Lukas Mueller",
        "Ella Schwartz",
        "Konstantin Hofer",
        "Amy Gruber",
        "Ben Huber",
        "Emely Egger",
        "Jonas Wagner",
        "Finja Schmidt",
        "Elias Weiss",
        "Amelie Wolf",
        "Niklas Haas",
        "Luise Fuchs",
        "David Edner",
        "Frieda Schmid",
        "Oskar Maier",
        "Katharina Weber",
        "Philipp Reiter",
        "Romy Wimmer",
        "Leon Baumgartner",
        "Juna Lackner",
        "Noah Stadler",
        "Theresa Berger",
        "Luis Pichler",
        "Eva Moser",
        "Paul Aigner",
        "Julia Koller",
        "Finn Graf",
        "Anna Lechner",
        "Felix Maier"
    };

    // Employer
    private static final String TEST_EMPLOYER_FIRST_NAME = "FIRST NAME";
    private static final String TEST_EMPLOYER_LAST_NAME = "LAST NAME";
    private static final String TEST_PUBLIC_INFO = "PUBLIC INFO";
    private static final String TEST_PASSWORD = "123456789";

    // Event
    private static final String TEST_EVENT_TITLE1 = "Weihnachtsfeier";
    private static final String TEST_EVENT_TITLE2 = "Putzhilfe";
    private static final String TEST_EVENT_TITLE3 = "BabysitterIn gesucht für 3 jähriges Mädchen";
    private static final String TEST_EVENT_TITLE4 = "Computerunterricht";
    private static final String TEST_EVENT_DESCRIPTION1 = "Unsere Firma plant für den 24.12 Nachmittag eine kleine Feier und wir benötigen KellnerInnen und eine(n) DJ.";
    private static final String TEST_EVENT_DESCRIPTION2 = "Reinigungsarbeit für das gesamte Haus (Küche, Wohnzimmer, Badezimmer, Schlafzimmer), zirka 60m²";
    private static final String TEST_EVENT_DESCRIPTION3 = "Sind auf der Suche nach einem netten Menschen der gerne und gut mit unserer Kleinen umgehen und aufpassen kann!";
    private static final String TEST_EVENT_DESCRIPTION4 = "Unsere Oma hat einen neuen Laptop bekommen und braucht dringend Einstiegshilfe!";

    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final ProfileRepository profileRepository;
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final InterestAreaRepository interestAreaRepository;
    private final InterestRepository interestRepository;
    private final TimeRepository timeRepository;
    private final Employee_TasksService employee_tasksService;
    private final NotificationRepository notificationRepository;
    private final EmployeeService employeeService;


    public TestDataGenerator(EmployeeRepository employeeRepository, EmployerRepository employerRepository,
                             ProfileRepository profileRepository, PasswordEncoder passwordEncoder,
                             EventRepository eventRepository, AddressRepository addressRepository,
                             TaskRepository taskRepository, InterestAreaRepository interestAreaRepository,
                             InterestRepository interestRepository, TimeRepository timeRepository,
                             Employee_TasksService employee_tasksService, NotificationRepository notificationRepository,
                             EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employerRepository = employerRepository;
        this.profileRepository = profileRepository;
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.taskRepository = taskRepository;
        this.interestAreaRepository = interestAreaRepository;
        this.passwordEncoder = passwordEncoder;
        this.interestRepository = interestRepository;
        this.timeRepository = timeRepository;
        this.employee_tasksService = employee_tasksService;
        this.notificationRepository = notificationRepository;
        this.employeeService = employeeService;
    }

    public void generateEmployers() {
        if (employerRepository.findAll().size() > 0) {
            LOGGER.debug("employers already generated");
        } else {
            LOGGER.debug("generating {} employer entries", companyNames.length);
            for (String name : companyNames) {
                at.ac.tuwien.sepm.groupphase.backend.entity.Profile employerProfile =
                    at.ac.tuwien.sepm.groupphase.backend.entity.Profile.ProfileBuilder.aProfile()
                        .withEmail("test@" + name.replace(" ", "").toLowerCase() + ".at")
                        .withForename(TEST_EMPLOYER_FIRST_NAME)
                        .withName(TEST_EMPLOYER_LAST_NAME)
                        .withPassword(passwordEncoder.encode(TEST_PASSWORD))
                        .withPublicInfo(TEST_PUBLIC_INFO)
                        .isEmployer(true)
                        .build();

                Employer employer = Employer.EmployerBuilder.aEmployer()
                    .withCompanyName(name)
                    .withDescription("generated employer")
                    .withProfile(employerProfile)
                    .build();
                LOGGER.debug("saving employer {}", employer);
                Long id = profileRepository.save(employerProfile).getId();
                employer.setId(id);
                employerRepository.save(employer);
            }
        }
    }

    private void generateEmployees() {
        if (employeeRepository.findAll().size() > 0) {
            LOGGER.debug("employees already generated");
        } else {
            LOGGER.debug("generating {} employee entries", names.length);
            int count = 0;
            Faker faker = new Faker();
            for (String name : names) {
                at.ac.tuwien.sepm.groupphase.backend.entity.Profile employeeProfile =
                    at.ac.tuwien.sepm.groupphase.backend.entity.Profile.ProfileBuilder.aProfile()
                        .withEmail(name.replace(" ", ".").toLowerCase() + "@jobconnect.test")
                        .withForename(name.split(" ")[0])
                        .withName(name.split(" ")[1])
                        .withPassword(passwordEncoder.encode(TEST_PASSWORD))
                        .withPublicInfo(TEST_PUBLIC_INFO)
                        .isEmployer(false)
                        .build();

                Employee employee = Employee.EmployeeBuilder.aEmployee()
                    .withGender(count % 2 == 0 ? Gender.FEMALE : Gender.MALE)
                    .withBirthDate(convertToLocalDateTime(faker.date().birthday(18, 35)))
                    .withProfile(employeeProfile)
                    .build();

                LOGGER.debug("saving employee {}", employee);
                Long id = profileRepository.save(employeeProfile).getId();
                employee.setId(id);
                employeeRepository.save(employee);
                count++;
            }
        }
    }

    private void generateInterestAreas() {
        if (interestAreaRepository.findAll().size() > 0) {
            LOGGER.debug("interestAreas already generated");
        } else {
            LOGGER.debug("generating {} interestAreas entries", 16);
            InterestArea interestArea1 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Service")
                .withDescription("Promo (z.B. Flyer verteilen)")
                .build();
            InterestArea interestArea1_1 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Service")
                .withDescription("Verkaufshilfe")
                .build();
            InterestArea interestArea1_2 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Service")
                .withDescription("Messebetreuung")
                .build();
            InterestArea interestArea1_3 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Service")
                .withDescription("KellnerIn")
                .build();
            InterestArea interestArea2 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Haushalt")
                .withDescription("Babysitter")
                .build();
            InterestArea interestArea2_1 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Haushalt")
                .withDescription("Tiersitter")
                .build();
            InterestArea interestArea2_2 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("IT")
                .withDescription("Computerhilfe")
                .build();
            InterestArea interestArea2_3 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Haushalt")
                .withDescription("Gartenhilfe")
                .build();
            InterestArea interestArea2_4 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Haushalt")
                .withDescription("Putzhilfe")
                .build();
            InterestArea interestArea3 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Handwerk")
                .withDescription("Umzugshelfer (vor allem schwere Lasten tragen)")
                .build();
            InterestArea interestArea3_1 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Handwerk")
                .withDescription("Autofahrer")
                .build();
            InterestArea interestArea3_2 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Handwerk")
                .withDescription("Aufbauhilfe (z.B. Bühnen)")
                .build();
            InterestArea interestArea3_3 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Handwerk")
                .withDescription("Aufbauhilfe (z.B. Möbel)")
                .build();
            InterestArea interestArea4 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Musik")
                .withDescription("DJ")
                .build();
            InterestArea interestArea4_1 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("IT")
                .withDescription("Backend Dev")
                .build();
            InterestArea interestArea4_2 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("IT")
                .withDescription("Frontend Dev")
                .build();

            LOGGER.debug("saving interestArea {}", interestArea1);
            interestAreaRepository.save(interestArea1);
            LOGGER.debug("saving interestArea {}", interestArea1_1);
            interestAreaRepository.save(interestArea1_1);
            LOGGER.debug("saving interestArea {}", interestArea1_2);
            interestAreaRepository.save(interestArea1_2);
            LOGGER.debug("saving interestArea {}", interestArea1_3);
            interestAreaRepository.save(interestArea1_3);

            LOGGER.debug("saving interestArea {}", interestArea2);
            interestAreaRepository.save(interestArea2);
            LOGGER.debug("saving interestArea {}", interestArea2_1);
            interestAreaRepository.save(interestArea2_1);
            LOGGER.debug("saving interestArea {}", interestArea2_2);
            interestAreaRepository.save(interestArea2_2);
            LOGGER.debug("saving interestArea {}", interestArea2_3);
            interestAreaRepository.save(interestArea2_3);
            LOGGER.debug("saving interestArea {}", interestArea2_4);
            interestAreaRepository.save(interestArea2_4);

            LOGGER.debug("saving interestArea {}", interestArea3);
            interestAreaRepository.save(interestArea3);
            LOGGER.debug("saving interestArea {}", interestArea3_1);
            interestAreaRepository.save(interestArea3_1);
            LOGGER.debug("saving interestArea {}", interestArea3_2);
            interestAreaRepository.save(interestArea3_2);
            LOGGER.debug("saving interestArea {}", interestArea3_3);
            interestAreaRepository.save(interestArea3_3);

            LOGGER.debug("saving interestArea {}", interestArea4);
            interestAreaRepository.save(interestArea4);
            LOGGER.debug("saving interestArea {}", interestArea4_1);
            interestAreaRepository.save(interestArea4_1);
            LOGGER.debug("saving interestArea {}", interestArea4_2);
            interestAreaRepository.save(interestArea4_2);

        }
    }

    private void generateInterests() {
        if (interestRepository.findAll().size() > 0) {
            LOGGER.debug("interests already generated");
        } else {
            LOGGER.debug("generating {} interests entries", 5);

            for (Employee employee :
                employeeRepository.findAll()) {

                Interest interest1 = Interest.InterestBuilder.aInterest()
                    .withName("IT")
                    .withDescription("ich programmiere gerne")
                    .withInterestArea(interestAreaRepository.getOne(15L))
                    .withEmployee(employee)
                    .build();
                Interest interest2 = Interest.InterestBuilder.aInterest()
                    .withName("Babysitter")
                    .withDescription("passe gerne auf kleine Kinder und Babys auf")
                    .withInterestArea(interestAreaRepository.getOne(5L))
                    .withEmployee(employee)
                    .build();
                Interest interest3 = Interest.InterestBuilder.aInterest()
                    .withName("spazieren gehen")
                    .withDescription("in meiner Freizeit verbringe ich gerne Zeit im Freien")
                    .withInterestArea(interestAreaRepository.getOne(6L))
                    .withEmployee(employee)
                    .build();
                Interest interest4 = Interest.InterestBuilder.aInterest()
                    .withName("Musik produzieren")
                    .withDescription("ab und zu versuche ich mich als Hobby-DJ")
                    .withInterestArea(interestAreaRepository.getOne(14L))
                    .withEmployee(employee)
                    .build();
                Interest interest5 = Interest.InterestBuilder.aInterest()
                    .withName("Bücher lesen")
                    .withDescription("ich lese gerne")
                    .withInterestArea(null)
                    .withEmployee(employee)
                    .build();

                LOGGER.debug("saving interest {}", interest1);
                interestRepository.save(interest1);
                LOGGER.debug("saving interest {}", interest2);
                interestRepository.save(interest2);
                LOGGER.debug("saving interest {}", interest3);
                interestRepository.save(interest3);
                LOGGER.debug("saving interest {}", interest4);
                interestRepository.save(interest4);
                LOGGER.debug("saving interest {}", interest5);
                interestRepository.save(interest5);
            }

        }
    }

    private void generateTimes(Float ratio) {
        if (timeRepository.findAll().size() > 0) {
            LOGGER.debug("times already generated");
        } else {
            LOGGER.debug("randomly generating time entries for random employees");
            Faker faker = new Faker();
            Random random = new Random();
            Calendar calendar = Calendar.getInstance();
            calendar.set(2021, Calendar.FEBRUARY, 28);
            for (Employee employee : employeeRepository.findAll()) {
                if (random.nextDouble() <= ratio){
                    for (int i = 0; i < random.nextInt(5) + 1; i++) {
                        Date start = DateUtils.round(faker.date().between(new Date(System.currentTimeMillis()), calendar.getTime()), Calendar.HOUR);
                        Date end = DateUtils.round(DateUtils.addHours(faker.date().future(random.nextInt(12) + 1, TimeUnit.HOURS, start), 1), Calendar.HOUR);
                        // one semester 30% chance
                        if (random.nextDouble() <= 0.3) {
                            Date finalEnd = DateUtils.addWeeks(end, 17);
                            Time time1 = Time.TimeBuilder.aTime()
                                .withStart(convertToLocalDateTime(start))
                                .withEnd(convertToLocalDateTime(end))
                                .withFinalEndDate(convertToLocalDateTime(finalEnd))
                                .withVisible(true)
                                .withRef_Id(-1L)
                                .withEmployee(employee)
                                .build();
                            timeRepository.save(time1);
                            for (int j = 0; j < 18; j++) {
                                Time time = Time.TimeBuilder.aTime()
                                    .withStart(convertToLocalDateTime(DateUtils.addDays(start, j * 7)))
                                    .withEnd(convertToLocalDateTime(DateUtils.addDays(end, j * 7)))
                                    .withFinalEndDate(convertToLocalDateTime(DateUtils.addDays(end, j * 7)))
                                    .withVisible(false)
                                    .withRef_Id(-1L)
                                    .withEmployee(employee)
                                    .build();
                                LOGGER.debug("saving time (one semester) {}, for employee {}", time, employee);
                                timeRepository.save(time);
                            }
                        // one day 70% chance
                        } else {
                            Time time = Time.TimeBuilder.aTime()
                                .withStart(convertToLocalDateTime(start))
                                .withEnd(convertToLocalDateTime(end))
                                .withFinalEndDate(convertToLocalDateTime(end))
                                .withVisible(true)
                                .withRef_Id(-1L)
                                .withEmployee(employee)
                                .build();
                            LOGGER.debug("saving time {}, for employee {}", time, employee);
                            timeRepository.save(time);
                        }
                    }
                }
            }
        }
    }

    @PostConstruct
    public void generateActualEvents(){

        generateEmployers();
        generateEmployees();
        generateInterestAreas();
        generateInterests();
        generateTimes(0.75f);

        Faker faker = new Faker(new Locale("de-AT"));
        Random random = new Random();
        for (int i = 1; i <= 32; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2021, Calendar.APRIL, 30);
            Date start = faker.date().between(new Date(System.currentTimeMillis()), calendar.getTime());
            Date end = faker.date().future(14, TimeUnit.DAYS, start);
            Address address = Address.AddressBuilder.aAddress()
                .withAddressLine(faker.address().streetAddress())
                .withCity(faker.address().city())
                .withState(faker.address().state())
                .withZip(Integer.parseInt(faker.address().zipCode()))
                .build();
            Set<Task> tasks = generateRandomTasks(3);
            Event event = Event.EventBuilder.aEvent()
                .withTitle("Event " + i)
                .withDescription("Description " + i)
                .withStart(convertToLocalDateTime(start))
                .withEnd(convertToLocalDateTime(end))
                .withAddress(addressRepository.save(address))
                .withTask(tasks)
                .withEmployer(employerRepository.findByProfile_Id(random.nextInt(40) + 1L))
                .build();
            Event savedEvent = eventRepository.save(event);
            for (Task task : tasks) {
                task.setEvent(savedEvent);
                taskRepository.save(task);
            }
        }
        generateApplications(350, 1f);
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateApplications(Event event, Employee_Tasks employee_tasks, Employer employer, Long applicationId) {
        Notification notification = new Notification();
        notification.setEvent(event);
        notification.setRecipient(employee_tasks.getEmployee().getProfile());
        notification.setSender(employer.getProfile());
        notification.setTask(employee_tasks.getTask());
        notification.setSeen(false);
        if(employee_tasks.getAccepted()){
            notification.setMessage(String.format("Deine Bewerbung für das Event \"%s\" wurde akzeptiert", event.getTitle()));
            notification.setType(NotificationType.EVENT_ACCEPTED.name());
            employeeService.deleteTime(employee_tasks.getEmployee().getId(), employee_tasks.getTask().getId());
        }else{
            notification.setMessage(String.format("Deine Bewerbung für das Event \"%s\" wurde abgelehnt", event.getTitle()));
            notification.setType(NotificationType.EVENT_DECLINED.name());
        }

        employee_tasksService.updateStatus(employee_tasks);
        notificationRepository.deleteById(applicationId);
        notificationRepository.save(notification);
    }

    public void generateApplications(int count, float ratio) {
        Random random = new Random();
        List<Task> tasks = taskRepository.findAll();
        for (int i = 0; i < count; i++) {
            Optional<Employee> employee = employeeRepository.findById(41L + random.nextInt(30));
            Employee randomEmployee = employee.isPresent() ? employee.get() : null;
            Task randomTask = tasks.get(random.nextInt(tasks.size() - 1));
            Event event = eventRepository.findFirstByTasks(randomTask);
            Employer employer = employerRepository.findFirstByEvents(event);
            String message = "Sehr geehrte Damen und Herren,\r\n"+
                "hiermit bewerbe ich mich für die Stelle \"" + randomTask.getDescription() +"\" für das Event " + event.getTitle() + "\r\n" +
                "Mit freundlichen Grüßen\r\n" +
                randomEmployee.getProfile().getFirstName() + " " + randomEmployee.getProfile().getLastName();

            Notification existingApplication = notificationRepository.findFirstByEvent_IdAndSender_Id(event.getId(), randomEmployee.getId());
            if(!(existingApplication == null || existingApplication.getType().equalsIgnoreCase(NotificationType.NOTIFICATION.name()))){
                i--;
                continue;
            }
            try {
                employee_tasksService.applyForTask(randomEmployee, randomTask);
            } catch (AlreadyHandledException | NoAvailableSpacesException e) {
                //i--;
                continue;
            }

            Notification application = new Notification();
            application.setEvent(event);
            application.setMessage(message);
            application.setRecipient(employer.getProfile());
            application.setSender(randomEmployee.getProfile());
            application.setSeen(false);
            application.setTask(randomTask);
            application.setType(NotificationType.APPLICATION.name());

            Notification notification = new Notification();
            notification.setEvent(event);
            notification.setMessage("Es gibt eine neue Bewerbung für das Event: " + event.getTitle());
            notification.setRecipient(employer.getProfile());
            notification.setSender(null);
            notification.setSeen(false);
            notification.setTask(randomTask);
            notification.setType(NotificationType.NOTIFICATION.name());

            Long applicationId = notificationRepository.save(application).getId();
            notificationRepository.save(notification);
            if (random.nextDouble() <= ratio) {
                Employee_Tasks employee_tasks = new Employee_Tasks();
                employee_tasks.setAccepted(random.nextBoolean());
                employee_tasks.setEmployee(randomEmployee);
                employee_tasks.setTask(randomTask);
                updateApplications(event, employee_tasks, employer, applicationId);
            }
        }
    }

    public Set<Task> generateRandomTasks(int maxTasks) {
        Set<Task> tasks = new HashSet<>();
        List<InterestArea> areas = interestAreaRepository.findAll();
        Random random = new Random();

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/tasks.txt", "r");
            int length = (int)randomAccessFile.length();
            for (int i = random.nextInt(maxTasks) + 1; i > 0; i--) {
                int pos = random.nextInt(length);
                randomAccessFile.seek(pos);
                randomAccessFile.readLine();
                String line = randomAccessFile.readLine();
                if (line == null) continue;
                Task task = new Task();
                task.setDescription(line.substring(line.indexOf(",") + 1));
                task.setEmployeeCount(1 + random.nextInt(9));
                task.setPaymentHourly((double) (5 + random.nextInt(20)));
                task.setInterestArea(areas.get(random.nextInt(areas.size() - 1)));
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    //@PostConstruct
    public void generateEvents() {
        generateEmployers();
        generateEmployees();
        generateInterestAreas();
        generateInterests();
        generateTimes(0.75f);
        if (eventRepository.findAll().size() > 0) {
            LOGGER.debug("events already generated");
        } else {


        }
    }
}
