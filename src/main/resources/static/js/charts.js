
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
	