<html>
<body>
	<h1>Parking place add</h1>
<form method="post"  >
	<input type="text" name="parent" placeholder="Enter area"><br><br>
	<input type="text" name="name" placeholder="parking area name"><br><br>
	<input type="text" name="lng" placeholder="langitude"><br><br>
	<input type="text" name="lat" placeholder="lattitude"><br><br>
	<input type="submit" name="submit"  value="submit">
</form>
</body>
</html>
<?php
if(isset($_POST["submit"]))
{
	require "connect.php";
	$parent = $_POST["parent"];
	$name = $_POST["name"];
	$lng = $_POST["lng"];
	$lat = $_POST["lat"];
	$q = mysql_query("insert into parking_places (parent,name,lng,lat) values ('$parent','$name','$lng','$lat')");
	if($q){
		echo 'inserted succesfully';
	}
	else
	{
		echo 'failed';
	}
}
?>
