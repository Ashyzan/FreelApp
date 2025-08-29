
let hours;
let minutes;
let seconds;
let message;

crono = setInterval(incrementoTimer, 1000);

self.onmessage = function(event){
	
		hours = event.data.hours;
		minutes = event.data.minutes;
		seconds = event.data.seconds;
		message = event.data.message	
		console.log(message)
		//console.log(stampaRisultati(hours, minutes, seconds));
		incrementoTimer()
}
 
	
	function incrementoTimer(){
		console.log("sono in incrementoTimer - worker")
				seconds++;							
				if (seconds == 60) {
					seconds = 0;
									
					if(minutes <= 60){
						minutes++;
					}else {
						minutes = 0;
						seconds = 0;
						hours++;
					}
				}
				
				let stringaRisultato = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
				self.postMessage(stringaRisultato)
				console.log("HO INVIATO I RISULTATI DA WORKER");
				
	}

	
