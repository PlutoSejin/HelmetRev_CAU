<?php
    $conn = mysqli_connect("localhost", "brain2020", "k~410528", "brain2020");
    $userID = $_POST["userID"];

    $statement = mysqli_prepare($conn, "SELECT USER_EMAIL FROM USER_INFO WHERE USER_EMAIL = ?    ");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID);


    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){
        $response["success"]=false;
        $response["userID"]=$userID;
    }

    echo json_encode($response);



?>
