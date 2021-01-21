package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.AlreadyHandledException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NoAvailableSpacesException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.Employee_TasksService;
import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
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
import java.nio.charset.StandardCharsets;
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
    private static final String TEST_PUBLIC_INFO = "PUBLIC INFO";
    private static final String TEST_PASSWORD = "123456789";
    private static final int NUMBER_OF_PRIVATE_EMPLOYERS = 20;

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
            Random random = new Random();
            for (String companyName : companyNames) {
                String name = names[random.nextInt(names.length - 1)];
                at.ac.tuwien.sepm.groupphase.backend.entity.Profile employerProfile =
                    at.ac.tuwien.sepm.groupphase.backend.entity.Profile.ProfileBuilder.aProfile()
                        .withEmail("test@" + companyName.replace(" ", "").toLowerCase() + ".at")
                        .withForename(name.split(" ")[0])
                        .withName(name.split(" ")[1])
                        .withPassword(passwordEncoder.encode(TEST_PASSWORD))
                        .withPublicInfo(TEST_PUBLIC_INFO)
                        .isEmployer(true)
                        .build();

                Employer employer = Employer.EmployerBuilder.aEmployer()
                    .withCompanyName(companyName)
                    .withDescription(
                        random.nextBoolean() ?
                            "Wir sind " + companyName + ", welches " + (1950 + random.nextInt(70)) + " von " + name + " gegründet wurde. " +
                                (random.nextBoolean() ?
                                    "Das Ziel von " + companyName + " hat sich seit der Gründung nicht verändert, wir versorgen unsere Kunden mit den besten Produkten zu einem fairen Preis."
                                :
                                   companyName + " setzt die neusten state of the art Technologien direkt bei Ihnen zu Hause ein, um alle möglichen Probleme zu loesen.")
                            :
                            companyName + " ist ein neues Startup, welches sich aus " + (5 + random.nextInt(10)) + " engagierte MitarbeiterInnen zusammensetzt. " +
                                (random.nextBoolean() ?
                                    "Unser Ziel ist es all ihre Bedürfnisse zu erfuellen."
                                :
                                    "Als neues Startup versuchen wir ständig neue innovative Ideen an den Markt und direkt zu Ihnen zu bringen.")
                    )
                    .withProfile(employerProfile)
                    .build();
                LOGGER.debug("saving employer {}", employer);
                Long id = profileRepository.save(employerProfile).getId();
                employer.setId(id);
                employerRepository.save(employer);
            }
            for (int i = 0; i < NUMBER_OF_PRIVATE_EMPLOYERS; i++) {
                String name = names[i];
                at.ac.tuwien.sepm.groupphase.backend.entity.Profile privateEmployer =
                    at.ac.tuwien.sepm.groupphase.backend.entity.Profile.ProfileBuilder.aProfile()
                        .withEmail("test@" + name.replace(" ", "").toLowerCase() + ".at")
                        .withForename(name.split(" ")[0])
                        .withName(name.split(" ")[1])
                        .withPassword(passwordEncoder.encode(TEST_PASSWORD))
                        .withPublicInfo(TEST_PUBLIC_INFO)
                        .isEmployer(true)
                        .build();

                Employer employer = Employer.EmployerBuilder.aEmployer()
                    .withCompanyName("Privatperson")
                    .withProfile(privateEmployer)
                    .build();
                LOGGER.debug("saving employer {}", employer);
                Long id = profileRepository.save(privateEmployer).getId();
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
            LOGGER.debug("generating interestAreas entries");
            List<InterestArea> areas = new LinkedList<>();
            try {
                RandomAccessFile areasFile = new RandomAccessFile("src/main/resources/interestAreas.txt", "r");
                String line;
                while ((line = areasFile.readLine()) != null) {
                    line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    String[] split = line.split(";");
                    areas.add(InterestArea.InterestAreaBuilder.aInterest()
                        .withArea(split[1])
                        .withDescription(split[2])
                        .build());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            interestAreaRepository.saveAll(areas);
        }
    }

    private void generateInterests() {
        if (interestRepository.findAll().size() > 0) {
            LOGGER.debug("interests already generated");
        } else {
            LOGGER.debug("generating {} interests entries", 5);
            Random random = new Random();
            int areasCount = interestAreaRepository.findAll().size() - 1;
            for (Employee employee : employeeRepository.findAll()) {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/interests.txt", "r");
                    int length = (int) randomAccessFile.length();
                    for (int i = random.nextInt(3) + 1; i > 0; i--) {
                        int pos = random.nextInt(length);
                        randomAccessFile.seek(pos);
                        randomAccessFile.readLine();
                        String line = new String(randomAccessFile.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                        if (line.length() == 0) {
                            i--;
                            continue;
                        }
                        String[] parts = line.split(";");
                        Interest interest = Interest.InterestBuilder.aInterest()
                            .withName(parts[1])
                            .withDescription(parts.length > 2 ? parts[2].replace("\"", "") : "TODO: add description to interests.txt")
                            .withInterestArea(interestAreaRepository.getOne(1L + random.nextInt(areasCount)))
                            .withEmployee(employee)
                            .build();

                        LOGGER.debug("saving interest {}", interest);
                        interestRepository.save(interest);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                if (random.nextDouble() <= ratio) {
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
    public void generateEvents() {

        generateEmployers();
        generateEmployees();
        generateInterestAreas();
        generateInterests();
        generateTimes(0.75f);

        Faker faker = new Faker(new Locale("de-AT"));
        Random random = new Random();
        try {
            RandomAccessFile eventFile = new RandomAccessFile("src/main/resources/events.txt", "r");
            String line;
            while ((line = eventFile.readLine()) != null) {
                line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                Calendar calendar = Calendar.getInstance();
                calendar.set(2021, Calendar.APRIL, 30);
                Date start = roundTimeToQuarter(faker.date().between(new Date(System.currentTimeMillis()), calendar.getTime()));
                Date end = roundTimeToQuarter(faker.date().future(14, TimeUnit.DAYS, start));

                Address address = Address.AddressBuilder.aAddress()
                    .withAddressLine(faker.address().streetAddress())
                    .withCity(faker.address().city())
                    .withState(faker.address().state())
                    .withZip(Integer.parseInt(faker.address().zipCode()))
                    .build();

                //Set<Task> tasks = generateRandomTasks(3);

                //get list of task ids
                String[] taskIdStrings = line.split(";")[2].split(",");
                List<Integer> taskIds = new LinkedList<>();
                for (String s : taskIdStrings) {
                    taskIds.add(Integer.valueOf(s));
                }
                Set<Task> tasks = generateTasksByIds(taskIds);

                Event event = Event.EventBuilder.aEvent()
                    .withTitle(line.split(";")[1])
                    .withDescription(line.split(";")[3])
                    .withStart(convertToLocalDateTime(start))
                    .withEnd(convertToLocalDateTime(end))
                    .withAddress(addressRepository.save(address))
                    .withTask(tasks)
                    .withEmployer(line.split(";")[4].equalsIgnoreCase("firma") ? employerRepository.findByProfile_Id(random.nextInt(companyNames.length) + 1L) : employerRepository.findByProfile_Id(random.nextInt(NUMBER_OF_PRIVATE_EMPLOYERS) + companyNames.length + 1L))
                    .build();
                Event savedEvent = eventRepository.save(event);
                for (Task task : tasks) {
                    task.setEvent(savedEvent);
                    taskRepository.save(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateApplications(300, 0.5f);
    }

    public Date roundTimeToQuarter(Date dateToRound) {
        Calendar round = Calendar.getInstance();
        round.setTime(dateToRound);
        int unrounded = round.get(Calendar.MINUTE);
        int mod = unrounded % 15;
        round.add(Calendar.MINUTE, mod < 8 ? -mod : (15 - mod));
        return round.getTime();
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
        if (employee_tasks.getAccepted()) {
            notification.setMessage(String.format("Deine Bewerbung für das Event \"%s\" wurde akzeptiert", event.getTitle()));
            notification.setType(NotificationType.EVENT_ACCEPTED.name());
            employeeService.deleteTime(employee_tasks.getEmployee().getId(), employee_tasks.getTask().getId());
        } else {
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
            Optional<Employee> employee = employeeRepository.findById(companyNames.length + NUMBER_OF_PRIVATE_EMPLOYERS + 1L + random.nextInt(30));
            Employee randomEmployee = employee.orElse(null);
            if (randomEmployee == null) {
                continue;
            }
            Task randomTask = tasks.get(random.nextInt(tasks.size() - 1));
            Event event = eventRepository.findFirstByTasks(randomTask);
            Employer employer = employerRepository.findFirstByEvents(event);
            String message = "Sehr geehrte Damen und Herren,\r\n" +
                "hiermit bewerbe ich mich für die Stelle \"" + randomTask.getDescription() + "\" für das Event " + event.getTitle() + "\r\n" +
                "Mit freundlichen Grüßen\r\n" +
                randomEmployee.getProfile().getFirstName() + " " + randomEmployee.getProfile().getLastName();

            Notification existingApplication = notificationRepository.findFirstByEvent_IdAndSender_Id(event.getId(), randomEmployee.getId());
            if (!(existingApplication == null || existingApplication.getType().equalsIgnoreCase(NotificationType.NOTIFICATION.name()))) {
                i--;
                continue;
            }
            try {
                employee_tasksService.applyForTask(randomEmployee, randomTask);
            } catch (AlreadyHandledException e) {
                i--;
                continue;
            } catch (NoAvailableSpacesException e) {
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
            int length = (int) randomAccessFile.length();
            for (int i = random.nextInt(maxTasks) + 1; i > 0; i--) {
                int pos = random.nextInt(length);
                randomAccessFile.seek(pos);
                randomAccessFile.readLine();
                String line = new String(randomAccessFile.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                if (line.length() == 0) {
                    i--;
                    continue;
                }
                Task task = new Task();
                task.setDescription(line.substring(line.indexOf(";") + 1));
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

    public Set<Task> generateTasksByIds(List<Integer> ids) {
        Set<Task> tasks = new HashSet<>();
        Random random = new Random();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/tasks.txt", "r");
            List<String> taskStrings = new LinkedList<>();
            String taskLine;
            while ((taskLine = randomAccessFile.readLine()) != null) {
                taskLine = new String(taskLine.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                taskStrings.add(taskLine);
            }

            for (Integer id : ids) {
                String line = taskStrings.get(id - 1);
                if (line == null) continue;
                Task task = new Task();
                task.setDescription(line.split(";")[1]);
                task.setEmployeeCount(1 + random.nextInt(4));
                task.setPaymentHourly((double) (5 + random.nextInt(20)));
                if (line.split(";").length > 2) {
                    Optional<InterestArea> area = interestAreaRepository.findById(Long.parseLong(line.split(";")[2]));
                    area.ifPresent(task::setInterestArea);
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
