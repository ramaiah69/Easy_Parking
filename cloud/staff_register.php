<?php
	require "connect.php";
	if(!empty($_POST["name"]) && !empty($_POST["id"]) && !empty($_POST["email"]) && !empty($_POST["password"]) )
	{
		
		$id=$_POST["id"];
		$name=$_POST["name"];
		$password=$_POST["password"];
		$email=$_POST["email"];
		$query=mysql_query("insert into staff (id,password,name,email) values ('$id','$password','$name','$email');");
		if($query)
			echo "true";
		else
			echo "false";
	}
	else
	{
		echo 'Please fill the details without empty';
	}
?>
