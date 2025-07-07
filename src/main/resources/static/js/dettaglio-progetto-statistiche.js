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
const boundaries = document.getElementById('boundaries');
const MONTHS = [
  'January',
  'February',
  'March',
  'April',
  'May',
  'June',
  'July',
  'August',
  'September',
  'October',
  'November',
  'December'
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

/* setup */
const inputs = {
  min: 0,
  max: 100,
  count: 12,
  decimals: 2,
  continuity: 1
};

const generateLabels = () => {
  return MONTHS;
};

const generateData = () => (inputs);


async function getDataJsonProgetto() {
  const url_apiStatisticheProgetto = '/api/statistiche-dettaglio-progetto/' + progettoId;
  console.log(url_apiStatisticheProgetto)
  try {
    const response = await fetch(url_apiStatisticheProgetto);
    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }

    const json = await response.json();
    console.log(json);
	
	
	/*config*/
	const config = (boundaries , {
	  type: 'line',
	  data: [json.uno, json.due, json.tre],
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
	console.log(config);
	/* data */
	const data = {
	  labels: generateLabels(),
	  datasets: [
	    {
	      label: 'Task',
	      data: generateData(),
	      borderColor: CHART_COLORS.red,
	      backgroundColor: (CHART_COLORS.yellow),
	      fill: true
	    }
	  ]
	};
	console.log(data);

	
	
	
  } catch (error) {
    console.error(error.message);
  }
}


