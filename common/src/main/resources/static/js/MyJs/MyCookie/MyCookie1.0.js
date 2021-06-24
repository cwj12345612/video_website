/*
自定义Cookie的crud操作
 */
/*
存储一个Cookie
 */
function setCookie(name, value) {

    document.cookie = name + "=" + value + ";";
}