<?php 

	/////Connection with database/////////////////
	$mysqlhost = "localhost";
	$user= "root";
	$dbname1 = "connections";
	$sqlpass='';
	$handler = new PDO("mysql:host=$mysqlhost;dbname=$dbname1", $user, $sqlpass);
	////////////////////////////
	
	
	///////////////Recieving info from client////////////////////////////
	$type = $_POST['type'];
	$myname = $_POST['myname'];
	$mymac = $_POST['mymac'];
	$hismac = $_POST['hismac'];
	$hisname = $_POST['hisname'];
	/////////////////////////////////////////
	
	
	if(isset($type) && isset($myname) && isset($mymac) && isset($hismac) && isset($hisname) ){
		if($type =="connect"){
			$allow = 0;
		}
		else if($type=="disconnect"){
			
			$allow =1;
		}
		if($allow == 0){
			$mac1 = $handler->query("SELECT myname FROM wifi WHERE mymac='$mymac'");
			$mac2 = $mac1 ->fetch()[0];
			if($mac2 ==NULL){
				//echo "No such user available<br>";
				$hisid = NULL;
				$pdoQuery = "INSERT INTO `wifi` (`id`, `myname`, `mymac`, `hismac`,`hisname`, `allow`) 
					VALUES (:hisid, :myname, :mymac, :hismac,:hisname ,:allow)";
					$pdoResult = $handler->prepare($pdoQuery);
					$pdoExec = $pdoResult->execute(array(":hisid"=>$hisid,":myname"=>$myname,":mymac"=>$mymac,
					":hismac"=>$hismac,":hisname"=>$hisname,":allow"=>$allow));
					if($pdoExec)
					{
						echo 'Your entry has been saved\n';
					}else{
						echo 'Error in posting the answer<br>';
					}
			}
			else{
				$allow1 = $handler->query("SELECT allow FROM wifi WHERE mymac='$mymac' AND hismac='$hismac'");
				$allow2 = $allow1 ->fetch()[0];
				//echo "Available, his name is:$name2";
				if($allow2 != 0){
					$freq = $handler->query("SELECT frequency FROM wifi WHERE mymac='$mymac'");
					$freq1 = $freq->fetch()[0];
					$freq1++;
					$sql = "UPDATE `wifi` SET `frequency`=:freq1 WHERE `mymac`=:mymac";
					$pdoResult1 = $handler->prepare($sql);
					$pdoExec1=$pdoResult1->execute(array(":freq1"=>$freq1,":mymac"=>$mymac));
					if($pdoExec1){
						
						echo "updated frequency\n";
					}
					else{
						echo "frequency not updated<br>";
					}
					
				}
				
			
			}
			$sql2 = "UPDATE `wifi` SET `allow`=:allow WHERE `mymac`=:mymac";
					$pdoResult2 = $handler->prepare($sql2);
					$pdoExec2=$pdoResult2->execute(array(":allow"=>$allow,":mymac"=>$mymac));
					if($pdoExec2){
						
						echo "updated allow\n";
					}
					else{
						echo "allow not updated";
					}
		}
		else if($allow == 1){
					$sql3 = "UPDATE `wifi` SET `allow`=:allow WHERE `mymac`=:mymac";
					$pdoResult3 = $handler->prepare($sql3);
					$pdoExec3=$pdoResult3->execute(array(":allow"=>$allow,":mymac"=>$mymac));
					if($pdoExec3){
						
						echo "updated allow\n";
					}
					else{
						echo "allow not updated dis";
					}
			
		}
			
	}
	else{
		
		echo "Some or all info are missing\n";
	}
	


?>