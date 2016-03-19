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
        $stmt = self::$db->prepare("INSERT INTO Raters (email, genderCode, birthYear, classId, dateAdded) VALUES (?, ?, ?, ?, ?)");
        $stmt->bindParam(1, $user->email);
        $stmt->bindParam(2, $user->genderId);
        $stmt->bindParam(3, $user->birthYear);
        $stmt->bindParam(4, $user->classId);
        $stmt->bindParam(5, $user->createDate);
        $stmt->execute();
    }

    public function getUser(&$user)
    {
        $stmt = self::$db->prepare("SELECT 0 FROM Raters WHERE email = ?");
        $stmt->execute(array($user->email));

        return $stmt->fetch() ? true : false;
    }

    public function getListOfSurveys()
    {
        $sql = "
                SELECT COUNT(modelId), modelId
                FROM `Ratings`
                GROUP BY modelId
                ORDER BY COUNT(modelId) DESC
                LIMIT 15
                ";

        $stmt = self::$db->prepare($sql);
        $stmt->execute();

        $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $results;
    }

    public function saveSurveys(&$data)
    {
        $sql = 'INSERT INTO Ratings (raterId, modelId, attributeId, ratingValue) VALUES ';
        $insertQuery = array();
        $insertData = array();
        foreach ($data as $row)
        {
            $insertQuery[] = '(?, ?, ?, ?)';
            $insertData[] = $memberid;
            $insertData[] = $row;
        }

        if (!empty($insertQuery))
        {
            $sql .= implode(', ', $insertQuery);
            $stmt = self::$db->prepare($sql);
            $stmt->execute($insertData);
        }
    }
}
?>
