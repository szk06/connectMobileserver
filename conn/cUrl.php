<?php 
	
	
	//First Create app is to fetch data from the internet
	$ch = curl_init();//initialize the session
	
	curl_setopt($ch, CURLOPT_URL,"http://www.touch.com.lb/autoforms/portal/touch");	//set options on the session
	//We set options based on what we want to do, in this case we only need to fetch the data in this php file
	
	
	//start of execute the session
	curl_exec($ch);
	
	//session close
	curl_close($ch);
	
?>