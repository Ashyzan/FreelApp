
// elementi DOM per animazione lista task al click su contatore
let taskCardList = document.getElementById('task-card');
let buttontest = document.getElementById('buttontest');





// recupero elementi dal DOM per navBar	
const navBar = document.getElementById('navbar');
const navOptionsMobile = document.querySelector('#options-button-mobile');
const navOptionsDesktop = document.querySelector('.nav-option-desktop');


//recupero elemento dal DOM per animazione partenza contatore
const dettaglioContatoreTop = document.getElementById('dettaglio-contatore-top');
const dettaglioContatoreBottom = document.getElementById('dettaglio-contatore-bottom');
const altezzaDisplay = window.screen.height;

//recupero elementi dal DOM per errore numero massimo caratteri textArea
const contatoreCaratteriCinquecento = document.getElementById('contatore-caratteri-cinquecento');
const textAreaDescrizioneCinquecento = document.getElementById('textarea-cinquecento');
const tectAreaCinquecentoError = document.getElementById('textarea-cinquecento-error');
const contatoreCaratteriCinquecentoContainer = document.getElementById('contatore-caratteri-cinquecento-container')

//recupero dal DOM elemento main-contianer per dinamicizzare h della main section di ogni template
const mainContainerTemplate = document.getElementById('main-container')

//vengono inizializzate variabili  fuori dalla funzione function regolazioneAltezzaColonneLayout perche poi viene aggiornata man mano che viene utilizzata per
//l'adattamento della dimenzione delle colonne del dettaglio progetto
let altezzaNavbar = navBar.offsetHeight;
let altezzaWindow = window.innerHeight;
let altezzaMainContainerTemplate = altezzaWindow - altezzaNavbar
let altezzaPrecedenteMainContainerTemplate = altezzaMainContainerTemplate

window.addEventListener('resize', regolazioneMainContainer);


//*******  funzione che in tutti i template misura le dimensioni della navbar e calcola di conseguenza le dimensioni del main-container ********************
function regolazioneMainContainer() {
	altezzaNavbar = navBar.offsetHeight;
	console.log("°° altezza navbar: " + altezzaNavbar)
	altezzaWindow = window.innerHeight;
	console.log("°° altezzaWindow: " + altezzaWindow)
	altezzaMainContainerTemplate = altezzaWindow - altezzaNavbar
	console.log("°° altezzaMainContainerTemplate: " + altezzaMainContainerTemplate)
	mainContainerTemplate.classList.remove('h-[' + altezzaPrecedenteMainContainerTemplate + 'px]')
	mainContainerTemplate.classList.add('h-[' + altezzaMainContainerTemplate + 'px]')
	altezzaPrecedenteMainContainerTemplate = altezzaMainContainerTemplate;
}


// ********** funzioni per navBar *********************************************************************  		
function onToggleMenu(e) {
	navBar.classList.toggle('top-[-100%]')
	navBar.classList.toggle('top-14')
}

function onToggleOptionsMobile(e) {
	navOptionsMobile.classList.toggle('hidden')
}

function onToggleOptionsDesktop(e) {
	navOptionsDesktop.classList.toggle('top-[-100%]')
	navOptionsDesktop.classList.toggle('top-14')
}







// ********* animazione avvio contatore ******************************************************************************
function controlloContatoreAttivato() {

	if (contatoreAttivato == true) {
		if (window.innerWidth < 1024) {
			document.getElementById('bottom').scrollIntoView({ behavior: "smooth" });
			//document.getElementById('main-section').scrollTop = document.getElementById('main-section').scrollHeight + 100;
			setTimeout(() => {
				dettaglioContatoreBottom.classList.add('animate_animated', 'animate__bounceIn');
			}, 500);
		}
		dettaglioContatoreTop.classList.add('animate_animated', 'animate__bounceIn');
	}
}



//******************* animazione se contatore cliccato su play/pause prima del refresh in mobile  ************/
function contatoreCliccatoPlayPauseInMobile() {

	if (contatoreCliccatoPreRefresh == true && window.innerWidth < 1024) {
		document.getElementById('bottom').scrollIntoView({ behavior: "smooth" })
	}
}







//*******  funzione per il countdown di caratteri nelle textarea di max 500 caratteri ********************
if ((contatoreCaratteriCinquecento != null) && (textAreaDescrizioneCinquecento != null)
	&& (tectAreaCinquecentoError != null)) {

	let textAreaCharsLength = textAreaDescrizioneCinquecento.value.length;
	const maxChar = 500;
	if (textAreaCharsLength != maxChar) {
		contatoreCaratteriCinquecento.innerHTML = maxChar - textAreaCharsLength;
	} else {
		contatoreCaratteriCinquecento.innerHTML = maxChar;
	}

	textAreaDescrizioneCinquecento.addEventListener('keyup', function() {

		textAreaCharsLength = textAreaDescrizioneCinquecento.value.length;
		if (textAreaCharsLength == maxChar) {
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



