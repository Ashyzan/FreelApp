
const timerElement = document.querySelector("#timer");

let interval;
let seconds = 0;
let minutes = 0;
let hours = 0;

if (localStorage.getItem("seconds") != 0) 
     seconds = localStorage.getItem("seconds");

if (localStorage.getItem("minutes") != 0) 
     minutes = localStorage.getItem("minutes");
     
if (localStorage.getItem("hours") != 0) 
     hours = localStorage.getItem("hours");
     
function start() {
 interval = setInterval(timer, 1000); 
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

   localStorage.setItem('hours', hours);
   localStorage.setItem('minutes', minutes);
   localStorage.setItem('seconds', seconds);
  
   timerElement.innerHTML = digitZero(localStorage.getItem("hours")) + ":" + digitZero(localStorage.getItem("minutes")) + ":" + digitZero(localStorage.getItem("seconds"));

 }