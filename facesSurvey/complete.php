<?php
// Comment out when not debugging
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once './functions.inc.php';
require_once './errors.inc.php';

// if there is no session, go back to registration
if (!isset($_SESSION['User']))
    header('Location: index.php');

// User attempted to access complete page without having finished survey, send them back
if (isset($_SESSION['modelCt']) && isset($_SESSION['User']))
{
    if ($_SESSION['modelCt'] < 14)
        header('Location: survey.php');
}

$database = new Database();

// go ahead and save new user
if ($database->saveUser($_SESSION['User']))
{
    // grab the ID for the user freshly put into the db
    $userDbId = $database->getUserId($_SESSION['User']);
    $database->saveSurveys($userDbId, $_SESSION['surveyData']);

    // Unset all of the session variables.
    $_SESSION = array();

    // If it's desired to kill the session, also delete the session cookie.
    // Note: This will destroy the session, and not just the session data!
    if (ini_get("session.use_cookies"))
    {
        $params = session_get_cookie_params();
        setcookie(session_name(), '', time() - 42000,
            $params["path"], $params["domain"],
            $params["secure"], $params["httponly"]
        );
    }
    // Finally, destroy the session.
    session_destroy();
}
else
{
    echo 'Database save failure.';
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>UNCA Faces Survey</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <!-- FontAwesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    </head>

    <body>

        <div class="container">
            <div class="row">
                <img style="margin-top:20px;" src="unc_asheville_logo.gif" style="height:60%;" alt="UNCA" />
                <p style="margin:14px 20px 0 0;" class="pull-right">
            </div>

            <div class="row omb_row-sm-offset-3">
                <div class="col-xs-12 col-sm-6 col-sm-offset-3">
                    <h1>Survey Completed</h1>
                    <?php
                    // Error message display
                    if (isset($_POST['error']))
                        echo '<div class="alert alert-dismissible alert-danger">'.$_POST["error"].'</div>';
                    ?>
                    Thank you for participating!
                </div>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    </body>
</html>
