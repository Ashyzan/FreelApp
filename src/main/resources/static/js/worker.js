//worker di timer.js che riceve ore, minuti e secondo e li da in pasto con un setInterval di un secondo alla
//funzione incrementoTimer che fa scorrere il tempo e ad ogni iterazione manda il postMessage al timer.js con 
//la stringa del timer nel formato 00:00:00 all addEventListner che li stampa sul DOM nei relativi timers
let hours;
let minutes;
let seconds;
let iterazioni;
let stringaRisultato

//clearInterval()
//crono = setInterval(incrementoTimer, 1000);
let start = performance.now();
let baseSeconds; //tempo di partenza


self.onmessage = function(event){

	baseSeconds = event.data.finalTimeSec;

	crono = setInterval(() =>{
	
	const elapsed = Math.floor((performance.now() - start)/1000)
	const totalSeconds = baseSeconds + elapsed;
	
	const hh = String(Math.floor(totalSeconds / 3600)).padStart(2, "0");
	const mm = String(Math.floor((totalSeconds % 3600)/60)).padStart(2, "0");
	const ss = String(totalSeconds % 60).padStart(2, "0");
	
	stringaRisultato = (`${hh}:${mm}:${ss}`);
	
	console.log("**RISULTATO DA WORKER: " + stringaRisultato)
					self.postMessage({
						stringaRisultato: stringaRisultato,
						iterazioni: iterazioni,
					})
	
	},250);
	
};


//self.onmessage = function(event){
//		hours = event.data.hours;
//		minutes = event.data.minutes;
//		seconds = event.data.seconds;

//		self.postMessage({
//							stringaRisultato: stringaRisultato,
//							iterazioni: iterazioni,
//		incrementoTimer()
//}
 
	
//	function incrementoTimer(){
//				iterazioni++;
//				seconds++;		
//								
//					seconds = 0;
									
//					if(minutes <= 60){
//						minutes++;
//					}else {
//						minutes = 0;
//						hours++;
//					}
//				}
				
//				stringaRisultato = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
//				console.log("**RISULTATO DA WORKER: " + stringaRisultato)
//				self.postMessage({
//					stringaRisultato: stringaRisultato,
//					iterazioni: iterazioni,
//				})
				
				
//	}

	
