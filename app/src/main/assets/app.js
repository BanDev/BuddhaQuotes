fade();
fade_b();

//Fade something in
function fade() {
    var op = 0.05;  // initial opacity
    document.getElementById("demo").style.display = 'block';
    var timer = setInterval(function () {
        if (op >= 1){
            clearInterval(timer);
        }
        document.getElementById("demo").style.opacity = op;
        document.getElementById("demo").style.filter = 'alpha(opacity=' + op * 100 + ")";
        op += op * 0.05;
    }, 10);
}

function fade_b() {
    var op = 0.05;  // initial opacity
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

    function animate2(){
        fade();
        fade_b();
        document.getElementById("b").onclick = function() {
  rnd();
};
        document.getElementById("spinner").classList.add("spin");
        rnd();
        setTimeout(() => {  document.getElementById("spinner").classList.remove("spin"); document.getElementById("b").onclick = function(){
        animate2();}}, 500);

    }
    rnd();
    function rnd(){
        text = getQuote();
        document.getElementById("demo").innerHTML = text;
        document.getElementById("demo").style.opacity = "0";
    }

