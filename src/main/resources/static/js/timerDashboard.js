	let hours = finalTimeSec / 3600;
	let minutes = (finalTimeSec % 3600) / 60;
	let seconds = finalTimeSec % 60;
	
	const timerUno =document.getElementById('timerUno');
	
	console.log("finalTimeSec iniziale Dashboard: " + finalTimeSec)
	console.log("ore iniziali Dashboard: " + hours);
	console.log("minuti iniziali Dashboard: " + minutes);
	console.log("secndi iniziali Dashboard: " + seconds);
				
		
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

		// formattato con 2 cifre, per difetto
		timerUno.innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);

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