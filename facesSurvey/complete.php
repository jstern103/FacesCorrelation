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

// has the data been recorded yet?
if (!isset($_SESSION['finished']))
{
    // go ahead and save new user
    if ($database->saveUser($_SESSION['User']))
    {
        // grab the ID for the user freshly put into the db
        $userDbId = $database->getUserId($_SESSION['User']);
        $database->saveSurveys($userDbId, $_SESSION['surveyData']);
        $_SESSION['finished'] = true;
        session_destroy(); // just in case
    }
    else
    {
        echo 'Database save failure.';
    }
}

?>

You're finished!
