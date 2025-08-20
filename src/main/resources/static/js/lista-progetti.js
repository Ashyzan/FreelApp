
console.log("sono in lista-progetti.js")

const api_urlFiltriListaClientiAll = 'http://localhost:8080/api/filtri/clienti-all';
const api_urlFiltriListaClientiSearch = 'http://localhost:8080/api/filtri/clienti-search?input=';

//recupero elementi dal DOM
const ordinamentoListaPiuMenoRecente = document.getElementById('ordinamentoListaPiuMenoRecente');
//const ordinamentoListaPiuMenoRecenteMobile = document.getElementById('ordinamentoListaPiuMenoRecenteMobile');

//recupero campi input filtri dal DOM 
const pulsanteSelectFiltroPerClienti = document.getElementById('pulsante-select-filtro-per-clienti')
const dataModificaProgetto = document.getElementById('dataModificaProgetto');
const dataCreazioneProgetto = document.getElementById('dataCreazioneProgetto');
const piuRecente = document.getElementById('piuRecente');
const menoRecente = document.getElementById('menoRecente');
const aperto = document.getElementById('aperto');
const chiuso = document.getElementById('chiuso');
const inputFiltroProgettoPerClienteSelect = document.getElementById('input-filtro-progetto-per-cliente-select');
const inputFiltroProgettoPerClienteSearch = document.getElementById('filtro-progetto-per-cliente-search-input');
const filtroProgettoPerClienteSearch = document.getElementById('filtro-progetto-per-cliente-search');
const filtroProgettoPerClienteSelect = document.getElementById('filtro-progetto-per-cliente-select');
const formFiltri = document.getElementById('form-filtri-progetto');

//variabile di lavoro per filtri (se non selezionato cliente viene mandato al backend il valore -1 corrispondente a quello del relativo static non assegnato)
let idClienteSelezionato = -1;
let nomeClienteSelezionato;


//addeventlistener che cambia il pulsante del filtro cliente se il campo, precedentemente riempito per selezione, viene cancellato a mano
inputFiltroProgettoPerClienteSelect.addEventListener ('keyup', function (){
	if(inputFiltroProgettoPerClienteSelect.value == ""){
		passaAFiltroClientiSearchMode()
		pulsanteSelectFiltroPerClienti.innerHTML = `
							<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
									onclick="getJsonListaClientiAll()">
								<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
							</button>`
	}
});

//addeventlistener che cambia il pulsante del filtro cliente se il campo, precedentemente riepito per input, viene cancellato a mano
inputFiltroProgettoPerClienteSearch.addEventListener ('keyup', function (){
	if(inputFiltroProgettoPerClienteSearch.value == ""){
		passaAFiltroClientiSearchMode()
		pulsanteSelectFiltroPerClienti.innerHTML = `
							<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
									onclick="getJsonListaClientiAll()">
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


//funzione che chiude e ripristina il filtro per clienti
function ripristinaFiltroPerClienti(){
	idClienteSelezionato = -1
	document.getElementById('clienti-select-all').innerHTML =` `;
	passaAFiltroClientiSearchMode()
	pulsanteSelectFiltroPerClienti.innerHTML = `
					<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
							onclick="getJsonListaClientiAll()">
						<img  class="size-6" src="/img/sources/icons/arrow-down.svg">
					</button>`
	document.getElementById('input-filtro-progetto-per-cliente-select').value = null;
	
}


//funzione che al click sul cliente del filtro per clienti lo seleziona
function selezionaCliente(idCliente, nomeCliente){
	idClienteSelezionato = idCliente;
	nomeClienteSelezionato = nomeCliente;
	console.log("nomeClienteSelezionato --- in seleziona cliente: " + nomeClienteSelezionato);
	passaAFiltroClientiSelectMode()
	document.getElementById('input-filtro-progetto-per-cliente-select').value = nomeCliente;
	filtroProgettoPerClienteSearch.classList.add('hidden');
	filtroProgettoPerClienteSelect.classList.remove('hidden')
	document.getElementById('clienti-select-all').innerHTML =` `;
	pulsanteSelectFiltroPerClienti.innerHTML = `
				<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
						onclick="ripristinaFiltroPerClienti()">
						<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
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


//funzione che da filtro per clienti per ricerca passa a quello per select all 
function passaAFiltroClientiSelectMode(){
	filtroProgettoPerClienteSearch.classList.add('hidden');
	filtroProgettoPerClienteSelect.classList.remove('hidden');
	document.getElementById('clienti-from-search').innerHTML =` `;
	document.getElementById('clienti-select-all').innerHTML =` `;
	
	
}


//funzione che da filtro per clienti per select all passa a quello per ricerca 
function passaAFiltroClientiSearchMode(){
	inputFiltroProgettoPerClienteSearch.value ="";
	filtroProgettoPerClienteSearch.classList.remove('hidden');
	filtroProgettoPerClienteSelect.classList.add('hidden');
	document.getElementById('clienti-from-search').innerHTML =` `;
	document.getElementById('clienti-select-all').innerHTML =` `;
	
	
}

//********************** inserimento lista filtri se applicati  *******************************/

const stringaFiltri = document.getElementById('stringaFiltri');
console.log("filtroStatoProgetto: " + filtroStatoProgetto);
console.log("filtroOrdinamentoProgetto: " + filtroOrdinamentoProgetto);
console.log("filtroNomeCliente: " + filtroNomeCliente);
console.log("filtroDataOrdinamentoProgetto: " + filtroDataOrdinamentoProgetto);
if(stringaFiltri != null){
		if(filtroStatoProgetto != null && filtroDataOrdinamentoProgetto != null && filtroNomeCliente != null){
			if(filtroOrdinamentoProgetto != null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong> <strong>${filtroOrdinamentoProgetto}</strong></li>
														<li>- cliente <strong>${filtroNomeCliente}</strong></li>
														<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
											   		</ul>
												</div>
										   </div>`				
			} else if(filtroOrdinamentoProgetto == null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
													<div class="col col-span-2 text-start">
														<ul class="ps-4">
															<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong></li>
															<li>- cliente <strong>${filtroNomeCliente}</strong></li>
															<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
												   		</ul>
													</div>
											   </div>`		
			}
		}
	
			
		if(filtroStatoProgetto == null && filtroDataOrdinamentoProgetto != null && filtroNomeCliente != null){
			if(filtroOrdinamentoProgetto != null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong> <strong>${filtroOrdinamentoProgetto}</strong></li>
														<li>- cliente <strong>${filtroNomeCliente}</strong></li>
											   		</ul>
												</div>
										   </div>`				
			}else if(filtroOrdinamentoProgetto == null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
													<div class="col col-span-2 text-start">
														<ul class="ps-4">
															<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong></li>
															<li>- cliente <strong>${filtroNomeCliente}</strong></li>
												   		</ul>
													</div>
												 </div>`			
			}
		}
			
		if(filtroStatoProgetto != null && filtroDataOrdinamentoProgetto == null && filtroNomeCliente != null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- cliente <strong>${filtroNomeCliente}</strong></li>
														<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
											   		</ul>
												</div>
									   </div>`
		}

		if(filtroStatoProgetto != null && filtroDataOrdinamentoProgetto != null && filtroNomeCliente == null){
			if(filtroOrdinamentoProgetto != null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
													<div class="col col-span-2 text-start">
														<ul class="ps-4">
															<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong> <strong>${filtroOrdinamentoProgetto}</strong></li>
															<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
												   		</ul>
													</div>
											   </div>`							
			}else if(filtroOrdinamentoProgetto == null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
													<div class="col col-span-2 text-start">
														<ul class="ps-4">
															<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong></li>
															<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
												   		</ul>
													</div>
											   </div>`			
			}
		}
		
		if(filtroStatoProgetto != null && filtroDataOrdinamentoProgetto == null && filtroNomeCliente == null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
											   		</ul>
												</div>
										   </div>`			
		}
		
		if(filtroStatoProgetto == null && filtroDataOrdinamentoProgetto != null && filtroNomeCliente == null){
			if(filtroOrdinamentoProgetto != null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
													<div class="col col-span-2 text-start">
														<ul class="ps-4">
															<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong> <strong>${filtroOrdinamentoProgetto}</strong></li>
												   		</ul>
													</div>
											   </div>`			
				
			}else if(filtroOrdinamentoProgetto == null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
													<div class="col col-span-2 text-start">
														<ul class="ps-4">
															<li>- ordinamento per <strong>${filtroDataOrdinamentoProgetto}</strong></li>
												   		</ul>
													</div>
											   </div>`						
			}
		}
		
		if(filtroStatoProgetto == null && filtroDataOrdinamentoProgetto == null && filtroNomeCliente != null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- cliente <strong>${filtroNomeCliente}</strong></li>
											   		</ul>
												</div>
										   </div>`			
		}
}






//funzione che al click mostra filtri mobile o desktop
function mostraFiltri(){
	const imgFiltraPer = document.getElementById('img-filtra-per');
	const tabellaFiltriDesktop = document.getElementById('tabella-filtri-desktop');
	
		if(window.getComputedStyle(tabellaFiltriDesktop).display === 'none'){
			console.log("sono qui")
			tabellaFiltriDesktop.classList.remove('hidden');
			imgFiltraPer.src="";
			imgFiltraPer.src="/img/sources/icons/arrow-up.svg"
		} else{
			tabellaFiltriDesktop.classList.add('hidden');
			imgFiltraPer.src="";
			imgFiltraPer.src="/img/sources/icons/arrow-down.svg"
		}
	
		console.log("nomeClienteSelezionato: " + nomeClienteSelezionato)
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
	const inputRadiodataModificaProgetto = document.getElementById('inputRadiodataModificaProgetto');
	const inputRadiodataCreazioneProgetto = document.getElementById('inputRadiodataCreazioneProgetto');
	const inputRadioOrdinaProgettoPiuRecente = document.getElementById('inputRadioOrdinaProgettoPiuRecente');
	const inputRadioOrdinaProgettoMenoRecente = document.getElementById('inputRadioOrdinaProgettoMenoRecente');
	
	if(filtriAttiviInListaProgetto == true){
		console.log("filtroOrdinamentoProgetto: " + filtroOrdinamentoProgetto)
		if(filtroDataOrdinamentoProgetto == "data di modifica"){
			inputRadiodataModificaProgetto.innerHTML = "";
			inputRadiodataModificaProgetto.innerHTML = `<input type="radio" id="dataModificaProgetto" name="dataOrdinamentoProgetto" value="dataModificaProgetto" onclick="visualizzaordinamentoListaPiuMenoRecente()" checked="checked">
															<label for="piuRecente">Data di modifca</label>`;
															
			inputRadiodataCreazioneProgetto.innerHTML = "";
			inputRadiodataCreazioneProgetto.innerHTML = `<input type="radio" id="dataCreazioneProgetto" name="dataOrdinamentoProgetto" value="dataCreazioneProgetto" onclick="visualizzaordinamentoListaPiuMenoRecente()">
														<label for="menoRecente">Data di crezione</label>`;
															 											
		} else if(filtroDataOrdinamentoProgetto == "data di creazione"){
			inputRadiodataModificaProgetto.innerHTML = "";
			inputRadiodataModificaProgetto.innerHTML = `<input type="radio" id="dataModificaProgetto" name="dataOrdinamentoProgetto" value="dataModificaProgetto" onclick="visualizzaordinamentoListaPiuMenoRecente()">
															<label for="piuRecente">Data di modifca</label>`;
																		
			inputRadiodataCreazioneProgetto.innerHTML = "";
			inputRadiodataCreazioneProgetto.innerHTML = `<input type="radio" id="dataCreazioneProgetto" name="dataOrdinamentoProgetto" value="dataCreazioneProgetto" onclick="visualizzaordinamentoListaPiuMenoRecente()" checked="checked">
															<label for="menoRecente">Data di crezione</label>`;
		}
		
		if(filtroDataOrdinamentoProgetto != ""){
			visualizzaordinamentoListaPiuMenoRecente();
			//visualizzaordinamentoListaPiuMenoRecenteMobile();
			if(filtroOrdinamentoProgetto == "più recente"){
				inputRadioOrdinaProgettoPiuRecente.innerHTML = "";
				inputRadioOrdinaProgettoPiuRecente.innerHTML = `<input type="radio" id="piuRecente" name="ordinaProgetto" value="piuRecente" checked="checked">
															<label for="piuRecente">Più recente</label>`;
				
				inputRadioOrdinaProgettoMenoRecente.innerHTML = "";
				inputRadioOrdinaProgettoMenoRecente.innerHTML = `<input type="radio" id="menoRecente" name="ordinaProgetto" value="menoRecente" >
															<label for="menoRecente">Meno recente</label>`;
				
			}else if(filtroOrdinamentoProgetto == "meno recente"){
				inputRadioOrdinaProgettoPiuRecente.innerHTML = "";
				inputRadioOrdinaProgettoPiuRecente.innerHTML = `<input type="radio" id="piuRecente" name="ordinaProgetto" value="piuRecente" >
																	<label for="piuRecente">Più recente</label>`;
								
				inputRadioOrdinaProgettoMenoRecente.innerHTML = "";
				inputRadioOrdinaProgettoMenoRecente.innerHTML = `<input type="radio" id="menoRecente" name="ordinaProgetto" value="menoRecente" checked="checked">
																	<label for="menoRecente">Meno recente</label>`;
			}
		}
		
		if(filtroStatoProgetto === "APERTO"){
			inputRadioChiuso.innerHTML ="";
			inputRadioChiuso.innerHTML = `<input type="radio" id="chiuso" name="statoProgetto" value="chiuso" >
											<label for="chiuso">Chiuso</label>`
			inputRadioAperto.innerHTML = "";
			inputRadioAperto.innerHTML = `<input type="radio" id="aperto" name="statoProgetto" value="aperto" checked="checked">
											<label for="aperto">Aperto</label>`
		
		} else if(filtroStatoProgetto === "CHIUSO"){
			inputRadioChiuso.innerHTML ="";
						inputRadioChiuso.innerHTML = `<input type="radio" id="chiuso" name="statoProgetto" value="chiuso" checked="checked">
														<label for="chiuso">Chiuso</label>`
						inputRadioAperto.innerHTML = "";
						inputRadioAperto.innerHTML = `<input type="radio" id="aperto" name="statoProgetto" value="aperto">
														<label for="aperto">Aperto</label>`
		}
		
		if(filtroNomeCliente != null){
			idClienteSelezionato = filtroIdClienteSelezionato;
			inputFiltroProgettoPerClienteSearch.value = filtroNomeCliente;
			pulsanteSelectFiltroPerClienti.innerHTML = `
								<button type="button" class="border bg-[#0057A5] rounded-r-lg shadow-md w-full h-8 flex items-center justify-center"
										onclick="ripristinaFiltroPerClienti()">
										<img  class="size-6" src="/img/sources/icons/close-button-yellow.svg">
								</button>` 
		}
	
	}
}

//funzione che intercetta il form dei filtri e assegna come valore all'id progetto quello corretti
//intercettazione form prima del submit per validazione
formFiltri.addEventListener('submit', validation)
		
function validation(e){
	e.preventDefault()
	document.getElementById('input-filtro-progetto-per-cliente-select').value = idClienteSelezionato;
	formFiltri.submit(); 
}		
