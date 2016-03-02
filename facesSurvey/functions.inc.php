<?php

include_once './User.class.php';
include_once './Database.class.php';

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
        if (empty($email) || $email == "")
        {
            sendError(1); // email field left blank
            return;
        }
        if (!isValidEmail($email))
        {
            sendError(2); // invalid email format
            return;
        }
        if (strlen($email) > 50)
        {
            sendError(3); // email address too large
            return;
        }

        // Birth year validation checks
        if ($birthyear == "")
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
        if ($gender == "")
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
