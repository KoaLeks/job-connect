package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InterestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InterestRepository interestRepository;

    @Autowired
    public InterestServiceImpl(InterestRepository interestRepository){
        this.interestRepository = interestRepository;
    }

    @Override
    public List<Interest> findAll() {
        LOGGER.debug("Find all Interests");
        return interestRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.debug("delete interest with id");
        interestRepository.deleteById(id);
    }
}
