<?php
    $conn = mysqli_connect("localhost", "brain2020", "k~410528", "brain2020");

    mysqli_query($conn,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $machineID = $_POST["machineID"];
    $time = $_POST["time"];
    $response = array();
    if ($conn->connect_error) {
        $response["success"] = false;
        echo json_encode($response);
    }
    $CH0 = $_POST["CH0"];
    $CH1 = $_POST["CH1"];
    $CH2 = $_POST["CH2"];
    $CH3 = $_POST["CH3"];
    $CH4 = $_POST["CH4"];
    $CH5 = $_POST["CH5"];
    $CH6 = $_POST["CH6"];
    $CH7 = $_POST["CH7"];
    $CH8 = $_POST["CH8"];
    $CH9 = $_POST["CH9"];
    $CH10 = $_POST["CH10"];
    $CH11 = $_POST["CH11"];
    $CH12 = $_POST["CH12"];
    $CH13 = $_POST["CH13"];
    $CH14 = $_POST["CH14"];
    $CH15 = $_POST["CH15"];
    $CH16 = $_POST["CH16"];
    $CH17 = $_POST["CH17"];
    $CH18 = $_POST["CH18"];
    $CH19 = $_POST["CH19"];
    $CH20 = $_POST["CH20"];
    $CH21 = $_POST["CH21"];
    $CH22 = $_POST["CH22"];
    $CH23 = $_POST["CH23"];
    $CH24 = $_POST["CH24"];
    $CH25 = $_POST["CH25"];
    $CH26 = $_POST["CH26"];
    $CH27 = $_POST["CH27"];
    $CH28 = $_POST["CH28"];
    $CH29 = $_POST["CH29"];
    $CH30 = $_POST["CH30"];
    $CH31 = $_POST["CH31"];
    $CH32 = $_POST["CH32"];
    $CH33 = $_POST["CH33"];
    $CH34 = $_POST["CH34"];
    $CH35 = $_POST["CH35"];
    $CH36 = $_POST["CH36"];
    $CH37 = $_POST["CH37"];
    $CH38 = $_POST["CH38"];
    $CH39 = $_POST["CH49"];
    $CH40 = $_POST["CH40"];
    $CH41 = $_POST["CH41"];
    $CH42 = $_POST["CH42"];
    $CH43 = $_POST["CH43"];
    $CH44 = $_POST["CH44"];
    $CH45 = $_POST["CH45"];
    $CH46 = $_POST["CH46"];
    $CH47 = $_POST["CH47"];
    $CH48 = $_POST["CH48"];
    $CH49 = $_POST["CH49"];
    $CH50 = $_POST["CH50"];

    $statement = mysqli_prepare($conn,"INSERT INTO BRAIN_WAVE_2(machine_id, user_id,input_date, 
                           ch0,ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8,ch9,
                           ch10,ch11,ch12,ch13,ch14,ch15,ch16,ch17,ch18,ch19,
                           ch20,ch21,ch22,ch23,ch24,ch25,ch26,ch27,ch28,ch29,
                           ch30,ch31,ch32,ch33,ch34,ch35,ch36,ch37,ch38,ch39,
                           ch40,ch41,ch42,ch43,ch44,ch45,ch46,ch47,ch48,ch49,ch50) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
    mysqli_stmt_bind_param($statement, "sssiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii",$machineID,$userID,$time, $CH0,$CH1,$CH2,$CH3,$CH4,$CH5,$CH6,$CH7,$CH8,$CH9,$CH10
        ,$CH11,$CH12,$CH13,$CH14,$CH15,$CH16,$CH17,$CH18,$CH19,$CH20
        ,$CH21,$CH22,$CH23,$CH24,$CH25,$CH26,$CH27,$CH28,$CH29,$CH30
        ,$CH31,$CH32,$CH33,$CH34,$CH35,$CH36,$CH37,$CH38,$CH39,$CH40
        ,$CH41,$CH42,$CH43,$CH44,$CH45,$CH46,$CH47,$CH48,$CH49,$CH50);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_execute($statement);

    $response["success"] = true;
    $response["sex"]=$sex;

    echo json_encode($response);
?>