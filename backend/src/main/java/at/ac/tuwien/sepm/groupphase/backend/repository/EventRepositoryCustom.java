package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Time;


import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getAllEventsByMatchingTimesAndInterests(List<Time> times, List<Interest> interests);
}
