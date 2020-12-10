package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    // Interest area
    private static final int NUMBER_OF_InterestAreas_TO_GENERATE = 10;

    // Employer
    private static final String TEST_EMPLOYER_FIRST_NAME = "FIRST NAME";
    private static final String TEST_EMPLOYER_LAST_NAME = "LAST NAME";
    private static final String TEST_EMPLOYER_PUBLIC_INFO = "PUBLIC INFO";
    private static final String TEST_EMPLOYER_PASSWORD = "123456789";

    // Event
    private static final String TEST_EVENT_TITLE = "EVENT TITLE";
    private static final String TEST_EVENT_DESCRIPTION = "EVENT DESCRIPTION";

    private static final LocalDateTime TEST_EVENT_START1
        = LocalDateTime.of(2020, 12, 18, 12, 0, 0, 0);
    private static final LocalDateTime TEST_EVENT_END1
        = LocalDateTime.of(2020, 12, 21, 19, 0, 0, 0);

    private static final LocalDateTime TEST_EVENT_START2
        = LocalDateTime.of(2020, 12, 20, 12, 30, 0, 0);
    private static final LocalDateTime TEST_EVENT_END2
        = LocalDateTime.of(2020, 12, 22, 12, 0, 0, 0);

    private static final LocalDateTime TEST_EVENT_START3
        = LocalDateTime.of(2020, 12, 24, 0, 0, 0, 0);
    private static final LocalDateTime TEST_EVENT_END3
        = LocalDateTime.of(2021, 1, 1, 0, 0, 0, 0);

    private static final LocalDateTime TEST_EVENT_START4
        = LocalDateTime.of(2020, 12, 22, 17, 30, 0, 0);
    private static final LocalDateTime TEST_EVENT_END4
        = LocalDateTime.of(2021, 12, 26, 15, 0, 0, 0);

    // Addresses
    private static final Address TEST_ADDRESS1 = Address.AddressBuilder.aAddress()
        .withAddressLine("Baumstrasse 5")
        .withCity("Wien")
        .withState("Wien")
        .withZip(1070)
        .build();
    private static final Address TEST_ADDRESS2 = Address.AddressBuilder.aAddress()
        .withAddressLine("Gipfelgasse 22")
        .withCity("Mariazell")
        .withState("Steiermark")
        .withZip(8100)
        .build();
    private static final Address TEST_ADDRESS3 = Address.AddressBuilder.aAddress()
        .withAddressLine("Servusweg 12")
        .withCity("Feldkirch")
        .withState("Vorarlberg")
        .withZip(6800)
        .build();
    private static final Address TEST_ADDRESS4 = Address.AddressBuilder.aAddress()
        .withAddressLine("EVENT ADDRESS 4")
        .withCity("Villach")
        .withState("Kärnten")
        .withZip(9500)
        .build();

    // Tasks
    private static final Task TEST_TASK1 = Task.TaskBuilder.aTask()
        .withDescription("Flyer verteilen")
        .withEmployeeCount(10)
        .withPaymentHourly(9.0)
        .build();
    private static final Task TEST_TASK2 = Task.TaskBuilder.aTask()
        .withDescription("Rezeptionist/Rezeptionistin für den Empfang und Betreuung von Kunden")
        .withEmployeeCount(5)
        .withPaymentHourly(10.0)
        .build();
    private static final Task TEST_TASK3 = Task.TaskBuilder.aTask()
        .withDescription("Kellner/Kellerin")
        .withEmployeeCount(2)
        .withPaymentHourly(8.0)
        .build();
    private static final Task TEST_TASK4 = Task.TaskBuilder.aTask()
        .withDescription("Assistenten fürs Kamerateam")
        .withEmployeeCount(3)
        .withPaymentHourly(16.0)
        .build();
    private static final Task TEST_TASK5 = Task.TaskBuilder.aTask()
        .withDescription("Hilfsarbeiter für diverse körperliche Arbeiten")
        .withEmployeeCount(13)
        .withPaymentHourly(7.0)
        .build();
    private static final Task TEST_TASK6 = Task.TaskBuilder.aTask()
        .withDescription("Gartenhelfer")
        .withEmployeeCount(8)
        .withPaymentHourly(10.0)
        .build();


    private final EmployerRepository employerRepository;
    private final ProfileRepository profileRepository;
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final InterestAreaRepository interestAreaRepository;


    public TestDataGenerator(EmployerRepository employerRepository, ProfileRepository profileRepository,
                             PasswordEncoder passwordEncoder, EventRepository eventRepository,
                             AddressRepository addressRepository, TaskRepository taskRepository,
                             InterestAreaRepository interestAreaRepository) {
        this.employerRepository = employerRepository;
        this.profileRepository = profileRepository;
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.taskRepository = taskRepository;
        this.interestAreaRepository = interestAreaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
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
                        .withPassword(passwordEncoder.encode(TEST_EMPLOYER_PASSWORD))
                        .withPublicInfo(TEST_EMPLOYER_PUBLIC_INFO)
                        .isEmployer(true)
                        .build();

                Employer employer = Employer.EmployerBuilder.aEmployer()
                    .withCompanyName(name)
                    .withDescription("generatead employer")
                    .withProfile(employerProfile)
                    .build();
                LOGGER.debug("saving employer {}", employer);
                Long id = profileRepository.save(employerProfile).getId();
                employer.setId(id);
                employerRepository.save(employer);
            }
        }
    }

    @PostConstruct
    private void generateInterestAreas() {
        if (interestAreaRepository.findAll().size() > 0) {
            LOGGER.debug("interestAreas already generated");
        } else {
            LOGGER.debug("generating {} interestAreas entries", NUMBER_OF_InterestAreas_TO_GENERATE);
            InterestArea interestArea1 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Promo")
                .withDescription("Flyer verteilen")
                .build();
            LOGGER.debug("saving interestArea {}", interestArea1);
            interestAreaRepository.save(interestArea1);
            InterestArea interestArea2 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Gastro")
                .withDescription("Gäste bedienen und Tische abräumen")
                .build();
            LOGGER.debug("saving interestArea {}", interestArea2);
            interestAreaRepository.save(interestArea2);
            InterestArea interestArea3 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Körperliches")
                .withDescription("schwere Lasten tragen")
                .build();
            LOGGER.debug("saving interestArea {}", interestArea3);
            interestAreaRepository.save(interestArea3);

        }
    }

    @PostConstruct
    public void generateEvents() {
        if (eventRepository.findAll().size() > 0) {
            LOGGER.debug("events already generated");
        } else {

            TEST_TASK1.setInterestArea(interestAreaRepository.getOne(1L));
            TEST_TASK2.setInterestArea(interestAreaRepository.getOne(1L));
            TEST_TASK3.setInterestArea(interestAreaRepository.getOne(2L));
            TEST_TASK4.setInterestArea(interestAreaRepository.getOne(2L));
            TEST_TASK5.setInterestArea(interestAreaRepository.getOne(3L));
            TEST_TASK6.setInterestArea(interestAreaRepository.getOne(3L));

            Set<Task> tasks1 = new HashSet<>();
            tasks1.add(TEST_TASK1);

            Set<Task> tasks2 = new HashSet<>();
            tasks2.add(TEST_TASK2);
            tasks2.add(TEST_TASK3);

            Set<Task> tasks3 = new HashSet<>();
            tasks3.add(TEST_TASK4);

            Set<Task> tasks4 = new HashSet<>();
            tasks4.add(TEST_TASK5);
            tasks4.add(TEST_TASK6);

            Event event1 = Event.EventBuilder.aEvent()
                .withTitle(TEST_EVENT_TITLE)
                .withDescription(TEST_EVENT_DESCRIPTION)
                .withStart(TEST_EVENT_START1)
                .withEnd(TEST_EVENT_END1)
                .withAddress(addressRepository.save(TEST_ADDRESS1))
                .withTask(tasks1)
                .withEmployer(employerRepository.findByProfile_Email("test@freedommap.at"))
                .build();
            Event event2 = Event.EventBuilder.aEvent()
                .withTitle(TEST_EVENT_TITLE)
                .withDescription(TEST_EVENT_DESCRIPTION)
                .withStart(TEST_EVENT_START2)
                .withEnd(TEST_EVENT_END2)
                .withAddress(addressRepository.save(TEST_ADDRESS2))
                .withTask(tasks2)
                .withEmployer(employerRepository.findByProfile_Email("test@galaxyman.at"))
                .build();
            Event event3 = Event.EventBuilder.aEvent()
                .withTitle(TEST_EVENT_TITLE)
                .withDescription(TEST_EVENT_DESCRIPTION)
                .withStart(TEST_EVENT_START3)
                .withEnd(TEST_EVENT_END3)
                .withAddress(addressRepository.save(TEST_ADDRESS3))
                .withTask(tasks3)
                .withEmployer(employerRepository.findByProfile_Email("test@futureplan.at"))
                .build();
            Event event4 = Event.EventBuilder.aEvent()
                .withTitle(TEST_EVENT_TITLE)
                .withDescription(TEST_EVENT_DESCRIPTION)
                .withStart(TEST_EVENT_START4)
                .withEnd(TEST_EVENT_END4)
                .withAddress(addressRepository.save(TEST_ADDRESS4))
                .withTask(tasks4)
                .withEmployer(employerRepository.findByProfile_Email("test@maxaprofit.at"))
                .build();

            LOGGER.debug("saving event {}", event1);
            eventRepository.save(event1);
            LOGGER.debug("saving event {}", event2);
            eventRepository.save(event2);
            LOGGER.debug("saving event {}", event3);
            eventRepository.save(event3);
            LOGGER.debug("saving event {}", event4);
            eventRepository.save(event4);

            TEST_TASK1.setEvent(event1);
            TEST_TASK2.setEvent(event2);
            TEST_TASK3.setEvent(event2);
            TEST_TASK4.setEvent(event3);
            TEST_TASK5.setEvent(event4);
            TEST_TASK6.setEvent(event4);

            LOGGER.debug("saving task {}", TEST_TASK1);
            taskRepository.save(TEST_TASK1);
            LOGGER.debug("saving task {}", TEST_TASK2);
            taskRepository.save(TEST_TASK2);
            LOGGER.debug("saving task {}", TEST_TASK3);
            taskRepository.save(TEST_TASK3);
            LOGGER.debug("saving task {}", TEST_TASK4);
            taskRepository.save(TEST_TASK4);
            LOGGER.debug("saving task {}", TEST_TASK5);
            taskRepository.save(TEST_TASK5);
            LOGGER.debug("saving task {}", TEST_TASK6);
            taskRepository.save(TEST_TASK6);
        }
    }

}
