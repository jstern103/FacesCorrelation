<?php

class Survey
{
    public $raterId;
    public $modelId;
    public $attributeId;
    public $ratingValue;

    public function __construct($raterId, $modelId, $attributeId, $ratingValue)
    {
        $this->raterId = $raterId;
        $this->modelId = $modelId;
        $this->attributeId = $attributeId;
        $this->ratingValue = $ratingValue;
    }
}

?>
