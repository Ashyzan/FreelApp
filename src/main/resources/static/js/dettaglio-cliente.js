
//recupero elementi dal DOM
const elementiInformativiCliente = document.getElementsByClassName('elemento-informativo-cliente');
const arrayElementiInformativiCliente = [...elementiInformativiCliente];



//listener che al variare delle dimensioni della window richiama la funzione per troncare se nesserio o ripristinare il testo
window.addEventListener('resize', truncateElements );


//funzione che al caricamento della pagina dettaglio cliente vede se ci sono testi di elementi da troncare e ne applica le classi necessarie
function truncateElements(){

	arrayElementiInformativiCliente.forEach(item =>{
			
		if(item.offsetWidth < item.scrollWidth){
			item.classList.add('truncate','hover:overflow-visible', 'hover:bg-white', 'hover:z-50', 'hover:rounded-lg', 'hover:shadow-md', 'hover:px-2', 'hover:w-fit', 'hover:h-fit')
		} else if(item.offsetWidth >= item.scrollWidth){
			item.classList.remove('truncate','hover:overflow-visible', 'hover:bg-white', 'hover:z-50', 'hover:rounded-lg', 'hover:shadow-md', 'hover:px-2', 'hover:w-fit', 'hover:h-fit')
		}
	})
	
}


