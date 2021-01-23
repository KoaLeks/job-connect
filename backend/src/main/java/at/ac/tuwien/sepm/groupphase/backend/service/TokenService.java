package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;

public interface TokenService {

    /**
     * Gets the corresponding email to the authentication header
     *
     * @param authHeader authentication header of request
     * @return email corresponding to the authHeader
     */
    String getEmailFromHeader(String authHeader);

    /**
     * Gets the corresponding profile to the authentication header
     *
     * @param authHeader authentication header of request
     * @return profile corresponding to the authHeader
     */
    Profile getProfileFromHeader(String authHeader);

    /**
     * Gets the corresponding employer profile to the authentication header
     *
     * @param authHeader authentication header of request
     * @return employer profile corresponding to the authHeader
     */
    Employer getEmployerFromHeader(String authHeader);

    /**
     * Gets the corresponding employee profile to the authentication header
     *
     * @param authHeader authentication header of request
     * @return employee profile corresponding to the authHeader
     */
    Employee getEmployeeFromHeader(String authHeader);
}
