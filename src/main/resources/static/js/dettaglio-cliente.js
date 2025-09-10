
//recupero elementi dal DOM
const elementiInformativiCliente = document.getElementsByClassName('elemento-informativo-cliente');
const arrayElementiInformativiCliente = [...elementiInformativiCliente];
let secondaColonnaDettaglioCliente = document.getElementById('seconda-colonna-dettaglio-cliente');
let primaColonnaDettaglioCliente = document.getElementById('prima-colonna-dettaglio-cliente');


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

const tooltipdettaglio = document.getElementById('tooltipdettaglio');
function tooltipDettaglio(event){
	tooltipdettaglio.classList.remove('hidden')
	console.log("coordinata x: " + event.clientX)
	console.log("coordinata y: " + event.clientY)
	tooltipdettaglio.style.left = (event.clientX - 70)+'px'
	tooltipdettaglio.style.top = (event.clientY - 40)+'px'
	
}

function terminaTooltipDettaglio(){
	tooltipdettaglio.classList.add('hidden')
}