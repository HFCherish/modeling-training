function view() {
    var titleNodes = document.getElementsByClassName("title");
    // for(var i=0; i<titleNodes.length; i++) {
    // 	titleNodes.item(i).innerHTML =
    // }
    Array.from(titleNodes).forEach(function (titleNode) {
        titleNode.innerHTML = "<h1>my test title</h1>";
    });

    var textNodes = document.getElementsByClassName("text");
    Array.from(textNodes).forEach(function (textNode) {
        textNode.innerHTML = "<textarea readonly>my test textarea</textarea>";
    })
    // while(titleNode.firstChild) {
    // 	titleNode.removeChild(titleNode.firstChild);
    // }
    // titleNode.appendChild()
}

function render() {
    var titleNodes = document.getElementsByClassName("title");
    Array.from(titleNodes).forEach(function (titleNode) {
        titleNode.innerHTML = "<input type='text'>";
    });

    var textNodes = document.getElementsByClassName("text");
    Array.from(textNodes).forEach(function (textNode) {
        textNode.innerHTML = "<textarea></textarea>";
    });
}