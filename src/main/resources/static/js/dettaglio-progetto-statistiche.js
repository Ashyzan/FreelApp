const descrizioneContratta = document.getElementById('descrizioneContratta');
const descrizioneEspansa = document.getElementById('descrizioneEspansa');
const buttonEspandiDescrizione = document.getElementById('buttonEspandiDescrizione');
const buttonRiduciDescrizione = document.getElementById('buttonRiduciDescrizione');


// ************* logica di espansione e riduzione descrizione
if(buttonEspandiDescrizione != null){
	buttonEspandiDescrizione.addEventListener('click', mostraDescrizioneCompleta);	
}
buttonRiduciDescrizione.addEventListener('click', mostraDescrizioneRidotta);

function mostraDescrizioneCompleta(){
	descrizioneContratta.classList.add('hidden');
	descrizioneEspansa.classList.remove('hidden');
}

function mostraDescrizioneRidotta(){
	descrizioneContratta.classList.remove('hidden');
	descrizioneEspansa.classList.add('hidden');
}

//************** logica chiamata API per grafici statistiche */
const url_apiStatisticheTask = '/api/statistiche-dettaglio-task/';

function apiStatisticheJson(idTask){
	
	//esegue il fetch solo se il task è attivo
	if(statoTask != "inattivo"){
		
		console.log("url statistiche: " + url_apiStatisticheTask)
		
		fetch(url_apiStatisticheTask + idTask)
					   .then(response => {
					     if (!response.ok) {
					       throw new Error('Network response was not ok');
					     }
					     return response.json();
					   })
					   .then(datajson => {
							if(datajson.giorniChiusuraStimata != null){
								if(datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata > 0){
									contenitoreGiorniChiusuraStimata.innerHTML = `<div class="text-red-600 text-center mb-3">Chiusura stimata superata di ${datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata} giorni</div>`
								}
								
								if(datajson.giorniChiusuraStimata.giorniAncoraDisponibili > 0){
									contenitoreGiorniChiusuraStimata.innerHTML = `	<div class="text-center font-bold pe-1 py-1">Countdown chiusura stimata</div>
																					<div class="md:hidden text-center mb-2"> giorni rimanenti ${datajson.giorniChiusuraStimata.giorniAncoraDisponibili }</div>
																					<canvas class="m-auto  mb-2" id="chiusuraStimata"></canvas>`
									creaGraficoGiorniStimati(datajson);	 
								} else if(datajson.giorniChiusuraStimata.giorniAncoraDisponibili == 0 && datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata  == 0){
									contenitoreGiorniChiusuraStimata.innerHTML = `<div class="text-center mb-3">Oggi è il giorno di chiusura Stimata</div>`
								}							
							}
							
							tempoBudgetParzialeDaTipologia(datajson);
								 
					   })
					   .catch(error => {
					     console.error('Error:', error);
					   });
					   
	}
	
}



/*grafico*/
const boundaries = document.getElementById('boundaries');

/* setup */
const inputs = {
  min: 0,
  max: 100,
  count: 8,
  decimals: 2,
  continuity: 1
};

const generateLabels = () => {
  return Utils.months({count: inputs.count});
};

const generateData = () => (Utils.numbers(inputs));

/*config*/
new Chart = (boundaries , {
  type: 'line',
  data: data,
  options: {
    plugins: {
      filler: {
        propagate: false,
      },
      title: {
        display: true,
        text: (ctx) => 'Fill: ' + ctx.chart.data.datasets[0].fill
      }
    },
    interaction: {
      intersect: false,
    }
  },
});

/* data */
const data = {
  labels: generateLabels(),
  datasets: [
    {
      label: 'Task',
      data: generateData(),
      borderColor: Utils.CHART_COLORS.red,
      backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red),
      fill: false
    }
  ]
};

