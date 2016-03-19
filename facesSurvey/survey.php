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

if (isset($_POST['submit']))
{
   SubmitSurvey();
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
    <link rel="stylesheet" href="sliders.css">
    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <style>
    .input-group-addon {
        min-width:120px;
        text-align:left;
    }
    .input-group {
        min-width: 500px;
    }
    </style>
  </head>

  <body>

<div class="container">
    <div class="row">
        <img style="margin-top:20px;"class="pull-left" src="https://www1.cfnc.org/school_logos/CFNC/University_of_North_Carolina_at_Asheville/unc_asheville_logo.gif" style="height:60%;" alt="UNCA" />
        <p style="margin:14px 20px 0 0;" class="pull-right">
        <a href="javascript:close_window();"><i class="fa fa-sign-out"></i> Exit</a></p>
    </div>

    <div class="row">
        <div class="col-md-6">
            <?php
            // Error message display
            if (isset($_POST['error']))
                echo '<div class="alert alert-dismissible alert-danger">'.$_POST["error"].'</div>';
            ?>
            <form action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="post" class="form-inline" id="survey-form">
                <div class="input-group">
                    <span class="input-group-addon">Unattractive</span>
                    <input type="range" name="attractiveness" min="-100" max="100" value="0" class="form-control">
                    <span class="input-group-addon">Attractive</span>
                </div>
                <span class="help-block"></span>

                <div class="input-group">
                    <span class="input-group-addon">Male</span>
                    <input type="range" name="malefemale" min="-100" max="100" value="0" class="form-control">
                    <span class="input-group-addon">Female</span>
                </div>
                <span class="help-block"></span>

                <div class="input-group">
                    <span class="input-group-addon">Intimidating</span>
                    <input type="range" name="initimapproach" min="-100" max="100" value="0" class="form-control">
                    <span class="input-group-addon">Approachable</span>
                </div>
                <span class="help-block"></span>

                <div class="input-group">
                    <span class="input-group-addon">Deceitful</span>
                    <input type="range" name="deceittrust" min="-100" max="100" value="0" class="form-control">
                    <span class="input-group-addon">Trustworthy</span>
                </div>
                <span class="help-block"></span>

                <div class="input-group">
                    <span class="input-group-addon">Sad</span>
                    <input type="range" name="sadhappy" min="-100" max="100" value="0" class="form-control">
                    <span class="input-group-addon">Happy</span>
                </div>
                <span class="help-block"></span>

                <button class="btn btn-lg btn-primary btn-block" id="survey-submit" type="submit" name="submit">Submit</button>
            </form>
        </div>
      <div class="col-md-6">
        <img src="ComputerScience.jpg" style="width:75%;" />
        <div id="dialog" style="display:none">
            <p>You have left all slider values as their default, neutral positions. Are you sure this is what you intented?</p>
        </div>
      </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js" integrity="sha256-xNjb53/rY+WmG+4L6tTl9m6PpqknWZvRt0rO1SRnJzw=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <script>
    $('input[type="range"]').on('mouseup', function() {
      this.blur();
    });

    function submit_form()
    {
        //$('#survey-form').submit();
        //$('form[class="form-inline"]').submit();
        document.getElementById("survey-form").submit();
    }

    var currentForm;
    $('#dialog').dialog({
        autoOpen: false,
        title: "Confirm Input",
        resizable: false,
        height: 220,
        modal: true,
        buttons: {
            "Yes, Submit": function() {
                submit_form();
                $(this).dialog('close');
            },
            Cancel: function() {
                $(this).dialog('close');
            }
        }
    });

    $('#survey-submit').on('click', function(e)
    {
        var attractiveness = $("input[name='attractiveness']").val();
        var malefemale = $("input[name='malefemale']").val();
        var initimapproach = $("input[name='initimapproach']").val();
        var deceittrust = $("input[name='deceittrust']").val();
        var sadhappy = $("input[name='sadhappy']").val();

        if (attractiveness == 0 && malefemale == 0 && initimapproach == 0 && deceittrust == 0 && sadhappy == 0)
        {
            //e.preventDefault();
            currentForm = $(this).closest("form");
            $('#dialog').dialog('open');
            return false;
        }
    });
</script>
  </body>
</html>
