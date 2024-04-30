package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RandomGeneratorImpl implements RandomGenerator {
    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
