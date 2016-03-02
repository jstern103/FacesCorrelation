<?php
// Comment out when not debugging
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once './functions.inc.php';
require_once './errors.inc.php';

// if there is no session, go back to index
if (!isset($_SESSION['User']))
    header('Location: index.php');

echo 'Welcome ' . $_SESSION['User']->email;
?>