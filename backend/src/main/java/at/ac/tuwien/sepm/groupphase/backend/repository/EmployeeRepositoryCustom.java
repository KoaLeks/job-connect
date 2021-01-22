package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EmployeeRepositoryCustom {
    List<Employee> findEmployeesByInterestAreasAndStartTimes(Set<Long> interestAreas, Set<LocalDateTime> timestamps);
}
