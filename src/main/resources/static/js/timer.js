let hours = finalTime / 3600;
let minutes = (finalTime % 3600) / 60;
let seconds = finalTime % 60;

const timerElement = document.querySelector("#timer");
			
function tempochescorre() {

	let tempochescorre = finalTime + timenow;
	let hours = tempochescorre / 3600;
	let minutes = (tempochescorre % 3600) / 60;
	let seconds = tempochescorre % 60;
	
	seconds++;

	if (seconds == 60) {
		minutes++;
		seconds = 0;
	}

	if (minutes == 60) {
		minutes = 0;
		hours++;
	}
	stampacontatore();

}

function stampacontatore() {

	document.getElementById('timer').innerHTML = ('0' + Math.floor(hours)).slice(-2) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);

}

function timerstart() {


	if (contatoreTrue && contatoreIsRun) {
		setInterval(tempochescorre, 1000);

		console.log("isRun !!");
		console.log("finaltime is " + finalTime);
		console.log("timenow is " + timenow)

	}

	else if (contatoreTrue) {

		tempochescorre();
		stampacontatore();
		console.log(finalTime);
	}

	else {

		console.log("finaltime is not defined" + finalTime);
	}

}