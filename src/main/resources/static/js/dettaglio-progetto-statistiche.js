console.log("LEGGO IL FILE DETTAGLIO PROGETTO STATISTICHE");


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
	
	/* data */
		const labels = [1, 2, 3, 4, 5, 6, 7];
		const data = {
		  labels: labels,
		  datasets: [{
			barPercentage: 1,
		    label: 'I task del progetto',
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


new Chart ( 
	document.getElementById('boundaries'),
	{
		  type: 'bar',
		  data: data,
		  options: {
//		          scales: {
//		              
//		              yAxes: [{
//		              ticks: {
//		              
//		                     min: 0,
//		                     max: 100,
//		                     callback: function(value){return value+ "%"}
//		                  },  
//		  								scaleLabel: {
//		                     display: true,
//		                     labelString: "Percentage"
//		                  }
//		              }]
//		          }
		      },
		});

  } catch (error) {
    console.error(error.message);
	
  }
}


