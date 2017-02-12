<?php 

	/////Connection with database/////////////////
	$mysqlhost = "sql212.freecluster.eu";
	$user= "fceu_17988664";
	$dbname1 = "fceu_17988664_mydata";
	$sqlpass='ma3roofforyou';
	$handler = new PDO("mysql:host=$mysqlhost;dbname=$dbname1", $user, $sqlpass);
	////////////////////////////
	
	
	///////////////Recieving info from client////////////////////////////
	$name = $_POST['name'];
	$catchedname = $_POST['catname'];
	$catchfrequency = $_POST['catfrequency'];
	$mobilenum = $_POST['mobilenum'];
	/////////////////////////////////////////
	
	$hisid= NULL;
	if(isset($name) && isset($catchedname) && isset($catchfrequency) && isset($mobilenum)){
		/////////////////////Inserting in the database//////////////////////////
		$pdoQuery = "INSERT INTO `start` (`id`, `name`, `namecatched`, `frequency`, `mobile`) 
				VALUES (:hisid, :name, :catchedname, :catfrequency, :mobilenum)";
				$pdoResult = $handler->prepare($pdoQuery);
				$pdoExec = $pdoResult->execute(array(":hisid"=>$hisid,":name"=>$name,":catchedname"=>$catchedname,
				":catfrequency"=>$catchfrequency,":mobilenum"=>$mobilenum));
				if($pdoExec)
				{
					echo 'Your answer has been posted<br>';
				}else{
					echo 'Error in posting the answer<br>';
				}
	}
	else{
		exit("not well set");
	}
	
	
?>