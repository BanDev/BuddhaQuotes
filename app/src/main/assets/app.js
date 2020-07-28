//Run fade command.
fade();
//Run fade command for the by section.
fade_by();

//Fade something in.
function fade() {
    var op = 0.05;  // initial opacity.
    document.getElementById("text").style.display = 'block';
    var timer = setInterval(function () {
        if (op >= 1){
            clearInterval(timer);
        }
        document.getElementById("text").style.opacity = op;
        document.getElementById("text").style.filter = 'alpha(opacity=' + op * 100 + ")";
        op += op * 0.05;
    }, 10);
}

//Fade in the by script.
function fade_by() {
    var op = 0.05;  // initial opacity.
    document.getElementById("by").style.display = 'block';
    var timer = setInterval(function () {
        if (op >= 1){
            clearInterval(timer);
        }
        document.getElementById("by").style.opacity = op;
        document.getElementById("by").style.filter = 'alpha(opacity=' + op * 100 + ")";
        op += op * 0.05;
    }, 10);
}

//When a user clicks the action button.
function animate_spinner(){
    fade();
    fade_by();
    //Disable animation, just send the user straight to the rnd(); function.
    document.getElementById("button").onclick = function() {NewQuote();};
    document.getElementById("spinner").classList.add("spin");
    NewQuote();
    //Wait 500ms and then remove the animation, then re-enable the spinner animation.
    setTimeout(() => {  document.getElementById("spinner").classList.remove("spin"); document.getElementById("button").onclick = function(){animate_spinner();}}, 500);
}

//Get a new quote for the first time app is run.
NewQuote();

//Gets a quote from the quotes.js file and then updates the text file.
function NewQuote(){
    text = getQuote();
    document.getElementById("button").style.background = "#ffbd59";
    document.getElementById("text").innerHTML = text;
    document.getElementById("text").style.opacity = "0";
}

