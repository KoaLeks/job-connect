package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;

import java.util.List;

public interface InterestAreaService {

    /**
     * Saves new InterestArea in database.
     *
     * @param interestArea that is being saved to database.
     * @return the newly created interestArea.
     */
    InterestArea saveInterestArea(InterestArea interestArea);

    /**
     * Finds all InterestAreas.
     *
     * @return list of all InterestAreas.
     */
    List<InterestArea> findAll();

    /**
     * Finds an InterestArea by id.
     *
     * @param id of the InterestArea to find
     * @return InterestArea with id.
     */
    InterestArea findById(Long id);
}