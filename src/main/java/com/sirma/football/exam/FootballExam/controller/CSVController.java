package com.sirma.football.exam.FootballExam.controller;

import com.sirma.football.exam.FootballExam.service.CSVValidationAndSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * REST controller that handles the upload, validation, and saving of CSV files.
 */
@RestController
@RequestMapping("/football")
public class CSVController {

    @Autowired
    private CSVValidationAndSaveService csvValidationAndSaveService;

    /**
     * POST endpoint for uploading CSV files
     * It validates the file name and then processes the file
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        List<String> allowedFileNames = Arrays.asList("players.csv", "teams.csv", "matches.csv", "records.csv");

        if (fileName == null || !allowedFileNames.contains(fileName.toLowerCase())) {
            return ResponseEntity.badRequest().body("Invalid file name. Please upload " + allowedFileNames);

        } else {
            return processCSV(file, fileName);
        }

    }

    /**
     * Helper method to process the uploaded CSV file based on its file name
     * It validates and processes the file accordingly
     */
    private ResponseEntity<String> processCSV(MultipartFile file, String expectedFileName) {
        if (file.isEmpty()) {
            System.out.println(ResponseEntity.badRequest().body("File is empty. Please upload a valid " + expectedFileName));

        }

        String fileName = file.getOriginalFilename();

        try {
            switch (fileName.toLowerCase()) {
                case "players.csv":
                    csvValidationAndSaveService.validatePlayersCsv(file);
                    break;

                case "teams.csv":
                    csvValidationAndSaveService.validateTeamsCsv(file);
                    break;

                case "matches.csv":
                    csvValidationAndSaveService.validateMatchesCsv(file);
                    break;

                case "records.csv":
                    csvValidationAndSaveService.validateRecordsCsv(file);
                    break;

                default:
                    return ResponseEntity.badRequest().body("Unknown file type. Please upload a valid file.");

            }

            String entityType = determineEntityType(fileName);
            /**
             * Saves the validated data to the corresponding table in the database
             */
            csvValidationAndSaveService.SaveData(file, entityType);

            return ResponseEntity.ok("File: " + fileName + " uploaded, validated and saved into " + entityType + " table successfully!");


        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());

        }

    }

    /**
     * Method to determine the entity type based on the file name
     */
    public String determineEntityType(String fileName) {
        switch (fileName.toLowerCase()) {
            case "players.csv":
                return "players";
            case "teams.csv":
                return "teams";
            case "matches.csv":
                return "matches";
            case "records.csv":
                return "records";
            default:
                return null;

        }

    }

}
