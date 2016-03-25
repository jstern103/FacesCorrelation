<?php

include_once './User.class.php';
include_once './Database.class.php';
include_once './Survey.class.php';

session_start();

function Register()
{
    $date = date("Y-m-d");
    $email = &$_POST["email"];
    $agreement = &$_POST["agreement"];
    $birthyear = &$_POST["birthyear"];
    $gender = &$_POST["gender"];
    $class = &$_POST["class"];

    // Remove whitespace + tags to prevent XSS
    $email = strip_tags(trim($email));
    $birthyear = strip_tags(trim($birthyear));
    $gender = strip_tags(trim($gender));
    $class = strip_tags(trim($class));

    if (isset($agreement)) // agreed to IRB statement
    {
        // Email validation checks
        if (is_null($email) || $email == "")
        {
            sendError(1); // email field left blank
            return;
        }
        if (!isValidEmail($email))
        {
            sendError(2); // invalid email format
            return;
        }
        if (!strpos($email, "@unca.edu"))
        {
            sendError(13); // not an @unca.edu email address
            return;
        }
        if (strlen($email) > 50)
        {
            sendError(3); // email address too large
            return;
        }

        // Birth year validation checks
        if (is_null($birthyear) || $birthyear == "")
        {
            sendError(4); // birth year left blank
            return;
        }
        if (!isValidYear($birthyear))
        {
            sendError(5); // invalid year format or not within range
            return;
        }

        // Gender validation checks
        if (is_null($gender) || $gender == "")
        {
            sendError(6); // no gender selected
            return;
        }
        if (!isValidGender($gender))
        {
            sendError(7); // invalid gender selection
            return;
        }

        // Class validation checks
        if ($class == "")
        {
            sendError(8); // class left blank
            return;
        }
        if (!isValidClass($class))
        {
            sendError(9); // invalid class selection
            return;
        }

        // Save user info to a session variable. We do not save to the DB until user has
        // completed the entire survey.
        $_SESSION['User'] = new User($email, $birthyear, $gender, $class, $date);
        $database = new Database();

        // Is user already registered?
        if ($database->getUser($_SESSION['User']))
        {
            sendError(10); // already registered
            return;
        }

        // Everything is OK, send onward to survey
        header('Location: survey.php');
    }
    else
        sendError(11); // hasn't agreed to IRB statement
}


function SubmitSurvey()
{
    $attractiveness = &$_POST["attractiveness"];
    $malefemale = &$_POST["malefemale"];
    $hostfriend = &$_POST["hostfriend"];
    $initimapproach = &$_POST["initimapproach"];
    $deceittrust = &$_POST["deceittrust"];
    $sadhappy = &$_POST["sadhappy"];

    // Remove whitespace + tags to prevent XSS
    $attractiveness = strip_tags(trim($attractiveness));
    $malefemale = strip_tags(trim($malefemale));
    $hostfriend = strip_tags(trim($hostfriend));
    $initimapproach = strip_tags(trim($initimapproach));
    $deceittrust = strip_tags(trim($deceittrust));
    $sadhappy = strip_tags(trim($sadhappy));

    if (validateAndConvertSurveyRange($attractiveness) && validateAndConvertSurveyRange($malefemale) && validateAndConvertSurveyRange($hostfriend)
        && validateAndConvertSurveyRange($initimapproach) && validateAndConvertSurveyRange($deceittrust) && validateAndConvertSurveyRange($sadhappy))
    {
        // I should have prepared for a loop to be here, but I'll just change it later....
        // This is storing each input slider value as a new "Survey" object into a large session array of all their inputs for each surveyData
        // that will all be properly inserted into the Ratings table at the end of the survey
        $currModelId = $_SESSION["modelsToReviewArray"][$_SESSION['modelCt']];
        array_push($_SESSION['surveyData'], new Survey($currModelId, 1, $attractiveness));
        array_push($_SESSION['surveyData'], new Survey($currModelId, 2, $malefemale));
        array_push($_SESSION['surveyData'], new Survey($currModelId, 5, $hostfriend));
        array_push($_SESSION['surveyData'], new Survey($currModelId, 9, $initimapproach));
        array_push($_SESSION['surveyData'], new Survey($currModelId, 10, $deceittrust));
        array_push($_SESSION['surveyData'], new Survey($currModelId, 11, $sadhappy));

        // Debug only
        //print_r($_SESSION['surveyData']);

        if ($_SESSION['modelCt'] >= 14) // they finished, no surveys left
        {
            header('Location: complete.php');
        }
    }
    else
    {
        sendError(12); // invalid range of input value
    }
}

function validateAndConvertSurveyRange(&$input)
{
    if (!is_null($input))
    {
        if ($input >= -100 && $input <= 100)
        {
            $input /= 100; // convert to -1.00 to 1.00 range for database
            return true;
        }
    }

    return false;
}


function isValidEmail($email)
{
    return (filter_var($email, FILTER_VALIDATE_EMAIL) && preg_match('/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/', $email)) ? true : false;
}

function isValidYear($year)
{
    return (($year >= 1916 && $year <= date('Y')) && preg_match('/^\d{4}$/', $year)) ? true : false;
}

// Validates if correct gender was sent from drop down
// Received by reference, and sets $gender to the corresponding genderCode.
function isValidGender(&$gender)
{
    switch ($gender)
    {
        case 'female':
            $gender = 1;
            return true;
        case 'male':
            $gender = 2;
            return true;
        case 'other':
            $gender = 3;
            return true;
        case 'pnta':
            $gender = 4;
            return true;
        default: // improper gender received
            return false;
    }
}

// Validates if correct class was sent from drop down
// Received by reference, and sets $class to the corresponding classId.
function isValidClass(&$class)
{
    switch ($class)
    {
        case 'student':
            $class = 1;
            return true;
        case 'staff':
            $class = 2;
            return true;
        case 'other':
            $class = 3;
            return true;
        default: // improper class received
            return false;
    }
}

?>
