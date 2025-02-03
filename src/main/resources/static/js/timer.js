let hours = finalTime / 3600;
let minutes = (finalTime % 3600) / 60;
let seconds = finalTime % 60;

const timerElement = document.querySelector("#timer");

function tempochescorre() {
	seconds++;
	stampacontatore();

	if (seconds >= 60) {
		seconds = 0;
		minutes++;
	}

	if (minutes >= 60) {
		minutes = 0;
		hours++;
	}


}

function stampacontatore() {

	// formattato con 2 cifre, per difetto
	document.getElementById('timer').innerHTML = ('0' + Math.floor(hours)).slice(-2) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);

}

function timerstart() {


	if (contatoreTrue && contatoreIsRun) {
		// eseguo prim la funzione una volta per togliere il lag di 1 secondo, poi entro nel ciclo
		tempochescorre();
		setInterval(tempochescorre, 1000);

		console.log("isRun !!");
		console.log("finaltime is " + finalTime);
		console.log("ore " + hours + " minuti " + minutes + " secondi " + seconds)
		console.log("timenow is " + timenow)

	}

	else if (contatoreIsRun !== true) {

		stampacontatore();
		console.log("contatore non attivo , finaltime = " + finalTime);
	}

	else {

		console.log("finaltime is not defined , finaltime = " + finalTime);
	}

}