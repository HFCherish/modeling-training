function render() {
    var showButtons = document.getElementsByClassName("showButton");
    for (var i = 0; i < showButtons.length; i++) {
        var showButton = showButtons[i];
        showButton.onclick = function (evt) {
            var parentNode = this.parentNode;
            var titleNode = parentNode.querySelector('li.title');
            var textNode = parentNode.querySelector('li.text');
            var previousTitle = titleNode.firstElementChild.value;
            previousTitle = previousTitle == undefined ? "title" : previousTitle;
            var previousText = textNode.firstElementChild.value;
            previousText = previousText == undefined ? "" : previousText;
            titleNode.innerHTML = "<h1>" + previousTitle + "</h1>";
            textNode.innerHTML = "<textarea readonly>" + previousText +"</textarea>";
        };
    }
}

function edit() {
    var editButtons = document.getElementsByClassName("editButton");
    for (var i = 0; i < editButtons.length; i++) {
        editButtons[i].onclick = function () {
            var parentNode = this.parentNode;
            var titleNode = parentNode.querySelector('li.title');
            var textNode = parentNode.querySelector('li.text');
            var previousTitle = titleNode.firstElementChild.textContent;
            var previousText = textNode.firstElementChild.textContent;
            titleNode.innerHTML = "<input type='text' value='" + previousTitle + "'/>";
            textNode.innerHTML = "<textarea>" + previousText +"</textarea>";
        }
    }
}

function addColumn() {
    var addColumnButtons = document.getElementsByClassName("addColumn");
    for( var i=0; i<addColumnButtons.length; i++ ) {
        addColumnButtons[i].onclick = function () {
            var parentNode = this.parentNode;
            var columns = parentNode.querySelector('ul.columns');


            var column = document.createElement('li');
            column.className = 'column';

            var addRowButton = document.createElement('button');
            addRowButton.className = 'addRow';
            addRowButton.innerText = 'add';
            column.appendChild(addRowButton);

            var rows = document.createElement('ul');
            rows.className = 'rows';
            column.appendChild(rows);

            columns.appendChild(column);

            addRow();
            render();
            edit();
        }
    }
}

function addRow() {
    var addRowButtons = document.getElementsByClassName("addRow");
    for( var i=0; i<addRowButtons.length; i++ ) {
        addRowButtons[i].onclick = function () {
            var parentNode = this.parentNode;
            var rows = parentNode.querySelector('ul.rows');


            var row = document.createElement('li');
            row.className = 'row';

            var component = createNode('ul', 'component');
            component.appendChild(createNode('button', 'showButton', 'show'));
            component.appendChild(createNode('button', 'editButton', 'edit'));
            component.appendChild(createNode('li', 'title')).appendChild(createNode('h1', null, 'title'));
            component.appendChild(createNode('li', 'text')).appendChild(createNode('textarea', null, 'the content'));
            row.appendChild(component);

            rows.appendChild(row);

            render();
            edit();
        }
    }
}

function createNode(type, className, innerText) {
    var node = document.createElement(type);
    if(typeof className === 'string') node.className = className;
    if(typeof innerText === 'string') node.innerText = innerText;
    return node;
}

addColumn();
addRow();
render();
edit();