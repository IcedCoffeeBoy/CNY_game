package com.dsta.CNYBackend.poll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PollService {
    private PollRepository pollRepository;

    @Autowired
    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll save(Poll poll) {
        Poll saved = this.pollRepository.saveAndFlush(poll);
        return saved;
    }

    public Optional<Poll> getPollByPosition(Integer position) {
        Poll poll = new Poll();
        poll.setQuestionPosition(position);
        return this.pollRepository.findOne(Example.of(poll));
    }
    
    public List<Poll> getAll() {
        return this.pollRepository.findAll();
    }

}
