console.log("leggo il file modale.js");

// per attivare la modale è necessario inserire nell'html le seguenti funzioni al click:
//onclick="apriModale()" per aprire la modale
//onclick="chiudiModale()" per chiudere la modale
//dare alla modale id "modale" e classe "scale-0"

//recupero dal DOM elementi della modale
const modale = document.getElementById('modale');
const modaleCloseButton = document.getElementById('modale-close-button');
const contenutoModale = document.getElementById('contenuto-modale');

// *******************************  FUNZIONI GENERICHE MODALE ******************************

//------- funzione apertura modale -------
function apriModale(){
	modale.classList.remove('scale-0');
}


//------- funzione chiusura modale -------
function chiudiModale(){
	modale.classList.add('scale-0');
}



// *******************************  FUNZIONE MODALE STOP TASK ******************************

//funzione creazione modale STOP
function creaModaleStop(){
	contenutoModale.innerHTML = `<form action="/Contatore/stop/${taskInUsoId}" 
				 method="post">
			<div class="flex flex-col justify-center p-2 text-center ">
				<div class="p-3 text-[#0057A5]">
					<p>Stai per terminare definitivamente il task <strong>${taskInUsoName}</strong> relativo al progetto
						<strong>${taskInUsoProgetto}</strong>.
						Vuoi procedere?
					</p>
				</div>
				<div class="p-6">
					<button type="submit"
						class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">
						Conferma</button>
				</div>
			</div>
		</form>`
	
		apriModale()
}


// *******************************  FUNZIONE MODALE ORE LAVORATE ******************************

//funzione creazione e apertura modale ORE LAVORATE
function creaApriModaleOreLavorate(idTaskselected, taskSelectedName){
	
	//creazione data odierna formattata
	const today = new Date();
	const yyyy = today.getFullYear();
	let mm = today.getMonth() + 1; // i mesi iniziano a  0!
	let dd = today.getDate();

	if (dd < 10) dd = '0' + dd;
	if (mm < 10) mm = '0' + mm;

	const formattedToday =   yyyy  + '-' + mm + '-' + dd ;
	contenutoModale.innerHTML = `	<div class="w-full h-auto bg-white rounded-b-lg p-3">
						<div class="mb-3 text-[#0057A5]">
							<form action="/orelavorate/${idTaskselected}"	method="post">

							<h3 class="text-center font-bold text-[#0057A5] font-bold mb-2">Inserimento ore lavorate al task:</h3>
							<h2 class="text-center text-2xl font-bold text-[#ffe541] mb-2">${taskSelectedName.toUpperCase()}</h2>
							<div class="py-1 px-3">
								<div class="grid grid-cols-2 flex items-center mt-2 mb-1">
									<p class="col mb-2 ">Inserisci la data</p>
									<input type="date" name="date" value="${formattedToday}" datetimepicker-options="${formattedToday}"
										class=" col md:text-xl border border-[#0057A5] h-8  text-[#0057A5] rounded-lg block w-full text-center bg-gray-100"
										id="dataOreLavorate">
								</div>
								<div class="grid grid-cols-2 flex items-center mb-2">
									<p class="col ">Inserisci ore</p>
									<input type="time" name="time" value="01:00"
										class="col h-8 md:text-xl border border-[#0057A5] text-[#0057A5] rounded-lg block w-full text-center	bg-gray-100"
										id="tempoOreLavorate">
								</div>
								<div class="grid grid-cols-5 my-4">
									<div class="min-md:col "></div>
									<div class="min-md:col-span-4 col-span-5">
										<div class="col text-left">
											<input type="radio" name="aggiungiOre" id="aggiungiOre" value="1" checked="checked">
											<label for="aggiungiOre" class="pe-1" >Aggiungi al task attivo</label>
										</div>
										<div class="col text-left">
											<input type="radio" name="aggiungiOre" id="sovrascriviOre" value="0">
											<label for="sovrascriviOre" class="ps-1">Sovrascrivi e chiudi task</label><br>
										</div>
									</div>
								</div>
								<div class="text-center py-2">
									<button class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]"
										type="submit">
										Aggiungi
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>`;
				
		apriModale();
}



// *******************************  FUNZIONE MODALE RESET TASK ******************************

//funzione creazione e apertura modale RESET
function creaApriModaleReset(idTaskselected, taskName, progettoName){
	contenutoModale.innerHTML = `	<form action="/Contatore/reset/${idTaskselected}" method="post">
						<div class="flex flex-col justify-center p-2 text-center ">
							<div class="p-3 text-[#0057A5]">
								<p>
									Stai per resettare definitivamente il timer del task <strong>${taskName}</strong> relativo al progetto
									<strong>${progettoName}</strong>.
									Vuoi procedere?
								</p>
							</div>
							<div class="p-6">
								<button type="submit"
									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">
									Conferma</button>
							</div>
						</div>
					</form>`;
		apriModale();
}



// *******************************  FUNZIONI MODALE DELETE ******************************

//funzione creazione e apertura modale DELETE CLIENTE
function creaApriModaleDeleteCliente(idClienteSelected, clienteLabel){
	contenutoModale.innerHTML = `	<form action="/Clienti/delete/${idClienteSelected}" method="post">
							<div class="flex flex-col justify-center p-2 text-center ">
								<div class="p-3 text-[#0057A5]">
									<p>Stai per cancellare il
										cliente <strong>${clienteLabel}</strong>
										e tutto ciò che ad esso
										è collegato. Vuoi
										procedere?</p>
								</div>
								<div class="p-6">
									<button type="submit"
										class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
								</div>
							</div>
						</form>`;
				
			apriModale();	
				
}

//funzione creazione e aprtura modale DELETE PROGETTO
function creaApriModaleDeleteProgetto(idProgetto, progettoSelectedName, progettoSelectedClienteName){
	contenutoModale.innerHTML = `	<form action="/Progetti/delete/${idProgetto}" method="post">
							<div class="flex flex-col justify-center p-2 text-center ">
								<div class="p-3 text-[#0057A5]">
									<p>Stai per cancellare il
										Progetto <strong>${progettoSelectedName}</strong>
										 relativo al cliente
										<strong> ${progettoSelectedClienteName} </strong>
										con tutti i relativi
										Task. Vuoi procedere?
									</p>
								</div>
								<div class="p-6">
									<button type="submit"
										class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
								</div>
							</div>
						</form>`
					
					apriModale();
}

//funzione creazione e apertuta modale DELETE TASK	
function creaApriModaleDeleteTask(idTask, taskSelectedName, taskSelectedProgettoName){
	contenutoModale.innerHTML = `	<form action="/Task/delete/${idTask}" method="post">
							<div class="flex flex-col justify-center p-2 text-center ">
								<div class="p-3 text-[#0057A5]">
									<p>Stai per cancellare il task <strong>${taskSelectedName} </strong>
										relativo al progetto <strong>${taskSelectedProgettoName} </strong>.
										Vuoi procedere?</p>
								</div>
								<div class="p-6">
									<button type="submit"
										class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
								</div>
							</div>
						</form>`;
						
				apriModale();
}


// *******************************  FUNZIONI MODALE START IN EDIT E INSERT ******************************

//funzione creazione e apertura modale START CLIENTE in edit e insert
//function creaApriModaleStartInInsertEditMode(clienteSelectedLabel){
//	contenutoModale.innerHTML = `	<form id="form-start-pause" action="/start/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//								<p>Proseguendo le modifiche non salvate al Cliente 
//								<strong> ${clienteSelectedLabel}</strong> andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//				apriModale();
//}

//funzione creazione e apertura modale START PROGETTO in edit e insert
//function creaApriModaleStartInInsertEditProgettoMode(progettoSelectedLabel){
//	contenutoModale.innerHTML = `	<form id="form-start-pause" action="/start/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//								<p>Proseguendo le modifiche non salvate al Progetto 
//								<strong> ${progettoSelectedLabel}</strong> andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//				apriModale();
//}

//funzione creazione e apertura modale START TASK in edit
//function creaApriModaleStartInEditTaskMode(taskSelectedId,taskSelectedName, taskSelectedProgettoName){
//	contenutoModale.innerHTML = `	<form id="form-start-pause" action="/start/${taskInUsoId}" method="post">
//											<input type="hidden" name="endPoint" value=${endPoint}${taskSelectedId}>
//											<div class="flex flex-col justify-center p-2 text-center ">
//												<div class="p-3 text-[#0057A5]">
//													<p>Proseguendo le modifiche non salvate al task <strong>${taskSelectedName} </strong>
//														 relativo al progetto <strong>${taskSelectedProgettoName} </strong> andranno perse. Vuoi procedere?</p>
//												</div>
//												<div class="p-6">
//													<button type="submit"
//														class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//												</div>
//											</div>
//										</form>`;
//										
//				apriModale();
//}

//funzione creazione e apertura modale START TASK in insert  da side-bar(no-progetto)
//function creaApriModaleStartInInsertTaskMode_sideBar(){
//	contenutoModale.innerHTML = `<form id="form-start-pause" action="/start/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//								<p>Proseguendo le modifiche non salvate al Task andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//					
//			apriModale();
//}

//funzione creazione e apertura modale START TASK in insert  da progetto
//function creaApriModaleStartInInsertTaskMode_fromProgetto(){
//	contenutoModale.innerHTML = `<form id="form-start-pause" action="/start/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}${progettoId}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//								<p>Proseguendo le modifiche non salvate al Task 
//								relativo al progetto <strong>${taskProgettoName}</strong> andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px] ">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//				
//			apriModale();
//	
//}




// *******************************  FUNZIONI MODALE PAUSE IN EDIT E INSERT ******************************

//funzione creazione e apertura modale PAUSE CLIENTE in edit e insert
//function creaApriModalePauseInInsertEditMode(clienteSelectedLabel){
//	contenutoModale.innerHTML = `	<form id="form-start-pause" action="/Contatore/pause/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//							<p>Proseguendo le modifiche non salvate al Cliente 
//							<strong> ${clienteSelectedLabel}</strong> andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//				apriModale();
//}

//funzione creazione e apertura modale PAUSE PROGETTO in edit e insert
//function creaApriModalePauseInInsertEditProgettoMode(progettoSelectedLabel){
//	contenutoModale.innerHTML = `	<form id="form-start-pause" action="/Contatore/pause/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//							<p>Proseguendo le modifiche non salvate al Progetto
//							<strong> ${progettoSelectedLabel}</strong> andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//				apriModale();
//}

//funzione creazione e apertura modale PAUSE TASK in edit
//function creaApriModalePauseInEditTaskMode(taskSelectedId, taskSelectedName, taskSelectedProgettoName){
//	contenutoModale.innerHTML = `	<form id="form-start-pause" action="/Contatore/pause/${taskInUsoId}" method="post">
//											<input type="hidden" name="endPoint" value=${endPoint}${taskSelectedId}>
//											<div class="flex flex-col justify-center p-2 text-center ">
//												<div class="p-3 text-[#0057A5]">
//													<p>Proseguendo le modifiche non salvate al task <strong>${taskSelectedName} </strong>
//														 relativo al Progetto <strong>${taskSelectedProgettoName} </strong> andranno perse. Vuoi procedere?</p>
//												</div>
//												<div class="p-6">
//													<button type="submit"
//														class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//												</div>
//											</div>
//										</form>`;
//										
//				apriModale();
//}

//funzione creazione e apertura modale PAUSE TASK in insert  da side-bar(no-progetto)
//function creaApriModalePauseInInsertTaskMode_sideBar(){
//	contenutoModale.innerHTML = `<form id="form-start-pause" action="/Contatore/pause/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//								<p>Proseguendo le modifiche non salvate al Task andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//					
//			apriModale();
//}

//funzione creazione e apertura modale PAUSE TASK in insert  da progetto
//function creaApriModalePauseInInsertTaskMode_fromProgetto(){
//	contenutoModale.innerHTML = `<form id="form-start-pause" action="/Contatore/pause/${taskInUsoId}" method="post">
//						<input type="hidden" name="endPoint" value="${endPoint}${progettoId}">
//						<div class="flex flex-col justify-center p-2 text-center ">
//							<div class="p-3 text-[#0057A5]">
//								<p>Proseguendo le modifiche non salvate al nuovo Task 
//								relativo al progetto <strong>${taskProgettoName}</strong> andranno perse. Vuoi procedere?</p>
//							</div>
//							<div class="p-6">
//								<button type="submit"
//									class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
//							</div>
//						</div>
//					</form>`;
//				
//			apriModale();
//	
//}

//************************************* FUNZIONE MODALE CHIUSURA PROGETTO ************************ */
function creaModaleChiudiProgetto(progettoId, progettoName){
	contenutoModale.innerHTML = `	<form action="/Progetti/chiudi/${progettoId}"  method="post" >							
							<div class="flex flex-col justify-center p-2 text-center ">
								<div class="p-3 text-[#0057A5]">
									<p>Stai per chiudere il progetto <strong> ${progettoName}</strong>, tutti i task ad esso associati 
									saranno chiusi. Vuoi procedere?</p>
								</div>
								<div class="p-6">
									<button type="submit"
										class="col w-fit shadow-md shadow-blue-500/50 border-2 border-[#0057A5] hover:bg-[#FFE541] text-[#0057A5] font-bold px-3 py-[5px] rounded-[35px]">Conferma</button>
								</div>
							</div>
						</form>`;
					apriModale();
}
