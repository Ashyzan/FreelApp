//worker di timer.js che riceve ore, minuti e secondo e li da in pasto con un setInterval di un secondo alla
//funzione incrementoTimer che fa scorrere il tempo e ad ogni iterazione manda il postMessage al timer.js con 
//la stringa del timer nel formato 00:00:00 all addEventListner che li stampa sul DOM nei relativi timers
let hours;
let minutes;
let seconds;
let iterazioni;
let stringaRisultato

clearInterval()
crono = setInterval(incrementoTimer, 1000);

//blocco di codice che elimina lag di caricamento pagina e viene eseguito una volta sola
iterazioni++;
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
// fineblocco di codice che elimina lag di caricamento pagina 

self.onmessage = function(event){
		hours = event.data.hours;
		minutes = event.data.minutes;
		seconds = event.data.seconds;
		self.postMessage({
							stringaRisultato: stringaRisultato,
							iterazioni: iterazioni,
						})
		incrementoTimer()
}
 
	
	function incrementoTimer(){
				iterazioni++;
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
				
				stringaRisultato = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
				console.log("**RISULTATO DA WORKER: " + stringaRisultato)
				self.postMessage({
					stringaRisultato: stringaRisultato,
					iterazioni: iterazioni,
				})
				
				
	}

	
