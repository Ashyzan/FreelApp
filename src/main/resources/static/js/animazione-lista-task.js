//dimensione box 172px

const containers = document.getElementsByClassName('container');
const forms = document.getElementsByClassName('form-cambio-contatore');




function moveUp(id){
 
    const idSelected = id;
    const containerSelected = document.getElementById(idSelected);
    const distance = (id)*171
	const idFormSelected = `form-${id}`;
	const formSelected = document.getElementById(idFormSelected)
	
	
	
    containerSelected.classList.add('duration-300', 'scale-[1.1]')
    if(id != 0){
        setTimeout(function(){
            containerSelected.classList.remove('duration-250')
            containerSelected.classList.add( `translate-y-[-${distance}px]`, 'duration-[3s]', 'delay-200')

            for( i = 0 ; i < containers.length ; i++){
                let currentId = containers[i].id;
                let currentContainer = containers[i];
                if((id-currentId)>0){
                 
                    currentContainer.classList.add('translate-y-[171px]', 'duration-[3s]', 'delay-200')
                }     
            }
			
        }, 500)
        setTimeout(function(){
            containerSelected.classList.remove('scale-[1.1]')
			
        }, 2500)

		setTimeout(function(){
			formSelected.submit()
		 }, 5200)

    } else {
        setTimeout(function(){
			 containerSelected.classList.remove('scale-[1.1]')
        }, 500)
		
		setTimeout(function(){
			formSelected.submit()
		}, 600)
			
    }
}

//funzione che porta lentamente al top seguendo animazione
function moveSlowlyToTop(url){
	 setTimeout( function() { window.location = url }, (1300) );
	
}
