console.log("sono in lista-task.js")

const api_urlFiltriListaProgettiSearch = 'http://localhost:8080/api/filtri-task/progetti-search?input=';
const api_urlFiltriListaProgettiAll = 'http://localhost:8080/api/filtri-task/progetti-all';

//recupero dal DOM elementi per i filtri
const pulsanteCambioSearchSelectFiltroPerProgetti = document.getElementById('pulsante-cambio-search-select-filtro-per-progetti');
const filtroTaskPerProgettoSearch = document.getElementById('filtro-task-per-progetto-search');
const filtroTaskPerProgettoSelect = document.getElementById('filtro-task-per-progetto-select');



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


//funzione che da filtro task per progetti select all passa a quello di ricerca
function ripristinaFiltroPerProgetti(){
	filtroTaskPerProgettoSearch.classList.remove('hidden');
	document.getElementById('filtro-task-per-progetto-search-input').value="";
	filtroTaskPerProgettoSelect.classList.add('hidden');
	document.getElementById('progetti-from-search').innerHTML =` `;
	document.getElementById('progetti-select-all').innerHTML =` `;
	pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
							
	pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
				<button type="button" class="border bg-[#84ABD3] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
						onclick="getJsonListaProgettiAll()">
					<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
				</button>`
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
				`<button type="button" value="${item.id}" 
						class="border-b-1 p-1 shadow-sm w-full hover:bg-[#FFE541]/50 text-start text-[#0057A5] px-1 truncate">
						${item.name}
				</button>`
				});				
	
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
									
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
																<button type="button" class="border bg-[#84ABD3] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
																		onclick="ripristinaFiltroPerProgetti()">
																	<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
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
			`<button type="button" value="${item.id}" 
					class="border-b-1 p-1 shadow-sm w-full hover:bg-[#FFE541]/50 text-start text-[#0057A5] px-1 truncate">
					${item.name}
			</button>`
		});
		
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = "";
											
		pulsanteCambioSearchSelectFiltroPerProgetti.innerHTML = `
			<button type="button" class="border bg-[#84ABD3] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
					onclick="ripristinaFiltroPerProgetti()">
					<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
			</button>` 
		
		
	
}




