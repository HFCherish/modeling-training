function render() {
    var parentNode = this.parentNode;
    var titleNode = parentNode.querySelector('li.title');
    var textNode = parentNode.querySelector('li.text');
    var previousTitle = titleNode.firstElementChild.value;
    previousTitle = previousTitle == undefined ? "title" : previousTitle;
    var previousText = textNode.firstElementChild.value;
    previousText = previousText == undefined ? "" : previousText;
    titleNode.innerHTML = "<h1>" + previousTitle + "</h1>";
    textNode.innerHTML = "<textarea readonly>" + previousText + "</textarea>";
}

function edit() {
    var parentNode = this.parentNode;
    var titleNode = parentNode.querySelector('li.title');
    var textNode = parentNode.querySelector('li.text');
    var previousTitle = titleNode.firstElementChild.textContent;
    var previousText = textNode.firstElementChild.textContent;
    titleNode.innerHTML = "<input type='text' value='" + previousTitle + "'/>";
    textNode.innerHTML = "<textarea>" + previousText + "</textarea>";
}

function addColumn() {
    var parentNode = this.parentNode;
    var columns = parentNode.querySelector('ul.columns');

    columns.appendChild(createNode('li', 'column'))
        .appendChild(createNode('ul', 'rows'))
        .appendChild(createNode('li'))
        .appendChild(createNode('button', 'addRow', 'add'));

    flush();
}

function addRow() {
    var rows = this.parentNode.parentNode;

    var row = createNode('li', 'row');

    var component = createNode('ul', 'component');
    component.appendChild(createNode('button', 'showButton', 'show'));
    component.appendChild(createNode('button', 'editButton', 'edit'));
    component.appendChild(createNode('li', 'title'))
        .appendChild(createNode('input')
            .setAttributes({
                'type': 'text',
                'value': 'title'
            }));
    component.appendChild(createNode('li', 'text')).appendChild(createNode('textarea', null, 'the content'));
    row.appendChild(component);

    rows.appendChild(row);

    flush();
}

function addEventListener(buttonClass, eventListener) {
    var buttons = document.getElementsByClassName(buttonClass);
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].onclick = eventListener;
    }
}

function createNode(type, className, innerText) {
    var node = document.createElement(type);
    if (typeof className === 'string') node.className = className;
    if (typeof innerText === 'string') node.innerText = innerText;
    return node;
}

Element.prototype.setAttributes = function (nameValues) {
    if (this instanceof Element) {
        for (var attrName in nameValues) {
            this.setAttribute(attrName, nameValues[attrName]);
        }
        return this;
    }
}

function flush() {
    addEventListener('addColumn', addColumn);
    addEventListener('addRow', addRow);
    addEventListener('showButton', render);
    addEventListener('editButton', edit);
}

flush();
