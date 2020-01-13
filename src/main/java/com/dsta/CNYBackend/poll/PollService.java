package com.dsta.CNYBackend.poll;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {
    private PollRepository pollRepository;


    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll save(Poll poll) {
        return this.pollRepository.save(poll);
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
