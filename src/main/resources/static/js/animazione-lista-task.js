console.log("leggo il file animazione-lista-task");
//dimensione box 172px

const containers = document.getElementsByClassName('container');
const forms = document.getElementsByClassName('form-cambio-contatore');
const contatoreBottom = document.getElementById('dettaglio-contatore-bottom');


function moveUp(id){

		const idFormSelected = `form-${id}`;
		const formSelected = document.getElementById(idFormSelected)
	    const idSelected = id;
	    const containerSelected = document.getElementById(idSelected);
	    const distance = (id)*171
	
	if(window.innerWidth >= 1024){
			    containerSelected.classList.add('duration-300', 'scale-[1.1]')
			    if(id != 0){
			        setTimeout(function(){
			            containerSelected.classList.remove('duration-250')
			            containerSelected.classList.add( `translate-y-[-${distance}px]`, 'duration-[1s]', 'delay-200')
			
			            for( i = 0 ; i < containers.length ; i++){
			                let currentId = containers[i].id;
			                let currentContainer = containers[i];
			                if((id-currentId)>0){
			                 
			                    currentContainer.classList.add('translate-y-[171px]', 'duration-[1s]', 'delay-200')
			                }     
			            }
						
			        }, 300)
			        setTimeout(function(){
			            containerSelected.classList.remove('scale-[1.1]')
						
			        }, 1500)
			
					setTimeout(function(){
						formSelected.submit()
					 }, 2500)
			
			    } else {
			        setTimeout(function(){
						 containerSelected.classList.remove('scale-[1.1]')
			        }, 200)
					
					setTimeout(function(){
						formSelected.submit()
					}, 500)
						
			    }
		
	}else {
		formSelected.submit()

	}
	
}

//funzione che porta lentamente al top seguendo animazione
function moveSlowlyToTop(url){
	if(window.innerWidth >= 1024){
		 setTimeout( function() { window.location = url }, (1000) );				
	}
	
}
