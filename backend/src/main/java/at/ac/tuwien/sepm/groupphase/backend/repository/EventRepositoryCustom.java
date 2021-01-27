package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Time;


import java.util.List;

public interface EventRepositoryCustom {
    /**
     * Finds all Events with the given starting times and interests. If one list is empty, only the given arguments will be matched
     * @param times which should match with the Event combined by OR
     * @param interests which should match with the Events combined by OR
     * @return all matching Events
     */
    List<Event> getAllEventsByMatchingTimesAndInterests(List<Time> times, List<Interest> interests);
}
