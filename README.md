# FootballPairs
Understanding of the task: Creating backend logic for and app that calculates the time that football players played together in pairs. All the data must be loaded from CSV files and saved into database.

Algorithm: 
1. Validate CSV files.
2. Save all the data in database.
3. Process the data:
   1. Check every match by ID.
   2. Check every player ID in that match and calculate the time played together for every possible pair.
   3. Save all the data in database.
   4. Show the result ordered by minutes played together then player A ID then player B ID for every possible pair in every possible match.
