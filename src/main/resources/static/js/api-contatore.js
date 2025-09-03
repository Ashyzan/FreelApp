
//recuper elementi da DOM del dettaglio task per gestione worktime e icona stato in tempo reale dopo caricamento template
const dettaglioTaskImgStatoOnload = document.getElementById('dettaglio-task-img-stato-onload');
const dettaglioTaskImgStatoAfterContatoreApi = document.getElementById('dettaglio-task-img-stato-after-contatore-api');
const dettaglioTaskWorktimeOnload = document.getElementById('dettaglio-task-work-time-onload');
const dettaglioTaskWorktimeAfterContatoreApi = document.getElementById('dettaglio-task-work-time-after-contatore-api');



//recuper elementi dal DOM da lista task per gestione icona di stato task in uso
const listaTask = document.getElementsByClassName('item-elenco-tasks');


//recupero elemeti dal DOM dettaglio progetto per gestione icona di stato task in uso in lista dei task del progetto
const listaTaskDettaglioProgetto =document.getElementsByClassName('item-elenco-tasks-dettaglio-progetto');
const arrayListaTaskDettaglioProgetto = [...listaTaskDettaglioProgetto];



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
																	
								timerstart();
								
								//gestione icona di stato per contatore api senza refresh template
								if(descrizioneTaskId != null){
									if(descrizioneTaskId === taskInUsoId){
										dettaglioTaskImgStatoOnload.classList.add('hidden');
										statoDettaglioTaskdaContatoreApi()									
									}									
								}
								
								//gestione icona di stato del primo elemento in task list template
								gestioneIconaStatoTaskList()
								
								//gestione icona di stato dell'elenco dei task in lista progetto
								gestioneIconaStatoDettaglioProgetto()
									
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
								
								terminaWorker()
								
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
								
								//gestione icona di stato per contatore api senza refresh template
								if(descrizioneTaskId != null){
									if(descrizioneTaskId === taskInUsoId){
										dettaglioTaskImgStatoOnload.classList.add('hidden');
										dettaglioTaskWorktimeOnload.classList.add('hidden')
										statoDettaglioTaskdaContatoreApi()	
										worktimeDettaglioTaskdaContatoreApi()
										
									}	
								}
								
								//gestione icona di stato del primo elemento in task list template
								gestioneIconaStatoTaskList()
								
								//gestione icona di stato dell'elenco dei task in lista progetto
								gestioneIconaStatoDettaglioProgetto()
							});
								 
		
	
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
		pauseBottomAfterApi.innerHTML = `<button type="button" class="flex items-center hover:opacity-75 " onclick="pauseContatoreApi(${taskInUsoId})">
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
								
}


//funzione che all'interazione del contatore in dettaglio task ne modica icona di stato senza refresh
function statoDettaglioTaskdaContatoreApi(){
	
		if(contatoreIsRun === true){
			dettaglioTaskImgStatoAfterContatoreApi.innerHTML = `<img class="size-5 inline"
															src="/img/sources/icons/contatore-on-start.svg" alt="contatore in start">`
		} else{
			dettaglioTaskImgStatoAfterContatoreApi.innerHTML = `<img class="size-5 inline"
															src="/img/sources/icons/contatore-on-pause.svg" alt="contatore in pausa">`				
		}

}


//funzione che all'interazione della pausa del contatore in dettaglio task aggiorna worktime senza refresh
function worktimeDettaglioTaskdaContatoreApi(){								
	dettaglioTaskWorktimeAfterContatoreApi.innerHTML =  Math.floor(hours) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);
}


//funzione che gestisce l'icona di stato e il worktime del primo item della taskList senza fare il refresh della pagina
function gestioneIconaStatoTaskList(){
	if(listaTask != null){
		const iconaStatoOnload = document.getElementById('icona-stato-onload-0');
		const iconaStatoAfterContatoreApi = document.getElementById('icona-stato-after-contatore-api-0');
		const workTimeOnload = document.getElementById('work-time-onload-0');
		const workTimeAfterContatoreApi = document.getElementById('work-time-after-contatore-api-0')
		if(iconaStatoOnload != null){
			iconaStatoOnload.classList.add('hidden');			
		}
		
		if(contatoreIsRun === true){
			if(iconaStatoAfterContatoreApi != null){
				iconaStatoAfterContatoreApi.innerHTML = `<img class="size-5 inline float-right"
															src="/img/sources/icons/contatore-on-start.svg" alt="contatore in start">`				
			}
		} else if(contatoreIsRun === false){
			if(workTimeOnload != null){
				workTimeOnload.classList.add('hidden');				
			}
			if(workTimeAfterContatoreApi != null && iconaStatoAfterContatoreApi != null){
				workTimeAfterContatoreApi.innerHTML = Math.floor(hours) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);
				iconaStatoAfterContatoreApi.innerHTML = `<img class="size-5 inline float-right"
															src="/img/sources/icons/contatore-on-pause.svg" alt="contatore in pausa">`								
			}
		}
	}
	
}

//funzione che gestisce l'icona di stato e della taskList  in DETTAGLIO PROGETTO senza fare il refresh della pagina
function gestioneIconaStatoDettaglioProgetto(){
	if(listaTaskDettaglioProgetto != null){
		let taskInUsoId_forWorking = "task-" + taskInUsoId;
		arrayListaTaskDettaglioProgetto.forEach(item => {
			if(item.id === taskInUsoId_forWorking){
				const iconaStatoOnload_forWorking = "icona-stato-onload-" + item.id;
				//console.log("iconaStatoOnload_forWorking: " + iconaStatoOnload_forWorking);
				const iconaStatoOnload = document.getElementById(iconaStatoOnload_forWorking);
				const iconaStatoAfterContatoreApi_forWorking = "icona-stato-after-contatore-api-" + item.id;
				//console.log("iconaStatoAfterContatoreApi_forWorking: " + iconaStatoAfterContatoreApi_forWorking)
				const iconaStatoAfterContatoreApi = document.getElementById(iconaStatoAfterContatoreApi_forWorking);
				iconaStatoOnload.classList.add('hidden');
				if(contatoreIsRun === true){
					if(iconaStatoAfterContatoreApi != null){
						iconaStatoAfterContatoreApi.innerHTML = `<img class="h-5"
																	src="/img/sources/icons/contatore-on-start.svg" alt="contatore in start">`	
					}			
				}else if(contatoreIsRun === false){
					if(iconaStatoAfterContatoreApi != null){
						iconaStatoAfterContatoreApi.innerHTML = `<img class="h-5"
																	src="/img/sources/icons/contatore-on-pause.svg" alt="contatore in pausa">`
					}		
				}
			}
		});
		
	}
}



