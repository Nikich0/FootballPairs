package com.sirma.football.exam.FootballExam.controller;

import com.sirma.football.exam.FootballExam.model.PlayerInteractions;
import com.sirma.football.exam.FootballExam.service.PairsProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller responsible for processing player interactions
 */
@RestController
@RequestMapping("/football")
public class ProcessController {

    @Autowired
    private PairsProcessingService pairProcessingService;

    @PostMapping("/check")
    public String processPlayerInteractions() {
        pairProcessingService.processRecords();
        return "Player interactions processed and saved successfully.";

    }

    @PostMapping("/orderedlist")
    public List<PlayerInteractions> getOrderedInteractions() {
        return pairProcessingService.getAllInteractionsOrdered();

    }

}