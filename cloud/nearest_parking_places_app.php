<?php
	//$parent=$_POST["parent"];
	$parent = $_POST["parent"];
	require "connect.php";
	$query = mysql_query("select * from parking_places where parent='$parent'");
	
	$arr = array();
	if(mysql_num_rows($query))
	{
		while($data=mysql_fetch_assoc($query))
		{
			$arr[] = $data;
		}
		echo json_encode($arr);
	}
	else
	{
		echo 'There is no parking places nearest you';
	}
	
?>
