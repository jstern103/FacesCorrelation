<?php

class User
{
    public $email;
    public $birthYear;
    public $genderId;
    public $classId;
    public $createDate;

    public function __construct($email, $birthYear, $genderId, $classId, $createDate)
    {
        $this->email = $email;
        $this->birthYear = $birthYear;
        $this->genderId = $genderId;
        $this->classId = $classId;
        $this->createDate = $createDate;
    }
}

?>
