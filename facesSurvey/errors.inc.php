<?php

function getError($num)
{
    switch($num)
    {
        case 1: return "Email address field was left blank.";
        case 2: return "Email address was not formatted properly.";
        case 3: return "Email address is too large.";
        case 4: return "Birth year was not specified.";
        case 5: return "Birth year was invalid format or range.";
        case 6: return "No gender was selected.";
        case 7: return "An improper gender was submitted. Please use one from the drop down options.";
        case 8: return "No UNCA class type was specified.";
        case 9: return "Invalid UNCA class type. Please select one from the drop down list.";
        case 10: return "User is already registered. You may not retake this survey.";
        case 11: return "Cannot proceed unless the IRB statement is agreed to.";
        case 12: return "Value was beyond possible value range limits.";
        case 13: return "";
        case 14: return "";
        case 15: return "";
        case 16: return "";

        default: return "Invalid error message.";
    }
}

function sendError($str)
{
    $_POST["error"] = getError($str);
}

?>
