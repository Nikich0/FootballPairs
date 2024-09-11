package com.sirma.football.exam.FootballExam.service;

import com.sirma.football.exam.FootballExam.model.PlayerInteractions;
import com.sirma.football.exam.FootballExam.model.Records;
import com.sirma.football.exam.FootballExam.repository.PlayerInteractionRepository;
import com.sirma.football.exam.FootballExam.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service that handles the logic of the api and saves the result in database table
 */
@Service
public class PairsProcessingService {

    @Autowired
    private RecordsRepository recordsRepository;

    @Autowired
    private PlayerInteractionRepository playerInteractionRepository;

    /**
     * Method that processes all records to calculate the total time hat each pair of players
     * spent on the field together during the same match.
     */
    public void processRecords() {

        List<Records> records = recordsRepository.findAll();
        /**
         * Map that stores the total minutes played together for each player pair
         */
        Map<String, Integer> playerPairMinutes = new HashMap<>();

        /**
         * Iterates over each record to process interactions
         */
        for (Records record : records) {
            int playerId = record.getPlayerId();
            int matchId = record.getMatchId();
            int fromMinutes = record.getFromMinutes();
            int toMinutes = record.getToMinutes();

            /**
             * Gets all records for the same matchId
             */
            List<Records> matchRecords = recordsRepository.findByMatchId(matchId);

            /**
             * Compares the current player record with other players records in the same match
             */
            for (Records otherRecord : matchRecords) {
                int otherPlayerId = otherRecord.getPlayerId();

                /**
                 *  Skip if the players are the same or if playerId > otherPlayerId
                 */
                if (playerId != otherPlayerId && playerId < otherPlayerId) {
                    int otherFromMinutes = otherRecord.getFromMinutes();
                    int otherToMinutes = otherRecord.getToMinutes();

                    /**
                     * Calculates overlap in play time between the two players
                     */
                    int maxFromMinutes = Math.max(fromMinutes, otherFromMinutes);
                    int minToMinutes = Math.min(toMinutes, otherToMinutes);

                    /**
                     * Adds minutes only if they overlap in play time
                     */
                    if (maxFromMinutes < minToMinutes) {
                        int overlapMinutes = minToMinutes - maxFromMinutes;

                        /**
                         * Creates a unique key for the player pair only when playerId < otherPlayerId
                         */
                        String pairKey = playerId + "-" + otherPlayerId;

                        /**
                         * Accumulates the overlap minutes for this player pair
                         */
                        playerPairMinutes.put(pairKey, playerPairMinutes.getOrDefault(pairKey, 0) + overlapMinutes);

                    }

                }

            }

        }

        /**
         * Saves the calculated minutes played together for each player pair
         */
        for (Map.Entry<String, Integer> entry : playerPairMinutes.entrySet()) {
            /**
             * Gets players IDs from the key
             */
            String[] playersPair = entry.getKey().split("-");
            int aPlayerId = Integer.parseInt(playersPair[0]);
            int bPlayerId = Integer.parseInt(playersPair[1]);
            int minutesPlayedTogether = entry.getValue();

            /**
             * Creates and saves the PlayerInteractions entity
             */
            PlayerInteractions interaction = new PlayerInteractions(aPlayerId, bPlayerId, minutesPlayedTogether);
            playerInteractionRepository.save(interaction);

        }

    }

    /**
     * Retrieves all player interactions from the database - ordered
     */
    public List<PlayerInteractions> getAllInteractionsOrdered() {
        return playerInteractionRepository.findAllOrdered();

    }

}