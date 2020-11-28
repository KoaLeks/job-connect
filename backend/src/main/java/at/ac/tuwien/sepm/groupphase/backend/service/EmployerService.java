package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;


public interface EmployerService {

    Long createEmployer(Employer employer);
}
