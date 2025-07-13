
console.log("sono in lista-progetti.js")


//********************** inserimento lista filtri se applicati  *******************************/

const stringaFiltri = document.getElementById('stringaFiltri');
console.log("filtroStatoProgetto: " + filtroStatoProgetto);
console.log("filtroOrdinamentoProgetto: " + filtroOrdinamentoProgetto);
console.log("filtroNomeCliente: " + filtroNomeCliente);
if(stringaFiltri != null){
		if(filtroStatoProgetto != null && filtroOrdinamentoProgetto != null && filtroNomeCliente != null){
				stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
												<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
														<li>- ordinamento dal progetto <strong>${filtroOrdinamentoProgetto}</strong></li>
														<li>- cliente <strong>${filtroNomeCliente}</strong></li>
											   		</ul>
												</div>
										   </div>`
		}
	
			
		if(filtroStatoProgetto == null && filtroOrdinamentoProgetto != null && filtroNomeCliente != null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
											<div class="col col-span-2 text-start">
												<ul class="ps-4">
													<li>- ordinamento dal progetto <strong>${filtroOrdinamentoProgetto}</strong></li>
													<li>- cliente <strong>${filtroNomeCliente}</strong></li>
										   		</ul>
											</div>
									   </div>`
		}
			
		if(filtroStatoProgetto != null && filtroOrdinamentoProgetto == null && filtroNomeCliente != null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
														<li>- cliente <strong>${filtroNomeCliente}</strong></li>
											   		</ul>
												</div>
									   </div>`
		}

		if(filtroStatoProgetto != null && filtroOrdinamentoProgetto != null && filtroNomeCliente == null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
														<li>- ordinamento dal progetto <strong>${filtroOrdinamentoProgetto}</strong></li>
											   		</ul>
												</div>
										   </div>`			
		}
		
		if(filtroStatoProgetto != null && filtroOrdinamentoProgetto == null && filtroNomeCliente == null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- stato progetto <strong>${filtroStatoProgetto}</strong></li>
											   		</ul>
												</div>
										   </div>`			
		}
		
		if(filtroStatoProgetto == null && filtroOrdinamentoProgetto != null && filtroNomeCliente == null){
			stringaFiltri.innerHTML = `<div class="grid grid-cols-3">
											<div class="col col-span-1 text-end">Elenco filtrato per:</div>
												<div class="col col-span-2 text-start">
													<ul class="ps-4">
														<li>- ordinamento dal progetto <strong>${filtroOrdinamentoProgetto}</strong></li>
											   		</ul>
												</div>
										   </div>`			
		}
		
		if(filtroStatoProgetto == null && filtroOrdinamentoProgetto == null && filtroNomeCliente != null){
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




//************** funzioni per lista progetti ordinati per cliente o datainizio *********************************

// recupero elementi DOM per lista progetti ordinati per cliente o datainizio
const filtraPerClientiButton = document.getElementById('filtra-per-clienti-button');
const filtraPerDataButton = document.getElementById('filtra-per-data-button');
const listCliente = document.getElementById('list-cliente');
const listDataInizio = document.getElementById('list-data-inizio');
const ricercaVuotaData = document.getElementById('ricerca-vuota-data');
const ricercaVuotaClienti = document.getElementById('ricerca-vuota-clienti');
const paginazioneData = document.getElementById('paginazione-data');
const paginazioneClienti = document.getElementById('paginazione-clienti');
let paginazioneAttivaCliente = new Boolean();	
let paginazioneAttivaData = new Boolean();

	//funzione che nel tempalte listaProgetti, in onload verifica la visualizzazione scelta dall'utente
	//e se la scelta è per data lascia tutto come default, se la scelta è per cliente ne cambia la visualizzazione
	//inoltre se si è in searchmode cambia paginazione
function visualizzazioneProgettiDaSceltaUtente(){
	if(ordinaElencoProgettiPerData == false){ //ordinaElencoProgettiPerData, dato che arriva dal BE
		if ((listCliente != null) && (paginazioneClienti != null)) {
			switchToVisualizzazioneClienti()
		}		
	}
} 


	//funzione che fa lo switch su visualizzazione per clienti
function switchToVisualizzazioneClienti(){
	listCliente.classList.remove('hidden');
	paginazioneClienti.classList.remove('hidden');
	listDataInizio.classList.add('hidden');
	paginazioneData.classList.add('hidden');		
	filtraPerClientiButton.classList.add('opacity-50', 'pointer-events-none');
	filtraPerDataButton.classList.remove('opacity-50', 'pointer-events-none');
	
	//chiamata api al metodo che cambia la scelta visualizzazione da data a cliente
	const api_urlOrdinaElencoProgettiPerCliente = 'http://localhost:8080/api/progetti/cambiaOrdinePerCliente';
			 fetch(api_urlOrdinaElencoProgettiPerCliente);
}

//funzione che fa lo switch su visualizzazione per data
function switchToVisualizzazioneData(){
	listCliente.classList.add('hidden');
	paginazioneClienti.classList.add('hidden');
	listDataInizio.classList.remove('hidden');
	paginazioneData.classList.remove('hidden');
	filtraPerClientiButton.classList.remove('opacity-50', 'pointer-events-none');
	filtraPerDataButton.classList.add('opacity-50', 'pointer-events-none');
	
	//chiamata api al metodo che cambia la scelta visualizzazione da cliente a data
	const api_urlOrdinaElencoProgettiPerData = 'http://localhost:8080/api/progetti/cambiaOrdinePerData';
		 fetch(api_urlOrdinaElencoProgettiPerData);
}

function attivaPaginazione1() {
	switchToVisualizzazioneClienti()
}


function attivaPaginazione2() {
	switchToVisualizzazioneData()
}

//funzione che al clicl mostra filtri mobile o desktop
function mostraFiltri(){
	const imgFiltraPer = document.getElementById('img-filtra-per');
	const tabellaFiltriMobile = document.getElementById('tabella-filtri-mobile');
	const tabellaFiltriDesktop = document.getElementById('tabella-filtri-desktop');
	if(window.innerWidth >= 1024){
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
	}else {
		if(window.getComputedStyle(tabellaFiltriMobile).display === 'none'){
			tabellaFiltriMobile.classList.remove('hidden');
			imgFiltraPer.src="";
			imgFiltraPer.src="/img/sources/icons/arrow-up.svg"
		} else{
			tabellaFiltriMobile.classList.add('hidden');
			imgFiltraPer.src="";
			imgFiltraPer.src="/img/sources/icons/arrow-down.svg"
		}
	}
}



