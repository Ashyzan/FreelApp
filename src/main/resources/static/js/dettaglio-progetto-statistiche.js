console.log("LEGGO IL FILE DETTAGLIO PROGETTO STATISTICHE");


const descrizioneContratta = document.getElementById('descrizioneContratta');
const descrizioneEspansa = document.getElementById('descrizioneEspansa');
const buttonEspandiDescrizione = document.getElementById('buttonEspandiDescrizione');
const buttonRiduciDescrizione = document.getElementById('buttonRiduciDescrizione');

//recupero elementi da DOM in Dettaglio Progetti
const secondaColonnaDettaglioProgetto = document.getElementById('seconda-colonna-dettaglio-progetto');
const terzaColonnaDettaglioProgetto = document.getElementById('terza-colonna-dettaglio-progetto');
//const tBodySecondaColonnaDettaglioProgetto = document.getElementById('t-body-seconda-colonna-dettaglio-progetto');

//vengono inizializzate variabili  fuori dalla funzione function regolazioneAltezzaColonneLayout perche poi viene aggiornata man mano che viene utilizzata per
//l'adattamento della dimenzione delle colonne del dettaglio progetto
let primaColonnaDettaglioProgetto = document.getElementById('prima-colonna-dettaglio-progetto');
let altezzaPrimaColonnaDettaglioProgetto = "h-[" + primaColonnaDettaglioProgetto.offsetHeight + "px]";
let altezzaPrecedentePrimaColonnaDettaglioProgetto = primaColonnaDettaglioProgetto.offsetHeight;
//let altezzaTbodySecondaColonnaDettaglioProgetto = "max-h-[" + (primaColonnaDettaglioProgetto.offsetHeight-(primaColonnaDettaglioProgetto.offsetHeight*0.13)) + "px]";

//tooltip per elenco in overflow dal DOM
const tooltipDettaglio = document.getElementById('tooltip-dettaglio');
const tooltipModifica = document.getElementById('tooltip-modifica');
const tooltipSelezionaContatore = document.getElementById('tooltip-seleziona-contatore');

//listeners che al variare delle dimensioni della window richiamano la funzione per ridimensionare l'altezza max delle colonne
window.addEventListener('resize', regolazioneAltezzaColonneLayout );

// ************* logica di espansione e riduzione descrizione
if(buttonEspandiDescrizione != null){
	buttonEspandiDescrizione.addEventListener('click', mostraDescrizioneCompleta);	
}
buttonRiduciDescrizione.addEventListener('click', mostraDescrizioneRidotta);

function mostraDescrizioneCompleta(){
	descrizioneContratta.classList.add('hidden');
	descrizioneEspansa.classList.remove('hidden');
	regolazioneAltezzaColonneLayout() //regola l'alatezza delle altre colonne - funzione presa da layout.js
}

function mostraDescrizioneRidotta(){
	descrizioneContratta.classList.remove('hidden');
	descrizioneEspansa.classList.add('hidden');
	regolazioneAltezzaColonneLayout() //regola l'alatezza delle altre colonne - funzione presa da layout.js
}

//************** logica chiamata API per grafici statistiche */





/*grafico*/

const MONTHS = [
  'Gen',
  'Feb',
  'Mar',
  'Apr',
  'Mag',
  'Giu',
  'Lug',
  'Ago',
  'Set',
  'Ott',
  'Nov',
  'Dic'
];

const CHART_COLORS = {
  red: 'rgb(255, 99, 132)',
  orange: 'rgb(255, 159, 64)',
  yellow: 'rgb(255, 205, 86)',
  green: 'rgb(75, 192, 192)',
  blue: 'rgb(54, 162, 235)',
  purple: 'rgb(153, 102, 255)',
  grey: 'rgb(201, 203, 207)'
};

const NAMED_COLORS = [
  CHART_COLORS.red,
  CHART_COLORS.orange,
  CHART_COLORS.yellow,
  CHART_COLORS.green,
  CHART_COLORS.blue,
  CHART_COLORS.purple,
  CHART_COLORS.grey,
];


async function getDataJsonProgetto() {
  const url_apiStatisticheProgetto = '/api/statistiche-dettaglio-progetto/' + progettoId;
  console.log(url_apiStatisticheProgetto)
  try {
    const response = await fetch(url_apiStatisticheProgetto);
    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }

    const json = await response.json();
	
	console.log("json.valori " + json.valori);
	console.log("json.labels " + json.labels);
	
	/* data */

	
	
		const labels = json.labels;
		const data = {
		  labels: labels,
		  datasets: [{
			barPercentage: 1,
		    label: 'Task %',
		    data: json.valori,
			
		    backgroundColor: [
		      'rgba(255, 99, 132, 0.2)',
		      'rgba(255, 159, 64, 0.2)',
		      'rgba(255, 205, 86, 0.2)',
		      'rgba(75, 192, 192, 0.2)',
		      'rgba(54, 162, 235, 0.2)',
		      'rgba(153, 102, 255, 0.2)',
		      'rgba(201, 203, 207, 0.2)'
		    ],
		    borderColor: [
		      'rgb(255, 99, 132)',
		      'rgb(255, 159, 64)',
		      'rgb(255, 205, 86)',
		      'rgb(75, 192, 192)',
		      'rgb(54, 162, 235)',
		      'rgb(153, 102, 255)',
		      'rgb(201, 203, 207)'
		    ],
		    borderWidth: 1
		  }]
		};

if(json.valori != 0) {
new Chart ( 
	document.getElementById('boundaries'),
	{
		  type: 'bar',
		  data: data,
		  options: {
		                 
		              },
		});
}
  } catch (error) {
    console.error(error.message);
	
  }
}

//*******  funzione che in Dettaglio Progetto misura le dimensioni della prima colonna e 
//    l'assegna alla seconda e terza		 ********************

function regolazioneAltezzaColonneLayout(){
	
	
	const nuovaPrimaColonnaDettaglioProgetto = document.getElementById('prima-colonna-dettaglio-progetto');
	const nuovaAltezzaPrimaColonnaDettaglioProgetto = "h-[" + nuovaPrimaColonnaDettaglioProgetto.offsetHeight + "px]";
	//const nuovaAltezzaTbodySecondaColonnaDettaglioProgetto = "max-h-[" + (nuovaPrimaColonnaDettaglioProgetto.offsetHeight-(nuovaPrimaColonnaDettaglioProgetto.offsetHeight*0.13)) + "px]";	
	
	if(altezzaPrimaColonnaDettaglioProgetto === nuovaAltezzaPrimaColonnaDettaglioProgetto){
		console.log("sono in altezzaPrimaColonnaDettaglioProgetto === nuovaAltezzaPrimaColonnaDettaglioProgetto")
		secondaColonnaDettaglioProgetto.classList.add(altezzaPrimaColonnaDettaglioProgetto);
		terzaColonnaDettaglioProgetto.classList.add(altezzaPrimaColonnaDettaglioProgetto);
				
	}else {
		secondaColonnaDettaglioProgetto.classList.remove(altezzaPrimaColonnaDettaglioProgetto);
		terzaColonnaDettaglioProgetto.classList.remove(altezzaPrimaColonnaDettaglioProgetto);
		secondaColonnaDettaglioProgetto.classList.add(nuovaAltezzaPrimaColonnaDettaglioProgetto);
		terzaColonnaDettaglioProgetto.classList.add(nuovaAltezzaPrimaColonnaDettaglioProgetto);
	}
		
		altezzaPrimaColonnaDettaglioProgetto = nuovaAltezzaPrimaColonnaDettaglioProgetto;
	
}




//*******  funzione che in Dettaglio Cliente misura le dimensioni della prima colonna e 
//    l'assegna alla seconda 		 ********************
function regolazioneAltezzaColonneLayout(){
	altezzaPrimaColonnaDettaglioProgetto = primaColonnaDettaglioProgetto.offsetHeight;
	secondaColonnaDettaglioProgetto.classList.remove('max-h-['+ altezzaPrecedentePrimaColonnaDettaglioProgetto + 'px]')
	secondaColonnaDettaglioProgetto.classList.add('max-h-['+ altezzaPrimaColonnaDettaglioProgetto + 'px]')
	altezzaPrecedentePrimaColonnaDettaglioProgetto = altezzaPrimaColonnaDettaglioProgetto;
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


//tooltip SELEZIONA-CONTATORE
function avviaTooltipSelezionaContatore(event){
	tooltipSelezionaContatore.classList.remove('hidden')
	tooltipSelezionaContatore.style.left = (event.clientX - 90)+'px'
	tooltipSelezionaContatore.style.top = (event.clientY - 50)+'px'
	
}

function terminaTooltipSelezionaContatore(){
	tooltipSelezionaContatore.classList.add('hidden')
}
