package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployerRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateEmployer")
@Component
public class EmployerDataGenerator {

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

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String TEST_FIRST_NAME = "FIRST NAME";
    private static final String TEST_LAST_NAME = "LAST NAME";
    private static final String TEST_PUBLIC_INFO = "PUBLIC INFO";
    private static final String TEST_PASSWORD = "123456789";

    private final EmployerRepository employerRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;


    public EmployerDataGenerator(EmployerRepository employerRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.employerRepository = employerRepository;
        this.profileRepository = profileRepository;
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
                        .withForename(TEST_FIRST_NAME)
                        .withName(TEST_LAST_NAME)
                        .withPassword(passwordEncoder.encode(TEST_PASSWORD))
                        .withPublicInfo(TEST_PUBLIC_INFO)
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
}
