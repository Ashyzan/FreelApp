
//API 
const api_urlProjectsArchived = 'http://localhost:8080/api/statistics/achived-projects'

async function getProjectsArchived(){
	console.log("sono in getProjectsArchived")
	
		const response = await fetch(api_urlProjectsArchived);
		const projectsList = await response.json();
		
		//creo du earray vuouti che conterrano i dati degli assi del grafico e verrano utilizzati come etichette degli assi
		const projectNameList = [];
		const coutnTaskOfProject = [];
		
		projectsList.forEach(item =>{
		
			projectNameList.push(item.nome);
			coutnTaskOfProject.push(item.countTaskProgetto);
		
		}) 

		// donuts
		const data = {
		  labels: [
		    'Red',
		    'Blue',
		    'Yellow'
		  ],
		  datasets: [{
		    label: 'My First Dataset',
		    data: [300, 50, 100],
		    backgroundColor: [
		      'rgb(255, 99, 132)',
		      'rgb(54, 162, 235)',
		      'rgb(255, 205, 86)'
		    ],
		    hoverOffset: 4
		  }]
		};
		
		const donut = document.getElementById('donut');
					
		new Chart(donut, {
		  type: 'doughnut',
		  data: data,
		  options: {
		    responsive: true,
		    plugins: {
		      legend: {
		        position: 'top',
		      },
		      title: {
		        display: true,
		        text: 'Chart.js Doughnut Chart'
		      }
		    }
		  },
		});
		
		
//creazione grafico
	  const ctx = document.getElementById('myChart');

	  new Chart(ctx, {
	    type: 'bar',
	    data: {
	      labels: projectNameList,
	      datasets: [{
	        label: 'N. task',
	        data: coutnTaskOfProject,
	        borderWidth: 1
	      }]
	    },
	    options: {
	      scales: {
	        y: {
	          beginAtZero: true
	        }
	      }
	    }
	  });
	 }
	