
console.log("sono in lista-progetti.js")

//recupero elementi dal DOM
const ordinamentoListaPiuMenoRecente = document.getElementById('ordinamentoListaPiuMenoRecente');
const ordinamentoListaPiuMenoRecenteMobile = document.getElementById('ordinamentoListaPiuMenoRecenteMobile');

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


//funzione che al click della scelta ordina per Modifica o creazione fa apparire la scelta piu o meno recente
function visualizzaordinamentoListaPiuMenoRecente(){
	ordinamentoListaPiuMenoRecente.classList.remove('hidden')
}

function visualizzaordinamentoListaPiuMenoRecenteMobile(){
	ordinamentoListaPiuMenoRecenteMobile.classList.remove('hidden')
}

