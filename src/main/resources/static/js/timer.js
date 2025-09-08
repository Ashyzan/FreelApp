
const formContatoreErroreFinalSecond = document.getElementById('formContatoreErroreFinalsecond');
let iterazioni = 0;
let timerWorker = null;


//azione che compensa il lag di un secondo al reload della pagina
if(contatoreIsRun == true){
	console.log("finalTime pre: " + finalTimeSec)
	finalTimeSec++
	console.log("finalTime post: " + finalTimeSec)
}



	
	const timerElement = document.querySelector("#timer");
	const timerTitolo = document.getElementById('timerTitolo');
	const timerUno = document.getElementById('timerUno');
	const timerDue = document.getElementById('timerDue');
	
	//crono = setInterval(tempochescorre, 1000);
	
	
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
	
	
	
	
	
	
	
	


	//console.log("finalTimeSec iniziale: " + finalTimeSec)
	//console.log("ore iniziali: " + hours);
	//console.log("minuti iniziali: " + minutes);
	//console.log("secondi iniziali: " + seconds);
				
	//function tempochescorre() {
		
	//	if(contatoreIsRun == true){
			
			
	//							iterazioni ++;
	//							seconds++;							
	//							if (seconds == 59) {
	//									seconds = -1;
															
	//								if(minutes <= 59){
	//									minutes++;
	//								}else {
	//									minutes = 0;
	//									seconds = -1;
	//									hours++;
	//								}
	//							}	
						//******** fine porzione di codice spostata sul worker ********
					//inizializzaNuovoWorker()
					//verifica se il timer ha raggiuno il massimo consentito
				//	timeExceed(iterazioni);
	//	} else if(contatoreIsRun != true){ 
	//		stampacontatore();

	//	}
		
	//}

	
	//function stampacontatore() {
		//console.log("sono in stampa contatore")

		// formattato con 2 cifre, per difetto dopo aver verificato la condizione che gli oggetti HTML esistono
	//	if(timerElement != null){
	//		document.getElementById('timer').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
	//	}
		
	//	if(timerUno != null){
	//		document.getElementById('timerUno').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
	//	}
		
	//	if(timerDue != null){
	//		document.getElementById('timerDue').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
	//	}
		
	//	if(timerTitolo != null){
	//		document.getElementById('timerTitolo').innerHTML = "FreelApp - " + ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);			
	//	}


//	}
	
	function timerstart(){
		if (contatoreTrue && contatoreIsRun) {
				
				inizializzaNuovoWorker()
				//verifica se il timer ha raggiuno il massimo consentito
				timeExceed(iterazioni);
				//setInterval(tempochescorre, 1000);

			}

			else if (contatoreIsRun !== true) {

				//stampacontatore();
				//console.log("contatore non attivo , finaltime = " + finalTimeSec  );
			}

			else {
				
				//console.log("finaltime is not defined , finaltime = " + finalTimeSec);
			}
		if(contatoreAttivatoDaRapidButton == true){
				contatoreIsRun =true;
				contatoreTrue = true;
				inizializzaNuovoWorker()
				switchPulsantiContatore()
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



//funzione che  instanzia  uno nuovo worker così che ad ogni start si avvia un nuovo worker
	//la funzione passa al worker il valore  finaltime che servirà a far scorrere il tempo.
	//viene avviato (sia in focus che non) un addeventlistner che ad ogni messaggio ricevuto dal worker contenente 
	//il tempo istantaneo lo inserisce con innerHtml nei vari timer e che verifica il timeExceed
	function inizializzaNuovoWorker(){
		//stampacontatore()
			timerWorker = new Worker('/js/worker.js');
			
			timerWorker.postMessage({
							finalTimeSec: finalTimeSec,
						})				
		if(!document.hasFocus() || document.hasFocus()){
							timerWorker.addEventListener('message', function(event){
								if(timerElement != null){
									timerElement.innerHTML = event.data.stringaRisultato;
								}
								if(timerTitolo != null){
									timerTitolo.innerHTML = "FreelApp - " + event.data.stringaRisultato;					
								}
								if(timerUno != null){
									timerUno.innerHTML = event.data.stringaRisultato;
								}
								if(timerDue != null){
									timerDue.innerHTML = event.data.stringaRisultato;
								}	
								timeExceed(event.data.iterazioni)	
										
							})	
						}
	}
	
	
	
	function terminaWorker(){
		timerWorker.terminate()
		timerWorker = null;
	}


function switchPulsantiContatore(){
		//contatore-basso (mobile)
		//dopo aver caricato i pulsanti tramite thymelaf secondo quanto preso dal model, al click 
		//del play vengono nascosti e sostituiti da quelli generati da javascritp
		if (pauseBottomSvgBeforeApiContatoreNotRun != null && pauseBottomSvgBeforeApiContatoreNotRun.display != "none") {
			pauseBottomSvgBeforeApiContatoreNotRun.classList.add('hidden');
		}
		if (playBottomSvgBeforeApiContatorIsRun != null && playBottomSvgBeforeApiContatorIsRun.display != "none") {
			playBottomSvgBeforeApiContatorIsRun.classList.add('hidden');
		}
	
		//rende non cliccabile il pulsante play del contatore con il contatore in run
		playBottomAfterApi.innerHTML = `<img class="h-[45px] w-[45px]"
																		src="/img/sources/icons/play-blue.svg" alt="start">`;
	
		//rende cliccabile il pulsante pause del contatore con il contatore in run		
		pauseBottomAfterApi.innerHTML = `<button type="button" class="hover:opacity-75 " onclick="pauseContatoreApi(${taskInUsoId})">
																			<img class="h-[45px] w-[45px]"
																				src="/img/sources/icons/pause-blue.svg" alt="pause">
																		</button>`;
	
		//contatore-alto ( desktop)
		//dopo aver caricato i pulsanti tramite thymelaf secondo quanto preso dal model, al click 
		//del play vengono nascosti e sostituiti da quelli generati da javascritp
		if (pauseTopSvgBeforeApiContatoreNotRun != null && pauseTopSvgBeforeApiContatoreNotRun.display != "none") {
			pauseTopSvgBeforeApiContatoreNotRun.classList.add('hidden');
		}
		if (playTopSvgBeforeApiContatorIsRun != null && playTopSvgBeforeApiContatorIsRun.display != "none") {
			playTopSvgBeforeApiContatorIsRun.classList.add('hidden');
		}
	
	
		//rende non cliccabile il pulsante play del contatore con il contatore in run
		playTopAfterApi.innerHTML = `<img class="h-[29px] w-[29px]"
																					src="/img/sources/icons/play-blue.svg" alt="start">`;
	
		//rende cliccabile il pulsante pause del contatore con il contatore in run		
		pauseTopAfterApi.innerHTML = `<button type="button" class="hover:opacity-75 " onclick="pauseContatoreApi(${taskInUsoId})">
																					<img class="h-[29px] w-[29px]"
																						src="/img/sources/icons/pause-blue.svg" alt="pause">
																				</button>`;
}
	
	