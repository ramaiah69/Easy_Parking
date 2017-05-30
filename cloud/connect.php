<?php
	$con1 = mysql_connect("localhost","root","venky");
	$con2 = mysql_select_db("easyparking");
	if($con1 && $con2)
	{
		//echo 'Connection success';
	}
	else
	{
		echo "Database connection failed";
	}
	
?>
