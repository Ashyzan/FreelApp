
console.log("sono in lista-progetti.js")

//recupero elementi dal DOM
const ordinamentoListaPiuMenoRecente = document.getElementById('ordinamentoListaPiuMenoRecente');
//const ordinamentoListaPiuMenoRecenteMobile = document.getElementById('ordinamentoListaPiuMenoRecenteMobile');

//recupero campi input filtri dal DOM (valido sia per filtri dektop che mobile)
const dataModificaProgetto = document.getElementById('dataModificaProgetto');
const dataCreazioneProgetto = document.getElementById('dataCreazioneProgetto');
const piuRecente = document.getElementById('piuRecente');
const menoRecente = document.getElementById('menoRecente');
const aperto = document.getElementById('aperto');
const chiuso = document.getElementById('chiuso');



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
	
	}
}

		
