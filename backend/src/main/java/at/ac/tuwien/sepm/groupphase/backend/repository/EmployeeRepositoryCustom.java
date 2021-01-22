package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;

import java.util.List;
import java.util.Set;

public interface EmployeeRepositoryCustom {
    List<Employee> findEmployeesByInterestArea(Set<String> interestAreas);
}
