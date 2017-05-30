<?php
	require "connect.php";
	$id=$_POST["id"];
	$sno=$_POST["sno"];
	//$id=1;
	//$sno=2;
	date_default_timezone_set("Asia/Calcutta");
	$date=date("Y-m-d");
	$time=date("H:i:s");
	$q=mysql_query("insert into parking_register (id,sno,date,time) values ('$id','$sno','$date','$time')");
	$a=mysql_query("select available from parking_places where sno='$sno'");
	$result=mysql_fetch_array($a);
	$available=$result[0]-1;
	
	$s=mysql_query("update parking_places set available='$available' where sno='$sno'");
	if($q && $s)
	{
		echo 'true';
	}
	else
	{
		echo 'false';
	}
?>
