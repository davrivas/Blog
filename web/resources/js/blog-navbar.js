$(function () {
    var $navbar = $('#mainNav');
    if ($navbar.length) {
        var $blackNav = $('#blackNav');
        var navHeight = $navbar.height() + "px";
        $blackNav.height(navHeight);
    }
});