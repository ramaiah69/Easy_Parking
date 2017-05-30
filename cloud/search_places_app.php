<?php
	require "connect.php";
	$type=$_POST["type"];
	
	if($type=="search_places")
	{
		$parent=$_POST["parent"];
		//$parent = "nuzvid";
	
		$query = mysql_query("select * from parking_places where parent='$parent'");
		if(mysql_num_rows($query))
		{
			$arr = array();
			while($data=mysql_fetch_assoc($query))
			{
				$arr[] = $data;
			}
			echo json_encode($arr);
		}
		else
		{
			
			echo 'There is no parking places in this area';
		}
	}
	else if($type=="registered_places")
	{
		$id=$_POST["id"];
		$parent=$_POST["parent"];
		//$id="N110662";
		//$sno=2;
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
		
		$query = mysql_query("select * from parking_register inner join parking_places where id='N110662' and parent='$parent' and parking_places.sno=parking_register.sno and parking_register.time>'$new_time' and  parking_register.date='$date'");
		$data=mysql_fetch_array($query);
		if($data)
		{
			$arr = array();
			$arr[] = $data;
			echo json_encode($arr);
		}
		else
		{
			
		}
		
		{
			echo 'There is no parking places registered';
		}
	}
	
?>
