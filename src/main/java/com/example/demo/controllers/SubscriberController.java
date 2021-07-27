package com.example.demo.controllers;

import com.example.demo.dto.SubscriberDto;
import com.example.demo.model.Subscriber;
import com.example.demo.repository.SubscriberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SubscriberController {

    private final SubscriberRepository subscriberRepository;
    private final ModelMapper modelMapper;

    public SubscriberController(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping(value = "/subscribers")
    public void createSubscriber(@RequestBody SubscriberDto subscriberDto) {
        subscriberRepository.save(modelMapper.map(subscriberDto, Subscriber.class));
        for (int i = 0; i < 1000000; i++) {
            subscriberRepository.save(modelMapper.map(subscriberDto, Subscriber.class));
        }
    }

    @GetMapping(value = "/subscribers", produces = "application/json")
    public List<SubscriberDto> getSubscribers() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers.stream()
                .map(subscriber -> modelMapper.map(subscriber, SubscriberDto.class))
                .collect(Collectors.toList());
    }
}
