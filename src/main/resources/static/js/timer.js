	let hours = finalTimeSec / 3600;
	let minutes = (finalTimeSec % 3600) / 60;
	let seconds = finalTimeSec % 60;
	const timerElement = document.querySelector("#timer");
	const timerTitolo = document.getElementById('timerTitolo');
	const timerUno = document.getElementById('timerUno');
	const timerDue = document.getElementById('timerDue');
	
	
	console.log("finalTimeSec iniziale: " + finalTimeSec)
	console.log("ore iniziali: " + hours);
	console.log("minuti iniziali: " + minutes);
	console.log("secndi iniziali: " + seconds);
				
	function tempochescorre() {
	
		seconds++;
		stampacontatore();
		
		if (seconds >= 60) {
			seconds = 0;
			minutes++;
		}

		if (minutes >= 60) {
			minutes = 0;
			hours++;
		}
		

	}

	
	function stampacontatore() {

		// formattato con 2 cifre, per difetto dopo aver verificato la condizione che gli oggetti HTML esistono
		if(timerElement != null){
			document.getElementById('timer').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
		}
		
		if(timerUno != null){
			document.getElementById('timerUno').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
		}
		
		if(timerDue != null){
			document.getElementById('timerDue').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
		}
		
		if(timerTitolo != null){
			document.getElementById('timerTitolo').innerHTML = "FreelApp - " + ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
		}


	}
	
	function timerstart(){
		
		if (contatoreTrue && contatoreIsRun) {
				// eseguo prim la funzione una volta per togliere il lag di 1 secondo, poi entro nel ciclo
				tempochescorre();
				setInterval(tempochescorre, 1000);
			}

			else if (contatoreIsRun !== true) {

				stampacontatore();
				console.log("contatore non attivo , finaltime = " + finalTimeSec);
			}

			else  {
			
				console.log("finaltime is not defined , finaltime = " + finalTimeSec);
			}
		
	}