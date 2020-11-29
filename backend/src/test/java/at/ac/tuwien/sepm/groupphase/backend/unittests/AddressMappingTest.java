package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AddressMappingTest implements TestData {

    private Address address = Address.AddressBuilder.aAddress()
        .withCity(CITY)
        .withState(STATE)
        .withZip(ZIP)
        .withAddressLine(ADDRESS_LINE)
        .withAdditional(ADDITIONAL)
        .build();

}