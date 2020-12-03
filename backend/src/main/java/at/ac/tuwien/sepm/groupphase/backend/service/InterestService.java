package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;

import java.util.List;
import java.util.Set;

public interface InterestService {

    /**
     * Find all Interests.
     *
     * @return list of all Interests.
     */
    List<Interest> findAll();

}
