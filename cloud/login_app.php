<?php
	require "connect.php";
	if(!empty($_POST["id"]) && !empty($_POST["password"]) )
	{
		$id=$_POST["id"];
		
		$password=$_POST["password"];
		
		$query=mysql_query("select * from login where id='$id' and password='$password'");
		$data = mysql_fetch_array($query);
		if($data)
			echo "true";
		else
			echo "false";
	}
	else
	{
		echo 'Please fill the details without empty';
	}
?>
