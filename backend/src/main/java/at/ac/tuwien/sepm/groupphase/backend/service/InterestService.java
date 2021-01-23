package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;

import java.util.List;

public interface InterestService {

    /**
     * Finds all Interests.
     *
     * @return list of all Interests.
     */
    List<Interest> findAll();

    /**
     * deletes an Interest with given id.
     *
     * @param id of the Interest to delete
     */
    void deleteById(Long id);

}
