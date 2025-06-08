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

	
	
	
	
	
	//****************** CHIAMATA API PER START CONTATORE ********************************/
	
function startContatoreApi(id){
	const url_apiStart = 'http://localhost:8080/start/' + id;
	
	fetch(url_apiStart).then(response =>{
		
		if (!response.ok) {
							       throw new Error('Network response was not ok');
							     }
							     return response.json();
							})
							.then(data =>{
								
								//aggiorno le variabili
								contatoreTrue = data.contatoreIsTrue;
								finalTimeSec = Number(data.finaltime);
								console.log("finalTimeSec: "+ typeof finalTimeSec + " " + finalTimeSec)
								contatoreIsRun = data.contatoreIsRun;
								
								hours = finalTimeSec / 3600;
								minutes = (finalTimeSec % 3600) / 60;
								seconds = finalTimeSec % 60;
								
								timerstart()
								
								if(pauseTopSvgBeforeApiContatoreNotRun != null && pauseTopSvgBeforeApiContatoreNotRun.display != "none"){
									pauseTopSvgBeforeApiContatoreNotRun.classList.add('hidden');		
								}
								if(playTopSvgBeforeApiContatorIsRun != null && playTopSvgBeforeApiContatorIsRun.display != "none"){
									playTopSvgBeforeApiContatorIsRun.classList.add('hidden');		
								}
								
								
										
										playTopAfterApi.innerHTML = `<img class="h-[29px] w-[29px]"
																		//src="/img/sources/icons/play-blue.svg" alt="start">`;
										
										
										pauseTopAfterApi.innerHTML = `<button type="button" class="hover:opacity-75 " onclick="pauseContatoreApi(${taskInUsoId})">
																		<img class="h-[29px] w-[29px]"
																			src="/img/sources/icons/pause-blue.svg" alt="pause">
																	</button>`;
									
							});
								 
		
	
}
	
//****************** CHIAMATA API PER PAUSE CONTATORE ********************************/
	
function pauseContatoreApi(id){
	const url_apiStart = 'http://localhost:8080/Contatore/pause/' + id;
	
	fetch(url_apiStart).then(response =>{
		
		if (!response.ok) {
							       throw new Error('Network response was not ok');
							     }
							     return response.json();
							})
							.then(data =>{
								
								console.log("JSON: " + data)
								
								console.log("finaltime json:" + data.finaltime);
								//aggiorno le variabili
								contatoreTrue = data.contatoreIsTrue;
								finalTimeSec = Number(data.finaltime);
								console.log("finalTimeSec: "+ typeof finalTimeSec + " " + finalTimeSec)
								contatoreIsRun = data.contatoreIsRun;
								
								hours = finalTimeSec / 3600;
								minutes = (finalTimeSec % 3600) / 60;
								seconds = finalTimeSec % 60;
								clearInterval(crono);
								stampacontatore();
								
								
								
								if(playTopSvgBeforeApiContatoreNotRun != null && playTopSvgBeforeApiContatoreNotRun.display === "none"){
									playTopSvgBeforeApiContatoreNotRun.classList.remove('hidden');		
								}
								if(pauseTopSvgBeforeApiContatorIsRun != null && pauseTopSvgBeforeApiContatorIsRun.display === "none"){
									pauseTopSvgBeforeApiContatorIsRun.classList.remove('hidden');		
								}
								
												
								playTopAfterApi.innerHTML = `<button type="button" class="hover:opacity-75 " onclick="startContatoreApi(${taskInUsoId})">
																<img class="h-[29px] w-[29px]"
																	src="/img/sources/icons/play-blue.svg" alt="play">
															</button>`;
								
								
																		
								pauseTopAfterApi.innerHTML = `<img class="h-[29px] w-[29px]"
																//src="/img/sources/icons/pause-blue.svg" alt="pause">`;
							});
								 
		
	
}

	//console.log("finalTimeSec iniziale: " + finalTimeSec)
	//console.log("ore iniziali: " + hours);
	//console.log("minuti iniziali: " + minutes);
	//console.log("secondi iniziali: " + seconds);
				
	function tempochescorre() {
		
		if(contatoreIsRun !== true){
			stampacontatore();
		} else{ 
			
			iterazioni ++;
					
					seconds++;
					stampacontatore();
					
					if (seconds == 59) {
						seconds = -1;
						
						if(minutes <= 59){
							minutes++;
						}
							else {
							minutes = 0;
							seconds = -1;
							hours++;
						}
					}
					//verifica ogni secondo se il timer ha raggiuno il massimo consentito
					timeExceed(iterazioni);

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
				
				crono = setInterval(tempochescorre, 1000);
				//console.log("contatore attivo , finaltime = " + finalTimeSec + contatoreIsRun + testtaskdatestare);
			}

			else if (contatoreIsRun !== true) {

				stampacontatore();
				//console.log("contatore non attivo , finaltime = " + finalTimeSec + "testtaskdatestare " + testtaskdatestare);
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





	
	