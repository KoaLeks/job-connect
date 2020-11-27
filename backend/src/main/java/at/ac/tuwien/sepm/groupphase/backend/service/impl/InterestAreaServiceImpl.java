package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InterestAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

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
        LOGGER.trace("save interestArea({})", interestArea);
        //TODO validator.validateNewInterestArea(interestArea);
        return interestAreaRepository.save(interestArea);
    }
}
