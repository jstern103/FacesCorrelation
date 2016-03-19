<?php
// Comment out when not debugging
error_reporting(E_ALL);
ini_set('display_errors', '1');

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
    <title>Raters Page</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
  </head>

  <body>

<div class="container">
    <div class="row">
        <img style="margin-top:20px;" class="pull-left" src="https://www1.cfnc.org/school_logos/CFNC/University_of_North_Carolina_at_Asheville/unc_asheville_logo.gif" style="height:60%;" alt="UNCA" />
        <p style="margin:14px 20px 0 0;" class="pull-right">
        <a href="javascript:close_window();"><i class="fa fa-sign-out"></i> Exit</a></p>
    </div>

    <div class="row omb_row-sm-offset-3">
        <div class="col-xs-12 col-sm-6 col-sm-offset-3">
            <h1>Faces Registration</h1>
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

                <p>IRB statement goes here</p>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="agreement" required> I agree
                    </label>
              </div>

                <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">Submit</button>
            </form>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
  </body>
</html>
