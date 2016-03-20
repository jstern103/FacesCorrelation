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
            // error handler
        }
    }

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

    public function getUser(&$user)
    {
        $stmt = self::$db->prepare("SELECT 0 FROM Raters WHERE email = ?");
        $stmt->execute(array($user->email));

        return $stmt->fetch() ? true : false;
    }

    public function getUserId(&$user)
    {
        $stmt = self::$db->prepare("SELECT raterId FROM Raters WHERE email = ? LIMIT 1");
        $stmt->execute(array($user->email));

        return $stmt->fetch(PDO::FETCH_COLUMN, 0);
    }

    public function getListOfModels()
    {
        $sql = "
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

        return $results; // shuffles array
    }

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
