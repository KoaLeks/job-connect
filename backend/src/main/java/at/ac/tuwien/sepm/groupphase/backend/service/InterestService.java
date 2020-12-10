package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;

import java.util.List;

public interface InterestService {

    /**
     * Find all Interests.
     *
     * @return list of all Interests.
     */
    List<Interest> findAll();

    /**
     * deletes an Interest with given id.
     */
    void deleteById(Long id);

}
