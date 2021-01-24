package at.ac.tuwien.sepm.groupphase.backend.repository.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepositoryCustom;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Event> getAllEventsByMatchingTimesAndInterests(List<Time> times, List<Interest> interests) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> events = query.from(Event.class);

        List<Predicate> predicatesTimes = new ArrayList<>();
        for(Time time : times){
            predicatesTimes.add(cb.and(cb.greaterThanOrEqualTo(events.get(Event_.start) ,time.getStart()), cb.lessThanOrEqualTo(events.get(Event_.start) ,time.getEnd())));
        }

        Join<Event, Task> tasks = events.join(Event_.tasks, JoinType.LEFT);
        List<Predicate> predicatesInterests = new ArrayList<>();
        for(Interest interest : interests){
            predicatesInterests.add(cb.equal(tasks.get(Task_.interestArea), interest.getInterestArea()));
        }

        List<Predicate> finalPred = new ArrayList<>();
        if(times.size() != 0){
            finalPred.add(cb.or(predicatesTimes.toArray(new Predicate[0])));
        }
        if(interests.size() != 0){
            finalPred.add(cb.or(predicatesInterests.toArray(new Predicate[0])));
        }
        query.where(finalPred.toArray(new Predicate[0]));
        query.select(events);
        query.distinct(true);

        var res = em.createQuery(query).getResultList();
        return  res;
    }
}
