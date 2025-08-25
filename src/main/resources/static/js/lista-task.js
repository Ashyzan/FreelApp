console.log("sono in lista-task.js")

const api_urlFiltriListaClientiAll = 'http://localhost:8080/api/filtri/clienti-all';
const api_urlFiltriListaClientiSearch = 'http://localhost:8080/api/filtri/clienti-search?input=';
const api_urlFiltriListaProgettiSearch = 'http://localhost:8080/api/filtri/progetti-search?input=';
const api_urlFiltriListaProgettiAll = 'http://localhost:8080/api/filtri/progetti-all';
const api_urlFiltriListaProgettiByCliente = 'http://localhost:8080/api/filtri/progetti-by-cliente-?input=';





//recupero dal DOM elementi per i filtri
const pulsanteSelectFiltroPerClienti = document.getElementById('pulsante-select-filtro-per-clienti')
const pulsanteCambioSearchSelectFiltroPerProgetti = document.getElementById('pulsante-cambio-search-select-filtro-per-progetti');
const filtroTaskPerProgettoSearch = document.getElementById('filtro-task-per-progetto-search');
const filtroTaskPerProgettoSelect = document.getElementById('filtro-task-per-progetto-select');
const ordinamentoListaPiuMenoRecente = document.getElementById('ordinamentoListaPiuMenoRecente');
const dataModificaTask = document.getElementById('dataModificaTask');
const dataCreazioneTask = document.getElementById('dataCreazioneTask');
const piuRecente = document.getElementById('piuRecente');
const menoRecente = document.getElementById('menoRecente');
const aperto = document.getElementById('aperto');
const chiuso = document.getElementById('chiuso');
const formFiltri = document.getElementById('form-filtri-task');

const inputFiltroTaskPerClienteSelect = document.getElementById('input-filtro-task-per-cliente-select');
const inputFiltroTaskPerClienteSearch = document.getElementById('filtro-task-per-cliente-search-input');
const filtroTaskPerClienteSearch = document.getElementById('filtro-task-per-cliente-search');
const filtroTaskPerClienteSelect = document.getElementById('filtro-task-per-cliente-select');

const inputFiltroTaskPerProgettoSearch = document.getElementById('filtro-task-per-progetto-search-input');
const inputFiltroPerProgettoSelect = document.getElementById('filtro-task-per-progetto-select-input');
const inputFiltroTaskClientePerBackend = document.getElementById('input-filtro-task-per-cliente-backend');

//variabili di lavoro per filtri (se non selezionati cliente o progetti viene mandato al backend in valore -1 corrispondente a quello del relativo static non assegnato)
let idClienteSelezionato = -1;
let idProgettoSelezionato = -1;
let nomeClienteSelezionato;
let nomeProgettoSelezionato;



//******************** FUNZIONI FILTRI LISTA TASK ************************/
//funzione che al click mostra filtri mobile o desktop
function mostraFiltri(){
	const imgFiltraPer = document.getElementById('img-filtra-per');
	const tabellaFiltriTask = document.getElementById('tabella-filtri-task');
	
		if(window.getComputedStyle(tabellaFiltriTask).display === 'none'){
			tabellaFiltriTask.classList.remove('hidden');
			imgFiltraPer.src="";
			imgFiltraPer.src="/img/sources/icons/arrow-up.svg"
		} else{
			tabellaFiltriTask.classList.add('hidden');
			imgFiltraPer.src="";
			imgFiltraPer.src="/img/sources/icons/arrow-down.svg"
		}
	
}

//addeventlistener che cambia il pulsante del filtro cliente se il campo, precedentemente riempito per selezione, viene cancellato a mano
inputFiltroTaskPerClienteSelect.addEventListener ('keyup', function (){
	ripristinaFiltroPerClienti()
	if(inputFiltroTaskPerClienteSelect.value == ""){
		passaAFiltroClientiSearchMode()
		pulsanteSelectFiltroPerClienti.innerHTML = `
							<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
									onclick="getJsonListaClientiAll()">
								<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
							</button>`
	}
});

//addeventlistener che cambia il pulsante del filtro cliente se il campo, precedentemente riepito per input, viene cancellato a mano
inputFiltroTaskPerClienteSearch.addEventListener ('keyup', function (){
	if(inputFiltroTaskPerClienteSearch.value == ""){
		passaAFiltroClientiSearchMode()
		pulsanteSelectFiltroPerClienti.innerHTML = `
							<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
									onclick="getJsonListaClientiAll()">
								<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
							</button>`
	}
});



//addeventlistener che cambia il pulsante del filtro progetti se il campo, precedentemente riepito per input, viene cancellato a mano
inputFiltroTaskPerProgettoSearch.addEventListener ('keyup', function (){
	ripristinaFiltroPerProgetti()
	if(inputFiltroTaskPerProgettoSearch.value == ""){
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
							<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
									onclick="getJsonListaProgettiByCliente(${idClienteSelezionato}), sbloccaListaProgettiFiltrataPerCliente()">
								<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
							</button>`
	}
});

//funzione che al click sul pulsante di lista intera su filtra per clienti restitisce la lista intera dei clienti
async function getJsonListaClientiAll(){
	
	document.getElementById('clienti-select-all').innerHTML =` `;
	
		const response = await fetch(api_urlFiltriListaClientiAll);
		const list = await response.json();
		
		list.forEach(item =>{
			document.getElementById('clienti-select-all').innerHTML += 
			`<button type="button" value="${item.id}" id="cliente-${item.id}"
					class="border-b-1 p-1 shadow-sm w-full hover:bg-[#FFE541]/50  px-1 grid grid-cols-5" onclick="selezionaCliente(${item.id},'${item.labelCliente}')">
					<img class="col-span-1 size-6 flex justify-center items-center" src="${item.logoCliente}">
					<div class="col-span-4 text-[#0057A5] text-start truncate">${item.labelCliente}</div>
			</button>`
		});
		
		pulsanteSelectFiltroPerClienti.innerHTML = "";
											
		pulsanteSelectFiltroPerClienti.innerHTML = `
			<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
					onclick="ripristinaFiltroPerClienti()">
					<img  class="size-6" src="/img/sources/icons/arrow-up.svg">
			</button>` 
		
		
	
}



//funzione che all'inserimento dell'input filtra per clienti restituisce la lista filtrata di clienti
async function getJsonListaClientiSearch(input){
	
	document.getElementById('clienti-from-search').innerHTML =` `;
	document.getElementById('clienti-select-all').innerHTML =` `;
	if(input != "" || input != " "){
		const response = await fetch(api_urlFiltriListaClientiSearch + input);
		const list = await response.json();
				
		list.forEach(item =>{
			document.getElementById('clienti-from-search').innerHTML += 
					`<button type="button" value="${item.id}" id="cliente-${item.id}"
								class="border-b-1 p-1 shadow-sm w-full hover:bg-[#FFE541]/50  px-1 grid grid-cols-5" onclick="selezionaCliente(${item.id},'${item.labelCliente}')">
								<img class="col-span-1 size-6 flex justify-center items-center" src="${item.logoCliente}">
								<div class="col-span-4 text-[#0057A5] text-start truncate">${item.labelCliente}</div>
						</button>`
				});				
	
		pulsanteSelectFiltroPerClienti.innerHTML = "";
									
		pulsanteSelectFiltroPerClienti.innerHTML = `
					<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
							onclick="ripristinaFiltroPerClienti()">
							<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
					</button>` 
	}else if(input == "" || input == " "){
		pulsanteSelectFiltroPerClienti.innerHTML = "";
											
		pulsanteSelectFiltroPerClienti.innerHTML = `
					<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
							onclick="ripristinaFiltroPerClienti()">
							<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
					</button>` 
	}
	
}


//funzione che al click sul cliente del filtro per clienti lo seleziona
function selezionaCliente(idCliente, nomeCliente){
	idClienteSelezionato = idCliente;
	nomeClienteSelezionato = nomeCliente;
	sbloccaListaProgettiFiltrataPerCliente()
	passaAFiltroClientiSelectMode()
	getJsonListaProgettiByCliente(idClienteSelezionato)
	document.getElementById('input-filtro-task-per-cliente-select').value = nomeCliente;
	filtroTaskPerClienteSearch.classList.add('hidden');
	filtroTaskPerClienteSelect.classList.remove('hidden')
	document.getElementById('clienti-select-all').innerHTML =` `;
	pulsanteSelectFiltroPerClienti.innerHTML = `
				<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
						onclick="ripristinaFiltroPerClienti()">
						<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
				</button>` 
	
	pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
				<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
						onclick="sbloccaListaProgettiFiltrataPerCliente()">
						<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
				</button>` 
}


//funzione che da filtro per clienti per ricerca passa a quello per select all 
function passaAFiltroClientiSelectMode(){
	filtroTaskPerClienteSearch.classList.add('hidden');
	filtroTaskPerClienteSelect.classList.remove('hidden');
	document.getElementById('clienti-from-search').innerHTML =` `;
	document.getElementById('clienti-select-all').innerHTML =` `;
	
	
}

//funzione che chiude e ripristina il filtro per clienti
function ripristinaFiltroPerClienti(){
	idClienteSelezionato = -1
	idProgettoSelezionato = -1
	document.getElementById('clienti-select-all').innerHTML =` `;
	passaAFiltroClientiSearchMode()
	pulsanteSelectFiltroPerClienti.innerHTML = `
					<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
							onclick="getJsonListaClientiAll()">
						<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
					</button>`
	document.getElementById('input-filtro-task-per-cliente-select').value = null;
	
	pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
					<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
							onclick="getJsonListaProgettiAll()">
							<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
					</button>` 
	ripristinaFiltroPerProgetti()
}

//funzione che da filtro task per progetti select all passa a quello di ricerca
function ripristinaFiltroPerProgetti(){
	filtroTaskPerProgettoSearch.classList.remove('hidden');
	document.getElementById('filtro-task-per-progetto-search-input').value="";
	filtroTaskPerProgettoSelect.classList.add('hidden');
	document.getElementById('progetti-from-search').innerHTML =` `;
	document.getElementById('progetti-select-all').innerHTML =` `;
	pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
	if(idClienteSelezionato == -1){
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
					<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
							onclick="getJsonListaProgettiAll()">
						<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
					</button>`		
	}else if(idClienteSelezionato != -1){
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
					<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
							onclick="getJsonListaProgettiByCliente(${idClienteSelezionato}), sbloccaListaProgettiFiltrataPerCliente()">
						<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
					</button>`	
	}
}

//funzione che da filtro task per progetti per ricerca passa a quello per select all 
function passaAFiltroProgettiSelectMode(){
	filtroTaskPerProgettoSearch.classList.add('hidden');
	filtroTaskPerProgettoSelect.classList.remove('hidden');
	document.getElementById('progetti-from-search').innerHTML =` `;
	document.getElementById('progetti-select-all').innerHTML =` `;
	
	
}


//funzione che all'inserimento dell'input filtra per progetti restituisce la lista filtrata di progetti
async function getJsonListaProgettiSearch(input){
	
	document.getElementById('progetti-from-search').innerHTML =` `;
	if(input != "" || input != " "){
		const response = await fetch(api_urlFiltriListaProgettiSearch + input);
		const list = await response.json();
				
		list.forEach(item =>{
			document.getElementById('progetti-from-search').innerHTML += 
				`<button type="button" value="${item.id}"  id="progetto-${item.id}" onclick="selezionaProgetto(${item.id}, '${item.name}')"
						class="border-b-1 p-1 shadow-sm w-full hover:bg-[#FFE541]/50 text-start text-[#0057A5] px-1 truncate">
						${item.name}
				</button>`
				});				
	
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
									
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
																<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
																		onclick="ripristinaFiltroPerProgetti()">
																	<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
																</button>` 
	}else if(input == "" || input == " "){
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
											
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
																		<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
																				onclick="ripristinaFiltroPerProgetti()">
																			<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
																		</button>` 
	}
	
}

//funzione che al click sul pulsante di lista intera su filtra per progetti restitisce la lista intera di progetti
async function getJsonListaProgettiAll(){
	
	document.getElementById('progetti-select-all').innerHTML =` `;
	
		const response = await fetch(api_urlFiltriListaProgettiAll);
		const list = await response.json();
		
		list.forEach(item =>{
			document.getElementById('progetti-select-all').innerHTML += 
			`<button type="button" value="${item.id}" id="progetto-${item.id}" onclick="selezionaProgetto(${item.id}, '${item.name}', ${item.idCliente}, '${item.nomeCliente}')"
					class="border-b-1 p-1 shadow-sm w-full hover:bg-[#FFE541]/50 text-start text-[#0057A5] px-1 truncate">
					${item.name}
			</button>`
		});
		
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
											
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
			<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
					onclick="ripristinaFiltroPerProgetti()">
					<img  class="size-6" src="/img/sources/icons/arrow-up.svg">
			</button>` 
			
}


//funzione che alla selezione del cliente restituisce la lista filtrata di progetti relativi al quel cliente
async function getJsonListaProgettiByCliente(input){
	
	document.getElementById('progetti-select-all').classList.add('hidden')
	document.getElementById('progetti-select-all').innerHTML =` `;
	
		const response = await fetch(api_urlFiltriListaProgettiByCliente + input);
		const list = await response.json();
		if(list.length == 0){
			document.getElementById('progetti-select-all').innerHTML += 
						`<div class="border-b-1 p-1 shadow-sm w-full pointer-events-none text-start text-[#0057A5] px-1 truncate">Nessun progetto per il cliente selezionato</div>`
		}
		list.forEach(item =>{
			document.getElementById('progetti-select-all').innerHTML += 
			`<button type="button" value="${item.id}" id="progetto-${item.id}" onclick="selezionaProgetto(${item.id}, '${item.name}', ${item.idCliente}, '${item.nomeCliente}')"
					class="border-b-1 p-1 shadow-sm w-full hover:bg-[#FFE541]/50 text-start text-[#0057A5] px-1 truncate">
					${item.name}
			</button>`
		});
		
		
			
}

//funzione che dopo aver selezionato cliente rende visibile la lista dei progetti filtrati per la successiva selezione
function sbloccaListaProgettiFiltrataPerCliente(){
	document.getElementById('progetti-select-all').classList.remove('hidden')
	pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
				<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
						onclick="ripristinaFiltroPerProgetti()">
						<img  class="size-6" src="/img/sources/icons/arrow-up.svg">
				</button>` 
	
}

//funzione che al click sul progetto del filtro per clienti lo seleziona
function selezionaProgetto(idProgetto, nomeProgetto, idCliente, nomeCliente){
	idProgettoSelezionato = idProgetto;
	
	
	filtroTaskPerProgettoSearch.classList.remove('hidden');
		document.getElementById('filtro-task-per-progetto-search-input').value=nomeProgetto;
		filtroTaskPerProgettoSelect.classList.add('hidden');
		document.getElementById('progetti-from-search').innerHTML =` `;
		document.getElementById('progetti-select-all').innerHTML =` `;
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
								
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
						<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
								onclick="ripristinaFiltroPerProgetti()">
								<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
						</button>` 
						
		//selezione automatica del cliente relativo al progetto selezionato
		idClienteSelezionato = idCliente;
			nomeClienteSelezionato = nomeCliente;
			//sbloccaListaProgettiFiltrataPerCliente()
			passaAFiltroClientiSelectMode()
			//getJsonListaProgettiByCliente(idClienteSelezionato)
			document.getElementById('input-filtro-task-per-cliente-select').value = nomeCliente;
			filtroTaskPerClienteSearch.classList.add('hidden');
			filtroTaskPerClienteSelect.classList.remove('hidden')
			document.getElementById('clienti-select-all').innerHTML =` `;
			pulsanteSelectFiltroPerClienti.innerHTML = `
						<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
								onclick="ripristinaFiltroPerClienti()">
								<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
						</button>` 
}


//funzione che al click della scelta ordina per Modifica o creazione fa apparire la scelta piu o meno recente
function visualizzaordinamentoListaPiuMenoRecente(){
	ordinamentoListaPiuMenoRecente.classList.remove('hidden')
}

//funzione che se filtri attivi fa visualizzare all'interno del form scelta filtri le scelte attualmente in vigore
function filtriInUsoDaUtente(){
	console.log("sono in filtriInUsoDaUtente")
	const inputRadioAperto = document.getElementById('inputRadioAperto');
	const inputRadioChiuso = document.getElementById('inputRadioChiuso');
	const inputRadiodataModificaTask = document.getElementById('inputRadiodataModificaTask');
	const inputRadiodataCreazioneTask = document.getElementById('inputRadiodataCreazioneTask');
	const inputRadioOrdinaProgettoPiuRecente = document.getElementById('inputRadioOrdinaTaskPiuRecente');
	const inputRadioOrdinaProgettoMenoRecente = document.getElementById('inputRadioOrdinaTaskMenoRecente');
	
	if(filtriAttiviInListaTask == true){
		console.log("filtroOrdinamentoTask: " + filtroOrdinamentoTask)
		if(filtroDataOrdinamentoTask == "data di modifica"){
			inputRadiodataModificaTask.innerHTTask
			inputRadiodataModificaTask.innerHTML = `<input type="radio" id="dataModificaProgetto" name="dataOrdinamentoTask" value="dataModificaTask" onclick="visualizzaordinamentoListaPiuMenoRecente()" checked="checked">
															<label for="piuRecente">Data di modifca</label>`;
															
			inputRadiodataCreazioneTask.innerHTML = "";
			inputRadiodataCreazioneTask.innerHTML = `<input type="radio" id="dataCreazioneProgetto" name="dataOrdinamentoTask" value="dataCreazioneTask" onclick="visualizzaordinamentoListaPiuMenoRecente()">
														<label for="menoRecente">Data di crezione</label>`;
															 											
		} else if(filtroDataOrdinamentoTask == "data di creazione"){
			inputRadiodataModificaTask.innerHTML = "";
			inputRadiodataModificaTask.innerHTML = `<input type="radio" id="dataModificaProgetto" name="dataOrdinamentoTask" value="dataModificaTask" onclick="visualizzaordinamentoListaPiuMenoRecente()">
															<label for="piuRecente">Data di modifca</label>`;
																		
			inputRadiodataCreazioneTask.innerHTML = "";
			inputRadiodataCreazioneTask.innerHTML = `<input type="radio" id="dataCreazioneProgetto" name="dataOrdinamentoTask" value="dataCreazioneTask" onclick="visualizzaordinamentoListaPiuMenoRecente()" checked="checked">
															<label for="menoRecente">Data di crezione</label>`;
		}
		
		if(filtroDataOrdinamentoTask != ""){
			visualizzaordinamentoListaPiuMenoRecente();
			//visualizzaordinamentoListaPiuMenoRecenteMobile();
			if(filtroOrdinamentoTask == "più recente"){
				inputRadioOrdinaProgettoPiuRecente.innerHTML = "";
				inputRadioOrdinaProgettoPiuRecente.innerHTML = `<input type="radio" id="piuRecente" name="ordinaTask" value="piuRecente" checked="checked">
															<label for="piuRecente">Più recente</label>`;
				
				inputRadioOrdinaProgettoMenoRecente.innerHTML = "";
				inputRadioOrdinaProgettoMenoRecente.innerHTML = `<input type="radio" id="menoRecente" name="ordinaTask" value="menoRecente" >
															<label for="menoRecente">Meno recente</label>`;
				
			}else if(filtroOrdinamentoTask == "meno recente"){
				inputRadioOrdinaProgettoPiuRecente.innerHTML = "";
				inputRadioOrdinaProgettoPiuRecente.innerHTML = `<input type="radio" id="piuRecente" name="ordinaTask" value="piuRecente" >
																	<label for="piuRecente">Più recente</label>`;
								
				inputRadioOrdinaProgettoMenoRecente.innerHTML = "";
				inputRadioOrdinaProgettoMenoRecente.innerHTML = `<input type="radio" id="menoRecente" name="ordinaTask" value="menoRecente" checked="checked">
																	<label for="menoRecente">Meno recente</label>`;
			}
		}
		
		if(filtroStatoTask === "APERTO"){
			inputRadioChiuso.innerHTML ="";
			inputRadioChiuso.innerHTML = `<input type="radio" id="chiuso" name="statoTask" value="chiuso" >
											<label for="chiuso">Chiuso</label>`
			inputRadioAperto.innerHTML = "";
			inputRadioAperto.innerHTML = `<input type="radio" id="aperto" name="statoTask" value="aperto" checked="checked">
											<label for="aperto">Aperto</label>`
		
		} else if(filtroStatoTask === "CHIUSO"){
			inputRadioChiuso.innerHTML ="";
						inputRadioChiuso.innerHTML = `<input type="radio" id="chiuso" name="statoTask" value="chiuso" checked="checked">
														<label for="chiuso">Chiuso</label>`
						inputRadioAperto.innerHTML = "";
						inputRadioAperto.innerHTML = `<input type="radio" id="aperto" name="statoTask" value="aperto">
														<label for="aperto">Aperto</label>`
		}
	
		if(filtroNomeCliente != null){
					idClienteSelezionato = filtroIdClienteSelezionato;
					nomeClienteSelezionato = filtroNomeCliente;
					inputFiltroTaskPerClienteSearch.value = filtroNomeCliente;
					pulsanteSelectFiltroPerClienti.innerHTML = `
									<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
											onclick="ripristinaFiltroPerClienti()">
											<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
									</button>` 
					getJsonListaProgettiByCliente(filtroIdClienteSelezionato);
					pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
									<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
											onclick="sbloccaListaProgettiFiltrataPerCliente()">
											<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
									</button>`
		}
		
		if(filtroNomeProgetto != null){
			idProgettoSelezionato = filtroIdProgettoSelezionato;
			nomeProgettoSelezionato = filtroNomeProgetto;
			document.getElementById('filtro-task-per-progetto-search-input').value = nomeProgettoSelezionato;
			pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
									<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
											onclick="ripristinaFiltroPerProgetti()">
											<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
									</button>`
			
		}
	}
}


//funzione che da filtro per clienti per select all passa a quello per ricerca 
function passaAFiltroClientiSearchMode(){
	inputFiltroTaskPerClienteSearch.value ="";
	filtroTaskPerClienteSearch.classList.remove('hidden');
	filtroTaskPerClienteSelect.classList.add('hidden');
	document.getElementById('clienti-from-search').innerHTML =` `;
	document.getElementById('clienti-select-all').innerHTML =` `;
	
	
}


//********************** inserimento lista filtri se applicati  *******************************/

const stringaFiltri = document.getElementById('stringaFiltri');

if(stringaFiltri != null){
	if(testoFinaleFiltri != ""){
		stringaFiltri.innerHTML = testoFinaleFiltri;		
	}
		
}



//funzione che intercetta il form dei filtri e assegna come valore all'id progetto e cliente quelli corretti
//intercettazione form prima del submit per validazione
formFiltri.addEventListener('submit', validation)
		
function validation(e){
	e.preventDefault()
	document.getElementById('filtro-task-per-progetto-select-input').value = idProgettoSelezionato;
	inputFiltroTaskClientePerBackend.value = idClienteSelezionato;
	formFiltri.submit(); 
}
