<?php 
	$mymac = $_POST['mymac'];
	if(isset($mymac)){
		//echo $mymac;
		$mysqlhost = "localhost";
		$user= "root";
		$dbname1 = "connections";
		$sqlpass='';
		$handler = new PDO("mysql:host=$mysqlhost;dbname=$dbname1", $user, $sqlpass);
		$row = $handler ->query("SELECT mobile FROM sms WHERE send='1'");
		//echo"<br>";
		foreach($row as $rows){
			echo $rows['mobile']." ";
		}
		
	}
	else
	{
		exit("not set");
	}
	


?>