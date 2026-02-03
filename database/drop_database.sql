-- Drop Database Script
-- Use with caution - This will delete all data!

USE finalproject;

-- Drop tables in reverse order of dependencies
DROP TABLE IF EXISTS cropsales;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS contactus;
DROP TABLE IF EXISTS membership;

-- Drop database (uncomment if you want to completely remove the database)
-- DROP DATABASE IF EXISTS finalproject;

SELECT 'Database tables dropped successfully' AS Status;
