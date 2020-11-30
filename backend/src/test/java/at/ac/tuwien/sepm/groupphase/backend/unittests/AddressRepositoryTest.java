package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class AddressRepositoryTest implements TestData {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void givenNothing_whenSaveMessage_thenFindListWithOneElementAndFindMessageById() {
        Address address = Address.AddressBuilder.aAddress()
            .withCity(CITY)
            .withState(STATE)
            .withZip(ZIP)
            .withAddressLine(ADDRESS_LINE)
            .withAdditional(ADDITIONAL)
            .build();

        addressRepository.save(address);

        assertAll(
            () -> assertEquals(1, addressRepository.findAll().size()),
            () -> assertNotNull(addressRepository.findById(address.getId()))
        );
    }
}