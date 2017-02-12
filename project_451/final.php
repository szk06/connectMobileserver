<?php  
	
	
		$mysqlhost = "localhost";
		$mymac =$_POST['mymac'];
		$user= "root";
		$name = array();
		$freq= array();
		$dbname1 = "connections";
		$sqlpass='';
		$handler = new PDO("mysql:host=$mysqlhost;dbname=$dbname1", $user, $sqlpass);
		$counter =0;
		
		 if(isset ($mymac)){
			$row = $handler->query("SELECT hisname, frequency FROM wifi WHERE mymac='$mymac' AND allow=0");
			print "Connected to this device:$mymac";
			foreach($row as $rows){
				$name[] = $rows['hisname'];
				$freq[] = $rows['frequency'];
				print $counter."name:".$rows['hisname']."frequency:".$frequency;
				$counter++;
				
			}
		}
		else{
			exit("error");
		}
		
		
		
	


?>


<html>
    <head>
        <script language="javascript" type="text/javascript"  src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
        <script language="javascript" type="text/javascript" src="http://localhost:8080/Project_451/arbor.js" ></script>
        <script language="javascript" type="text/javascript" src="http://localhost:8080/Project_451/graphics.js" ></script>
        <script language="javascript" type="text/javascript" src="http://localhost:8080/Project_451/renderer.js" ></script>
    </head>
     <body>
      <canvas id="viewport" width="800" height="600"></canvas>
      <script language="javascript" type="text/javascript">
            var sys = arbor.ParticleSystem(1000, 400,1);
            sys.parameters({gravity:true});
            sys.renderer = Renderer("#viewport") ;
			
            var data = {
               nodes:{
                 <?=$mymac?>:{'color':'red','shape':'dot','label':'<?=$mymac?>'},
				 <?php ?>
                 dog:{'color':'green','shape':'dot','label':'dog'},
                 cat:{'color':'blue','shape':'dot','label':'cat'}
               }, 
               edges:{
                 animals:{ dog:{}, cat:{} }
               }
             };
            sys.graft(data);
            setTimeout(function(){
                        var postLoadData = {
                           nodes:{
                             joe:{'color':'orange','shape':'dot','label':'joe'},
                             fido:{'color':'green','shape':'dot','label':'fido'},
                             fluffy:{'color':'blue','shape':'dot','label':'fluffy'}
                           }, 
                           edges:{
                                dog:{ fido:{} },
                                cat:{ fluffy:{} },
                                joe:{ fluffy:{},fido:{} }
                           }
                         };
                         sys.graft(postLoadData);
                    },10000);
      </script>
    </body>
</html>