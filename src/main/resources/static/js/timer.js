console.log("leggo il file timer.js");

const formContatoreErroreFinalSecond = document.getElementById('formContatoreErroreFinalsecond');
let iterazioni = 0;

	let hours = finalTimeSec / 3600;
	let minutes = (finalTimeSec % 3600) / 60;
	let seconds = finalTimeSec % 60;
	const timerElement = document.querySelector("#timer");
	const timerTitolo = document.getElementById('timerTitolo');
	const timerUno = document.getElementById('timerUno');
	const timerDue = document.getElementById('timerDue');
	
//recupero elementi dal DOM per pulsanti pause e play dei contatori
	//    ------ pause TOP -------
	const pauseTopSvgBeforeApiContatoreNotRun = document.getElementById('pause-top-svg-before-api-contatoreNotRun');
	const pauseTopSvgBeforeApiContatorIsRun = document.getElementById('pause-top-svg-before-api-contatoreIsRun');
	const pauseTopAfterApi = document.getElementById('pause-top-after-api');

	//    ------ play TOP --------
	const playTopSvgBeforeApiContatoreNotRun = document.getElementById('play-top-svg-before-api-contatoreNotRun');
	const playTopSvgBeforeApiContatorIsRun = document.getElementById('play-top-svg-before-api-contatoreIsRun');
	const playTopAfterApi = document.getElementById('play-top-after-api');

	//    ------ pause BOTTOM -------
	const pauseBottomSvgBeforeApiContatoreNotRun = document.getElementById('pause-bottom-svg-before-api-contatoreNotRun');
	const pauseBottomSvgBeforeApiContatorIsRun = document.getElementById('pause-bottom-svg-before-api-contatoreIsRun');
	const pauseBottomAfterApi = document.getElementById('pause-bottom-after-api');

	//    ------ play BOTTOM --------
	const playBottomSvgBeforeApiContatoreNotRun = document.getElementById('play-bottom-svg-before-api-contatoreNotRun');
	const playBottomSvgBeforeApiContatorIsRun = document.getElementById('play-bottom-svg-before-api-contatoreIsRun');
	const playBottomAfterApi = document.getElementById('play-bottom-after-api');
	
	//crono = setInterval(tempochescorre, 1000);
	
	//istanzia nuovo web worker che anche se la finestra perde il focus continua ad eseguire la funzione tempo che scorre
		let timerWorker = new Worker('/js/worker.js');
		timerWorker.terminate()
	
	function inizializzaNuovoWorker(){
		timerWorker.terminate();
		timerWorker = new Worker('/js/worker.js');
		timerWorker.postMessage({
			seconds: seconds,
			minutes: minutes,
			hours: hours,
			message : 'dati passati al worker in dopo istanziato worker'
		})		
		if(!document.hasFocus() || document.hasFocus()){
			timerWorker.addEventListener('message', function(event){
				if(timerElement != null){
					timerElement.innerHTML = event.data;
				}
				if(timerTitolo != null){
					timerTitolo.innerHTML = "FreelApp - " + event.data;					
				}
				if(timerUno != null){
					timerUno.innerHTML = event.data;
				}
				if(timerDue != null){
					timerDue.innerHTML = event.data;
				}	
					
					console.log("###DA WORKER --> " + event.data)
			})			
		}
	}
	
	function terminaWorker(){
		timerWorker.terminate()
		console.log("worker terminato")
	}
		

	
	

	
	
	
	
	
	
	
	
	
	


	//console.log("finalTimeSec iniziale: " + finalTimeSec)
	//console.log("ore iniziali: " + hours);
	//console.log("minuti iniziali: " + minutes);
	//console.log("secondi iniziali: " + seconds);
				
	function tempochescorre() {
		if(contatoreIsRun !== true){
			
			stampacontatore();
		} else{ 
			
				//	iterazioni ++;
				//	seconds++;							
				//	if (seconds == 59) {
				//			seconds = -1;
				//								
				//		if(minutes <= 59){
				//			minutes++;
				//		}else {
				//			minutes = 0;
				//			seconds = -1;
				//			hours++;
				//		}
				//	}	
				inizializzaNuovoWorker()
				}
					//verifica ogni secondo se il timer ha raggiuno il massimo consentito
					timeExceed(iterazioni);

		}
		
	

	
	function stampacontatore() {
		console.log("sono in stampa contatore")

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
		//verifica se il contatore è stato attivato da rapid button
					
//			if(contatoreAttivatoDaRapidButton === true){
//				contatoreTrue = true;
//				contatoreIsRun = true;
//				pulsantiContatoreInStart()
//			}
		

		if (contatoreTrue && contatoreIsRun) {
				// eseguo prim la funzione una volta per togliere il lag di 1 secondo, poi entro nel ciclo
				tempochescorre()

			}

			else if (contatoreIsRun !== true) {

				stampacontatore();
				//console.log("contatore non attivo , finaltime = " + finalTimeSec  );
			}

			else {
				
				//console.log("finaltime is not defined , finaltime = " + finalTimeSec);
			}
		
	
		
	}

//funzione che se il finaltime ha raggiunto il massimo assegna al form precompilato sul contatoreTop 
//un action e lo manda al backend per la validazione e la generazione del template di errore
function timeExceed(iterazioni){
	
			if((finalTimeSec + iterazioni) >= 31557600){
				
				formContatoreErroreFinalSecond.action = `/task/timeExceed/${taskInUsoId}`
				formContatoreErroreFinalSecond.submit(); 					
			}
}







	
	