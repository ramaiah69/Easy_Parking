<?php
	//$parent=$_POST["parent"];
	$parent = "nuzvid";
	$id=$_POST["id"];
	
	date_default_timezone_set("Asia/Calcutta");
	$date=date("Y-m-d");
	$time=date("H:i:s");
	$hours = date("H")-2;
	$min = date("i");
	$sec = date("s");
	$year = date("Y");
	$month = date("m");
	$day = date("y");
	$new_date = mktime($hours,$min,$sec,$day,$month,$year);
	$new_time=date("H:i:s",$new_date);
	
	require "connect.php";
	$query = mysql_query("select * from parking_register where id='$id' and time>'$new_time'");
	if(mysql_num_rows($query))
	{
		$data = mysql_fetch_array($query);
		$sno=$data["sno"];
		$q = mysql_query("select * from parking_places where sno='$sno'");
		$data = mysql_fetch_array($q);
		
		$arr = array();
		$arr[]=$data;
		echo json_encode($arr);		
	}
	else
	{
		echo 'There is no parking places nearest you';
	}
	
?>
