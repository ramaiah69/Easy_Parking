<?php

	require "connect.php";
	
	$cost = $_POST["cost"];
	
	$sno=$_POST["sno"];
	$q = mysql_query("update parking_places set cost='$cost' where sno='$sno'");
	if($q){
		echo 'inserted succesfully';
	}
	else
	{
		echo 'failed';
	}

?>
