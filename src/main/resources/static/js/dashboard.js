
//recupero elementi dal DOM
const tasks = document.getElementsByClassName('lista-ultimi-task');


//taskInUsoId.addEventListener('change', attivaElementoInTaskList)
console.log('taskInUsoId-> ' + taskInUsoId)

function attivaElementoInTaskList(){
	for(i=0; i<tasks.length; i++){
		console.log('tasks[i].id -> ' + tasks[i].id)
		if(tasks[i].id == taskInUsoId){
			
			const taskUtilizzato = document.getElementById('task-in-uso-' + taskInUsoId)
			taskUtilizzato.innerHTML = `<img src="/img/sources/icons/clock.gif" alt="task-in-uso" class="size-8 inline float-right rounded-full">`
		}
	}
	
}


function disattivaElementoInTaskList(){
	for(i=0; i<tasks.length; i++){
		console.log('tasks[i].id -> ' + tasks[i].id)
		if(tasks[i].id == taskInUsoId){
			
			const taskUtilizzato = document.getElementById('task-in-uso-' + taskInUsoId)
			taskUtilizzato.innerHTML = ``
		}
	}
	
}