<?php
    $conn = mysqli_connect("localhost", "brain2020", "k~410528", "brain2020");

    mysqli_query($conn,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $machineID = $_POST["machineID"];
    $channel = $_POST["channel"];
    $inputDate = $_POST["inputDate"];
    $response = mysqli_query($conn, "SELECT $channel FROM BRAIN_WAVE_2 WHERE MACHINE_ID='$machineID' AND USER_ID = '$userID' AND INPUT_DATE <= '$inputDate' ORDER BY INPUT_TIME_STAMP DESC");
    $result = array();
    $result['cnt'] =1;
    $result['row'] = mysqli_num_rows($response);
    $result["channel1"]=0;
    $result["channel2"]=0;
    $result["channel3"]=0;
    $result["channel4"]=0;
    $result["channel5"]=0;
    $result["INPUT_DATE"]=0;
    $result["INPUT_DATE2"]=0;
    $result["INPUT_DATE3"]=0;
    $result["INPUT_DATE4"]=0;
    $result["INPUT_DATE5"]=0;



    while(mysqli_num_rows($response) !=0){
        $row = mysqli_fetch_assoc($response);
        if($result['cnt']==1) {
            $result["success"] = true;
            $result["channel"] = $row[$channel];
            $result['INPUT_DATE'] = $row['INPUT_DATE'];
        }
        else if($result['cnt']==2){
            $result["channel2"] = $row[$channel];
            $result['INPUT_DATE2'] = $row['INPUT_DATE'];
        }
        else if($result['cnt']==3){

                $result["channel3"] = $row[$channel];
                $result['INPUT_DATE3'] = $row['INPUT_DATE'];
        }
        else if($result['cnt']==4){

                $result["channel4"] = $row[$channel];
                $result['INPUT_DATE4'] = $row['INPUT_DATE'];
        }
        else if($result['cnt']==5){
                $result["channel4"] = $row[$channel];
                $result['INPUT_DATE4'] = $row['INPUT_DATE'];
        }

        if($result['cnt']==1 && result['cnt'] == mysqli_close($response) ){
            echo json_encode($result);
            break;
        }
        else if($result['cnt'] ==2 && $result['cnt'] == mysqli_num_rows($response)){

            echo json_encode($result);
            break;
        }
        else if($result['cnt']==3 && $result['cnt']== mysqli_num_rows($response)){
            echo json_encode($result);
            break;
        }
        else if($result['cnt']==4 && $result['cnt']== mysqli_num_rows($response)){
            echo json_encode($result);
            break;
        }
        else if($result['cnt']==5){
            echo json_encode($result);
            break;
        }
        $result['cnt']++;

    }
    if(mysqli_num_rows($response)==0){
        $result["success"] = false;
        $result['cnt']=0;
        echo json_encode($result);
    }


    ?>