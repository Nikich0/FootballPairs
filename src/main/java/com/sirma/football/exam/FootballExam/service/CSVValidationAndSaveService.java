package com.sirma.football.exam.FootballExam.service;

import com.sirma.football.exam.FootballExam.model.Matches;
import com.sirma.football.exam.FootballExam.model.Players;
import com.sirma.football.exam.FootballExam.model.Records;
import com.sirma.football.exam.FootballExam.model.Teams;
import com.sirma.football.exam.FootballExam.repository.MatchRepository;
import com.sirma.football.exam.FootballExam.repository.PlayerRepository;
import com.sirma.football.exam.FootballExam.repository.RecordsRepository;
import com.sirma.football.exam.FootballExam.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service that handles the validation and saving of the CSV data
 */
@Service
public class CSVValidationAndSaveService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private RecordsRepository recordsRepository;

    private static final String[] expectedHeaderPlayer = {"ID", "TeamNumber", "Position", "FullName", "TeamID"};
    private static final String[] expectedHeaderTeam = {"ID", "Name", "ManagerFullName", "Group"};
    private static final String[] expectedHeaderMatch = {"ID", "ATeamID", "BTeamID", "Date", "Score"};
    private static final String[] expectedHeaderRecord = {"ID", "PlayerID", "MatchID", "fromMinutes", "toMinutes"};

    /**
     * Validation methods for each type of CSV file
     */
    public void validatePlayersCsv(MultipartFile file) throws IOException {
        validateCsv(file, expectedHeaderPlayer);

    }

    public void validateTeamsCsv(MultipartFile file) throws IOException {
        validateCsv(file, expectedHeaderTeam);

    }

    public void validateMatchesCsv(MultipartFile file) throws IOException {
        validateCsv(file, expectedHeaderMatch);

    }

    public void validateRecordsCsv(MultipartFile file) throws IOException {
        validateCsv(file, expectedHeaderRecord);

    }

    /**
     * Method that validates the CSV file by comparing headers and validating each row
     */
    public void validateCsv(MultipartFile file, String[] expectedHeaders) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int lineNum = 0;

            String fileName = file.getOriginalFilename();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                if (lineNum == 0) {
                    validateHeader(columns, expectedHeaders);
                } else {
                    validateRow(columns, lineNum, fileName);

                }

                lineNum++;

            }

            if (lineNum == 0) {
                throw new RuntimeException("CSV file is empty.");

            }

        }

    }

    /**
     * Validates the header of the CSV file if it matches the expected header
     */
    private void validateHeader(String[] columns, String[] expectedHeaders) {
        if (columns.length != expectedHeaders.length) {
            throw new RuntimeException("Invalid CSV header. Expected " + expectedHeaders.length + " columns.");

        }

        for (int i = 0; i < expectedHeaders.length; i++) {
            if (!columns[i].trim().equalsIgnoreCase(expectedHeaders[i])) {
                throw new RuntimeException("Invalid header. Expected '" + expectedHeaders[i] + "' but got '" + columns[i] + "'.");

            }

        }

    }

    /**
     * Validates each row of data based on the file type
     */
    private void validateRow(String[] columns, int lineNum, String fileName) {
        switch (fileName) {
            /**
             * Validation logic for "players.csv" rows
             */
            case "players.csv":
                if (columns.length != expectedHeaderPlayer.length) {
                    throw new RuntimeException("Invalid row at line: " + lineNum + ". Expected " + expectedHeaderPlayer.length + " columns.");

                }

                for (int i = 0; i < columns.length; i++) {
                    if (i == 3) {
                        if (!columns[i].matches("[\\p{L} ()'-]+")) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just letters");

                        }
                    } else if (i == 0 || i == 1 || i == 4) {
                        if (!containsOnlyDigits(columns[i])) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just digits");

                        }
                    } else {
                        if (!containsOnlyLetters(columns[i])) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just letters");

                        }

                    }

                }
                break;

            /**
             * Validation logic for "teams.csv" rows
             */
            case "teams.csv":
                if (columns.length != expectedHeaderTeam.length) {
                    throw new RuntimeException("Invalid row at line " + lineNum + ". Expected " + expectedHeaderTeam.length + " columns.");

                }

                for (int i = 0; i < columns.length; i++) {
                    if (i == 0) {
                        if (!containsOnlyDigits(columns[i])) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just digits");
                        }
                    } else {
                        if (!containsOnlyLetters(columns[i])) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just letters");

                        }

                    }

                }
                break;

            /**
             * Validation logic for "matches.csv" rows
             */
            case "matches.csv":
                if (columns.length != expectedHeaderMatch.length) {
                    throw new RuntimeException("Invalid row at line " + lineNum + ". Expected " + expectedHeaderMatch.length + " columns.");

                }

                for (int i = 0; i < columns.length; i++) {
                    if (i == 0 || i == 1 || i == 2) {
                        if (!containsOnlyDigits(columns[i])) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just digits");
                        }
                    } else if (i == 4) {
                        if (columns[4] == null || !columns[4].matches("\\d+-\\d+|\\d+\\(\\d+\\)-\\d+\\(\\d+\\)")) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected score: X-Y format");
                        }

                    } else {
                        if (!isValidDate(columns[3])) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected valid Date format");
                        }

                    }

                }
                break;

            /**
             * Validation logic for "matches.csv" rows
             */
            case "records.csv":
                if (columns.length != expectedHeaderRecord.length) {
                    throw new RuntimeException("Invalid row at line " + lineNum + ". Expected " + expectedHeaderRecord.length + " columns.");

                }

                for (int i = 0; i < columns.length; i++) {
                    if (i == 4) {
                        if (!(containsOnlyDigits(columns[i]) || columns[i].matches("NULL"))) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just digits or NULL");

                        }
                    } else {
                        if (!containsOnlyDigits(columns[i])) {
                            throw new RuntimeException("Invalid data input at row: " + lineNum + " and column: " + (i + 1) + " Expected just digits");

                        }

                    }

                }
                break;

            default:
                break;

        }

    }


    public static boolean containsOnlyLetters(String str) {
        return str != null && str.matches("[\\p{L} ]+");

    }

    public static boolean containsOnlyDigits(String str) {
        return str != null && str.matches("\\d+");
    }


    /**
     * Array of supported date formats
     */
    private static final String[] supportedDateFormats = {
            "MM/dd/yyyy",
            "MM-dd-yyyy",
            "yyyy-MM-dd",
            "yyyy/MM/dd",
            "dd-MM-yyyy",
            "dd/MM/yyyy"

    };

    /**
     * Method that checks if the date is valid by regex matching the supported formats
     */
    public static boolean isValidDate(String date) {
        List<String> regexList = new ArrayList<>();
        regexList.add("^(\\d{1,2})/(\\d{1,2})/(\\d{4})$");
        regexList.add("^(\\d{1,2})-(\\d{1,2})-(\\d{4})$");
        regexList.add("^(\\d{4})/(\\d{1,2})/(\\d{1,2})$");
        regexList.add("^(\\d{4})-(\\d{1,2})-(\\d{1,2})$");
        regexList.add("^(\\d{1,2})-(\\d{1,2})-(\\d{4})$");
        regexList.add("^(\\d{1,2})/(\\d{1,2})/(\\d{4})$");


        for (String regex : regexList) {
            if (date.matches(regex)) {
                return true;
            }

        }

        return false;
    }

    /**
     * Method that parses the date to one of the supported formats
     */
    public static Date parseDate(String date) throws ParseException {
        for (String format : supportedDateFormats) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormat.setLenient(false);

            return simpleDateFormat.parse(date);

        }

        throw new IllegalArgumentException("Invalid date format: " + date);

    }


    /**
     * Method that saves the CSV data in the database based on the entity type
     */
    public void SaveData(MultipartFile file, String entityType) throws IOException {
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();

            switch (entityType.toLowerCase()) {
                case "players":
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");

                        Players players = new Players();
                        players.setId(Integer.parseInt(data[0]));
                        players.setTeamNumber(Integer.parseInt(data[1]));
                        players.setPosition(data[2]);
                        players.setFullName(data[3]);
                        players.setTeamId(Integer.parseInt(data[4]));

                        playerRepository.save(players);

                    }
                    break;

                case "teams":
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");

                        Teams teams = new Teams();
                        teams.setId(Integer.parseInt(data[0]));
                        teams.setName(data[1]);
                        teams.setManagerName(data[2]);
                        teams.setFootballGroup(data[3]);

                        teamRepository.save(teams);

                    }
                    break;

                case "matches":
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");

                        Matches matches = new Matches();
                        matches.setId(Integer.parseInt(data[0]));
                        matches.setATeamId(Integer.parseInt(data[1]));
                        matches.setBTeamId(Integer.parseInt(data[2]));
                        matches.setDateOfMatch(parseDate(data[3]));
                        matches.setScore(data[4]);

                        matchRepository.save(matches);

                    }
                    break;

                case "records":
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");

                        Records records = new Records();
                        records.setId(Integer.parseInt(data[0]));
                        records.setPlayerId(Integer.parseInt(data[1]));
                        records.setMatchId(Integer.parseInt(data[2]));
                        records.setFromMinutes(Integer.parseInt(data[3]));
                        if (data[4].equals("NULL")) {
                            records.setToMinutes(90);
                        } else {
                            records.setToMinutes(Integer.parseInt(data[4]));
                        }

                        recordsRepository.save(records);

                    }
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported entity type: " + entityType);

            }

        } catch (ParseException e) {
            throw new RuntimeException(e);

        }

    }

}