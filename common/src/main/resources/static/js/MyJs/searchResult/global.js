let searchResult_search = window.location.search;

let searchResult_uri = decodeURI(searchResult_search);
let searchResult_videoName=searchResult_uri.substring(searchResult_uri.indexOf("=")+1);


let searchResult_$classMovie = $("#movie").find(".movie");
let searchResult_filmSize = searchResult_$classMovie.length;

