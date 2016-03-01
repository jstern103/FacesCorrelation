<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        require_once '../../webConnections/faces.inc';

        $connection = new mysqli($host, $user, $password, $db);
        if ($connection->connect_error) {
            die("Connection failed: " . $connection->connect_error);
        }

        $faces = $connection->query("SELECT  FROM  ORDER BY  ASC");

        $connection->close();
        ?>
    </body>
</html>
