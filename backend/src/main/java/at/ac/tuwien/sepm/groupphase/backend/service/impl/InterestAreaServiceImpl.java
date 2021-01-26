package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InterestAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

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
        return interestAreaRepository.findAll(Sort.by("area"));
    }

    @Override
    public InterestArea findById(Long id) {
        LOGGER.debug("Find InterestArea by id {}", id);
        if(id != null) {
            Optional<InterestArea> interestArea = interestAreaRepository.findById(id);
            if (interestArea.isPresent()) return interestArea.get();
            else throw new NotFoundException(String.format("Could not find interestArea with id %s", id));
        }
        return null;
    }
}
