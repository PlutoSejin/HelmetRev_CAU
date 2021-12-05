<?php

    $conn = mysqli_connect("localhost", "brain2020", "k~410528", "brain2020");

    mysqli_query($conn,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $machineID = $_POST["machineID"];

    $response = mysqli_query($conn, "SELECT INPUT_DATE FROM BRAIN_WAVE_2 WHERE MACHINE_ID='$machineID' AND USER_ID = '$userID' ORDER BY INPUT_TIME_STAMP DESC");
    $result = array();
    $result["success"] = false;
    $result['cnt']=0;

    $result['row'] = mysqli_num_rows($response);
    for($i=0 ; $i<mysqli_num_rows($response) ; $i++ ){
        $result["success"] =true;
        $row = mysqli_fetch_assoc($response);
        $date = strtotime($row['INPUT_DATE']);
        $Year = date('Y',$date);
        $Month = date('m',$date);
        $Day = date('d',$date);
        $result['year']= $Year;
        $result['month'] = $Month;
        $result['day']=$Day;
        $result['cnt']++;
        $result[(string)$result['cnt']] = $Year;
        $result['cnt']++;
        $result[(string)$result['cnt']] = $Month;
        $result['cnt']++;
        $result[(string)$result['cnt']] = $Day;
    }

    /*while(mysqli_num_rows($response) !=0){
        $result["success"] =true;
        //$row = mysqli_fetch_assoc($response);
        $result['cnt']++;
        $date = strtotime($row['INPUT_DATE']);
        $Year = date('Y',$date);
        $Month = date('m',$date);
        $Day = date('d',$date);
        $cnt++;
        $result['year']= $Year;
        $result['month'] = $Month;
        $result['day']=$Day;
        $result[(string)$cnt] = $Year;
        $cnt++;
        $result[(string)$cnt] = $Month;
        $cnt++;
        $result[(string)$cnt] = $Day;

    }*/
    echo json_encode($result);


    ?>


