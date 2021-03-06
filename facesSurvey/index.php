<?php
// Comment out when not debugging
error_reporting(E_ALL);
ini_set('display_errors', '1');

// Clear out possible previous sessions
if (session_status() == PHP_SESSION_ACTIVE)
{
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

require_once './functions.inc.php';
require_once './errors.inc.php';

// If user submits form, call Register function
if (isset($_POST['submit']))
{
   Register();
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>UNCA F.A.C.E.S. Survey</title>

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
                    <h1>F.A.C.E.S. Registration</h1>
                    <?php
                    // Error message display
                    if (isset($_POST['error']))
                        echo '<div class="alert alert-dismissible alert-danger">'.$_POST["error"].'</div>';
                    ?>
                    <form action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="post">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                            <input type="text" class="form-control" name="email" maxlength="50" placeholder="UNCA Email" required>
                        </div>
                        <span class="help-block"></span>

                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" name="birthyear" maxlength="50" placeholder="Birth Year" required>
                        </div>
                        <span class="help-block"></span>

                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-venus-mars"></i></span>
                            <select name="gender" class="form-control" required>
                                <option value="" disabled selected> - Gender - </option>
                                <option value="female">Female</option>
                                <option value="male">Male</option>
                                <option value="other">Other</option>
                                <option value="pnta">Prefer not to answer</option>
                            </select>
                        </div>
                        <span class="help-block"></span>

                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-university"></i></span>
                            <select name="class" class="form-control" required>
                                <option value="" disabled selected> - Class Type - </option>
                                <option value="student">Student</option>
                                <option value="staff">Faculty/Staff/Admin</option>
                                <option value="other">Other</option>
                            </select>
                        </div>
                        <span class="help-block"></span>

                        <strong>Informed Consent:</strong>
                        <ol>
                            <li>Your participation in this survey is voluntary. If you choose not to participate, it will not affect your current or future relations with the University.</li>
                            <li>You may exit the survey at any point. If you exit the survey before completion, all of your answers/evaluations will be removed from our system.</li>
                            <li>If you agree to complete this survey, please enter the requested demographic information. We will only use your identifying information (email) to reduce duplicate entries. We will not distribute or sell any identifying information you provide.</li>
                            <li>After entering your demographic information, you will be presented twelve three-dimensional head models -- each on a separate page.  Please evaluate each model according to your aesthetic preferences.</li>
                            <li>If you have any questions concerning this F.A.C.E.S. research project or your rights as participant, you may contact Dr. Marietta E. Cameron at 828.250.3919 or <a href="mailto:mcameron@unca.edu">mcameron@unca.edu</a>. You may also contact the UNC Asheville Institutional Review Board at 828.251.6313 or <a href="mailto:irb@unca.edu">irb@unca.edu</a></li>
                            <li>Please check the checkbox if you wish to continue.</li>
                        </ol>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="agreement" required> I have read, understood, and agree with the above statements.
                            </label>
                        </div>

                        <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">Submit</button>
                        <br /><br />
                    </form>
                </div>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    </body>
</html>
