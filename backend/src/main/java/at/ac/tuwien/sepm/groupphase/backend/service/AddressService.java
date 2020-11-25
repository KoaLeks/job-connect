package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;

public interface AddressService {

    /**
     * Saves new address in database.
     *
     * @param address that is being saved to database.
     * @return the newly created address.
     */
    Address saveAddress(Address address);
}
