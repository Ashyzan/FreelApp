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
	
	crono = setInterval(tempochescorre, 1000);
	
	
	
	
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
								contatoreIsRun = data.contatoreIsRun;
								
								hours = finalTimeSec / 3600;
								minutes = (finalTimeSec % 3600) / 60;
								seconds = finalTimeSec % 60;
								
								//contatore-basso (mobile)
									//dopo aver caricato i pulsanti tramite thymelaf secondo quanto preso dal model, al click 
									//del play vengono nascosti e sostituiti da quelli generati da javascritp
									if(pauseBottomSvgBeforeApiContatoreNotRun != null && pauseBottomSvgBeforeApiContatoreNotRun.display != "none"){
										pauseBottomSvgBeforeApiContatoreNotRun.classList.add('hidden');		
									}
									if(playBottomSvgBeforeApiContatorIsRun != null && playBottomSvgBeforeApiContatorIsRun.display != "none"){
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
									if(pauseTopSvgBeforeApiContatoreNotRun != null && pauseTopSvgBeforeApiContatoreNotRun.display != "none"){
										pauseTopSvgBeforeApiContatoreNotRun.classList.add('hidden');		
									}
									if(playTopSvgBeforeApiContatorIsRun != null && playTopSvgBeforeApiContatorIsRun.display != "none"){
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
										console.log("sono in startContatoreApi, un attimo prima di timerStart e contatoreIsRun = " + contatoreIsRun + 
												" contatoreTrue = " + contatoreTrue
										)								
								timerstart()
								
									
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
								
								//aggiorno le variabili
								contatoreTrue = data.contatoreIsTrue;
								finalTimeSec = Number(data.finaltime);
								contatoreIsRun = data.contatoreIsRun;
								
								hours = finalTimeSec / 3600;
								minutes = (finalTimeSec % 3600) / 60;
								seconds = finalTimeSec % 60;
								
								//contatore-basso (mobile)
									//dopo aver caricato i pulsanti tramite thymelaf secondo quanto preso dal model, al click 
									//del pause vengono nascosti e sostituiti da quelli generati da javascritp
									if(playBottomSvgBeforeApiContatoreNotRun != null && playBottomSvgBeforeApiContatoreNotRun.display != "none"){
										playBottomSvgBeforeApiContatoreNotRun.classList.add('hidden');		
									}
									if(pauseBottomSvgBeforeApiContatorIsRun != null && pauseBottomSvgBeforeApiContatorIsRun.display != "none"){
										pauseBottomSvgBeforeApiContatorIsRun.classList.add('hidden');		
									}
															
									//rende cliccabile il pulsante play del contatore con il contatore in pausa				
									playBottomAfterApi.innerHTML = `<button type="button" class="hover:opacity-75 " onclick="startContatoreApi(${taskInUsoId})">
																	<img class="h-[45px] w-[45px]"
																		src="/img/sources/icons/play-blue.svg" alt="play">
																</button>`;
									//rende  non cliccabile il pulsante pause del contatore con il contatore in pausa
									pauseBottomAfterApi.innerHTML = `<img class="h-[45px] w-[45px]"
																	src="/img/sources/icons/pause-blue.svg" alt="pause">`;					
								

								//contatore-alto (desktop)
									//dopo aver caricato i pulsanti tramite thymelaf secondo quanto preso dal model, al click 
									//del pause vengono nascosti e sostituiti da quelli generati da javascritp
									if(playTopSvgBeforeApiContatoreNotRun != null && playTopSvgBeforeApiContatoreNotRun.display != "none"){
										playTopSvgBeforeApiContatoreNotRun.classList.add('hidden');		
									}
									if(pauseTopSvgBeforeApiContatorIsRun != null && pauseTopSvgBeforeApiContatorIsRun.display != "none"){
										pauseTopSvgBeforeApiContatorIsRun.classList.add('hidden');		
									}
									
									//rende cliccabile il pulsante play del contatore con il contatore in pausa				
									playTopAfterApi.innerHTML = `<button type="button" class="hover:opacity-75 " onclick="startContatoreApi(${taskInUsoId})">
																	<img class="h-[29px] w-[29px]"
																		src="/img/sources/icons/play-blue.svg" alt="play">
																</button>`;
									//rende  non cliccabile il pulsante pause del contatore con il contatore in pausa
									pauseTopAfterApi.innerHTML = `<img class="h-[29px] w-[29px]"
																	src="/img/sources/icons/pause-blue.svg" alt="pause">`;
								
								//azzera il setInterval per evitare che agli start successivi la velocità del contatore aumenti
								//clearInterval(crono);
								stampacontatore();
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
		//console.log("sono in stampa contatore")

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
				
		if(contatoreAttivatoDaRapidButton === true){
			contatoreTrue = true;
			contatoreIsRun = true;
			pulsantiContatoreInStart()
		}
		
		
		if (contatoreTrue && contatoreIsRun) {
				// eseguo prim la funzione una volta per togliere il lag di 1 secondo, poi entro nel ciclo
				
				tempochescorre();
				
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




function pulsantiContatoreInStart(){
	//contatore-basso (mobile)
										//dopo aver caricato i pulsanti tramite thymelaf secondo quanto preso dal model, al click 
										//del play vengono nascosti e sostituiti da quelli generati da javascritp
										if(pauseBottomSvgBeforeApiContatoreNotRun != null && pauseBottomSvgBeforeApiContatoreNotRun.display != "none"){
											pauseBottomSvgBeforeApiContatoreNotRun.classList.add('hidden');		
										}
										if(playBottomSvgBeforeApiContatorIsRun != null && playBottomSvgBeforeApiContatorIsRun.display != "none"){
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
										if(pauseTopSvgBeforeApiContatoreNotRun != null && pauseTopSvgBeforeApiContatoreNotRun.display != "none"){
											pauseTopSvgBeforeApiContatoreNotRun.classList.add('hidden');		
										}
										if(playTopSvgBeforeApiContatorIsRun != null && playTopSvgBeforeApiContatorIsRun.display != "none"){
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
											console.log("sono in startContatoreApi, un attimo prima di timerStart e contatoreIsRun = " + contatoreIsRun + 
													" contatoreTrue = " + contatoreTrue
											)							
}


	
	