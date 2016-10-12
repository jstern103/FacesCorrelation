<?php

require_once('config.php');

class Database
{

    public static $db = false;

    function __construct()
    {
        if (self::$db === false)
            $this->connect();
    }

    private function connect()
    {
        $dsn = "mysql:dbname=" . Config::$DB_NAME . ";host=" . Config::$DB_SERVER;
        try
        {
            self::$db = new PDO($dsn, Config::$DB_USERNAME, Config::$DB_PASSWORD, array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES \'UTF8\''));
            self::$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        }
        catch (PDOException $e)
        {
            echo "Database Error: ".$e->getMessage();
        }
    }

    // Receives an object of the User class and saves that user to the database
    public function saveUser(&$user)
    {
        try
        {
            $stmt = self::$db->prepare("INSERT INTO Raters (email, genderCode, birthYear, classId, dateAdded) VALUES (?, ?, ?, ?, ?)");
            $stmt->bindParam(1, $user->email);
            $stmt->bindParam(2, $user->genderId);
            $stmt->bindParam(3, $user->birthYear);
            $stmt->bindParam(4, $user->classId);
            $stmt->bindParam(5, $user->createDate);
            $stmt->execute();

            return true;
        }
        catch (PDOException $e)
        {
            echo "Database Error: The user could not be added.<br>".$e->getMessage();
            return false;
        }
    }

    // Receives an object of the User class, performs a query to see if a user with the same email address exists or not
    public function getUser(&$user)
    {
        $stmt = self::$db->prepare("SELECT 0 FROM Raters WHERE email = ?");
        $stmt->execute(array($user->email));

        return $stmt->fetch() ? true : false;
    }

    // Receives an object of the User class, queries and returns that user's ID in the database (if exists)
    public function getUserId(&$user)
    {
        $stmt = self::$db->prepare("SELECT raterId FROM Raters WHERE email = ? LIMIT 1");
        $stmt->execute(array($user->email));

        return $stmt->fetch(PDO::FETCH_COLUMN, 0);
    }

    // Queries the database for 15 random models, or 15 least-surveyed models. Returns them in a random order.
    public function getListOfModels(&$user)
    {
        if ($user->genderId === "female") {
            // Select a random group with the fewest number of female raters
            $sql = "
            	SELECT modelId
            	FROM MGMap
            	WHERE groupId = (SELECT groupId
                FROM Raters
                WHERE groupId IS NOT NULL AND genderCode = 1
                GROUP BY groupId
                ORDER BY COUNT(groupId), ASC
                LIMIT 1)
            ";
        } else if ($user->genderId === "male") {
            // Select a random group with the fewest number of male raters
            $sql = "
        		SELECT modelId
            	FROM MGMap
            	WHERE groupId =(SELECT groupId
                FROM Raters
                WHERE groupId IS NOT NULL AND genderCode = 2
                GROUP BY groupId
                ORDER BY COUNT(groupId), ASC
                LIMIT 1)
            ";
        } else {
            // Select a random group with the fewest number of total raters
            $sql = "
            
                SELECT modelId
                FROM MGMap
                WHERE groupId = (SELECT groupId
                FROM Raters
                WHERE groupId IS NOT NULL
                GROUP BY groupId
                ORDER BY COUNT(groupId), ASC
                LIMIT 1)
            ";
        }
        $stmt = self::$db->prepare($sql);
        $stmt->execute();
        $results = $stmt->fetchAll(PDO::FETCH_COLUMN, 0);
        /*$sql = "
                SELECT modelId
                FROM `Ratings`
                GROUP BY modelId
                ORDER BY COUNT(modelId), RAND() DESC
                LIMIT 15
                "; // Ordering by COUNT() gets the least answered surveys, adding RAND() simply randomizes the order, but the results are the same.

        $stmt = self::$db->prepare($sql);
        $stmt->execute();

        // we have enough existing data to pull the most 15 under-reviewed models
        if ($stmt->rowCount() >= 15)
        {
            $results = $stmt->fetchAll(PDO::FETCH_COLUMN, 0);
        }
        else // not enough data yet, get models randomly for now
        {
            $sql = "
                    SELECT modelId FROM Models
                    ORDER BY RAND()
                    LIMIT 15
                    ";

            $stmt = self::$db->prepare($sql);
            $stmt->execute();

            $results = $stmt->fetchAll(PDO::FETCH_COLUMN, 0);
        }

        return $results; // shuffles array*/
    }

    // Receives an array of Surveys, as specified in the Survey class, as well as a user ID number retreived by calling getUserId().
    // A query is constructed based on the size of the object array, and then accordingly prepares and inserts into the database.
    public function saveSurveys(&$userDbId, &$data)
    {
        $sql = 'INSERT INTO Ratings (raterId, modelId, attributeId, ratingValue) VALUES ';
        $insertQuery = array();
        $insertData = array();
        foreach ($data as $row)
        {
            $insertQuery[] = '(?, ?, ?, ?)';
            $insertData[] = $userDbId;
            $insertData[] = $row->modelId;
            $insertData[] = $row->attributeId;
            $insertData[] = $row->ratingValue;
        }

        if (!empty($insertQuery))
        {
            $sql .= implode(', ', $insertQuery);
            $stmt = self::$db->prepare($sql);

            try
            {
                $stmt->execute($insertData);
            }
            catch (PDOException $e)
            {
                echo "Database Error: ".$e->getMessage();
            }
        }
    }
}
?>
