console.log("leggo il file layout.js");
// elementi DOM per animazione lista task al click su contatore
let taskCardList = document.getElementById('task-card');
let buttontest = document.getElementById('buttontest');



// recupero elementi DOM per lista progetti ordinati per cliente o datainizio
const filtraPerClientiButton = document.getElementById('filtra-per-clienti-button');
const filtraPerDataButton = document.getElementById('filtra-per-data-button');
const listCliente = document.getElementById('list-cliente');
const listDataInizio = document.getElementById('list-data-inizio');
const ricercaVuotaData = document.getElementById('ricerca-vuota-data');
const ricercaVuotaClienti = document.getElementById('ricerca-vuota-clienti');
const paginazioneData = document.getElementById('paginazione-data');
const paginazioneClienti = document.getElementById('paginazione-clienti');
let paginazioneAttivaCliente = new Boolean();
	paginazioneAttivaCliente = false; //assegna false per visualizzare di default quella per data
let paginazioneAttivaData = new Boolean();
	paginazioneAttivaData = true

// recupero elementi dal DOM per navBar	
const navBar = document.querySelector('.nav-bar');
const navOptionsMobile = document.querySelector('#options-button-mobile');
const navOptionsDesktop = document.querySelector('.nav-option-desktop');
  

//recupero elemento dal DOM per animazione partenza contatore
const dettaglioContatoreTop = document.getElementById('dettaglio-contatore-top');
const dettaglioContatoreBottom = document.getElementById('dettaglio-contatore-bottom');

//recupero elementi dal DOM per errore numero massimo caratteri textArea
const contatoreCaratteriCinquecento = document.getElementById('contatore-caratteri-cinquecento');
const textAreaDescrizioneCinquecento = document.getElementById('textarea-cinquecento');
const tectAreaCinquecentoError = document.getElementById('textarea-cinquecento-error');
const contatoreCaratteriCinquecentoContainer = document.getElementById('contatore-caratteri-cinquecento-container')

// funzioni per navBar 		
function onToggleMenu(e){
	navBar.classList.toggle('top-[-100%]')
	navBar.classList.toggle('top-14')
}

function onToggleOptionsMobile(e){
	navOptionsMobile.classList.toggle('hidden')
}

function onToggleOptionsDesktop(e){
	navOptionsDesktop.classList.toggle('top-[-100%]')
	navOptionsDesktop.classList.toggle('top-14')
}

//animazione avvio contatore
if(contatoreAttivato == true){
	dettaglioContatoreTop.classList.add('animate_animated','animate__bounceIn');
	dettaglioContatoreBottom.classList.add('animate_animated','animate__bounceIn');
}


function attivaPaginazione1() {
	paginazioneAttivaCliente = true;
	console.log('paginazioneAttivaCliente ' + paginazioneAttivaCliente);
	paginazioneAttivaData = false;
	console.log('paginazioneAttivaData ' + paginazioneAttivaData);
	filtraPerClientiButton.classList.add('opacity-50', 'pointer-events-none');
	filtraPerDataButton.classList.remove('opacity-50', 'pointer-events-none');
	

}

// mostra "non ci sono progetti""
			if ((ricercaVuotaClienti != null) && (ricercaVuotaData != null)) {
				ricercaVuotaClienti.classList.remove('hidden');
				ricercaVuotaData.classList.add('hidden');
			}
// funzione bottone filtro pagina progetti per cliente
if (filtraPerClientiButton != null) {
	filtraPerClientiButton.addEventListener('click', function() {
	if ((listCliente != null) && (paginazioneClienti != null)) {
		listCliente.classList.remove('hidden');
		paginazioneClienti.classList.remove('hidden');
		listDataInizio.classList.add('hidden');
		paginazioneData.classList.add('hidden');
		}		
				})
			}



function attivaPaginazione2() {
	paginazioneAttivaCliente = false;
	console.log('paginazioneAttivaCliente ' + paginazioneAttivaCliente);
	paginazioneAttivaData = true;
	console.log('paginazioneAttivaData ' + paginazioneAttivaData);
	filtraPerDataButton.classList.add('opacity-50', 'pointer-events-none');
	filtraPerClientiButton.classList.remove('opacity-50', 'pointer-events-none');

}

// mostra "non ci sono progetti"
if ((ricercaVuotaData != null) && (ricercaVuotaClienti != null)) {
	ricercaVuotaData.classList.remove('hidden');
	ricercaVuotaClienti.classList.add('hidden');
	}
// funzione bottone filtro pagina progetti per data inizio
if (filtraPerDataButton != null) {
filtraPerDataButton.addEventListener('click', function() {
if ((listDataInizio != null) && (paginazioneData != null)) {
	listDataInizio.classList.remove('hidden');
	paginazioneData.classList.remove('hidden');
	listCliente.classList.add('hidden');
	paginazioneClienti.classList.add('hidden');
			}
		
		})
	}


// funzione per il countdown di caratteri nelle textarea di max 500 caratteri
if((contatoreCaratteriCinquecento != null) && (textAreaDescrizioneCinquecento != null)
		 && (tectAreaCinquecentoError!= null)){

		let textAreaCharsLength = textAreaDescrizioneCinquecento.value.length;
		const maxChar = 500;
		if(textAreaCharsLength != maxChar){
			contatoreCaratteriCinquecento.innerHTML = maxChar - textAreaCharsLength;
		}else {
			contatoreCaratteriCinquecento.innerHTML = maxChar;			
		}
			
			textAreaDescrizioneCinquecento.addEventListener('keyup', function() {
		  	
			textAreaCharsLength = textAreaDescrizioneCinquecento.value.length;
		  	if (textAreaCharsLength == maxChar){
				contatoreCaratteriCinquecentoContainer.classList.add('hidden');
				tectAreaCinquecentoError.innerHTML = " Hai raggiunto il limite massimo di " + maxChar + " caratteri!";
		  	} else {
		   		let charRemained = maxChar - textAreaCharsLength;
				contatoreCaratteriCinquecentoContainer.classList.remove('hidden');
		    	contatoreCaratteriCinquecento.innerHTML = charRemained;
				tectAreaCinquecentoError.innerHTML = "";
		  		}
			});
	
}

//if(openModalButton != null){
//	openModalButton.addEventListener('click', function(){
//		openModal()
//	})	
//}

//if(closeModalButton != null){
//	closeModalButton.addEventListener('click', function(){
//		closeModal()
//	})	
//}



