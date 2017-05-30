<?php

	require "connect.php";
	$parent = $_POST["parent"];
	$name = $_POST["name"];
	$lng = $_POST["lng"];
	$cost = $_POST["cost"];
	$lat = $_POST["lat"];
	$id=$_POST["id"];
	$q = mysql_query("insert into parking_places (parent,name,lng,lat,cost) values ('$parent','$name','$lng','$lat','$cost')");
	$query = mysql_query("select sno from parking_places where lng='$lng' and lat='$lat'");
	$data= mysql_fetch_array($query);
	$p_id=$data[0];
	$s = mysql_query("update staff set p_id='$p_id' where id='$id'");
	if($q && $s){
		echo 'inserted succesfully';
	}
	else
	{
		echo 'failed';
	}

?>
