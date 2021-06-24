
let MovieSection_search = window.location.search;
let MovieSection_uri = decodeURI(MovieSection_search);
let MovieSection_$filmTypeCookie = MovieSection_uri.substring(MovieSection_search.lastIndexOf("=") + 1);
let MovieSection_typeEnglish = leftTypeMap.get(MovieSection_$filmTypeCookie);
$("#MovieTitle").html(MovieSection_$filmTypeCookie);

$("#page").html(MovieSection_$filmTypeCookie+"չʾ");
let MovieSection_$classMovie = $("#movie").find(".movie");
let MovieSection_FilmLength = MovieSection_$classMovie.length;
