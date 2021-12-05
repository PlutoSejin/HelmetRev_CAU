<?php
    $conn = mysqli_connect("localhost", "brain2020", "k~410528", "brain2020");

    mysqli_query($conn,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $machineID = $_POST["machineID"];
    $startDate= $_POST["startDate"];
    $endDate= $_POST["endDate"];
    $result = array();
    $result['cnt'] =0;
    $result['av0'] = 0;
    $result['av1'] = 0;
    $result['av2'] = 0;
    $result['av3'] = 0;
    $result['av4'] = 0;
    $result['av5'] = 0;
    $result['av6'] = 0;
    $result['av7'] = 0;
    $result['av8'] = 0;
    $result['av9'] = 0;
    $result['av10'] = 0;
    $result['av11'] = 0;
    $result['av12'] = 0;
    $result['av13'] = 0;
    $result['av14'] = 0;
    $result['av15'] = 0;
    $result['av16'] = 0;
    $result['av17'] = 0;
    $result['av18'] = 0;
    $result['av19'] = 0;
    $result['av20'] = 0;
    $result['av21'] = 0;
    $result['av22'] = 0;
    $result['av23'] = 0;
    $result['av24'] = 0;
    $result['av25'] = 0;
    $result['av26'] = 0;
    $result['av27'] = 0;
    $result['av28'] = 0;
    $result['av29'] = 0;
    $result['av30'] = 0;
    $result['av31'] = 0;
    $result['av32'] = 0;
    $result['av33'] = 0;
    $result['av34'] = 0;
    $result['av35'] = 0;
    $result['av36'] = 0;
    $result['av37'] = 0;
    $result['av38'] = 0;
    $result['av39'] = 0;
    $result['av40'] = 0;
    $result['av41'] = 0;
    $result['av42'] = 0;
    $result['av43'] = 0;
    $result['av44'] = 0;
    $result['av45'] = 0;
    $result['av46'] = 0;
    $result['av47'] = 0;
    $result['av48'] = 0;
    $result['av49'] = 0;
    $result['av50'] = 0;
    $result['CH0'] = 0;
    $result['CH1'] = 0;
    $result['CH2'] = 0;
    $result['CH3'] = 0;
    $result['CH4'] = 0;
    $result['CH5'] = 0;
    $result['CH6'] = 0;
    $result['CH7'] = 0;
    $result['CH8'] = 0;
    $result['CH9'] = 0;
    $result['CH10'] = 0;
    $result['CH11'] = 0;
    $result['CH12'] = 0;
    $result['CH13'] = 0;
    $result['CH14'] = 0;
    $result['CH15'] = 0;
    $result['CH16'] = 0;
    $result['CH17'] = 0;
    $result['CH18'] = 0;
    $result['CH19'] = 0;
    $result['CH20'] = 0;
    $result['CH21'] = 0;
    $result['CH22'] = 0;
    $result['CH23'] = 0;
    $result['CH24'] = 0;
    $result['CH25'] = 0;
    $result['CH26'] = 0;
    $result['CH27'] = 0;
    $result['CH28'] = 0;
    $result['CH29'] = 0;
    $result['CH30'] = 0;
    $result['CH31'] = 0;
    $result['CH32'] = 0;
    $result['CH33'] = 0;
    $result['CH34'] = 0;
    $result['CH35'] = 0;
    $result['CH36'] = 0;
    $result['CH37'] = 0;
    $result['CH38'] = 0;
    $result['CH39'] = 0;
    $result['CH40'] = 0;
    $result['CH41'] = 0;
    $result['CH42'] = 0;
    $result['CH43'] = 0;
    $result['CH44'] = 0;
    $result['CH45'] = 0;
    $result['CH46'] = 0;
    $result['CH47'] = 0;
    $result['CH48'] = 0;
    $result['CH49'] = 0;
    $result['CH50'] = 0;
    $result['row'] = 0;
    $result['INPUT_DATE'] = 0;
    $inputDate = date('m-d-Y h:i:s a', time());
    $response = mysqli_query($conn, "SELECT * FROM BRAIN_WAVE_2 WHERE MACHINE_ID='$machineID' AND USER_ID = '$userID' ORDER BY INPUT_TIME_STAMP DESC LIMIT 1");
    if(mysqli_num_rows($response)==0){
        $result["success"] = false;
        echo json_encode($result);
        mysqli_close($conn);
    }
    else{
        $row = mysqli_fetch_assoc($response);
        $result["success"] = true;
        $result["CH0"] = $row['CH0'];
        $result["CH1"] = $row['CH1'];
        $result['CH2'] = $row['CH2'];
        $result['CH3'] = $row['CH3'];
        $result['CH4'] = $row['CH4'];
        $result['CH5'] = $row['CH5'];
        $result['CH6'] = $row['CH6'];
        $result['CH7'] = $row['CH7'];
        $result['CH8'] = $row['CH8'];
        $result['CH9'] = $row['CH9'];
        $result['CH10'] = $row['CH10'];
        $result['CH11'] = $row['CH11'];
        $result['CH12'] = $row['CH12'];
        $result['CH13'] = $row['CH13'];
        $result['CH14'] = $row['CH14'];
        $result['CH15'] = $row['CH15'];
        $result['CH16'] = $row['CH16'];
        $result['CH17'] = $row['CH17'];
        $result['CH18'] = $row['CH18'];
        $result['CH19'] = $row['CH19'];
        $result['CH20'] = $row['CH20'];
        $result['CH21'] = $row['CH21'];
        $result['CH22'] = $row['CH22'];
        $result['CH23'] = $row['CH23'];
        $result['CH24'] = $row['CH24'];
        $result['CH25'] = $row['CH25'];
        $result['CH26'] = $row['CH26'];
        $result['CH27'] = $row['CH27'];
        $result['CH28'] = $row['CH28'];
        $result['CH29'] = $row['CH29'];
        $result['CH30'] = $row['CH30'];
        $result['CH31'] = $row['CH31'];
        $result['CH32'] = $row['CH32'];
        $result['CH33'] = $row['CH33'];
        $result['CH34'] = $row['CH34'];
        $result['CH35'] = $row['CH35'];
        $result['CH36'] = $row['CH36'];
        $result['CH37'] = $row['CH37'];
        $result['CH38'] = $row['CH38'];
        $result['CH39'] = $row['CH39'];
        $result['CH40'] = $row['CH40'];
        $result['CH41'] = $row['CH41'];
        $result['CH42'] = $row['CH42'];
        $result['CH43'] = $row['CH43'];
        $result['CH44'] = $row['CH44'];
        $result['CH45'] = $row['CH45'];
        $result['CH46'] = $row['CH46'];
        $result['CH47'] = $row['CH47'];
        $result['CH48'] = $row['CH48'];
        $result['CH49'] = $row['CH49'];
        $result['CH50'] = $row['CH50'];
        $result['INPUT_DATE'] = $row['INPUT_DATE'];
        $inputDate= $result['INPUT_DATE'];
    }

    $response = mysqli_query($conn, "SELECT * FROM BRAIN_WAVE_2 WHERE MACHINE_ID='$machineID' AND USER_ID = '$userID' AND INPUT_DATE <= $inputDate   ORDER BY INPUT_TIME_STAMP DESC");
    $result['row'] = mysqli_num_rows($response);
    while(mysqli_num_rows($response) !=0){
        $row = mysqli_fetch_assoc($response);
        $result['av0'] += $row['CH0'];
        $result['av1'] += $row['CH1'];
        $result['av2'] += $row['CH2'];
        $result['av3'] += $row['CH3'];
        $result['av4'] += $row['CH4'];
        $result['av5'] += $row['CH5'];
        $result['av6'] += $row['CH6'];
        $result['av7'] += $row['CH7'];
        $result['av8'] += $row['CH8'];
        $result['av9'] += $row['CH9'];
        $result['av10'] += $row['CH10'];
        $result['av11'] += $row['CH11'];
        $result['av12'] += $row['CH12'];
        $result['av13'] += $row['CH13'];
        $result['av14'] += $row['CH14'];
        $result['av15'] += $row['CH15'];
        $result['av16'] += $row['CH16'];
        $result['av17'] += $row['CH17'];
        $result['av18'] += $row['CH18'];
        $result['av19'] += $row['CH19'];
        $result['av20'] += $row['CH20'];
        $result['av21'] += $row['CH21'];
        $result['av22'] += $row['CH22'];
        $result['av23'] += $row['CH23'];
        $result['av24'] += $row['CH24'];
        $result['av25'] += $row['CH25'];
        $result['av26'] += $row['CH26'];
        $result['av27'] += $row['CH27'];
        $result['av28'] += $row['CH28'];
        $result['av29'] += $row['CH29'];
        $result['av30'] += $row['CH30'];
        $result['av31'] += $row['CH31'];
        $result['av32'] += $row['CH32'];
        $result['av33'] += $row['CH33'];
        $result['av34'] += $row['CH34'];
        $result['av35'] += $row['CH35'];
        $result['av36'] += $row['CH36'];
        $result['av37'] += $row['CH37'];
        $result['av38'] += $row['CH38'];
        $result['av39'] += $row['CH39'];
        $result['av40'] += $row['CH40'];
        $result['av41'] += $row['CH41'];
        $result['av42'] += $row['CH42'];
        $result['av43'] += $row['CH43'];
        $result['av44'] += $row['CH44'];
        $result['av45'] += $row['CH45'];
        $result['av46'] += $row['CH46'];
        $result['av47'] += $row['CH47'];
        $result['av48'] += $row['CH48'];
        $result['av49'] += $row['CH49'];
        $result['av50'] += $row['CH50'];

        if($result['cnt'] ==1 && $result['cnt'] ==mysqli_num_rows($response)){

            echo json_encode($result);
            break;
        }
        else if($result['cnt'] ==2 && $result['cnt'] ==mysqli_num_rows($response)){

            echo json_encode($result);
            break;
        }
        else if($result['cnt']==3 && $result['cnt']==mysqli_num_rows($response)){
            echo json_encode($result);
            break;
        }
        else if($result['cnt']==4 && $result['cnt']==mysqli_num_rows($response)){
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
        echo json_encode($result);
    }

