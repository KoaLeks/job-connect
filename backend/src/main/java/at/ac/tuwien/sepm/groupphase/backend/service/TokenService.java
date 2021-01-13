package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;

public interface TokenService {
    String getEmailFromHeader(String authHeader);
    Profile getProfileFromHeader(String authHeader);
    Employer getEmployerFromHeader(String authHeader);
    Employee getEmployeeFromHeader(String authHeader);
}
