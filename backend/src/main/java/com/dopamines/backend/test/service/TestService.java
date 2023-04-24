package com.dopamines.backend.test.service;

import com.dopamines.backend.test.dto.TestDto;
import com.dopamines.backend.test.entity.Test;
import com.dopamines.backend.test.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public long getCount() {
        return testRepository.count();
    }

    public List<TestDto> getCustom(String name) {
        List<Test> tests = testRepository.findByNameContaining(name);
        return tests.stream()
                .map(test -> new TestDto(test.getName()))
                .collect(Collectors.toList());
    }
}