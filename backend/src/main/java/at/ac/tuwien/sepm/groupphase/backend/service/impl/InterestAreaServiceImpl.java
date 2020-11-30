package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InterestAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class InterestAreaServiceImpl implements InterestAreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InterestAreaRepository interestAreaRepository;

    @Autowired
    public InterestAreaServiceImpl(InterestAreaRepository interestAreaRepository) {
        this.interestAreaRepository = interestAreaRepository;
    }

    @Override
    public InterestArea saveInterestArea(InterestArea interestArea) {
        LOGGER.debug("save interestArea({})", interestArea);
        return interestAreaRepository.save(interestArea);
    }

    @Override
    public List<InterestArea> findAll() {
        LOGGER.debug("Find all InterestAreas");
        //TODO lazy loading of interests and tasks
        return interestAreaRepository.findAll();
    }
}
