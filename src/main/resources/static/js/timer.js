
const timerElement = document.querySelector("#timer");

let interval;
let seconds = 0;
let minutes = 0;
let hours = 0;

function start() {
	 interval = setInterval(timer, 1000);  // instead use setTimeout()
	 // see for reference https://stackoverflow.com/questions/29971898/how-to-create-an-accurate-timer-in-javascript
	 // https://stackoverflow.com/questions/6577246/how-to-access-a-java-object-in-javascript-from-jsp
 }
 
 const pause = () => {
      clearInterval(interval);
 }

 const stop = () => {
     clearInterval(interval);
  }
 
const digitZero = (digit) => {
    if (digit < 10)
        return `0${digit}`;
    else return digit;
}

function timer() {
    seconds++;

    if (seconds == 60) {
          minutes++;
          seconds = 0;  
    }

    if (minutes == 60) {
          minutes = 0;
          hours++;
    }

    timerElement.innerHTML = digitZero(hours) + ":" + digitZero(minutes) + ":" + digitZero(seconds);
  }

