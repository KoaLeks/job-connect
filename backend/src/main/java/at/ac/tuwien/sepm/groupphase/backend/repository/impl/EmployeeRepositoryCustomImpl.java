package at.ac.tuwien.sepm.groupphase.backend.repository.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.beans.IntrospectionException;
import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employee> findEmployeesByInterestArea(Set<Integer> interestAreas) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        Join<Employee, Interest> interests =  employee.join(Employee_.interests, JoinType.INNER);
        Join<Interest, InterestArea> areas = interests.join(Interest_.INTEREST_AREA, JoinType.INNER);

        for(Integer interest : interestAreas){
            query.where(cb.equal(areas.get(InterestArea_.id), interest));
        }

        query.select(employee);
        List<Employee> result = em.createQuery(query).getResultList();


        /*SetJoin<person, Address> adressJoin = person.join(Person_.adresses);
        criteriaQuery.where(criteriaBuilder.like(adressJoin.get(Address_.city), "Mannheim"));
        criteriaQuery.select(person);
        List<person> resultList = em.createQuery(criteriaQuery).getResultList();*/

        return result;
    }
}
