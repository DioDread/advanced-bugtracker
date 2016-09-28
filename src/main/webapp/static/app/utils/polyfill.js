(function () {
    if (!window) {
        return;
    }
    if (!NodeList.prototype.forEach) {
        NodeList.prototype.forEach = Array.prototype.forEach;
    }
}());
