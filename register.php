<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');

    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $machine_id = 8888;
        $userID = $_POST["userID"];
        $userPassword = $_POST["userPassword"];
        $userName = $_POST["userName"];
        $conn = mysqli_connect("localhost", "brain2020", "k~410528", "brain2020");
        $response = array();
        if ($conn->connect_error) {
            $response["success"] = false;
        }

        $userPassword = password_hash($userPassword, PASSWORD_DEFAULT);

        $statement = mysqli_prepare($conn, "INSERT INTO USER_INFO(machine_id, user_name, user_email, user_password) VALUES (?,?,?,?)");
        mysqli_stmt_bind_param($statement, "isss",$machine_id,  $userName,$userID,$userPassword);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_execute($statement);


        $response["success"] = true;
        $response["userID"]= $userID;


        echo json_encode($response);


        //$sql = "INSERT INTO USER_INFO (machine_id, user_name, user_email, user_password) VALUES ('$machine_id','$userName', '$userID', '$userPassword')";
        //mysqli_query($conn, $sql);
        //commit;
        //$result["success"] = true;
        //$result["message"] = "success";
        //$result["userID"] = $userID;
        //echo json_encode($result);
    }


?>

