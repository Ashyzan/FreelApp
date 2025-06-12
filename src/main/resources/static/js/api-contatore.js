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