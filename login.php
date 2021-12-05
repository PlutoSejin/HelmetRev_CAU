<?php
    $conn = mysqli_connect("localhost", "brain2020", "k~410528", "brain2020");

    mysqli_query($conn,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];


    $response = mysqli_query($conn,"SELECT * FROM USER_INFO WHERE USER_EMAIL='$userID' ");
    $result = array();

    if(mysqli_num_rows($response)==1){

        $row = mysqli_fetch_assoc($response);
        $hash = $row['USER_PASSWORD'];
        if(password_verify($userPassword, $hash)){
            $index['userID'] = $row['USER_EMAIL'];
            $index['userName'] = $row['USER_NAME'];
            $index['machineID'] = $row['MACHINE_ID'];
            $index['Hz'] = $row['Hz'];

            array_push($result['login'], $index);

            $result["success"] = true;
            $result["userName"] = $index['userName'];
            $result["machineID"] = $index['machineID'];
            $result["Hz"] = $index['Hz'];
            echo json_encode($result);
            mysqli_close($conn);
        } else{
            $result["success"] = false;

            echo json_encode($result);
            mysqli_close($conn);
        }

}
?>

