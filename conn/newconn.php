<?php 
	$mobile_number = $_POST['mobile_number'];
	$message = $_POST['message'];
	$size = $_POST['size'];
	if(isset($mobile_number) && isset($message ))
	{
		/////////////////////////////////////Connect to Database///////////////////
		$mysqlhost = "localhost";
		$user= "root";
		$dbname1 = "connections";
		$sqlpass='';
		$handler = new PDO("mysql:host=$mysqlhost;dbname=$dbname1", $user, $sqlpass);
		/////////////////////////////////////
		$length = (int)$size;
		//echo "Mobile Number: ".$mobile_number;
		//echo "size is:".$size;
		$counter =0;
		$obj=json_decode($message, true);
		$myname = $obj['myname'];
		//echo "myname: ".$myname;
		$mymac = $obj ['mymac'];
		//echo "mymac: ".$mymac;
		
		while($counter<$length){
				
			$items = strval($counter);
			$mymsg= $obj[$items];
			$arr = explode("-",$mymsg);
			$type= $arr[0];
			//echo "type: ".$type;
			$hisname= $arr[1];
			
			//echo "hisname:".$hisname;
			$hismac=  $arr[2];
			//echo "hismac:".$hismac;
			//echo "mymsg: ".$mymsg;
			
			//Add phone number database
			
			$row=$handler->query("SELECT * FROM sms WHERE mac='$mymac'");
			foreach ($row as $elem ){
				$mobnumber = $elem['mobile']; 
			}
			if($mobnumber==NULL){
				$hisid =NULL;
				$send=1;
				$pdoQuery = "INSERT INTO `sms` (`id`, `mac`, `mobile`, `send`) 
				VALUES (:hisid, :mymac, :mobile_number, :send)";
				$pdoResult = $handler->prepare($pdoQuery);
				$pdoExec = $pdoResult->execute(array(":hisid"=>$hisid,":mymac"=>$mymac,":mobile_number"=>$mobile_number,
				":send"=>$send));
				if($pdoExec)
				{
					echo 'Mobile Number posted';
				}else{
					echo 'Error in posting mobile<br>';
				}
				
			}
			else{ 
				$sql = "UPDATE `sms` SET `mobile`=:mobile_number WHERE mac=:mymac";
					$pdoResult = $handler->prepare($sql);
					$pdoExec=$pdoResult->execute(array(":mobile_number"=>$mobile_number,":mymac"=>$mymac));
					if($pdoExec){
						echo "mobile number updated";
					}
					else{
						exit("mobile not updated<br>");
					}
			}
		
		
			//Start of adding connections to the database
			
			if($type=="connect"){
				$row=$handler->query("SELECT * FROM wifi WHERE mymac='$mymac' AND hismac='$hismac'");
				foreach ($row as $elm){
					$ifadd = $elm['allow'];
					$freq = $elm['frequency'];
				}
				if($ifadd==NULL){
					$hisid=NULL;
					$allow=0;
					$frequency=1;
					$pdoQuery = "INSERT INTO `wifi` (`id`, `myname`, `mymac`, `hismac`, `hisname`, `allow`, `frequency`) 
					VALUES (:hisid, :myname, :mymac, :hismac, :hisname, :allow, :frequency)";
					$pdoResult = $handler->prepare($pdoQuery);
					$pdoExec = $pdoResult->execute(array(":hisid"=>$hisid,":myname"=>$myname,":mymac"=>$mymac,
					":hismac"=>$hismac,":hisname"=>$hisname,":allow"=>$allow,":frequency"=>$frequency));
					if($pdoExec)
					{
						echo 'Your Connections are updated';
					}else{
						echo 'Error in posting the answer<br>';
					}
				}
				else if($ifadd==1){
					$newfreq = $freq+1;
					$newallow = 0;
					$sql = "UPDATE `wifi` SET `allow`=:newallow,`frequency`=:newfreq WHERE mymac=:mymac AND hismac=:hismac";
					$pdoResult = $handler->prepare($sql);
					$pdoExec=$pdoResult->execute(array(":newallow"=>$newallow,":newfreq"=>$newfreq,":mymac"=>$mymac,":myname"=>
					$myname));
					if($pdoExec){
						echo "allow and frequency updated";
					}
					else{
						exit("Data not inserted<br>");
					}
				}
				
				
			}
			else if($type="disconnect"){
				$row=$handler->query("SELECT * FROM wifi WHERE mymac='$mymac' AND hismac='$hismac'");
				foreach ($row as $elm){
					$ifadd = $elm['allow'];
					$freq = $elm['frequency'];
				}
				$newallow = 1;
				$sql = "UPDATE `wifi` SET `allow`=:newallow WHERE mymac=:mymac AND hismac=:hismac";
				$pdoResult = $handler->prepare($sql);
				$pdoExec=$pdoResult->execute(array(":newallow"=>$newallow,":mymac"=>$mymac,":myname"=>
				$myname));
				if($pdoExec){
					echo "allow updated";
				}
				else{
					exit("Data not inserted<br>");
				}
			}
			
			$counter++;
	
	
		}
	}
	
		
	
	else {
		exit("not set");
	}


?>