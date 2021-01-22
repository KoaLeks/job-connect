package at.ac.tuwien.sepm.groupphase.backend.repository.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.beans.IntrospectionException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employee> findEmployeesByInterestAreasAndStartTimes(Set<Long> interestAreas, Set<LocalDateTime> startTimes) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        Join<Employee, Interest> interests =  employee.join(Employee_.interests, JoinType.LEFT);
        List<Predicate> predicatesArea = new ArrayList<>();
        for(Long interest : interestAreas){
            predicatesArea.add(cb.equal(interests.get(Interest_.interestArea), interest));
        }

        Join<Employee, Time> times = employee.join(Employee_.times, JoinType.LEFT);
        List<Predicate> predicatesStart = new ArrayList<>();
        for(LocalDateTime start : startTimes){
            predicatesStart.add(cb.and(cb.lessThanOrEqualTo(times.get(Time_.start), start), cb.greaterThanOrEqualTo(times.get(Time_.end), start)));
        }

        if(predicatesArea.size() == 0 && predicatesStart.size() != 0) {
            query.where(cb.or(predicatesStart.toArray(new Predicate[0])));
        }
        if(predicatesArea.size() != 0 && predicatesStart.size() == 0) {
            query.where(cb.or(predicatesArea.toArray(new Predicate[0])));
        }
        if(predicatesArea.size() != 0 && predicatesStart.size() != 0) {
            query.where(cb.and(cb.or(predicatesArea.toArray(new Predicate[0])), cb.or(predicatesStart.toArray(new Predicate[0]))));
        }
        query.select(employee);
        query.distinct(true);

        var res = em.createQuery(query).getResultList();

        return res;
    }
}
