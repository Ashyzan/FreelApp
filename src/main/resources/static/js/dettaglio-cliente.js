
//recupero elementi dal DOM
const elementiInformativiCliente = document.getElementsByClassName('elemento-informativo-cliente');
const arrayElementiInformativiCliente = [...elementiInformativiCliente];
let secondaColonnaDettaglioCliente = document.getElementById('seconda-colonna-dettaglio-cliente');
let primaColonnaDettaglioCliente = document.getElementById('prima-colonna-dettaglio-cliente');

//tooltip per elenco in overflow
const tooltipDettaglio = document.getElementById('tooltip-dettaglio');
const tooltipModifica = document.getElementById('tooltip-modifica');
const tooltipChiudiProgetto = document.getElementById('tooltip-chiudi-progetto');
const tooltipApriProgetto = document.getElementById('tooltip-apri-progetto');
const tooltipElimina = document.getElementById('tooltip-elimina');

//listeners che al variare delle dimensioni della window richiamano la funzione per troncare se nesserio o ripristinare il testo, e la funzione per
//ridimensionare l'altezza max della seconda colonna
window.addEventListener('resize', truncateElements );
window.addEventListener('resize', regolazioneAltezzaColonneLayout );


//vengono inizializzate variabili  fuori dalla funzione function regolazioneAltezzaColonneLayout perche poi viene aggiornata man mano che viene utilizzata per
//l'adattamento della dimenzione delle colonne del dettaglio progetto
let altezzaPrimaColonnaDettaglioCliente = primaColonnaDettaglioCliente.offsetHeight;
let altezzaPrecedentePrimaColonnaDettaglioCliente = primaColonnaDettaglioCliente.offsetHeight;


//funzione che al caricamento della pagina dettaglio cliente vede se ci sono testi di elementi da troncare e ne applica le classi necessarie
function truncateElements(){

	arrayElementiInformativiCliente.forEach(item =>{
			
		if(item.offsetWidth < item.scrollWidth){
			item.classList.add('truncate','hover:overflow-visible', 'hover:bg-white', 'hover:z-50', 'hover:rounded-lg', 'hover:shadow-md', 'hover:px-2', 'hover:w-fit', 'hover:h-fit')
		} else if(item.offsetWidth >= item.scrollWidth){
			item.classList.remove('truncate','hover:overflow-visible', 'hover:bg-white', 'hover:z-50', 'hover:rounded-lg', 'hover:shadow-md', 'hover:px-2', 'hover:w-fit', 'hover:h-fit')
		}
	})
	
}

//*******  funzione che in Dettaglio Cliente misura le dimensioni della prima colonna e 
//    l'assegna alla seconda 		 ********************
function regolazioneAltezzaColonneLayout(){
	altezzaPrimaColonnaDettaglioCliente = primaColonnaDettaglioCliente.offsetHeight;
	secondaColonnaDettaglioCliente.classList.remove('max-h-['+ altezzaPrecedentePrimaColonnaDettaglioCliente + 'px]')
	secondaColonnaDettaglioCliente.classList.add('max-h-['+ altezzaPrimaColonnaDettaglioCliente + 'px]')
	altezzaPrecedentePrimaColonnaDettaglioCliente = altezzaPrimaColonnaDettaglioCliente;
}

//************** funzioni per attivazione e chiusura tooltip in elenco overflow ******************/

//tooltip DETTAGLIO
function avviaTooltipDettaglio(event){
	tooltipDettaglio.classList.remove('hidden')
	tooltipDettaglio.style.left = (event.clientX - 70)+'px'
	tooltipDettaglio.style.top = (event.clientY - 50)+'px'
	
}

function terminaTooltipDettaglio(){
	tooltipDettaglio.classList.add('hidden')
}


//tooltip MODIFICA
function avviaTooltipModifica(event){
	tooltipModifica.classList.remove('hidden')
	tooltipModifica.style.left = (event.clientX - 70)+'px'
	tooltipModifica.style.top = (event.clientY - 50)+'px'
	
}

function terminaTooltipModifica(){
	tooltipModifica.classList.add('hidden')
}


//tooltip CHIUDI-PROGETTO
function avviaTooltipChiudiProgetto(event){
	tooltipChiudiProgetto.classList.remove('hidden')
	tooltipChiudiProgetto.style.left = (event.clientX - 70)+'px'
	tooltipChiudiProgetto.style.top = (event.clientY - 50)+'px'
	
}

function terminaTooltipChiudiProgetto(){
	tooltipChiudiProgetto.classList.add('hidden')
}


//tooltip APRI-PROGETTO
function avviaTooltipApriProgetto(event){
	tooltipApriProgetto.classList.remove('hidden')
	tooltipApriProgetto.style.left = (event.clientX - 70)+'px'
	tooltipApriProgetto.style.top = (event.clientY - 50)+'px'
	
}

function terminaTooltipApriProgetto(){
	tooltipApriProgetto.classList.add('hidden')
}


//tooltip ELIMINA
function avviaTooltipElimina(event){
	tooltipElimina.classList.remove('hidden')
	tooltipElimina.style.left = (event.clientX - 70)+'px'
	tooltipElimina.style.top = (event.clientY - 50)+'px'
	
}

function terminaTooltipElimina(){
	tooltipElimina.classList.add('hidden')
}
